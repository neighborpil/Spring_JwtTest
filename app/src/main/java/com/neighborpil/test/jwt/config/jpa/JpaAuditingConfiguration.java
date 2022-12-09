package com.neighborpil.test.jwt.config.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfiguration {

    @Bean
    public AuditorAware<String> auditorProvider() {

        // TODO 지금은 1로 하고 나중에 로그인한 유저로 변경
        return () -> Optional.of("1");
//        return () -> Optional.of(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
    }
}
