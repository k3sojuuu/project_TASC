//package com.example.gatewave.filter;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class AuthenFilter extends AbstractGatewayFilterFactory<AuthenFilter.Config> {
//    @Autowired
//    JwtProvider jwtProvider;
//
//    public AuthenFilter() {
//        super(Config.class);
//    }
//
//    @Override
//    public GatewayFilter apply(Config config) {
//        return ((exchange, chain) ->
//        {
//            //Check author from header
//            if (exchange.getRequest().getURI().getPath().contains("/private/")) {
//                //Missing
//                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
//                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//                    return exchange.getResponse().setComplete();
//                }
//
//                //Had author
//                String authHeaders = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
//                if (authHeaders != null && authHeaders.startsWith("Bearer "))
//                    authHeaders = authHeaders.substring(7);
//                //validate token
//                try {
//                    boolean check = jwtProvider.validateToken(authHeaders);
//                    if (check == false) {
//                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//                        return exchange.getResponse().setComplete();
//                    }
//                } catch (Exception e) {
//                    System.out.println("Invalid access ...!");
//                    throw new RuntimeException("Un Authorization");
//                }
//            }
//            return chain.filter(exchange);
//        }
//        );
//    }
//
//    public static class Config {
//
//    }
//
//}
