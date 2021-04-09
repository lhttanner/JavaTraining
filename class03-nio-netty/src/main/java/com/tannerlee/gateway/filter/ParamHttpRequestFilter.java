package com.tannerlee.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * com.tannerlee.gateway.filter
 *
 * @author liht
 * @date 2021/4/8
 */
@Slf4j
public class ParamHttpRequestFilter implements HttpRequestFilter {
    /**
     * 对没有参数的，默认追加一个参数
     *
     * @param fullRequest
     * @param ctx
     */
    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        String url = fullRequest.uri();
        log.info("modify before url : {}", url);
        url = url + "tannerlee/echo?s=没有输入参数";
        log.info("modify after url : {}", url);
        fullRequest.setUri(url);
    }
}
