package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import data.Entity;

public class DatabaseHelper {
	public static void initDatabase() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		String testTable = "CREATE TABLE IF NOT EXISTS TestTable ("
				+ "ID int NOT NULL AUTO_INCREMENT,"
				+ "MatchId TEXT, "
				+ "MarketId TEXT, "
				+ "OutputId TEXT,"
				+ "Specifications TEXT,"
				+ "Timestamp TIMESTAMP(6),"
				+ "PRIMARY KEY (ID));";
		
		Connection conn = DatabaseHandler.getConnection();
		DatabaseHandler.update(conn, testTable);
		DatabaseHandler.closeConnection(conn);
	}
	
	private static ArrayList<String> preparePackets(ArrayList<Entity> entities, int packetSize) {
		
		ArrayList<String> packets = new ArrayList<>();
				
		int counter = 0;
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("INSERT IGNORE INTO TestTable (MatchId, MarketId, OutputId, Specifications) VALUES ");
		
		for (Entity e : entities) {
			counter ++;
			
			String spec = (e.specifications.length() > 0) ? e.specifications : "''";
			if (counter > 1)
				buffer.append(",");
			String input = " (" + e.matchId + ",'" + e.marketId + "'," + e.outputId + "," + spec + ")";
			
			buffer.append(input);
 				
			if (counter >= packetSize) {
				counter = 0;
				
				String sql = buffer.toString();
	 			
				packets.add(sql);
	 			
				buffer = new StringBuffer();
	 			buffer.append("INSERT IGNORE INTO TestTable (MatchId, MarketId, OutputId, Specifications) VALUES ");
			} 
		}
		if (buffer.length() > 0) {
			String sql = buffer.toString();
 			packets.add(sql);
		}
		return packets;
	}
	
	private static ArrayList<String> preparePackets(ArrayList<Entity> entities) {
		
		ArrayList<String> packets = new ArrayList<>();
		
		for (Entity e : entities) {
			String spec = (e.specifications.length() > 0) ? e.specifications : "''";
			String input = " (" + e.matchId + ",'" + e.marketId + "'," + e.outputId + "," + spec + ")";
			
			StringBuffer buffer = new StringBuffer();
			buffer.append("INSERT IGNORE INTO TestTable (MatchId, MarketId, OutputId, Specifications) VALUES ");
			buffer.append(input);
			
			String sql = buffer.toString();
	 		packets.add(sql);
		}
		return packets;
	}
	
	public static void store(ArrayList<Entity> entities) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		ArrayList<String> packets = preparePackets(entities, 10000);
		
		Connection	conn = DatabaseHandler.getConnection();
		
		System.out.println("Number of entities to be inserted: " + entities.size());
		
		for (String packet : packets) {
			try {
				int count = DatabaseHandler.update(conn, packet);
				System.out.println("Inserted: " + count);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		DatabaseHandler.closeConnection(conn);
	}
}
