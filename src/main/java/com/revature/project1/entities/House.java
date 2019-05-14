package com.revature.project1.entities;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 * A 2-dimension representation of rooms to structure the house in the game.
 * @author RCP
 *
 */
public class House {
	Scanner scanIn = new Scanner(System.in);
	public Room[][] rooms = new Room[5][5];
	
	public void fillRooms(){
		Random rand = new Random();
		ArrayList<Monster> monsters = HouseDAO.loadMonsters();
		ArrayList<Event> events = HouseDAO.loadEvents();
		ArrayList<Item> items = HouseDAO.loadItems();
		
		// Create an ArrayList of shuffled room types to be randomly assigned to the rooms
		ArrayList<RoomType> types = new ArrayList<>();
		RoomType type = RoomType.MONSTER;
		for (int i=0;i<24;i++){
			if (i==7) type = RoomType.EVENT;
			else if(i==14) type = RoomType.ITEM;
			types.add(type);
		}
		Collections.shuffle(types);
		
		for (int row = 0; row < 5; row++){
			for (int col = 0; col < 5; col++){
				// Adjust for the Entrance Hall located at (0,2)
				if (row == 0 && col == 2) col++;
				if (row > 0 || (row == 0  && col > 2)){
					rooms[row][col].type = types.get(5*row+col-1);
				} else rooms[row][col].type = types.get(col);
				
				if (rooms[row][col].type==RoomType.MONSTER){
					Monster currMonster = monsters.remove(rand.nextInt(monsters.size()));
					rooms[row][col].giveContents(currMonster);
				}
				else if (rooms[row][col].type==RoomType.EVENT){
					Event currEvent = events.remove(rand.nextInt(events.size()));
					rooms[row][col].giveContents(currEvent);
				}
				else if (rooms[row][col].type==RoomType.ITEM){
					Item currItem = items.remove(rand.nextInt(items.size()));
					rooms[row][col].giveContents(currItem);
				}
			}
		}
		this.rooms[2][0].visited = true;
	}
	
	public void clearRooms(){
		this.rooms = new Room[5][5];
	}
	
	public void replaceRoom(Room newRoom, int row, int col){
		this.rooms[col][row] = newRoom;
	}

	public Room getRoom(int rowCoord, int colCoord){
		if (colCoord < 0 || rowCoord < 0 || colCoord > 4 || rowCoord > 4){
			System.out.println("Invalid room choice");
			return null;
		}
		return rooms[rowCoord][colCoord];
	}
	
	public void addRoom(int rowCoord, int colCoord, Room room){
		rooms[rowCoord][colCoord] = room;
	}
	
	public Room enterRoom(Player player){
		Room currRoom;
		// 1 is up
		// 2 is right
		// 3 is down
		// 4 is left
		System.out.println("Please choose a direction (1, 2, 3, or 4): ");
		int choice = scanIn.nextInt();
		
		switch (choice){
			case 1:
				currRoom = this.getRoom(player.getColCoord(), player.getRowCoord()+1);
				player.setCoord(player.getColCoord(), player.getRowCoord()+1);
				break;
			case 2:
				currRoom = this.getRoom(player.getColCoord()+1,player.getRowCoord());
				player.setCoord(player.getColCoord()+1, player.getRowCoord());
				break;
			case 3:
				currRoom = this.getRoom(player.getColCoord(), player.getRowCoord()-1);
				player.setCoord(player.getColCoord(), player.getRowCoord()-1);
				break;
			case 4:
				currRoom = this.getRoom(player.getColCoord()-1, player.getRowCoord());
				player.setCoord(player.getColCoord()-1, player.getRowCoord());
				break;
			default:
				System.out.println("Unrecognized input");
				currRoom = new Room();
				break;
		}
		return currRoom;
	}
	
	public void displayHouse(){
		for (int row = 4 ; row >= 0 ; row--){
			for (int col = 0; col < 5 ; col++){
				if (rooms[col][row].visited || (row==0 && col == 2)) System.out.print(" [ X ] ");
				else System.out.print(" [   ] ");
			}
			System.out.println();
		}
	}
	
	// TODO DELETE
	// ONLY FOR TESTING PURPOSES
	public void displayAllRoomInfo(){
		for (int i = 0 ; i < 5 ; i++){
			for (int j = 0 ; j < 5 ; j++){
				if (rooms[i][j].getMonster()!=null){
					System.out.println("Room at ("+i+","+j+") contains "+rooms[i][j].getMonster().getName());
				}
				else if (rooms[i][j].getItem() != null){
					System.out.println("Room at ("+i+","+j+") contains "+rooms[i][j].getItem().getName());
				}
				else if (rooms[i][j].getEvent() != null){
					System.out.println("Room at ("+i+","+j+") contains "+rooms[i][j].getEvent().getName());
				}
			}
		}
	}
	
	
	
}
