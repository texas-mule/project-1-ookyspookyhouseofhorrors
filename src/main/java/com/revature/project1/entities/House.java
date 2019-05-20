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
		LoadGameResouces lgr = new LoadGameResouces();
		ArrayList<Monster> monsters = lgr.getRandomMonsters(); /*HouseDAO.loadMonsters();*/
		ArrayList<Event> events = lgr.getRandomEvents(); /*HouseDAO.loadEvents();*/
		ArrayList<Item> items = lgr.getRandomItems(); /*HouseDAO.loadItems();*/
		
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
		this.rooms[0][2].visited = true;
		this.rooms[0][2].type = RoomType.ENTRANCE;
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
			// TODO Alter Exception handling
		}
		return rooms[rowCoord][colCoord];
	}
	
	public void addRoom(int rowCoord, int colCoord, Room room){
		rooms[rowCoord][colCoord] = room;
	}
	
	public Room enterRoom(Player player){
        Room currRoom = new Room();
        // 1 is up
        // 2 is right
        // 3 is down
        // 4 is left
        System.out.println("Please choose a direction (1, 2, 3, or 4): ");
        int choice = scanIn.nextInt();
        switch (choice){
        case 1:
            if(player.getRowCoord() < 4){
                currRoom = this.getRoom(player.getRowCoord()+1, player.getColCoord());
                player.setCoord(player.getRowCoord()+1, player.getColCoord());
                break;
            }else{
                currRoom = this.getRoom(player.getRowCoord(), player.getColCoord());
                System.out.println("You cannot proceed in this direction!");
                break;
            }
        case 2:
            if(player.getColCoord() < 4){
                currRoom = this.getRoom(player.getRowCoord(),player.getColCoord()+1);
                player.setCoord(player.getRowCoord(), player.getColCoord()+1);
                break;
            }else{
                currRoom = this.getRoom(player.getRowCoord(), player.getColCoord());
                System.out.println("You cannot proceed in this direction!");
                break;
            }
        case 3:
            if(player.getRowCoord() > 0){
                currRoom = this.getRoom(player.getRowCoord()-1, player.getColCoord());
                player.setCoord(player.getRowCoord()-1, player.getColCoord());
                break;
            }else{
                currRoom = this.getRoom(player.getRowCoord(), player.getColCoord());
                System.out.println("You cannot proceed in this direction!");
                break;
            }
        case 4:
            if(player.getColCoord() > 0){
                currRoom = this.getRoom(player.getRowCoord(), player.getColCoord()-1);
                player.setCoord(player.getRowCoord(), player.getColCoord()-1);
                break;
            }else{
                currRoom = this.getRoom(player.getColCoord(), player.getRowCoord());
                System.out.println("You cannot proceed in this direction!");
                break;
            }
        default:
            System.out.println("Unrecognized input");
            currRoom = new Room();
            break;
        }
        return currRoom;
    }
    
	
	/*
	 *  Displays the entirety of the house with visited rooms marked as X and the current room as O
	 */
	public void displayHouse(Player player){
		for (int row = 4 ; row >= 0 ; row--){
			for (int col = 0; col < 5 ; col++){
				if (row == player.getRowCoord() && col == player.getColCoord()) System.out.print(" [ O ] ");
				else if (rooms[row][col].visited) System.out.print(" [ X ] ");
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
	
	// CREATES AN HTTPRequest FOR GET TO RETRIEVE THE HOUSE DATA FROM A PLAYER 
	public void loadHouse(Player player){
		String url = "/player/"+player.getName()+"/house";
		HttpRequest httpReq = new HttpRequest(url,"GET");
		String response = httpReq.getResponse();
		httpReq.close();
		if (response.equals("null")){
			System.out.println("Could not find house data for user "+player.getName()+"...Creating new house.");
			this.rooms = HouseDAO.loadRooms();
			this.fillRooms();
		} else {
		this.rooms = HouseDAO.loadRooms();
		this.setHouse(response);
		}
	}
	
	public void saveHouse(Player player){
		String url = "/player/"+player.getName()+"/house?house="+this.getHouse();
		HttpRequest httpReq = new HttpRequest(url,"POST");
		httpReq.getResponse();
		httpReq.close();
	}
	
	public String getHouse(){
		StringBuilder outputBuilder = new StringBuilder();
		for (int row = 0 ; row < 5 ; row++){
			for (int col = 0 ; col < 5 ; col++){
				switch (this.rooms[row][col].getType()){
					case MONSTER: outputBuilder.append("0"); break;
					case EVENT: outputBuilder.append("1");	break;
					case ITEM:	outputBuilder.append("2");	break;
					case ENTRANCE:	outputBuilder.append("3");	break;
				}
				outputBuilder.append("$");
				switch (rooms[row][col].getType()){
					case MONSTER: outputBuilder.append(rooms[row][col].getMonster().getId()); break;
					case EVENT:outputBuilder.append(rooms[row][col].getEvent().getId()); break;
					case ITEM:	outputBuilder.append(rooms[row][col].getItem().getId()); break;
					case ENTRANCE:	outputBuilder.append("0");
				}
				outputBuilder.append("$");
				if (rooms[row][col].visited) outputBuilder.append("1");
				else outputBuilder.append("0");
				outputBuilder.append((row==4 && col==4) ? "" : ",");
			}
		}
		return outputBuilder.toString();
	}
	
	// Converts the HOUSEdata String into a set house with filled rooms
	public void setHouse(String data){
		data = data.replaceAll("\"", "");
		String[] dataArr = data.split(",");
		LoadGameResouces lgr = new LoadGameResouces();
		for(int index=0 ; index< 25 ; index++){
			String[] dataValues = dataArr[index].split("\\$");
			//rooms[index/5][index%5] = new Room();
			switch (dataValues[0]){
				case "0":	
					rooms[index/5][index%5].type = RoomType.MONSTER;	
					rooms[index/5][index%5].giveContents(lgr.getMonsterById(Integer.parseInt(dataValues[1])));
					break;
				case "1":	
					rooms[index/5][index%5].type = RoomType.EVENT;	
					rooms[index/5][index%5].giveContents(lgr.getEventById(Integer.parseInt(dataValues[1])));
					break;
				case "2":
					rooms[index/5][index%5].type = RoomType.ITEM;
					rooms[index/5][index%5].giveContents(lgr.getItemById(Integer.parseInt(dataValues[1])));
					break;
				case "3":	
					rooms[index/5][index%5].type = RoomType.ENTRANCE;	
					break;
				default:
					System.out.println("Setting the house has failed.");
					break;
			}		
			if (dataValues[2].equals("1")) {
				rooms[index/5][index%5].visited = true;
			} else rooms[index/5][index%5].visited = false;
		}
		
	}
	
	
	
}
