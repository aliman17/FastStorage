package main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import data.DataLoader;
import data.Entity;
import database.DatabaseHelper;

public class Application {
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		
		// Load data.
		String fileName = "./data.txt";
		ArrayList<Entity> entities = DataLoader.load(fileName);	
		
		// Sort data. 
		entities.sort(null);
		
		// Write to database.
		DatabaseHelper.initDatabase();
		DatabaseHelper.store(entities);
	}
}
