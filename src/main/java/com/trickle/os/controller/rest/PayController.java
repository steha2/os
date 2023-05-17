package com.trickle.os.controller.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PayController {
	
    @CrossOrigin
	@GetMapping("/os/gm-payment")
	public String requestPayment(long price, long amount, String gmUserId, String itemId, String items, String callbackUrl, Model model) {
		model.addAttribute("price", price);
		model.addAttribute("amount", amount);
		model.addAttribute("itemId", itemId);
		model.addAttribute("gmUserId", gmUserId);
		model.addAttribute("items", items);
		model.addAttribute("callbackUrl", callbackUrl);
		return "/login/gm-payment";
	}
}
