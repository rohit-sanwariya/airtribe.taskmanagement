package task.management.app.taks.management.ai.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import task.management.app.taks.management.ai.services.JwtTokenService;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");
        final String prefix = "Bearer ";
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        if(header.startsWith(prefix)){
            try {
                final String jwt = header.substring(prefix.length());
                final String username = jwtTokenService.extractUsername(jwt);
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if(username != null && authentication == null){
                    UserDetails user = userDetailsService.loadUserByUsername(username);
                    if(jwtTokenService.isTokenValid(jwt,user)){
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                user.getAuthorities()
                        );
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                        filterChain.doFilter(request,response);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


    }
}
