package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import entity.StuctEntity;
import entity.TotalStuctEntity;

public class StuctDao implements IStuctDao {
	private Connection conn;
	{
		try {
			String fileName="/jdbc.properties";//这里是指放在classes下，如果有包的话，前面加包名即可。例：/com/web/db.properties
		    String driver = "";  
		    String url = "";  
		    String username ="";
		    String password = "";  
		    InputStream in = StuctDao.class.getResourceAsStream(fileName);
		    Properties pts = new Properties();
		    pts.load(in);
		    in.close();  
            if(pts.containsKey("jdbc.driver")){  
                driver = pts.getProperty("jdbc.driver");  
            }  
            if(pts.containsKey("jdbc.url")){  
                url = pts.getProperty("jdbc.url");  
            }  
            if(pts.containsKey("jdbc.user")){  
                username = pts.getProperty("jdbc.user");  
            }  
            if(pts.containsKey("jdbc.password")){  
                password = pts.getProperty("jdbc.password");  
            }  
			Class.forName(driver);
			conn = DriverManager.getConnection(url,username,password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<StuctEntity> queryStuct(String tableName) {
		List<StuctEntity> stuctList = new ArrayList<StuctEntity>();
		StuctEntity stuct = null;
		ResultSet colRet = null;
		try {
			DatabaseMetaData m_DBMetaData = conn.getMetaData();
			colRet = m_DBMetaData.getColumns(null, "%", tableName,
					"%");
			while (colRet.next()) {
				stuct = new StuctEntity();
				saveColumnStuctToEntity(colRet, stuct);
				stuctList.add(stuct);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(null != colRet) {
				try {
					colRet.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		return stuctList;
	}
	/**
	 * 转化表数据到entity
	 * @param colRet
	 * @param stuct
	 * @throws SQLException
	 */
	private void saveColumnStuctToEntity(ResultSet colRet,StuctEntity stuct) throws SQLException {
		stuct.setColumnName(colRet.getString("COLUMN_NAME"));
		stuct.setColumnType(colRet.getString("TYPE_NAME"));
		stuct.setDataSize(colRet.getInt("COLUMN_SIZE"));
		stuct.setRemark(colRet.getString("REMARKS"));
		stuct.setDefaultValue(colRet.getString("COLUMN_DEF"));
	}

	@Override
	public String queryTableSql(String tableName) {
		ResultSet rs = null;
		String createTableSql = null;
		try {
			PreparedStatement ps = conn.prepareStatement("show create table "+tableName);
			rs = ps.executeQuery();
			if (rs.next()) {
				System.out.println(rs.getString(rs.getMetaData().getColumnName(
						2)));
				createTableSql = rs.getString(rs.getMetaData().getColumnName(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(null != rs) {
				try {
					rs.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return createTableSql;
	}

	@Override
	public List<TotalStuctEntity> queryAllStuct() {
		List<TotalStuctEntity> totalList = new ArrayList<TotalStuctEntity>();
		TotalStuctEntity total = null;
		List<StuctEntity> stuctList = null;
		StuctEntity stuct = null;
		try {
            DatabaseMetaData dbmd=conn.getMetaData();
            ResultSet resultSet = dbmd.getTables(null, "%", "%", new String[] { "TABLE" });
            while (resultSet.next()) {
            	total = new TotalStuctEntity();
            	stuctList = new ArrayList<StuctEntity>();
                String tableName=resultSet.getString("TABLE_NAME");
                total.setTableName(tableName);
                ResultSet rs = dbmd.getColumns(null, "%", tableName, "%");
                while(rs.next()){
                	stuct = new StuctEntity();
                	saveColumnStuctToEntity(rs, stuct);
                	stuctList.add(stuct);
                }
                total.setColumnList(stuctList);
                total.setCreateTableSql(this.queryTableSql(tableName));
                
                totalList.add(total);
                System.out.println("完成");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return totalList;
	}

}
