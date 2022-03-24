package com.example.practice.securityConfi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.practice.fiter.JwtRequestFilter;
import com.example.practice.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	    @Autowired
	    CustomUserDetailsService customUserDetailsService;
	    
	    @Autowired
	    JwtRequestFilter jwtRequestFilter;
	 
	    
	    // huma aik new 
	    @Override        
	    protected void configure(HttpSecurity http) throws Exception {
	            http
	                    .csrf().disable()
	                    .cors().disable()
	                    .authorizeRequests().antMatchers("/get-token").permitAll()
	                    .anyRequest().authenticated()
	                    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	            
	            // ya end ma 
	            http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	    }

	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(customUserDetailsService);
	    }
	    @Bean
	    PasswordEncoder getPasswordEncoder(){
	        return new BCryptPasswordEncoder();
	    }
	    @Bean
	    AuthenticationManager getAuthenticationManager() throws Exception {
	        return this.authenticationManagerBean();
	    }
}
