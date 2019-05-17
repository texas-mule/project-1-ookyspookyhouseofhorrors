package com.revature.project1.entities;

import java.util.Random;
import java.util.Scanner;

public class Monster {
	private int id;
	private String name;
	private String description;
	private int hp;
	private int strength;
	Scanner scanIn = new Scanner(System.in);
	Random random;
	int attack, totalDamage, damageTaken;
	String userInput;
		
	
	Monster(int id, String name, String description, int hp, int strength){
		this.id = id;
		this.name = name;
		this.description = description;
		this.hp = hp;
		this.strength = strength;
	}
	
	public String getId(){
		return Integer.toString(this.id);
	}
	
	public String getName(){
		return this.name;
	}
	public String getDescription(){
		return this.description;
	}
	
	public int getHP(){
		return this.hp;
	}
	
	public void alterHP(int alterAmount){
		this.hp += alterAmount;
	}
	
	public int getStrength(){
		return this.strength;
	}
	
	public Boolean initiateCombat(Player player){
		System.out.println(this.name+"--"+this.description);
		System.out.println("The monster appears before you. Attack it!");
		return combatSequence(player);
	}
	
	public Boolean combatSequence(Player player){
		while (player.getHP() > 0){
			damageTaken = fight(player);
			if (damageTaken == 0){
				victory();
				return true;
			}
			else
				player.alterHP(-damageTaken); 
				System.out.println(this.name+" has gained the advantage! You have lost "+damageTaken+" hp. You have "+player.getHP()+" remaining.");
				System.out.println(this.name+" now has "+this.hp+" hp remaining!");
			
		}
		defeat();
		return false;
	}
	
	/**
	 * Represents the action of fighting the monster. 
	 * 
	 * @return Monster's strength stat
	 */
	public int fight(Player player){
		for (int i = player.getMightStat() ; i > 0 ; i--){
			totalDamage += player.attack();
		}
		System.out.println("Dealt a total of "+totalDamage+" damage");
		if (totalDamage > this.getHP())
			return 0;
		else
			this.alterHP(-totalDamage);
			return this.getStrength();
	}
	
	public void defeat(){
		System.out.println();
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("You have been defeated. Your run is over.");
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.exit(0);
	}
	
	public void victory(){
		System.out.println();
		System.out.println("*********************************************");
		System.out.println("You successfully defeated "+this.getName()+"!");
		System.out.println("*********************************************");
		System.out.println();
	}
}
