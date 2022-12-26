package com.neighborpil.test.jwt.config.oauth2.filter;

import com.neighborpil.test.jwt.config.jwt.AuthToken;
import com.neighborpil.test.jwt.config.jwt.AuthTokenProvider;
import com.neighborpil.test.jwt.utils.CommonRequestContext;
import com.neighborpil.test.jwt.utils.HeaderUtil;
import com.nimbusds.openid.connect.sdk.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final String HEALTHCHECK_URI = "/api/v1/auth/healthcheck";

    private final AuthTokenProvider tokenProvider;
    private final CommonRequestContext commonRequestContext;
//    private final RedisAuthTokenRepository authTokenRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        String tokenString = HeaderUtil.getAccessToken(request);
        AuthToken authToken = tokenProvider.convertAuthToken(tokenString);

        if (!requestUri.equals(HEALTHCHECK_URI)) {
            Authentication authentication = tokenProvider.getAuthentication(authToken, request);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String uuid = authToken.getUuid(request);
            commonRequestContext.setUuid(uuid);
        }
        filterChain.doFilter(request, response);
    }
}
