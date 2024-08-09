package maelton.casal.vehicle_rental_api.api.v1.config.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import maelton.casal.vehicle_rental_api.api.v1.model.entity.User;
import maelton.casal.vehicle_rental_api.api.v1.exception.user.UserEmailNotFoundException;
import maelton.casal.vehicle_rental_api.api.v1.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenAuthenticationSecurityFilter extends OncePerRequestFilter {
    @Autowired
    JWTService jwtService;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException, UserEmailNotFoundException {

        String authenticationUserEmail = jwtService.validateToken(this.getToken(request));
        try {
            if (authenticationUserEmail != null) {
                User authenticationUser = userRepository.findUserByEmail(authenticationUserEmail).orElseThrow(
                        () -> new UserEmailNotFoundException(authenticationUserEmail)
                );
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        authenticationUserEmail,
                        null,
                        authenticationUser.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (UserEmailNotFoundException e) {
            filterChain.doFilter(request, response);
        } finally {
            filterChain.doFilter(request, response);
        }
    }

    private String getToken(HttpServletRequest request) {
        String authenticationHeader = request.getHeader("Authorization");
        if (authenticationHeader != null && authenticationHeader.startsWith("Bearer "))
            return authenticationHeader.replace("Bearer ", "");
        return null;
    }
}
