package bht.expense.gateway.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 姚轶文
 * @date 2021/4/8 13:54
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    //private final static String AUTH_PATH = "/expense-user-server/v2";

    private final static Set<String> PERMIT_PATH = new HashSet<>();

    static {
        PERMIT_PATH.add("/expense-user-server/v2");
        PERMIT_PATH.add("/expense-user-server/login");
        PERMIT_PATH.add("/expense-admin/v2");
        PERMIT_PATH.add("/expense-admin/login");
        PERMIT_PATH.add("/expense-file-server/v2");
        PERMIT_PATH.add("/expense-business/v2");
        PERMIT_PATH.add("/expense-business/user/login");
        PERMIT_PATH.add("/expense-business/user/reg");
        PERMIT_PATH.add("/expense-business/user/check-account");
        PERMIT_PATH.add("/expense-enterprise-server/v2");
        PERMIT_PATH.add("/expense-detail-server/v2");
        PERMIT_PATH.add("/expense-bpm-server/v2");
        PERMIT_PATH.add("/expense-email-server/v2");
    }

    @Autowired
    JwtTokenUtil jwtTokenUtil;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String url = exchange.getRequest().getURI().getPath();
        System.out.println(url);
        //跳过不需要验证的路径
        for(String path : PERMIT_PATH)
        {
            if(url.startsWith(path))
            {
                return chain.filter(exchange);
            }
        }


        //获取token
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (StringUtils.isBlank(token)) {
            //没有token
            return returnAuthFail(exchange, "请登陆");
        } else if(jwtTokenUtil.isTokenExpired(token)){
            return returnAuthFail(exchange,"token超时");
        }
        else {
            //有token
            try {
                ServerHttpRequest oldRequest= exchange.getRequest();
                URI uri = oldRequest.getURI();
                ServerHttpRequest  newRequest = oldRequest.mutate().uri(uri).build();
                // 定义新的消息头
                HttpHeaders headers = new HttpHeaders();
                headers.putAll(exchange.getRequest().getHeaders());
                headers.remove("Authorization");
                headers.set("Authorization",token);
                newRequest = new ServerHttpRequestDecorator(newRequest) {
                    @Override
                    public HttpHeaders getHeaders() {
                        HttpHeaders httpHeaders = new HttpHeaders();
                        httpHeaders.putAll(headers);
                        return httpHeaders;
                    }
                };
                return chain.filter(exchange.mutate().request(newRequest).build());
            }catch (Exception e) {
                e.printStackTrace();
                return returnAuthFail(exchange,"token验签失败");
            }
        }
    }
    private Mono<Void> returnAuthFail(ServerWebExchange exchange,String message) {
        ServerHttpResponse serverHttpResponse = exchange.getResponse();
        serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
        serverHttpResponse.getHeaders().set("Content-Type", "text/html;charset=utf-8");
        String resultData = "{\"status\":\"-1\",\"msg\":"+message+"}";
        byte[] bytes = resultData.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = serverHttpResponse.bufferFactory().wrap(bytes);
        return serverHttpResponse.writeWith(Flux.just(buffer));
    }
    @Override
    public int getOrder() {
        return 0;
    }
}