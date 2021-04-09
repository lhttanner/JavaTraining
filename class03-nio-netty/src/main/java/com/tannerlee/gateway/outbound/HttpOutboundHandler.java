package com.tannerlee.gateway.outbound;

import com.tannerlee.gateway.filter.HeaderHttpResponseFilter;
import com.tannerlee.gateway.filter.HttpRequestFilter;
import com.tannerlee.gateway.filter.HttpResponseFilter;
import com.tannerlee.gateway.router.HttpEndpointRouter;
import com.tannerlee.gateway.router.RandomHttpEndpointRouter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.nio.reactor.IOReactorConfig;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * com.tannerlee.gateway.outbound
 *
 * @author liht
 * @date 2021/4/8
 */
@Slf4j
public class HttpOutboundHandler {

    //    private CloseableHttpAsyncClient httpclient;
    private ExecutorService proxyService;
    private List<String> backendUrls;
    //过滤器
    HttpResponseFilter responseFilter = new HeaderHttpResponseFilter();
    //路由
    HttpEndpointRouter router = new RandomHttpEndpointRouter();

    public HttpOutboundHandler(List<String> backends) {
        //处理url
        this.backendUrls = backends.stream().map(this::formatUrl).collect(Collectors.toList());
        //执行线程数
        int cores = Runtime.getRuntime().availableProcessors();
        //
        long keepAliveTime = 1000;
        //队列大小
        int queueSize = 2048;
        //失败策略
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();//.DiscardPolicy();
        proxyService = new ThreadPoolExecutor(cores, cores,
                keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize),
                new NamedThreadFactory("proxyService"), handler);

        //TODO reactor 响应式IO 什么特点？？？？
        IOReactorConfig ioConfig = IOReactorConfig.custom()
                .setConnectTimeout(1000)
                .setSoTimeout(1000)
                .setIoThreadCount(cores)
                .setRcvBufSize(32 * 1024)
                .build();
        //TODO HttpAsyncClients  与普通的 HttpClients 有啥区别？
//        httpclient = HttpAsyncClients.custom().setMaxConnTotal(40)
//                .setMaxConnPerRoute(8)
//                .setDefaultIOReactorConfig(ioConfig)
//                .setKeepAliveStrategy((response,context) -> 6000)
//                .build();
//        httpclient.start();


    }

    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, HttpRequestFilter filter) {
        String backendUrl = router.route(this.backendUrls);
        filter.filter(fullRequest, ctx);
        final String url = backendUrl + fullRequest.uri();
        proxyService.submit(() -> handleResponse(fullRequest, ctx, url));
    }

    private String formatUrl(String backend) {
        return backend.endsWith("/") ? backend.substring(0, backend.length() - 1) : backend;
    }

    private void handleResponse(FullHttpRequest fullRequest, ChannelHandlerContext ctx, String url) {
        FullHttpResponse response = null;
        try {
//            String url = "http://localhost:9999/tannerlee/echo?s=" + "aaa";
            log.info("current url :{}",url);
            String value = HttpClientUtil.get(url);

            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(value.getBytes("UTF-8")));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", response.content().readableBytes());

            //返回的结果过滤一把
            responseFilter.filter(response);
        } catch (Exception e) {
//            System.out.println("处理出错:" + e.getMessage());
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx, e);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    //response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                }
            }
            ctx.flush();
            //ctx.close();
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
