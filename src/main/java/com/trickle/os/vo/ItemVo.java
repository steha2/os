package com.trickle.os.vo;

import lombok.*;

@Getter
@Setter
@ToString
public class ItemVo {
	private long id;
	private String name, path, userId, content, imagePath, regDate, score;
	private long price, numSold, numStock, numView, numComment;
	private Long sumScore;
	private Double avgScore;
	private int discount;
	
	public long getDcPrice() {
		return price * (100 - discount) / 100;
	}
}