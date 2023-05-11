package com.trickle.os.vo;

import lombok.*;

@Getter
@Setter
@ToString
public class CommentVo {
	private long id;
	private String userId;
	private String content, path, regDate;
	private double score;
}
