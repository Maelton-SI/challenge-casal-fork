package maelton.casal.vehicle_rental_api.api.v1.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

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
                    .requestMatchers(HttpMethod.PUT, "/**").hasAnyAuthority("SUPER_ADMIN_", "ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/**").hasAnyAuthority("SUPER_ADMIN", "ADMIN")

                    .anyRequest().permitAll()
            ).httpBasic(Customizer.withDefaults());

        return http.build();
    }

    //Passwords in database are encoded, AuthenticationManager.authenticate() uses it to compare request passwords
    //It automatically encodes passwords from requests and matches it with the ones stored in database
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}