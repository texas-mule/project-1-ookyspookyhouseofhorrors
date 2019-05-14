package com.revature.project1.entities;

import java.util.Scanner;

public class TurnSequence {
	Scanner scanIn = new Scanner(System.in);
	Room room;
	Player player;
	
	TurnSequence(Room room, Player player){
		this.room = room;
		this.player = player;
	}
	
	public void initiateCombat(){
		System.out.println("Initiating combat...");
		Monster monster = this.room.getMonster();
		System.out.println(monster.getDescription());
		
	}
	
	public void triggerEvent(){
		System.out.println("Triggering event...");
		
	}
	
	public void getItem(){
		System.out.println("Obtaining item...");
	}
}
