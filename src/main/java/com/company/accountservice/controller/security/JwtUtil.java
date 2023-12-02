package com.company.accountservice.controller.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;


@Service
public class JwtUtil {

	private final String secretKey;

	public JwtUtil(@Value("${secret.key}") String secretKey) {
		this.secretKey = secretKey;
	}

	public String extractUser(String token) {
		return extractClaims(token, Claims::getSubject);
	}

	public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public Claims extractAllClaims(String token) {
		return Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody();
	}

	public String generateToken(String email) {
		return Jwts
				.builder()
				.setSubject(email)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}

	public boolean validateToken(String token) {
		return !isTokenExpired(token);
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaims(token, Claims::getExpiration);
	}

}
