package com.yn.threadJob;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RunClass /* implements Runnable */ extends Thread {
	private static final String TESTURL = "jdbc:mysql://127.0.0.1/cmy";
	private static final String DRIVERNAME = "com.mysql.jdbc.Driver";
	private static final String TESTUSER = "root";
	private static final String TESTPASSWORD = "123456";
	private static final String NEWURL = "jdbc:mysql://120.76.98.74:3306/younen";
	private static final String NEWUSER = "root";
	private static final String NEWPASSWORD = "Engross0812";
//	private static final String OLDURL = "jdbc:mysql://120.76.98.74:3306/youneng";
//	private static final String OLDUSER = "root";
//	private static final String OLDPASSWORD = "Engross0812";
	private static Connection TESTCONN = null;
	private static Connection NEWCONN = null;
//	private static Connection OLDCONN = null;
	// 执行接口
	public static PreparedStatement ps;
	// 结果集
	public static ResultSet rs;

	int index = 0;

//	static List<String> sd = sd();
//	int size = sd.size();

	@Override
	public void run() {

		// 开始时间
//		Long begin = new Date().getTime();
//		doInsert();
		// 结束时间
//		Long end = new Date().getTime();
		// 耗时
//		System.out.println(size + "条数据插入花费时间 : " + (end - begin) / 1000 + " s" + "  插入完成");
//		System.out.println(size + "条数据插入花费时间 : " + (end - begin) + " ms" + "  插入完成");
	}

	/*private void doInsert() {
		try {
			getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// sql前缀
		String prefix = "INSERT INTO `user`(" + "`username`,`password`,`phone`,`email` " + ") VALUES  ";
		try {
			StringBuffer sb = new StringBuffer();
			// 设置事务为非自动提交
			TESTCONN.setAutoCommit(false);
			// 准备执行语句
			PreparedStatement pst = TESTCONN.prepareStatement("");
			// 外层循环，总提交事务次数
			for (int i = 1; i <= 5; i++) {
				synchronized (this) {
					sb = new StringBuffer();
					// 第j次提交步长
					for (int j = 0; j < sd.size(); j++) {
						// 构建SQL后缀
						sb.append("('" + (sd.get(j)) + "','123456'" + ",'" + j + "'" + ",'cmy@qq.com'" + "),");
					}
					// 构建完整SQL
					String sql = prefix + sb.substring(0, sb.length() - 1);
					// 添加执行SQL
					pst.addBatch(sql);
					// 执行操作
					pst.executeBatch();
					// 提交事务
					TESTCONN.commit();
					// 清空上一次添加的数据
					sb = new StringBuffer();
				}
			}
			// 头等连接
			pst.close();
			NEWCONN.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * 执行查询
	 * 
	 * @param sql（执行语句）
	 * @param objects
	 *            （参数）
	 * @return（返回结果集） @throws SQLException（会引发的异常）
	 */
	public static ResultSet executeQuery(String sql, Object... objects) throws SQLException {
		// 获取连接
		// getConnection();//测试的
		// getConnection1();//新表的
		getConnection3();// 旧表的。
		ps = NEWCONN.prepareStatement(sql);
		if (objects != null) {
			for (int i = 0; i < objects.length; i++) {
				ps.setObject((i + 1), objects[i]);
			}
		}
		// 获取执行的结果集
		rs = ps.executeQuery();
		// closeAll(conn1, ps, rs);
		return rs;
	}

/*	public List<AmPhaseRecord> getOldData() throws SQLException {
		List<Am1Phase> am1PhaseList = new ArrayList<Am1Phase>();
		List<Am3Phase> am3PhaseList = new ArrayList<Am3Phase>();
		String sql1 = "select * from youneng.am_1phase_2017_07_01 where meter_time >= 1707010000000 and meter_time <= 170701999999 ";
		String sql3 = "select * from youneng.am_3phase_2017_07_01 where meter_time >= 1707010000000 and meter_time <= 170701999999 ";
		ResultSet resultSet = executeQuery(sql1, null);
		while (resultSet.next()) {
			int rowId = resultSet.getInt("row_id");
			int cAddr = resultSet.getInt("c_addr");
			int iAddr = resultSet.getInt("i_addr");
			long dAddr = resultSet.getLong("d_addr");
			int dType = resultSet.getInt("d_type");
			int wAddr = resultSet.getInt("w_addr");
			long meterTime = resultSet.getLong("meter_time");
		}
		return null;
	}*/

	/**
	 * 
	 * @Title: sd @Description: TODO(测试用的。) @param @return 参数 @return
	 *         List<String> 返回类型 @throws
	 */
	/*public static final synchronized List<String> sd() {
		List<String> lists = new ArrayList<String>();
		String sql = " select * from am_phase_record_2017_05_02 ";
		ResultSet s;
		try {
			s = executeQuery(sql, null);
			while (s.next()) {
				String string = s.getString("am_phase_record_id");
				lists.add(string);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lists;
	}*/

	/**
	 * 
	 * @Title: getConnection @Description: TODO(获取数据库连接) @param @throws
	 *         SQLException 参数 @return void 返回类型 @throws
	 */
	public static void getConnection() throws SQLException {
		try {
			Class.forName(DRIVERNAME);
			TESTCONN = DriverManager.getConnection(TESTURL, TESTUSER, TESTPASSWORD);// 获取连接
			TESTCONN.setAutoCommit(false);// 关闭自动提交，不然conn.commit()运行到这句会报错
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*public static void getConnection1() throws SQLException {
		try {
			Class.forName(DRIVERNAME);
			OLDCONN = DriverManager.getConnection(OLDURL, OLDUSER, OLDPASSWORD);// 获取连接
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/

	public static void getConnection3() throws SQLException {
		try {
			Class.forName(DRIVERNAME);
			NEWCONN = DriverManager.getConnection(NEWURL, NEWUSER, NEWPASSWORD);// 获取连接
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭所有对象
	 * 
	 * @throws SQLException（会引发的异常）
	 */
	public static void closeAll(Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException("关闭连接失败" + e);
		}
	}

}
