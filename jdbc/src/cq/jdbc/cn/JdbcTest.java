package cq.jdbc.cn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JdbcTest {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Connection con = null;
		Statement stm = null;
		boolean isLogIn = false;
		String userName = "";
		String userPW = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "root");
			stm = con.createStatement();
//			String sql = "select * from pet";
//			ResultSet rs = stm.executeQuery(sql);
//			while(rs.next()) {//迭代器遍历
//				System.out.println(rs.getString(2)+"  "+rs.getString(3));
//			}
			String lname;
			String pw;
			String sql;
			int tiaoshu;
			ResultSet rs;
			li:while(true) {
				System.out.println("欢迎来到宠物系统");
				System.out.println("1.添加用户");
				System.out.println("2.修改用户密码");
				System.out.println("3.删除用户");
				System.out.println("4.查看所有用户");
				System.out.println("5.登录");
				System.out.println("6.查看个人信息");
				System.out.println("7.查询全部宠物信息");
				System.out.println("8.增加宠物");
				System.out.println("12.退出");
				int choose = sc.nextInt();
				li2:switch (choose) {
				case 1:
					System.out.println("请输入用户名：");
					lname = sc.next();
					rs = stm.executeQuery("select lname from lord");
					while(rs.next()) {
						if(rs.getString(1).equals(lname)) {
							System.out.println("用户名不能重复！");
							break li2;
						}
					}
					System.out.println("请输入密码：");
					pw = sc.next();
					sql = "insert into lord(lname,pw,money) values('"+lname+"','"+pw+"',500);";
					tiaoshu = stm.executeUpdate(sql);
					if(tiaoshu == 1) {
						System.out.println("添加用户成功！");
						tiaoshu = 0;
					}
					break;
				case 2:
					if(!isLogIn) {
						System.out.println("请先登录！");
					}else {
						System.out.println("请输入新密码：");
						pw = sc.next();
						System.out.println("请再次输入新密码：");
						String rePW = sc.next();
						if(pw.equals(rePW)) {
							sql = "update lord set pw = '"+rePW+"'where lname = '"+userName+"';";
							tiaoshu = stm.executeUpdate(sql);
							if(tiaoshu==1) {
								System.out.println("密码修改成功！");
							}else {
								System.out.println("系统出错，请联系管理员！");
							}
						}else {
							System.out.println("密码重复错误！");
							break;
						}
					}
					break;
				case 3:
					System.out.println("请输入用户名：");
					lname = sc.next();
					if(lname == userName) {
						System.out.println("不能删除正在登录的用户！");
						break;
					}
					sql = "delete from pet where pet.lid = (select lord.lid from lord where lord.lname = '"+lname+"')";
					stm.executeUpdate(sql);
					sql = "delete from lord where lname = '"+lname+"'";
					tiaoshu = stm.executeUpdate(sql);
					if(tiaoshu == 1) {
						System.out.println("删除用户成功！");
						tiaoshu = 0;
					}
					break;
				case 4:
					System.out.println("\t主人信息列表");
					System.out.println("编号\t"+"姓名\t"+"金币");
					sql = "select lid,lname,money from lord";
					rs = stm.executeQuery(sql);
					while(rs.next()) {
						System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getInt(3));
					}
					break;
				case 5:
					System.out.println("请输入用户名：");
					lname = sc.next();
					System.out.println("请输入密码：");
					pw = sc.next();
					sql = "select pw from lord where lname = '"+lname+"';";
					rs = stm.executeQuery(sql);
					String getPW = "";
					while(rs.next()) {
						getPW = rs.getString(1);
					}
					if(pw.equals(getPW)&& pw!="") {
						userName = lname;
						userPW = getPW;
						isLogIn = true;
						System.out.println("登录成功！");
					}else {
						System.out.println("用户名或密码错误！");
					}
					break;
				case 6:
					if(!isLogIn) {
						System.out.println("请先登录！");
					}else {
						sql ="select lname,money,pname,health,love,strain from (select * FROM lord where lord.lname = '"+userName+"') a,(select * from pet where pet.lid = (select lord.lid from lord where lord.lname = '"+userName+"')) b;";
						rs = stm.executeQuery(sql);
						while(rs.next()) {
							System.out.println(rs.getString(1)+"\t"+rs.getInt(2)+"\t"+rs.getString(3)+"\t"+rs.getInt(4)+"\t"+rs.getInt(5)+"\t"+rs.getString(6));
						}
					}
					break;
				case 7:
					System.out.println("\t\t宠物信息列表");
					System.out.println("编号\t"+"昵称\t"+"主人\t"+"健康值\t"+"亲密度\t"+"品种\t");
					sql = "select pid,pname,(select lname from lord where lord.lid = pet.lid),health,love,strain from pet";
					rs = stm.executeQuery(sql);
					while(rs.next()) {
						System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getInt(4)+"\t"+rs.getInt(5)+"\t"+rs.getString(6));
					}
					break;
			
				case 12:
					System.out.println("谢谢使用！");
					break li;
				default:
					break;
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stm!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
