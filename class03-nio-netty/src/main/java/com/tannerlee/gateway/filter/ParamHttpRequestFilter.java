package com.tannerlee.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * com.tannerlee.gateway.filter
 *
 * @author liht
 * @date 2021/4/8
 */
public class ParamHttpRequestFilter implements HttpRequestFilter {
    /**
     * 对没有参数的，默认追加一个参数
     *
     * @param fullRequest
     * @param ctx
     */
    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
//        fullRequest.uri(). + "s=aaaa";
        //TODO
    }
}
