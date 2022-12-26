package com.neighborpil.test.jwt.config;

import com.neighborpil.test.jwt.config.jwt.AuthTokenProvider;
import com.neighborpil.test.jwt.config.oauth2.entity.RoleType;
import com.neighborpil.test.jwt.config.oauth2.exception.RestAuthenticationEntryPoint;
import com.neighborpil.test.jwt.config.oauth2.filter.TokenAuthenticationFilter;
import com.neighborpil.test.jwt.config.oauth2.handler.TokenAccessDeniedHandler;
import com.neighborpil.test.jwt.config.oauth2.service.CustomUserDetailsService;
import com.neighborpil.test.jwt.config.properties.CorsProperties;
import com.neighborpil.test.jwt.utils.CommonRequestContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final CorsProperties corsProperties;
    private final AuthTokenProvider tokenProvider;
    private final CommonRequestContext commonRequestContext;
    private final CustomUserDetailsService userDetailsService;

    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider, commonRequestContext);
//        return new TokenAuthenticationFilter(tokenProvider, commonRequestContext, authTokenRepository);
    }

    /**
     * Cors configuration
     */
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(Arrays.asList(corsProperties.getAllowedHeaders().split(",")));
        corsConfiguration.setAllowedMethods(Arrays.asList(corsProperties.getAllowedMethods().split(",")));
        corsConfiguration.setAllowedOrigins(Arrays.asList(corsProperties.getAllowedOrigins().split(",")));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(corsProperties.getMaxAge());

        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
        httpSecurity.authenticationManager(authenticationManager);

        httpSecurity
                    .cors()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .csrf().disable()
                    .formLogin().disable()
                    .httpBasic().disable()
                    .exceptionHandling()
                    .authenticationEntryPoint(new RestAuthenticationEntryPoint()) // 400, 401
                    .accessDeniedHandler(tokenAccessDeniedHandler) // 403
                .and()
                    .authorizeRequests()
                    .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                    .antMatchers("/api/v1/auth/healthcheck").permitAll()
                    .antMatchers("/api/**").hasAnyAuthority(RoleType.USER.getCode())
                    .antMatchers("/api/**/admin/**").hasAnyAuthority(RoleType.ADMIN.getCode())
                    .anyRequest().authenticated();
            // TODO oauth2는 나중에 추가
        httpSecurity.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

}
