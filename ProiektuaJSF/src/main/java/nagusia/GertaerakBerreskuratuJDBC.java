package nagusia;

import java.sql.DriverManager;
import java.sql.ResultSet;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import domainua.Erabiltzailea;

public class GertaerakBerreskuratuJDBC {

	public static String getErabiltzaileaJDBC(Erabiltzailea e) {
		Connection c;
		PreparedStatement s;
		ResultSet rs;
		try {
		Class.forName("com.mysql.jdbc.Driver"); // Edo agian... com.mysql.cj.jdbc.Driver
		c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/gertaerak", "root", "admin");
		s = (PreparedStatement) c.prepareStatement("SELECT * FROM ERABILTZAILEA WHERE izena=?");
		s.setString(1,e.getIzena());
		rs = s.executeQuery();
		if (rs.next())
		 return (String)(rs.getString("IZENA")+"/"+rs.getString("PASAHITZA")+"/"+rs.getString("MOTA"));
		} catch (Exception ex) {ex.printStackTrace();}
		return "--/--/--"; }
}
