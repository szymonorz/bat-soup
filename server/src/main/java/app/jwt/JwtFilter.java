package app.jwt;

import app.model.CustomUserDetails;
import app.model.User;
import app.model.UserRepository;
import app.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sun.security.krb5.Credentials;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
            final String authHeader = httpServletRequest.getHeader("Authorization");
            String jwt = null;
            String username = null;
            if(authHeader!=null && authHeader.startsWith("Bearer "))
            {
                jwt = authHeader.substring(7);
                System.out.println("Brrrr "+jwt);
                username =  jwtUtil.getUsernameFromToken(jwt);
                System.out.println(username);
            }

            if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null)
            {
                CustomUserDetails userDetails= (CustomUserDetails) customUserDetailsService.loadUserByUsername(username);
                if(jwtUtil.validateToken(jwt,userDetails))
                {

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    System.out.println("Bearer "+jwt);
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    System.out.println(usernamePasswordAuthenticationToken);
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }

            filterChain.doFilter(httpServletRequest,httpServletResponse);

    }
}
