package com.tannerlee.gateway.filter;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * com.tannerlee.gateway.filter
 *
 * @author liht
 * @date 2021/4/8
 */
public interface HttpResponseFilter {
    /**
     * 返回的过滤器
     *
     * @param response
     */
    void filter(FullHttpResponse response);

}
