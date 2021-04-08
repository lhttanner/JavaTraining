package com.tannerlee.gateway.filter;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * com.tannerlee.gateway.filter
 *
 * @author liht
 * @date 2021/4/8
 */
public class HeaderHttpResponseFilter implements HttpResponseFilter {
    @Override
    public void filter(FullHttpResponse response) {
        response.headers().set("who", "tannerlee");
    }
}
