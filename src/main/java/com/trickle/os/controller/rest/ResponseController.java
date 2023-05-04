package com.trickle.os.controller.rest;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.trickle.os.dao.ItemDao;
import com.trickle.os.dao.MenuDao;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ResponseController{
	
	String a() {
		RestTemplate rt = new RestTemplate();
		rt.getForObject("localhost:8090/getList", String.class);
		return null;  
	}
}
