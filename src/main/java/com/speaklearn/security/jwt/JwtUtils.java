package com.speaklearn.security.jwt;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.speaklearn.exception.message.ErrorMessage;
import com.speaklearn.security.service.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {
	private Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	
	@Value("${speaklearn.app.jwtExpirationMs}") //getting data from yaml
	private long jwtExpirationMs;
	
	@Value("${speaklearn.app.jwtSecret}") //getting data from yaml
	private String jwtSecret;

	public String generateJwtToken(UserDetailsImpl userDetails) {
		return Jwts.builder().setSubject(userDetails.getEmail()).setIssuedAt(new Date()).
		setExpiration(new Date(new Date().getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}
	
	public String getEmailFromToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean validateJwtToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
			// logging if token throw error or any other errors.
			logger.error(String.format(ErrorMessage.JWTTOKEN_ERROR_MESSAGE, e.getMessage()));
		} 
		return false;
	}
}
