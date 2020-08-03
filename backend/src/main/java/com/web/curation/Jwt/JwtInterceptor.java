package com.web.curation.Jwt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.curation.exception.UnauthorizedException;
import com.web.curation.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor{
	private static final String HEADER_AUTH = "Authorization";
	
	@Autowired
	private JwtService jwtService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (request.getMethod().equals("OPTIONS")) {
			System.out.println("OPTIONS");
			return true;
		}
		final String token = request.getHeader(HEADER_AUTH);
		if(token != null && jwtService.isUsable(token)){
			return true;
		}else{
			throw new UnauthorizedException();
		}
	}
}