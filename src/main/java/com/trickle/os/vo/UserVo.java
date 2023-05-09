package com.trickle.os.vo;

import lombok.*;

@Getter
@Setter
@ToString
public class UserVo {
	private long idx;
	private String name;
	private String password;
	private String phone;
	private String email;
	
	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null)
			return false;
		if (getClass() != object.getClass())
			return false;
		UserVo otherUser = (UserVo) object;
		return idx == otherUser.idx;
	}
}
