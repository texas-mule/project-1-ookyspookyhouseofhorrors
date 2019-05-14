package com.revature.project1.entities;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

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
	
	public Player(String name, int hp, int mightStat, int sanityStat) {
		this.name = name;
		this.hp = hp;
		this.mightStat = mightStat;
		this.sanityStat = sanityStat;
		this.rowCoord = 0;
		this.colCoord = 2;
	}

	public int getHP(){
		return hp;
	}
	
	public void setCoord(int col, int row){
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
		this.alterMight(item.getMightBonus());
		this.alterSanity(item.getSanityBonus());
	}
	
	public String displayStats(){
		return "Name: "+name+", HP: "+hp+", Might: "+mightStat+", Sanity: "+sanityStat +", Coords:("
				+colCoord+","+rowCoord+")";
	}
}
