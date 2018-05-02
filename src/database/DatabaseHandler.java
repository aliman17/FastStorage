package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class DatabaseHandler {
	
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	static final String DB_URL =	"jdbc:mysql://localhost/AcademyDB?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	
	//  Database credentials
	static final String USER = "ales";
	static final String PASS = "1234";
	
	public static Connection getConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName(JDBC_DRIVER).newInstance();
		Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
		return conn;
	}
	
	public static void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}
	
	public static ArrayList<HashMap<String, String>> query(Connection conn, String query) throws SQLException {
		
		ArrayList<HashMap<String, String>> results = new ArrayList<>();
        
		PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        
        while (rs.next()) {
            HashMap<String,String> eachResult = new HashMap<String,String>();
        
            for (int i = 1; i <= columnsNumber; i++) { 
            		String label = rsmd.getColumnLabel(i);
            		String val = rs.getString(i);
                eachResult.put(label, val);  
            }
            results.add(eachResult);
        }
        rs.close();
        stmt.close();
        
        return results;
	}
	
	public static int update(Connection conn, String update) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(update);
        int count = stmt.executeUpdate();
        stmt.close();
        return count;
	}
}
