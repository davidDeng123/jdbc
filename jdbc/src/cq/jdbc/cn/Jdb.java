package cq.jdbc.cn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Jdb {
	public static void main(String[] args) throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root");
			Statement stmt=conn.createStatement();
			String sql="SELECT * from student where stuname like '%аж%'";
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()){
				System.out.println(rs.getString(1)+" "+rs.getString(2)+"  "+rs.getString(3));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
