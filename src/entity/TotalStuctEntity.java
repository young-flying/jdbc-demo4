package entity;

import java.util.List;

public class TotalStuctEntity {
	private List<StuctEntity> columnList;
	private String tableName;
	private String createTableSql;
	public List<StuctEntity> getColumnList() {
		return columnList;
	}
	public void setColumnList(List<StuctEntity> columnList) {
		this.columnList = columnList;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getCreateTableSql() {
		return createTableSql;
	}
	public void setCreateTableSql(String createTableSql) {
		this.createTableSql = createTableSql;
	}
}
