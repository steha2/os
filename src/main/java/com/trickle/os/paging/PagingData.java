package com.trickle.os.paging;

import static com.trickle.os.paging.FilterCondition.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trickle.os.util.StrUtil;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter
public class PagingData {
	@JsonIgnore private String columns;
	@JsonIgnore private String tableName;
	@JsonIgnore private String joinClause;
	private List<?> data;
	private List<FilterOption> option = new ArrayList<>();
	
//	@JsonIgnore
	private String orderBy;
	
	private long totalRows; // DB의 총 ROW 수
	private long maxPage; // 한번에 보여줄 페이징링크 숫자 최대치
	private long totalPages; // (long) Math.ceil((double) totalCount / count);
	private long nowPage; //현재 페이지
	private long rowCount; //한번에 보여줄 ROW 개수
	private long startPage; //페이징 링크 시작 번호
	private long endPage; //페이징 링크 끝 번호
	
	@JsonIgnore
	public String getWhereQuery() {
		if(option.isEmpty()) return "";
		return " WHERE " + option.stream().map(FilterOption::getQuery).collect(Collectors.joining(" AND "));
	}
	
	public void setTable(String tableName, Class<?> clazz) {
		this.tableName = tableName;
        columns = Arrays.asList(clazz.getDeclaredFields())
			.stream().map(f->StrUtil.toSnakeCase(f.getName())).collect(Collectors.joining(","));
	}
	
	public void setTable(String tableName, String columns) {
		this.tableName = tableName;
		this.columns = columns;
	}
	
	public void setColumns(String columns) {
		this.columns = columns;
	}
	
	public void removeOption(String key, FilterCondition condition) {
		option.removeIf(f->f.getKey().equals(key) && f.getCondition().equals(condition));
	}
	
	public void addOption(String key, FilterCondition condition, Object value1) {
		addOption(key, condition, value1, null);
	}
	
	public void addOption(String key, FilterCondition condition, Object value1, Object value2) {
		option.add(new FilterOption(key, condition, value1, value2));
	}
	
	public static void main(String[] args) {
		PagingData p = new PagingData();
		p.nowPage=1;
		p.maxPage=10;
		p.rowCount=10;
		p.calcPaging(101);
		System.out.println(p);
	}
	
	public void addContains(String key, Object value1) {
		addOption(key, CONTAINS, value1);
	}
	
	public void addBetween(String key, Object value1, Object value2) {
		addOption(key, BETWEEN, value1, value2);
	}
	
	public void calcPaging(long totalRows) {
		this.totalRows = totalRows;
		totalPages = (long) Math.ceil((double) totalRows / rowCount);
		if(nowPage > totalPages) nowPage = totalPages;
		startPage = (nowPage - 1) / maxPage * maxPage + 1;
		if (startPage > totalPages) startPage = totalPages;
		endPage = startPage + maxPage - 1;
		if (endPage > totalPages) {
		    endPage = totalPages;
		}
		if(nowPage < startPage) nowPage = startPage;
		if(nowPage > endPage) nowPage = endPage;
	}
	

    public long getStartRow() {
        return (nowPage - 1) * rowCount + 1;
    }

	public long getEndRow() {
        return nowPage * rowCount;
    }
	
	public void setOption(String option) {
//		System.out.println(option);
	    Gson gson = new Gson();
        Type type = new TypeToken<List<FilterOption>>(){}.getType();
        this.option = gson.fromJson(option, type);
//      System.out.println(this.option);
	}

	public void addColumn(String column) {
		columns += (columns == null || columns.isBlank() ? "" : ",") + column;
	}
}
