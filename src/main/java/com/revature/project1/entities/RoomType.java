package com.revature.project1.entities;


/**
 * Defines the type of a room.
 * Rooms can be Monster-type, Event-type, or Item-type
 * 
 * @param	val	the type of the room to be made
 * @author RCP
 *
 */
public enum RoomType {
	MONSTER("monster"), EVENT("event"), ITEM("item");
	
	private String val;
	
	private RoomType(String val){
		this.val = val;
	}
	
	@Override
	public String toString(){
		return val;
	}
}
