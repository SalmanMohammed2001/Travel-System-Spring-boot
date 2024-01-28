package com.travelserviceapi.travelserviceapi.jwt_config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;



public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {



    private final AuthenticationManager authenticationManager;

    /*@Value("${Secret.key}")
    private String secretKey;*/

    String secretKey="dcasdsfsadafasedfasfdasdfsdfsdfsdfsdfsdfsdfsdf";



    @Autowired
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            UsernameAndPasswordRequest userNameAndPasswordRequest=
                    new ObjectMapper().readValue(request.getInputStream(),UsernameAndPasswordRequest.class);

            Authentication authentication= new UsernamePasswordAuthenticationToken(
                    userNameAndPasswordRequest.getUsername(),
                    userNameAndPasswordRequest.getPassword()
            );

            return  authenticationManager.authenticate(authentication);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {


        String token= Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities",authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now()

                        .plusDays(31)))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes())).compact();

        response.addHeader("Authorization","Bearer "+token);



    }
}
