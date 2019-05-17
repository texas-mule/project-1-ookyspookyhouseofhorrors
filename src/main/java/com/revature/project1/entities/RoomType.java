package com.revature.project1.entities;


/**
 * Defines the type of a room.
 * Rooms can be Monster-type, Event-type, Item-type, or marked as the Entrance
 * 
 * @param	val	the type of the room to be made
 * @author RCP
 *
 */
public enum RoomType {
	MONSTER("monster"), EVENT("event"), ITEM("item"), ENTRANCE("entrance");
	
	private String val;
	
	private RoomType(String val){
		this.val = val;
	}
	
	@Override
	public String toString(){
		return val;
	}
}
