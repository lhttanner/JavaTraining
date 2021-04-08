package com.tannerlee.gateway.router;

import java.util.List;
import java.util.Random;

/**
 * com.tannerlee.gateway.router
 *
 * @author liht
 * @date 2021/4/8
 */
public class RandomHttpEndpointRouter implements HttpEndpointRouter {
    @Override
    public String route(List<String> urls) {
        int size = urls.size();
        Random random = new Random(System.currentTimeMillis());
        return urls.get(random.nextInt(size));
    }
}
