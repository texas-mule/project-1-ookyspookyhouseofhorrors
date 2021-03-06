package com.revature.project1.entities;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		String userInput = null;
		
		Scanner scanIn = new Scanner(System.in);
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("src/main/resources/connection.properties"));
		} catch (IOException e){
			e.printStackTrace();
		}
		
		try (Connection connection = DriverManager.getConnection(prop.getProperty("url"),
				prop.getProperty("username"),prop.getProperty("password"))){
			Player player;
			
			System.out.println("Would you like to view the [l]eaderboards or [p]lay the game?: ");
			do {userInput = scanIn.nextLine();}
			while(!userInput.equalsIgnoreCase("l") && !userInput.equalsIgnoreCase("p"));
			
            //Case L
            if (userInput.equalsIgnoreCase("l")){
                Player.displayLeaderboard();
                System.out.println("Press enter to proceed to the game!");
                userInput = scanIn.nextLine();
            }
            
            
            System.out.print("Please enter your existing name to resume a run or enter a new name: ");
            do { userInput = scanIn.nextLine();
            if(!userInput.matches("^[A-Za-z]{2,}$")){
                System.out.println("Name can only contain letters. Please enter a new name: ");
            }
            if (userInput.matches("\\s*")){
            	System.out.print("User not found. Please enter a valid username: ");
            }
            player = Player.getPlayer(userInput);
            if (player.getHP() <= 0){
            	System.out.println(player.getName() +" has been defeated. You are unable to continue playing as that user");
            	System.out.print("Please enter a different username: ");
            }} while (player == null || player.getHP() <= 0);
            
            
            
            System.out.println(player.loginString());
            
			// Create a DB connection ;; Assign room names randomly ;; Fill the rooms with event/monster/item
			HouseDAO.establish(connection);
			
			// Fill house data, 
			//		either by loading from player 
			//		or by creating a new house
			House house = new House();
			house.loadHouse(player);
			
			house.displayHouse(player);
			System.out.println("You are at "+player.getColCoord()+","+player.getRowCoord());
			
			Room currRoom;
			// Game rotation
			while (true){
				house.rooms[0][0].displayUponEntering();
				currRoom = house.enterRoom(player);
				currRoom.displayUponEntering();
				if (!currRoom.visited){
					currRoom.loadContents(player);
					player.incrementRoomCount();
				}
				
				System.out.println("Updating player "+player.displayStats());
				player.updatePlayer();
				house.saveHouse(player);
				
				house.displayHouse(player);

				userInput = scanIn.nextLine();
				if (userInput.equals("exit")) break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
