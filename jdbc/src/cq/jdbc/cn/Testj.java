package cq.jdbc.cn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Testj {
	public static void main(String[] args) throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root");
			Statement stmt = conn.createStatement();
//			String name = "David";
//			String sql =  "insert into tea(stuid,teacher,sub) value(2009,'"+name+"','oracle')";
			int id = 2009;
			String sql2 =  "delete from tea where stuid="+id;
			stmt.execute(sql2);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
