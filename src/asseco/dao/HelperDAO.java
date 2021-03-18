package asseco.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class HelperDAO {
	
	public static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	
	
	public static String getPassword() {
		String rez = "";
		Connection con  = null;		
		ResourceBundle bundle = PropertyResourceBundle.getBundle("asseco.dao.Values");		
		 
		try {
			String upit = bundle.getString("sql_pass");
			upit = upit.replace("[semaBaze]", bundle.getString("semaBaze"));
			con = connectionPool.checkOut();
			PreparedStatement ps = con.prepareStatement(upit);
			ps.setObject(1, bundle.getString("adminUsername"));
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				rez = rs.getString(1);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			connectionPool.checkIn(con);
		}
		return rez;
	}
	
	
	public static String getScenarionId(String id) {
		String rez = "";
		Connection con  = null;		
		ResourceBundle bundle = PropertyResourceBundle.getBundle("asseco.dao.Values");		
		 
		try {
			String upit = bundle.getString("sql_updateNotificationMessages");
			upit = upit.replace("[semaBaze]", bundle.getString("semaBaze"));
			con = connectionPool.checkOut();
			PreparedStatement ps = con.prepareStatement(upit);
			ps.setObject(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				rez = rs.getString(1);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			connectionPool.checkIn(con);
		}
		return rez;
	}
	
	public static String getScenarioTypeId() {
		String rez = "";
		Connection con  = null;		
		ResourceBundle bundle = PropertyResourceBundle.getBundle("asseco.dao.Values");		
		 
		try {
			String upit = bundle.getString("sql_notificationScenarioTypeid");
			upit = upit.replace("[semaBaze]", bundle.getString("semaBaze"));
			con = connectionPool.checkOut();
			PreparedStatement ps = con.prepareStatement(upit);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				rez = rs.getString(1);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			connectionPool.checkIn(con);
		}
		return rez;
	}
	
	
	public static String getUserId(String user) {
		String rez = "";
		Connection con  = null;		
		ResourceBundle bundle = PropertyResourceBundle.getBundle("asseco.dao.Values");		
		 
		try {
			String upit = bundle.getString("sql_userId");
			upit = upit.replace("[semaBaze]", bundle.getString("semaBaze"));
			con = connectionPool.checkOut();
			PreparedStatement ps = con.prepareStatement(upit);
			ps.setObject(1, user);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				rez = rs.getString(1);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			connectionPool.checkIn(con);
		}
		return rez;
	}
	
	public static ArrayList<String> getUredjaji(String user) {
		ArrayList<String> rez = new  ArrayList<String>();
		Connection con  = null;		
		ResourceBundle bundle = PropertyResourceBundle.getBundle("asseco.dao.Values");
		String userId = getUserId(user);
		 
		try {
			String upit = bundle.getString("sql_getUredjaji");
			upit = upit.replace("[semaBaze]", bundle.getString("semaBaze"));
			con = connectionPool.checkOut();
			PreparedStatement ps = con.prepareStatement(upit);
			ps.setObject(1, userId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				rez.add( String.valueOf(rs.getInt(1)));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			connectionPool.checkIn(con);
		}
		return rez;
	}
	
	public static String getServiceId(String service) {
		String rez = "";
		Connection con  = null;		
		ResourceBundle bundle = PropertyResourceBundle.getBundle("asseco.dao.Values");		
		 
		try {
			String upit = bundle.getString("sql_getServiceId");
			upit = upit.replace("[semaBaze]", bundle.getString("semaBaze"));
			con = connectionPool.checkOut();
			PreparedStatement ps = con.prepareStatement(upit);
			ps.setObject(1,service);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				rez = (rs.getString(1));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		finally {
			connectionPool.checkIn(con);
		}
		return rez;
	}
	
	
	

}
