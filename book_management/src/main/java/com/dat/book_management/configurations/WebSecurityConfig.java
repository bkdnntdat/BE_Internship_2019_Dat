package com.dat.book_management.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().disable();
        httpSecurity.csrf().disable();
        httpSecurity
                .authorizeRequests()
                .antMatchers("/css/**","**/css/**","/js/**", "**/js/**", "/images/**","/fonts/**" ,"**/fonts/**", "**/scss/**"
                        ,"**/less/**", "**/font-awesome-4.7.0/**","**/iconic/**","**/JosefinSans/**","**/source-sans-pro/**", "/vendor/**"
                        ,"**/icons/**", "**/animate/**","**/animsition/**","**/bootrap/**","**/countdowntime/**"
                        ,"**/css-hamburgers/**","**/daterangepicker/**","**/jquery/**","**/perfect-scrollbar/**","**/select2/**").permitAll()
                .antMatchers("/login","/", "/logining","/signup").permitAll()
                .anyRequest()
                .authenticated();
    }
}
