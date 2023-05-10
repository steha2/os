package com.trickle.os.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderItemVo {
	private String itemName, unique;
	private long qrt, price;
}