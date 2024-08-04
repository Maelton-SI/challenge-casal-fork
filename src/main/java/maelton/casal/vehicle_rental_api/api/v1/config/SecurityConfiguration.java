package maelton.casal.vehicle_rental_api.api.v1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(HttpMethod.POST, "/**").permitAll() //UNDER TESTS
                    .requestMatchers(HttpMethod.PUT, "/**").permitAll() //UNDER TESTS .hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/**").permitAll() //UNDER TESTS .hasRole("ADMIN")
                    .anyRequest().permitAll()
            ).csrf(csrf -> csrf
                    .disable()
            ).sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        return http.build();
    }
}