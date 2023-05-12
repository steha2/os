package com.trickle.os.vo;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trickle.os.paging.FilterOption;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderVo {
	private long id, userId, price;
	private String status="", pg="", pgName="", requestedAt, name="", items="";
	private List<OrderItem> itemList;
	
	public void setItems(String items) {
	    Gson gson = new Gson();
        Type type = new TypeToken<List<OrderItem>>(){}.getType();
        this.itemList = gson.fromJson(items, type);
        this.items = items;
	}
}

// [
//     {
//         item_name: '나는 아이템', //상품명
//         qty: 1, //수량
//         unique: '123', //해당 상품을 구분짓는 primary key
//         price: 1000, //상품 단가
//     }
// ]