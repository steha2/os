package paging;

import lombok.Getter;

@Getter
public class Paging {
	private long totalRows; // DB의 총 ROW 수
	private long maxPage; // 한번에 보여줄 페이징링크 숫자 최대치
	private long totalPages; // (long) Math.ceil((double) totalCount / count);
	private long nowPage; //현재 페이지
	private long rowCount; //한번에 보여줄 ROW 개수
	private long startPage; //페이징 링크 시작 번호
	private long endPage; //페이징 링크 끝 번호

	public Paging(long nowPage, long rowCount, long maxPage, long totalRows) {
		this.rowCount = rowCount;
		this.totalRows = totalRows;
		this.maxPage = maxPage;
		this.totalPages = (long) Math.ceil((double) totalRows / rowCount);
		this.nowPage = nowPage > totalPages ? totalPages : nowPage;
		startPage = (this.nowPage - 1) / maxPage * maxPage + 1;
		if (startPage > totalPages) startPage = totalPages;
		this.endPage = totalPages <= nowPage || startPage + maxPage >= totalPages ? totalPages : startPage + maxPage - 1;
	}

    public long getStartRow() {
        return (nowPage - 1) * rowCount + 1;
    }

	public long getEndRow() {
        return nowPage * rowCount;
    }

	@Override
	public String toString() {
		return "Paging [totalRows=" + totalRows + ", maxPage=" + maxPage + ", totalPages=" + totalPages
				+ ", nowPage=" + nowPage + ", rowCount=" + rowCount + ", startPage=" + startPage + ", endPage="
				+ endPage + ", getStartRow()=" + getStartRow() + ", getEndRow()=" + getEndRow() + "]";
	}
}
