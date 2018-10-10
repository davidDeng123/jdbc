package cq.jdbc.cn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Demo {
	public static void main(String[] args) throws SQLException {
		Connection conn=null;
		PreparedStatement ps = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root");
			String sql = "insert into tea(stuid,teacher,sub) value(?,?,?)";
//			String sql =  "insert into tea(stuid,teacher,sub) value(2006,'zhu','oracle')";
			ps = conn.prepareStatement(sql);
			ps.setObject(1, 2017);
			ps.setObject(2, "zhu");
			ps.setObject(3, "oracle");
			System.out.println("成功插入数据！");
			ps.execute();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			if(conn!=null) {
				conn.close();
			}if(ps!=null) {
				ps.close();
			}
		}
	}
}
