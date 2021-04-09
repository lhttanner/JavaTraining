package com.tannerlee.gateway.inbound;

import com.tannerlee.gateway.filter.HttpRequestFilter;
import com.tannerlee.gateway.filter.ParamHttpRequestFilter;
import com.tannerlee.gateway.outbound.HttpOutboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {
    private final List<String> proxyServer;

    private HttpOutboundHandler handler;

    private HttpRequestFilter filter = new ParamHttpRequestFilter();

    public HttpInboundHandler(List<String> proxyServer) {
        this.proxyServer = proxyServer;
        this.handler = new HttpOutboundHandler(this.proxyServer);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            log.info("channelRead流量接口请求开始，时间为{}", System.currentTimeMillis());
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
//            String uri = fullRequest.uri();
//            log.info("接收到的请求url为{}", uri);
//            if (uri.contains("/echo")) {
//                handlerTest(fullRequest, ctx);
//            } else {
//                handlerTest(fullRequest, ctx);
//            }
            //
            handler.handle(fullRequest, ctx, filter);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

}
