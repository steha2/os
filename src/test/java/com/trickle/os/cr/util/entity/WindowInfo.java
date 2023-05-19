package com.trickle.os.cr.util.entity;

import java.awt.Rectangle;

import lombok.*;

@Getter
@Setter
@ToString
public class WindowInfo {
	private String title;
	private Rectangle rect;
}
