package com.revature.project1.entities;

import java.util.Random;

/**
 * Room object to be placed in the house. Rooms have a coordinate to be used in the house 
 * object. Rooms possess a <b>name</b> and <b>description</b> in addition to containing 
 * either a monster, event, or item. A <b>visited</b> boolean tracks whether the room 
 * has been visited yet.
 * 
 * @param	name		String,		The name of the room pulled from the database
 * @param	description	String,		A description of the room pulled from the database
 * 
 * @author RCP
 *
 */
public class Room {
	public RoomType type;
	private String name;
	private String description;
	public boolean visited;
	
	private Monster monster;
	private Event event;
	private Item item;
	
	static Random rand = new Random();
	
	public Room(){
		
	}
	
	public Room(String name, String description){
		this.name = name;
		this.description = description;
		this.visited = false;
	}
	
	public void giveContents(Object obj){
		switch(this.type){
			case MONSTER:
				this.monster = (Monster)obj;
				break;
			case EVENT:
				this.event = (Event)obj;
				break;
			case ITEM:
				this.item = (Item)obj;
				break;
			default:
				System.out.println("RoomType not set.");
				break;
		}
	}
	
	public void loadContents(Player player){
		this.visited = true;
		switch(this.type){
			case MONSTER:
				Monster monster = this.getMonster();
				System.out.println("fight won: "+monster.initiateCombat(player));
				break;
			case EVENT:
				Event event = this.getEvent();
				event.triggerEvent(player);
				break;
			case ITEM:
				Item item = this.getItem();
				item.getItem(player);
				break;
			default:
				System.out.println("Could not determine room type");
				break;
		}
	}
	
	public Monster getMonster(){
		return this.monster;
	}
	
	public Event getEvent(){
		return this.event;
	}
	
	public Item getItem(){
		return this.item;
	}
	
	public String displayUponEntering(){
		return "You have entered the "+this.name +". " + this.description;
	}
	
	
}
