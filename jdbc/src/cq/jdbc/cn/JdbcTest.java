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
//			while(rs.next()) {//����������
//				System.out.println(rs.getString(2)+"  "+rs.getString(3));
//			}
			String lname;
			String pw;
			String sql;
			int tiaoshu;
			ResultSet rs;
			li:while(true) {
				System.out.println("��ӭ��������ϵͳ");
				System.out.println("1.����û�");
				System.out.println("2.�޸��û�����");
				System.out.println("3.ɾ���û�");
				System.out.println("4.�鿴�����û�");
				System.out.println("5.��¼");
				System.out.println("6.�鿴������Ϣ");
				System.out.println("7.��ѯȫ��������Ϣ");
				System.out.println("8.���ӳ���");
				System.out.println("12.�˳�");
				int choose = sc.nextInt();
				li2:switch (choose) {
				case 1:
					System.out.println("�������û�����");
					lname = sc.next();
					rs = stm.executeQuery("select lname from lord");
					while(rs.next()) {
						if(rs.getString(1).equals(lname)) {
							System.out.println("�û��������ظ���");
							break li2;
						}
					}
					System.out.println("���������룺");
					pw = sc.next();
					sql = "insert into lord(lname,pw,money) values('"+lname+"','"+pw+"',500);";
					tiaoshu = stm.executeUpdate(sql);
					if(tiaoshu == 1) {
						System.out.println("����û��ɹ���");
						tiaoshu = 0;
					}
					break;
				case 2:
					if(!isLogIn) {
						System.out.println("���ȵ�¼��");
					}else {
						System.out.println("�����������룺");
						pw = sc.next();
						System.out.println("���ٴ����������룺");
						String rePW = sc.next();
						if(pw.equals(rePW)) {
							sql = "update lord set pw = '"+rePW+"'where lname = '"+userName+"';";
							tiaoshu = stm.executeUpdate(sql);
							if(tiaoshu==1) {
								System.out.println("�����޸ĳɹ���");
							}else {
								System.out.println("ϵͳ��������ϵ����Ա��");
							}
						}else {
							System.out.println("�����ظ�����");
							break;
						}
					}
					break;
				case 3:
					System.out.println("�������û�����");
					lname = sc.next();
					if(lname == userName) {
						System.out.println("����ɾ�����ڵ�¼���û���");
						break;
					}
					sql = "delete from pet where pet.lid = (select lord.lid from lord where lord.lname = '"+lname+"')";
					stm.executeUpdate(sql);
					sql = "delete from lord where lname = '"+lname+"'";
					tiaoshu = stm.executeUpdate(sql);
					if(tiaoshu == 1) {
						System.out.println("ɾ���û��ɹ���");
						tiaoshu = 0;
					}
					break;
				case 4:
					System.out.println("\t������Ϣ�б�");
					System.out.println("���\t"+"����\t"+"���");
					sql = "select lid,lname,money from lord";
					rs = stm.executeQuery(sql);
					while(rs.next()) {
						System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getInt(3));
					}
					break;
				case 5:
					System.out.println("�������û�����");
					lname = sc.next();
					System.out.println("���������룺");
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
						System.out.println("��¼�ɹ���");
					}else {
						System.out.println("�û������������");
					}
					break;
				case 6:
					if(!isLogIn) {
						System.out.println("���ȵ�¼��");
					}else {
						sql ="select lname,money,pname,health,love,strain from (select * FROM lord where lord.lname = '"+userName+"') a,(select * from pet where pet.lid = (select lord.lid from lord where lord.lname = '"+userName+"')) b;";
						rs = stm.executeQuery(sql);
						while(rs.next()) {
							System.out.println(rs.getString(1)+"\t"+rs.getInt(2)+"\t"+rs.getString(3)+"\t"+rs.getInt(4)+"\t"+rs.getInt(5)+"\t"+rs.getString(6));
						}
					}
					break;
				case 7:
					System.out.println("\t\t������Ϣ�б�");
					System.out.println("���\t"+"�ǳ�\t"+"����\t"+"����ֵ\t"+"���ܶ�\t"+"Ʒ��\t");
					sql = "select pid,pname,(select lname from lord where lord.lid = pet.lid),health,love,strain from pet";
					rs = stm.executeQuery(sql);
					while(rs.next()) {
						System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getInt(4)+"\t"+rs.getInt(5)+"\t"+rs.getString(6));
					}
					break;
			
				case 12:
					System.out.println("ллʹ�ã�");
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
