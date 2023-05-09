package com.trickle.os.vo;

import java.util.List;

import lombok.*;

@Getter
@Setter
@ToString
public class CartVo {
	private List<ItemVo> items;
	private long rootId, userIdx, id;
	private String regDate;
}