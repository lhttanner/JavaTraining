package com.tannerlee.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * com.tannerlee.gateway.filter
 *
 * @author liht
 * @date 2021/4/8
 */
public interface HttpRequestFilter {

    void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx);

}
