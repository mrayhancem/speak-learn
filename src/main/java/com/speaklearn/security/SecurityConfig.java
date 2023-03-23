package com.speaklearn.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.speaklearn.security.jwt.AuthTokenFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
    	http.csrf().disable().
    	sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().
    	authorizeHttpRequests().requestMatchers(HttpMethod.OPTIONS,"/**").permitAll().and().
    	authorizeHttpRequests().requestMatchers("/register","/login","/files/download/**","/files/display/**"
        		,"/contactmessage/visitors","/actuator/info","/actuator/health").permitAll().
        anyRequest().authenticated();
        
        http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    @Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedHeaders("*").allowedMethods("*");
			}
		};
	}
    
    
	private static final String [] AUTH_WHITE_LIST= {
			"/v3/api-docs/**",
			"swagger-ui.html",
			"/swagger-ui/**",
			"/",
			"index.html",
			"/images/**",
			"/css/**",
			"/js/**"
	};
    
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		WebSecurityCustomizer customizer=new WebSecurityCustomizer() {
			@Override
			public void customize(WebSecurity web) {
				web.ignoring().requestMatchers(AUTH_WHITE_LIST);
			}
		};
		return customizer;
	}
	
    
    
    
    
    @Bean
    public AuthTokenFilter authTokenFilter() {
    	return new AuthTokenFilter();
    }
    
    
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
    	return 
    			http.getSharedObject(AuthenticationManagerBuilder.class)
    			.authenticationProvider(authProvider()).build();
    }
    
    
    @Bean 
    public DaoAuthenticationProvider authProvider() {
    	DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
    	authenticationProvider.setUserDetailsService(userDetailsService);
    	authenticationProvider.setPasswordEncoder(passwordEncoder());
    	return authenticationProvider;
    }
    
    
    
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder(10);
    }
    
    

}
