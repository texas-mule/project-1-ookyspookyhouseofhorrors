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

			System.out.println("Would you like to view the [l]eaderboards or Play the game?: ");
			userInput = scanIn.nextLine();
			
			if (userInput.equalsIgnoreCase("l"))
				Player.displayLeaderboard();
			userInput = scanIn.nextLine();
			System.out.print("Please enter your existing name to resume a run or enter a new name: ");
			Player player = Player.getPlayer("rob");
			System.out.println(player.loginString());
			
			HouseDAO.establish(connection);
			House house = HouseDAO.loadRooms();
			Room currRoom;
			
			house.fillRooms();
			
			// TODO DELETE
			house.displayAllRoomInfo();
			
			house.displayHouse();
			System.out.println("You are at "+player.getColCoord()+","+player.getRowCoord());
			
			// Game rotation
			while (true){
				currRoom = house.enterRoom(player);
				currRoom.displayUponEntering();
				if (!currRoom.visited){
					currRoom.loadContents(player);
					player.incrementRoomCount();
				}
				
				System.out.println("Updating player "+player.displayStats());
				player.updatePlayer();
				
				house.displayHouse();
				userInput = scanIn.nextLine();
				if (userInput.equals("exit")) break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
