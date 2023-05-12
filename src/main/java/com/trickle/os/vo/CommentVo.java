package com.trickle.os.vo;

import lombok.*;

@Getter
@Setter
@ToString
public class CommentVo {
	private long id;
	private long userId;
	private String userName, content, path, regDate;
	private double score;
}
