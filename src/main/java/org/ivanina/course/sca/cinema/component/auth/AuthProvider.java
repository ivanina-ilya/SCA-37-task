package org.ivanina.course.sca.cinema.component.auth;

import org.ivanina.course.sca.cinema.domain.User;
import org.ivanina.course.sca.cinema.domain.UserRole;
import org.ivanina.course.sca.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String name = auth.getName();
        String password = auth.getCredentials().toString();

        // TODO: get user by email, generate hash and compare
        User user = userService.getUserByEmail(name);

        if(user == null || user.getId() == null)
            throw new BadCredentialsException(String.format("Doesn't exist user with email '%s'", name));

        String hash = password;
        if(user.getPasswordHash() == null || !user.getPasswordHash().equals(hash))
            throw new BadCredentialsException("The password is wrong");

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(UserRole::toString)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(name, password,authorities);

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
