package paging;

import static paging.FilterCondition.*;

import java.util.*;
import java.util.stream.Collectors;

import com.trickle.os.util.StrUtil;

import lombok.*;

@Getter
@ToString
@Setter
public class PagingDto {
	private String columns;
	private String tableName;
	private Paging paging;
	private List<?> data;
	private List<FilterOption> option = new ArrayList<>();
	private String orderBy;
	private Class<?> resultType;
	private long nowPage;
	private long rowCount;
	private long maxPage;
	private long totalRows;
	
	public String getWhereQuery() {
		if(option.isEmpty()) return "";
		return "WHERE " + option.stream().map(FilterOption::getQuery).collect(Collectors.joining(" AND "));
	}
	
	public void setTable(String tableName, Class<?> clazz) {
		this.tableName = tableName;
        columns = Arrays.asList(clazz.getDeclaredFields())
			.stream().map(f->StrUtil.toSnakeCase(f.getName())).collect(Collectors.joining(","));
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
		if(option == null) option = new ArrayList<>();
		option.add(new FilterOption(key, condition, value1, value2));
	}
	
	public void addContains(String key, Object value1) {
		addOption(key, CONTAINS, value1);
	}
	
	public void addBetween(String key, Object value1, Object value2) {
		addOption(key, BETWEEN, value1, value2);
	}
	
	public void calcPaging() {
		this.paging = new Paging(nowPage, rowCount, maxPage, totalRows);
	}

    public long getStartRow() {
        return (nowPage - 1) * rowCount + 1;
    }

	public long getEndRow() {
        return nowPage * rowCount;
    }
}
