package maelton.casal.vehicle_rental_api.api.v1.config.security;

import maelton.casal.vehicle_rental_api.api.v1.config.security.jwt.TokenAuthenticationSecurityFilter;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
    @Autowired
    TokenAuthenticationSecurityFilter tokenAuthenticationSecurityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(HttpMethod.POST, "/v1/auth/**").permitAll()

                    .requestMatchers(HttpMethod.GET, "/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN", "USER")
                    .requestMatchers(HttpMethod.PUT, "/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN")

                    .requestMatchers(HttpMethod.POST, "/v1/users").hasAnyAuthority("SUPER_ADMIN", "ADMIN")
                    .requestMatchers(HttpMethod.POST, "/v1/cars").hasAnyAuthority("SUPER_ADMIN", "ADMIN")
                    .requestMatchers(HttpMethod.POST, "/v1/motorcycles").hasAnyAuthority("SUPER_ADMIN", "ADMIN")
                    .requestMatchers(HttpMethod.POST, "/v1/rentals").hasAnyAuthority("SUPER_ADMIN", "ADMIN")

                    //ADMINS CANT RENT DIRECTLY (USERS AND SUPERS CAN), THEY CAN CREATE AND UPDATED RENTALS THOUGH
                    .requestMatchers(HttpMethod.POST, "/v1/rentals/car").hasAnyAuthority("SUPER_ADMIN", "USER")
                    .requestMatchers(HttpMethod.POST, "/v1/rentals/motorcycle").hasAnyAuthority("SUPER_ADMIN", "USER")

                    .anyRequest().authenticated()
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
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/swagger-ui/**", "/v3/api-docs/**");
    }
}