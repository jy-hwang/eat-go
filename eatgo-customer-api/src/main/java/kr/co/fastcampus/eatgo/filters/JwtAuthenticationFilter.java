package kr.co.fastcampus.eatgo.filters;


import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.fastcampus.eatgo.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        Authentication authentication = getAuthentication(request);

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    private Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " 제거
            Claims claims = jwtUtil.getClaims(token);

            if (claims != null) {
                Long userId = claims.get("userId", Long.class);
                String nickname = claims.get("nickname", String.class);
                String role = claims.get("role", String.class);
                if (userId != null && nickname != null) {
                    CustomPrincipal principal = new CustomPrincipal(userId, nickname);
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            principal, null, null);
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    return auth;
                }
            }
        }
        return null;
    }
}
