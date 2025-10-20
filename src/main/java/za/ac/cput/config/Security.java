package za.ac.cput.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import za.ac.cput.jwt.JwtAuthFilter;

@Configuration
@EnableMethodSecurity
public class Security {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        http


                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
                )

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)



        .exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler()));
return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Access denied: You do not have permission to access this resource\"}");
        };

    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)  throws Exception {
//        return configuration.getAuthenticationManager();
//    }


    //@Bean
//    public UserDetailsService userDetailsService() {
//        var admin = User.withUsername("admin1")
//                .password("{noop}admin123")
//                .roles("ADMIN")
//                .build();
//
//
//
//            var student = User.withUsername("student1")
//                    .password("{noop}student123")
//                    .roles("STUDENT")
//                    .build();
//
//        var organizer = User.withUsername("organizer1")
//                .password("{noop}organizer123")
//                .roles("ORGANIZER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, student, organizer);
//        }
    }

