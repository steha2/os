package com.trickle.os.vo;

import lombok.*;

@Getter
@Setter
@ToString
public class OrderVo {
	private long id, userId, price;
	private String items, status, pg, requestedAt;
}

// [
//     {
//         item_name: '나는 아이템', //상품명
//         qty: 1, //수량
//         unique: '123', //해당 상품을 구분짓는 primary key
//         price: 1000, //상품 단가
//     }
// ]