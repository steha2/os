package com.trickle.os.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderItem {
	private String item_name, unique;
	private long qrt, price;
}