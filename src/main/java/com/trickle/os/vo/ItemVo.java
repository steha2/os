package com.trickle.os.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Getter
@Setter
@ToString
public class ItemVo {
	private long id;
	private String name, path, userId, content, imagePath, regDate, score;
	private long price, numSold, numStock, numView, sumScore, amount;
	private double avgScore;
	private int discount;
	private List<CommentVo> comments;
	
	public void addComment(CommentVo comment) {
		if(comments == null) comments = new ArrayList<>();
		comments.add(comment);
	}
	
	public long getDcPrice() {
		return price * (100 - discount) / 100;
	}
}