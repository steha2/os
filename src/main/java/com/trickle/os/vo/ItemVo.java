package com.trickle.os.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemVo {
	private long id;
	private String name, path, userId, content, imagePath, regDate, score;
	private long price, numSold, numStock, numView;
	private int discount;
}