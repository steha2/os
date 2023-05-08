package com.trickle.os.vo;

import lombok.*;

@Getter
@Setter
@ToString
public class CommentVo {
	private long id, parentId;
	private String userId;
	private String content;
	private double score;
}
