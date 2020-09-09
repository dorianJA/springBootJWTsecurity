package jm.springjwt.springjwtsecurity.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtFilter extends GenericFilterBean {

    private JwtProvider jwtProvider;

    public JwtFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter do filter");
        String token = jwtProvider.resolveToken((HttpServletRequest) servletRequest);
        System.out.println(token);
//        Cookie[] ar = ((HttpServletRequest)servletRequest).getCookies();
//        System.out.println(((HttpServletRequest)servletRequest).getHeader("Authorization"));
//        for(Cookie cook: ar){
//            System.out.println("Cookie: "+cook.getValue());
//        }
        try {
            if (token != null && jwtProvider.validateToken(token)) {
                Authentication authentication = jwtProvider.getAuthentication(token);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (JwtAuthenticationException e) {
            SecurityContextHolder.clearContext();
            throw new JwtAuthenticationException("JW token is expired or invalid");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
