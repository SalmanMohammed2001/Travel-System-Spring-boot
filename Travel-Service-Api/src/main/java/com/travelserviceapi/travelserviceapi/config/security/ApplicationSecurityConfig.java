package com.travelserviceapi.travelserviceapi.config.security;



import com.travelserviceapi.travelserviceapi.config.permission.ApplicationUserRole;
import com.travelserviceapi.travelserviceapi.jwt_config.JwtAuthenticationFilter;
import com.travelserviceapi.travelserviceapi.jwt_config.JwtTokenVerifier;
import com.travelserviceapi.travelserviceapi.service.impl.ApplicationUserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.travelserviceapi.travelserviceapi.config.permission.ApplicationUserRole.USER;


@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableMethodSecurity
public class ApplicationSecurityConfig {
    private final PasswordEncoder passwordEncoder;

    private  final ApplicationUserServiceImpl applicationUserService;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserServiceImpl applicationUserService) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;

    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

         http
                 //.csrf().disable()
                 .csrf(AbstractHttpConfigurer::disable)
                 .addFilter(new JwtAuthenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class))))
                 .addFilterAfter(new JwtTokenVerifier(),JwtAuthenticationFilter.class)
                 .authorizeHttpRequests(authz -> authz
                .requestMatchers(new AntPathRequestMatcher("/api/v1/customers/user/list")).permitAll()
                        .requestMatchers("/api/v1/**").hasRole(USER.name())

                 .anyRequest().authenticated());
            http.authenticationProvider(daoAuthenticationProvider());
        return http.build();
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }









}
