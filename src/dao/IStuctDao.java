package dao;

import java.util.List;

import entity.StuctEntity;
import entity.TotalStuctEntity;

public interface IStuctDao {
	/**
	 * 查询某表的列结构信息
	 * @param tableName
	 * @return
	 */
	List<StuctEntity> queryStuct(String tableName);
	/**
	 * 查询全部表的列结构信息
	 * @param tableName
	 * @return
	 */
	List<TotalStuctEntity> queryAllStuct();
	/**
	 * 查询创表语句
	 * @param tableName
	 * @return
	 */
	String queryTableSql(String tableName);
}
