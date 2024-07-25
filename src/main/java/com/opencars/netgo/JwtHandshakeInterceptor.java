package com.opencars.netgo;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.annotation.Nullable;
import java.util.Map;

public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private static final String AUTH_HEADER = "Authorization";

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {

        if (request.getHeaders().containsKey(AUTH_HEADER)) {
            String authToken = request.getHeaders().get(AUTH_HEADER).get(0).replace("Bearer ", "");
            if (isValidToken(authToken)) {
                return true;
            }
        }

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               @Nullable Exception exception) {

    }

    private boolean isValidToken(String token) {
        return true;
    }

}
