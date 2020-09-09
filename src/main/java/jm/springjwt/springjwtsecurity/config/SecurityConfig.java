package jm.springjwt.springjwtsecurity.config;

import jm.springjwt.springjwtsecurity.security.jwt.JwtConfigurer;
import jm.springjwt.springjwtsecurity.security.jwt.JwtProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private JwtProvider jwtProvider;

    public SecurityConfig(JwtProvider jwtProvider){
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http
                .csrf().disable()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/admin/*").hasAnyAuthority("ADMIN")
                .antMatchers("/user/*").hasAnyAuthority("USER")
                .anyRequest()
                .authenticated()
                .and()
                .apply(new JwtConfigurer(jwtProvider));

        http.cors();

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("POST","DELETE","PUT","GET")
                .allowCredentials(true)
                .allowedHeaders("*");
    }

}
