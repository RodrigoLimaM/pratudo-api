package br.com.pratudo.config.security;

import br.com.pratudo.user.client.UserClient;
import br.com.pratudo.user.model.User;
import br.com.pratudo.user.model.mapper.UserMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    TokenService tokenService;

    UserClient userClient;

    UserMapper userMapper;

    public TokenAuthenticationFilter(TokenService tokenService, UserClient userClient, UserMapper userMapper) {
        this.tokenService = tokenService;
        this.userClient = userClient;
        this.userMapper = userMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);

        if(tokenService.isValidToken(token)) {
            authenticate(token);
        }

        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7);
    }

    private void authenticate(String token) {
        String _id = tokenService.getUser_Id(token);
        User user = userClient.getUserBy_Id(_id)
                .map(userMapper::convertElasticsearchSingleUserToUser)
                .orElse(null);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
