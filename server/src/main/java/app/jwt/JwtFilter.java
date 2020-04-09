package app.jwt;

import app.model.User;
import app.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

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
    private UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
            final String authHeader = httpServletRequest.getHeader("Authorization");
            String email = null;
            String jwt = null;
            String username = null;
            User user = null;
            if(authHeader!=null && authHeader.startsWith("Bearer "))
            {
                jwt = authHeader.substring(7);
                email = jwtUtil.getEmailFromToken(jwt);
                System.out.println("Brrrr "+email);
                user = userRepository.findUserByEmail(email);
                username = userRepository.findUserByEmail(email).getUsername();
                System.out.println(username);
            }

            if(email!=null && SecurityContextHolder.getContext().getAuthentication() == null)
            {
                if(jwtUtil.validateToken(jwt,user))
                {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            user,null
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
