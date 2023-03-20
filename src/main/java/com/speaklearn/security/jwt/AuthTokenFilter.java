package com.speaklearn.security.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.speaklearn.exception.message.ErrorMessage;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthTokenFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	private Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//getting only the token part from http request
		String token = parseJwt(request);
		
		try {
			if(token != null && jwtUtils.validateJwtToken(token)) {
				String email = jwtUtils.getEmailFromToken(token);
				UserDetails userDetails = userDetailsService.loadUserByUsername(email);
				
				//creation digital id for the user with the given parameters, (null is the password area, but user was supposed to be authorized before this line)
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				//here finally given the credentions to security guard of the spring
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		} catch (Exception e) {
			//log everthing, this will throw user not found exception
			logger.error(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE,e.getMessage()));
		}
		
		filterChain.doFilter(request, response);
		
		
	}
	
	//if request good, get rid of bearer part
	private String parseJwt(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if(StringUtils.hasText(header) && header.startsWith("Bearer ")) {
			return header.substring(7);
		}
		return null;
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		//even if we don't put this code, we already enabled access to this two paths in sec config, but still we wanted to prevent these two path to go into filter system.
		AntPathMatcher antPathMatcher = new AntPathMatcher();
		return antPathMatcher.match("/register", request.getServletPath()) || antPathMatcher.match("/login", request.getServletPath());
	}

}
