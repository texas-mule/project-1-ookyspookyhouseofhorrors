package com.revature.project1.entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class HouseDAO {
	private static HouseDAO houseDAO;
	public static Connection connection;
	private static String sql;
	
	private HouseDAO(Connection connection) {
		this.connection = connection;
	}
	
	public HouseDAO getInstance() {
		return houseDAO;
	}
	
	public static HouseDAO establish(Connection connection) {
		if (houseDAO == null) houseDAO = new HouseDAO(connection);
		return houseDAO;
	}	
	
	public static House loadRooms(){
		House house = new House();
		sql = "SELECT * FROM rooms ORDER BY RANDOM()";
		
		try {
			Statement statement = connection.createStatement();
			statement.execute(sql);
			ResultSet rs = statement.getResultSet();
			rs.next();
			for (int row=0; row<5; row++){
				for (int col=0; col<5; col++){
					if (row==0 && col==2) {
						house.addRoom(row, col, new Room("Entrance Foyer","description"));
						col++;
					}
					house.addRoom(row, col, new Room(rs.getString("name"),
													rs.getString("description")));
					rs.next();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return house;
	}
	
	public static ArrayList<Monster> loadMonsters(){
		ArrayList<Monster> monsters = new ArrayList<Monster>();
		sql = "SELECT * FROM monsters ORDER BY RANDOM() LIMIT 7";
		
		try (Statement statement = connection.createStatement()){
			statement.execute(sql);
			ResultSet rs = statement.getResultSet();
			rs.next();
			for (int row = 0; row < 7; row++){
				monsters.add(new Monster(
						Integer.parseInt(rs.getString("id")),
						rs.getString("name"),
						rs.getString("description"),
						Integer.parseInt(rs.getString("hp")),
						Integer.parseInt(rs.getString("strength"))));
				rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return monsters;
	}
	
	public static ArrayList<Item> loadItems(){
		ArrayList<Item> items = new ArrayList<Item>();
		sql = "SELECT * FROM items ORDER BY RANDOM() LIMIT 11";
		
		try (Statement statement = connection.createStatement()){
			statement.execute(sql);
			ResultSet rs = statement.getResultSet();
			rs.next();
			for (int row = 0; row < 11; row++){
				items.add(new Item(
						Integer.parseInt(rs.getString("id")),
						rs.getString("name"),
						rs.getString("description"),
						Integer.parseInt(rs.getString("mightBonus")),
						Integer.parseInt(rs.getString("sanityBonus"))));
				rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return items;
	}
	
	public static ArrayList<Event> loadEvents(){
		ArrayList<Event> events = new ArrayList<Event>();
		sql = "SELECT * FROM events ORDER BY RANDOM() LIMIT 7";
		
		try (Statement statement = connection.createStatement()){
			statement.execute(sql);
			ResultSet rs = statement.getResultSet();
			rs.next();
			for (int row = 0; row < 7; row++){
				events.add(new Event(
						Integer.parseInt(rs.getString("id")),
						rs.getString("name"),
						rs.getString("description"),
						Integer.parseInt(rs.getString("sanityRoll"))));
				rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return events;
	}
}
