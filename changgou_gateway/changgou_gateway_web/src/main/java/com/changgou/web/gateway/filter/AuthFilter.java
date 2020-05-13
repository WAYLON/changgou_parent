package com.changgou.web.gateway.filter;

import com.changgou.web.gateway.service.AuthService;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.util.encoders.UrlBase64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter implements GlobalFilter, Ordered {

    public static final String Authorization = "Authorization";

    @Autowired
    private AuthService authService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //获取当前请求对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String path = request.getURI().getPath();
        if ("/api/oauth/login".equals(path) || !URLFilter.hasAuthorize(path)) {
            //放行
            return chain.filter(exchange);
        }
        //判断cookie上是否存在jti
        String jti = authService.getJtiFromCookie(request);
        if (StringUtils.isEmpty(jti)) {
            //拒绝访问,请求跳转
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        //判断redis中token是否存在
        String redisToken = authService.getJwtFromRedis(jti);
        if (StringUtils.isEmpty(redisToken)) {
            //拒绝访问，请求跳转
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        //校验通过 , 请求头增强，放行
        request.mutate().header(Authorization, "Bearer " + redisToken);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}