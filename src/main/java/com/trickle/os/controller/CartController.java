package com.trickle.os.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.trickle.os.dao.CartDao;
import com.trickle.os.dao.ItemDao;
import com.trickle.os.dao.MenuDao;
import com.trickle.os.vo.CartVo;
import com.trickle.os.vo.OrderVo;
import com.trickle.os.vo.RootVo;
import com.trickle.os.vo.UserVo;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CartController {

	private final MenuDao menuDao;
	private final CartDao cartDao;
	
	//id : Item Id
	@GetMapping("/login/openCart/{rootId}") 
	public String openCart(@PathVariable long rootId, HttpSession session, Model model) {
		UserVo user = (UserVo) session.getAttribute("user");
		RootVo root = menuDao.getRootById(rootId);
		model.addAttribute("root", root);
		model.addAttribute("items", cartDao.getCartItems(rootId, user.getId()));
		model.addAttribute("carts", cartDao.getCarts(rootId, user.getId()));
		return "/page/"+root.getType()+"/"+root.getId()+"/cart-"+rootId;
	}
	
	@RequestMapping(value="/login/addCart", produces ="text/plain;charset=UTF-8")
	@ResponseBody
	public String addCart(CartVo cart, HttpSession session) {
		UserVo user = (UserVo) session.getAttribute("user");
		if(user != null) {
			System.out.println(user);
			System.out.println(cart);
			cart.setUserId(user.getId());
			if(cartDao.getCartItem(cart) == null) {
				cartDao.addCart(cart);
				System.out.println(cart);
				return "장바구니에 넣기 성공";
			} else {
				return "이미 장바구니에 등록된 상품";
			}
		}
		return "실패";
	}
	
	@GetMapping("/login/deleteCart/{cartId}")
	@ResponseBody
	public String deleteCart(@PathVariable long cartId, HttpSession session) {
		UserVo user = (UserVo) session.getAttribute("user");
		if(user != null) cartDao.deleteCart(cartId, user.getId());
		return "삭제";
	}
	
	@GetMapping("/login/deleteCarts/{rootId}")
	@ResponseBody
	public String deleteCarts(@PathVariable long rootId, HttpSession session) {
		UserVo user = (UserVo) session.getAttribute("user");
		if(user != null) cartDao.deleteCarts(rootId, user.getId());
		return "여러개 삭제";
	}
	
	@GetMapping("/login/getOrderSeq")
	@ResponseBody
	public Integer getOrderSeq() {
		return cartDao.getOrderSeq();
	}
	
	@PostMapping("/login/addOrder")
	@ResponseBody
	public Boolean addOrder(OrderVo order, HttpSession session) {
		UserVo user = (UserVo) session.getAttribute("user");
		if(user != null) {
			order.setUserId(user.getId());
			cartDao.addOrder(order);
			return true;
		}
		return false;
	}
	
	@GetMapping("/login/payComplate/{orderId}")
	public String payComplate(@PathVariable long orderId, HttpSession session, Model model) {
		UserVo user = (UserVo) session.getAttribute("user");
		user.setRefId(orderId);
		model.addAttribute("order", cartDao.getOrder(user));
		return "/login/pay-complate";
	}
	
	@GetMapping("/login/myPage")
	public String myPage(HttpSession session, Model model) {
		UserVo user = (UserVo) session.getAttribute("user");
		model.addAttribute("orderList", cartDao.getOrders(user));
		return "/login/my-page";
	}
}