package paging;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FilterOption {
	private String key;
	private Object value1, value2;
	private FilterCondition condition;

	public FilterOption() {}
	
	public FilterOption(String key, Object condition, Object value1) {
		this(key,condition,value1,null);
	}
	
	public FilterOption(String key, Object condition, Object value1, Object value2) {
		this.key = key;
		this.value1 = value1;
		this.value2 = value2;
		setCondition(condition);
	}
	
	public void setCondition(Object condition) {
		this.condition = condition instanceof FilterOption ? (FilterCondition) condition : FilterCondition.valueOf(condition.toString()); 
	}
	
	public void setCondition(FilterCondition condition) {
		this.condition = condition;
	}
	
	public String getQuery() {
	    String query;
	    switch (condition) {
	        case EQUALS:
	            query = key + " = " + getValueString(value1);
	            break;
	        case NOT_EQUALS:
	            query = key + " != " + getValueString(value1);
	            break;
	        case GREATER_THAN:
	            query = key + " > " + getValueString(value1);
	            break;
	        case LESS_THAN:
	            query = key + " < " + getValueString(value1);
	            break;
	        case CONTAINS:
	            query = key + " LIKE '%" + value1.toString() + "%'";
	            break;
	        case NOT_CONTAINS:
	            query = key + " NOT LIKE '%" + value1.toString() + "%'";
	            break;
	        case STARTS_WITH:
	            query = key + " LIKE '" + value1.toString() + "%'";
	            break;
	        case ENDS_WITH:
	            query = key + " LIKE '%" + value1.toString() + "'";
	            break;
	        case BETWEEN:
	            query = key + " BETWEEN " + getValueString(value1) + " AND " + getValueString(value2);
	            break;
	        default:
	            throw new IllegalArgumentException("Invalid filter condition: " + condition);
	    }
	    return query;
	}

	private String getValueString(Object value) {
	    return "'" + value.toString() + "'";
	}
}