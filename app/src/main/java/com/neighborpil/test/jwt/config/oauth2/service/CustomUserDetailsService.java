package com.neighborpil.test.jwt.config.oauth2.service;

import com.neighborpil.test.jwt.config.oauth2.entity.UserPrincipal;
import com.neighborpil.test.jwt.config.oauth2.repository.UserRepository;
import com.neighborpil.test.jwt.user.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUuid(username)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find username"));

        return UserPrincipal.create(user);

    }
}
