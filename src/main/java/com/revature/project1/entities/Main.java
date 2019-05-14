package com.revature.project1.entities;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		//HTTPCALL GET RESOURCES ("LOCALHOST:8081)
		String userInput;
		
		Scanner scanIn = new Scanner(System.in);
		
		
		
		
		
		
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://isilo.db.elephantsql.com:5432/hsxmymzc",
				"hsxmymzc","MA2IOW7yUVohDVKl0haDxrU8luBqEt0E")){

			Map<String, String> parameters = new HashMap<>();
			parameters.put("name","newplayer");
//			parameters.put("hp", "25");
//			parameters.put("might", "5");
//			parameters.put("sanity", "5");
			
			HttpRequest httpReq = new HttpRequest("/player/newplayer","POST");
			httpReq.setHeaderJson();
			httpReq.addParameters(parameters);
			System.out.println(httpReq.getResponse());
			httpReq.close();
			
			
		// OLD CONNECTION TODO DELETE 
//		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://192.168.99.100:32769/postgres",
//				"postgres","postgres")){
			HouseDAO.establish(connection);
			House house = HouseDAO.loadRooms();
			Room currRoom;
			
			house.fillRooms();
			
			// TODO DELETE
			house.displayAllRoomInfo();
			
			System.out.println("Welcome, please enter your name: ");
			String name = scanIn.nextLine();
			
	
			// New player creation
			System.out.println("Creating player with name: "+name+", hp = 25, might = 5, sanity = 5");
			Player player = new Player(name, 25, 5, 5);
	
			house.displayHouse();
			System.out.println("You are at "+player.getColCoord()+","+player.getRowCoord());
			
			// Game rotation
			while (true){
				currRoom = house.enterRoom(player);
				currRoom.displayUponEntering();
				if (!currRoom.visited)
					currRoom.loadContents(player);
				System.out.println(player.displayStats());
				house.displayHouse();
				userInput = scanIn.nextLine();
				if (userInput.equals("exit")) break;
			}
		} catch (SQLException | MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
