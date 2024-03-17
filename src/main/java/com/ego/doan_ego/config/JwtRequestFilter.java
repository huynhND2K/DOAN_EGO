package com.ego.doan_ego.config;

import com.ego.doan_ego.config.service.impl.JwtAuthServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private JwtAuthServiceImpl jwtAuthServiceImpl;

    @Autowired
    public void setJwtAuthServiceImpl(JwtAuthServiceImpl jwtAuthServiceImpl) {
        this.jwtAuthServiceImpl = jwtAuthServiceImpl;
    }
    public JwtRequestFilter(JwtAuthServiceImpl jwtAuthServiceImpl, JwtTokenUtil jwtTokenUtil) {
        this.jwtAuthServiceImpl = jwtAuthServiceImpl;
        this.jwtTokenUtil = jwtTokenUtil;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String pathInfo = request.getRequestURI().substring(request.getContextPath().length());
        log.info("request: {}, path={}", request.getRequestURI(), pathInfo);
        String resUrl = pathInfo;
        final String requestTokenHeader = request.getHeader("Authorization");
//        if (resUrl.startsWith("/api/auth"))
//        {
//        } else{
            String username = null;
            String jwtToken = null;
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                jwtToken = requestTokenHeader.substring(7);
                try {
                    username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                } catch (IllegalArgumentException e) {
                    log.error("Unable to get JWT Token");
                } catch (ExpiredJwtException e) {
                    log.error("JWT Token has expired");
                }
            } else {
                logger.error("JWT Token does not begin with Bearer String");
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.jwtAuthServiceImpl.loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }

       // }
        filterChain.doFilter(request, response);
    }
}
