package com.trickle.os.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trickle.os.dao.UserDao;
import com.trickle.os.vo.UserVo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {
	
	private final UserDao userDao;
	
	@PostMapping("/login/auth")
	public ResponseEntity<String> auth(@RequestParam String name, @RequestParam String password) {
	    // 인증 로직 구현
	    UserVo user = new UserVo();
	    user.setName(name);
	    user.setPassword(password);
	    user = userDao.findByNamePw(user);
	    
	    if (user != null) {
	        return ResponseEntity.ok(user.getName());
	    }
	    ResponseEntity<String> fail = ResponseEntity.ok("");
	    return fail;
	}
}

class JWTUtil {
    private static final String SECRET_KEY = "myKey";

    public static String generateToken(String userId) {
        long expirationTime = 86400000; // 토큰의 만료 시간 설정 (예: 24시간)
        String token = Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        return token;
    }
}