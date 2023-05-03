package com.trickle.os.util;

import lombok.Getter;

@Getter
public class Paging {
	private int totalRows; // DB의 총 ROW 수
	private int maxPage; // 한번에 보여줄 페이징링크 숫자 최대치
	private int totalPages; // (int) Math.ceil((double) totalCount / count);
	private int nowPage; //현재 페이지
	private int rowCount; //한번에 보여줄 ROW 개수
	private int startPage; //페이징 링크 시작 번호
	private int endPage; //페이징 링크 끝 번호

	public Paging(int nowPage, int rowCount, int maxPage, int totalRows) {
		this.nowPage = nowPage;
		this.rowCount = rowCount;
		this.totalRows = totalRows;
		this.maxPage = maxPage;
		this.totalPages = (int) Math.ceil((double) totalRows / rowCount);
		if(nowPage > totalPages) this.nowPage = totalPages;
		startPage = (nowPage - 1) / maxPage * maxPage + 1;
		if (startPage > totalPages) startPage = totalPages;
		this.endPage = totalPages <= nowPage || startPage + maxPage >= totalPages ? totalPages : startPage + maxPage - 1;
	}

    public int getStartRow() {
        return (nowPage - 1) * rowCount + 1;
    }

	public int getEndRow() {
        return nowPage * rowCount;
    }

	@Override
	public String toString() {
		return "Paging [totalRows=" + totalRows + ", maxPage=" + maxPage + ", totalPages=" + totalPages
				+ ", nowPage=" + nowPage + ", rowCount=" + rowCount + ", startPage=" + startPage + ", endPage="
				+ endPage + ", getStartRow()=" + getStartRow() + ", getEndRow()=" + getEndRow() + "]";
	}
}
