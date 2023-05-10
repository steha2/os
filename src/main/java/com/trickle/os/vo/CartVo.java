package com.trickle.os.vo;

import lombok.*;

@Getter
@Setter
@ToString
public class CartVo {
	private long rootId, userId, cartId, itemId, amount;
	private String regDate;
}