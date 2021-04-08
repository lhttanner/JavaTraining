package com.tannerlee.gateway.router;

import java.util.List;

/**
 * com.tannerlee.gateway.router
 *
 * @author liht
 * @date 2021/4/8
 */
public interface HttpEndpointRouter {

    String route(List<String> urls);
}
