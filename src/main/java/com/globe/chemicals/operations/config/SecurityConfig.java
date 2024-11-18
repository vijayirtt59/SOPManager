package com.globe.chemicals.operations.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .headers(httpSecurityHeadersConfigurer -> {
                    httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
                })
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/", "/login", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/employee/**").hasRole("EMPLOYEE")
                        .requestMatchers("/employeeDetails").hasRole("EMPLOYEE")
                        .requestMatchers("/api/titles").hasAnyRole("ADMIN", "EMPLOYEE")
                        .requestMatchers("/api/objectives").hasRole("ADMIN")
                        .requestMatchers("/api/scopes").hasRole("ADMIN")
                        .requestMatchers("/api/responsibilities").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic()
                .and()
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler((request, response, authentication) -> {
                            String role = authentication.getAuthorities().iterator().next().getAuthority();
                            if (role.equals("ROLE_ADMIN")) {
                                response.sendRedirect("/admin");
                            } else if (role.equals("ROLE_EMPLOYEE")) {
                                response.sendRedirect("/employee");
                            }
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin123")
                .roles("ADMIN")
                .build();
        UserDetails employee = User.withDefaultPasswordEncoder()
                .username("employee")
                .password("employee123")
                .roles("EMPLOYEE")
                .build();
        return new InMemoryUserDetailsManager(admin, employee);
    }
}
