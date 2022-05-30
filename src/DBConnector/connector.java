package DBConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class connector {

	static String url = "jdbc:postgresql://localhost:5432/rezerve";
	static Connection Conn = null;

	public static void connect() {
		try {
			Conn = DriverManager.getConnection(url, "postgres", "1234");
			System.out.println("Ba[lanti Gerceklesti.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ResultSet listele(String sorgu) {
		try {
			Statement st = Conn.createStatement();
			ResultSet rs = st.executeQuery(sorgu);
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	public static void guncelle (String sorgu) {
		Statement st;
		try {
			st = Conn.createStatement();
			st.executeUpdate(sorgu);
			System.out.println("Guncelleme Gerceklesti !");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Guncelleme hatasi !");
		}
	}
}
