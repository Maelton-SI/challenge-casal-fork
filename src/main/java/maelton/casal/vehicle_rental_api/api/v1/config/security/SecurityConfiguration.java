package maelton.casal.vehicle_rental_api.api.v1.config.security;

import maelton.casal.vehicle_rental_api.api.v1.exception.user.UserEmailNotFoundException;
import maelton.casal.vehicle_rental_api.api.v1.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
    @Autowired
    TokenAuthenticationSecurityFilter tokenAuthenticationSecurityFilter;

    @Autowired
    UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(HttpMethod.POST, "/v1/auth/**").permitAll()

                    .requestMatchers(HttpMethod.POST, "/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN")
                    .requestMatchers(HttpMethod.GET, "/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN", "USER")
                    .requestMatchers(HttpMethod.PUT, "/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN")

                    .anyRequest().permitAll()
            ).addFilterBefore(tokenAuthenticationSecurityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findUserByEmail(username).orElseThrow(() -> new UserEmailNotFoundException(username));
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/swagger-ui/**", "/v3/api-docs/**");
    }
}