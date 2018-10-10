package cq.jdbc.cn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestRs {
	public static void main(String[] args) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root");
			String sql = "select stuid,teacher,sub from tea where stuid=2014";
//			Statement st = con.createStatement();
//			st.executeQuery(sql);
			ps = con.prepareStatement(sql);
//			ps.setObject(1, 2007);
			rs = ps.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getInt(1)+rs.getString(2)+rs.getString(3));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			
		}
	}
}
