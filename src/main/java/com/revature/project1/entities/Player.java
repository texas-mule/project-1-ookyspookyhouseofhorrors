package com.revature.project1.entities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class Player {
	private String name;
	private int hp;
	private int mightStat;
	private int sanityStat;
	Scanner scanIn = new Scanner(System.in);
	private Random random = new Random();
	private String userInput;
	private int attack;
	private int rowCoord;
	private int colCoord;
	ArrayList<Item> inventory = new ArrayList<>();
	private int roomCount;
	
	
	public Player(String data){
		
		// Parse HTTPresponse data into Person object
		data = data.substring(2,data.length()-2);
		String[] dataArr = data.split(",");
		for (int i=0 ; i< dataArr.length; i++){
			String[] pair = dataArr[i].trim().split("=");
			if (pair[0].equals("name")) this.name=pair[1];
			else if(pair[0].equals("hp")) this.hp=Integer.parseInt(pair[1]);
			else if(pair[0].equals("might")) this.mightStat=Integer.parseInt(pair[1]);
			else if(pair[0].equals("sanity")) this.sanityStat=Integer.parseInt(pair[1]);
		}
		this.rowCoord = 0;
		this.colCoord = 2;
		this.roomCount = 1;
	}
	
	
	public Player(String name, int hp, int mightStat, int sanityStat) {
		this.name = name;
		this.hp = hp;
		this.mightStat = mightStat;
		this.sanityStat = sanityStat;
		this.rowCoord = 0;
		this.colCoord = 2;
		this.roomCount = 1;
	}

	public String getName(){
		return this.name;
	}
	
	public int getHP(){
		return hp;
	}
	
	public void setCoord(int row, int col){
		this.rowCoord = row;
		this.colCoord = col;
	}

	public void setHP(int hp) {
		this.hp = hp;
	}

	public int getMightStat() {
		return mightStat;
	}

	public void setMightStat(int mightStat) {
		this.mightStat = mightStat;
	}

	public int getSanityStat() {
		return sanityStat;
	}

	public void setSanityStat(int sanityStat) {
		this.sanityStat = sanityStat;
	}

	public void alterMight(int alterAmount){
		this.mightStat += alterAmount;
	}
	
	public void alterSanity(int alterAmount){
		this.sanityStat += alterAmount;
	}
	
	public void alterHP(int alterAmount){
		this.hp += alterAmount;
	}
	
	public int getRowCoord() {
		return rowCoord;
	}

	public void setRowCoord(int rowCoord) {
		this.rowCoord = rowCoord;
	}

	public int getColCoord() {
		return colCoord;
	}

	public void setColCoord(int colCoord) {
		this.colCoord = colCoord;
	}
	
	public void incrementRoomCount(){
		this.roomCount++;
	}
	
	
	// CREATES AN HTTPRequest FOR POST TO SAVE A NEW PLAYER
	public void savePlayer(){
		String url = "/player?name="+name+"&hp="+hp+"&might="+mightStat+"&sanity="+sanityStat;
		HttpRequest httpReq = new HttpRequest(url,"POST");
		httpReq.getResponse();
		httpReq.close();
	}
	
	// CREATES AN HTTPRequest FOR GET TO RETRIEVE A PLAYER
	public static Player getPlayer(String name){
		String url = "/player/"+name;
		HttpRequest httpReq = new HttpRequest(url,"GET");
		String response = httpReq.getResponse();
		httpReq.close();
		return new Player(response);
	}
	
	// CREATES AN HTTPRequest FOR PUT FOR UPDATING A PLAYER
	public void updatePlayer(){
		int rooms = roomCount;
		String url = "/player?name="+name
					+"&hp="+hp
					+"&might="+mightStat
					+"&sanity="+sanityStat
					+"&rooms="+rooms;
		HttpRequest httpReq = new HttpRequest(url,"PUT");
		httpReq.getResponse();
		httpReq.close();
	}
	
	// CREATES AN HTTPRequest FOR TYING USERS AND ITEMS TOGETHER
	public void updatePlayerInventory(int itemID){
		String url = "/playerNewItem/"+name+"/"+itemID;
		HttpRequest httpReq = new HttpRequest(url,"PUT");
		httpReq.getResponse();
		httpReq.close();
	}
	
	// Returns the top 10 players from the database ordered by number of rooms discovered
	public static JSONArray getLeaderboards(){
		String url = "/player/leaderboard";
		HttpRequest httpReq = new HttpRequest(url,"GET");
		JSONArray leaderArray = httpReq.getJSONResponse();
		httpReq.close();
		return leaderArray;
	}
	
	public static void displayLeaderboard(){
		JSONArray leaderboards = getLeaderboards();
		
		System.out.println("++++++++++++++++++++");
		System.out.println("+Rooms\t|Name\t   +");
		System.out.println("+-------+----------+");
		for (int i=0 ; i < leaderboards.length() ; i++){
			JSONObject o = leaderboards.getJSONObject(i);
			System.out.println(o.get("rooms")+"\t|"+o.get("name"));
		}
		System.out.println("++++++++++++++++++++");
	}
	
	
	
	
	
	

	/**
	 * Represents an attack made by player against monster. Generates a random damage amount (1-6) 
	 * per "swing" where number of swings is equal to the player's Might stat.
	 */
	public int attack(){
		System.out.print("Press Enter to attack");
		userInput = scanIn.nextLine();
		attack = random.nextInt(6)+1;
		System.out.println("Dealt "+attack+" damage!");
		return attack;
	}
	
	public int will(){
		int will = 0;
		System.out.print("Press Enter to defend");
		userInput = scanIn.nextLine();
		will = random.nextInt(6)+1;
		System.out.println("Inspired "+will+" defense!");
		return will;
	}
	
	public void addItem(Item item){
		inventory.add(item);
		this.updatePlayerInventory(item.getIdInt());
		this.alterMight(item.getMightBonus());
		this.alterSanity(item.getSanityBonus());
	}
	
	public String loginString(){
		return "++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n"
				+ "Welcome "+name+". Stats HP:"+hp+", Might:"+mightStat+", Sanity:"+sanityStat+"\n"
				+ "++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n"; 
	}
	
	public String displayStats(){
		return "Name: "+name+", HP: "+hp+", Might: "+mightStat+", Sanity: "+sanityStat +", Rooms Visited: "
				+roomCount+", Coords:("+colCoord+","+rowCoord+")";
	}
}
