package app.config;

import app.model.CustomUserDetails;
import app.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(customUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity.csrf().disable();
        httpSecurity.httpBasic().disable();
        httpSecurity.authorizeRequests().antMatchers("**/**").permitAll();
    }
}
