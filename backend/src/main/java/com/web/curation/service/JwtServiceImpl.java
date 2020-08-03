package com.web.curation.service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.LinkedHashMap;

import com.web.curation.exception.UnauthorizedException;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("jwtService")
public class JwtServiceImpl implements JwtService{

	private static final String SALT =  "matdongsanSecret";
	
	@Override
	public <T> String create(String key, T data, String subject){
		Date now = new Date();
		String jwt = Jwts.builder()
						 .setHeaderParam("typ", "JWT")
						 .setHeaderParam("regDate", now.getTime())
						//  .setIssuedAt(now) 
                		//  .setExpiration(new Date(now.getTime() + 2 * 3600 * 1000)) // 2시간 유효
						 .setSubject(subject)
						 .claim(key, data)
						 .signWith(SignatureAlgorithm.HS256, this.generateKey())
						 .compact();
		return jwt;
	}
	
	public byte[] generateKey(){
		byte[] key = null;
		try {
			key = SALT.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			if(log.isInfoEnabled()){
				e.printStackTrace();
			}else{
				log.error("Making JWT Key Error ::: {}", e.getMessage());
			}
		}
		
		return key;
	}

	@Override
	public boolean isUsable(String jwt) {
		try{
			Jws<Claims> claims = Jwts.parser()
					  .setSigningKey(this.generateKey())
					  .parseClaimsJws(jwt);
			return true;
			
		}catch (Exception e) {
			
			if(log.isInfoEnabled()){
				e.printStackTrace();
			}else{
				log.error(e.getMessage());
			}
			throw new UnauthorizedException();
		}
	}
	
	@Override
	public Map<String, Object> get(String key) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String jwt = request.getHeader("Authorization");
		System.out.println(jwt);
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser()
						 .setSigningKey(SALT.getBytes("UTF-8"))
						 .parseClaimsJws(jwt);
		} catch (Exception e) {
			if(log.isInfoEnabled()){
				e.printStackTrace();
			}else{
				log.error(e.getMessage());
			}
			throw new UnauthorizedException();
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> value = (LinkedHashMap<String, Object>)claims.getBody().get(key);
		return value;
	}

	@Override
	public Object getUserId() {
		return this.get("user").get("userId");
	}

	@Override
	public Object getUserEmail() {
		return this.get("user").get("email");
	}
}