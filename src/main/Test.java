package main;

import java.util.List;

import dao.IStuctDao;
import dao.StuctDao;
import entity.StuctEntity;
import entity.TotalStuctEntity;

public class Test {
	
	private static void outColumnInfo(List<StuctEntity> stuctList) {
		if(null != stuctList && !stuctList.isEmpty()) {
			System.out.println("columnName\tcolumnType\tdataSize\tdefaultValue\tremark");
			for(StuctEntity st : stuctList) {
				System.out.println(st.getColumnName()+"\t\t"+st.getColumnType()+
						"\t\t"+st.getDataSize()+"\t\t"+st.getDefaultValue()+"\t\t"+st.getRemark());
			}
		}
	}

	public static void main(String[] args) {
		//题目4
		/*try {
			IStuctDao stuctDao = new StuctDao();
			List<StuctEntity> stuctList = stuctDao.queryStuct("user");
			if(null != stuctList && !stuctList.isEmpty()) {
				outColumnInfo(stuctList);
			}
			stuctDao.queryTableSql("user");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		try {
			IStuctDao stuctDao = new StuctDao();
			List<TotalStuctEntity> totalList = stuctDao.queryAllStuct();
			if(null != totalList && !totalList.isEmpty()) {
				for(TotalStuctEntity total: totalList) {
					System.out.println("表名"+total.getTableName());
					outColumnInfo(total.getColumnList());
					System.out.println(total.getCreateTableSql());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}

}
