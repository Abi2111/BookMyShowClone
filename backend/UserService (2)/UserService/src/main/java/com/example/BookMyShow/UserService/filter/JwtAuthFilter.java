package com.example.BookMyShow.UserService.filter;

import com.example.BookMyShow.UserService.Service.JwtService;
import com.example.BookMyShow.UserService.config.UserInfoUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("JwtAuthFilter Executed");
        String token = null;
        String username = null;

        Cookie[] cookies = request.getCookies();
        System.out.println("Cookies: " + Arrays.toString(cookies));
        if (cookies != null) {

            for (Cookie cookie : cookies) {

                if ("bearer".equals(cookie.getName())) {

                    token = cookie.getValue();
                    username = jwtService.extractUsername(token);
                    break;
                }
            }
        }

        if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            if (jwtService.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                System.out.println(
                        "Authorities = " +
                                userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder.getContext()
                        .setAuthentication(authToken);
            }
        }
        System.out.println("AUTHENTICATED");
        System.out.println(
                SecurityContextHolder.getContext().getAuthentication()
        );
        filterChain.doFilter(request, response);
    }
}