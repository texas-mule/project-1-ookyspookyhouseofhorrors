package com.revature.project1.entities;

public class Event {
	private int id, sanityRoll, will, totalWill;
	private String name, description;
	
	
	Event(int id, String name, String description, int sanityRoll){
		this.id = id;
		this.name = name;
		this.description = description;
		this.sanityRoll = sanityRoll;
	}
	
	public String getId(){
		return Integer.toString(this.id);
	}
	
	public String getName(){
		return this.name;
	}
	
	public void triggerEvent(Player player){
		System.out.println(this.name+"--"+this.description);
		System.out.println("The image attacks your sanity. Harden yourself!");
		testSanity(player);
	}
	
	public Boolean testSanity(Player player){
		if(fight(player)){
			victory();
			return true;
		}
		else {
			player.setHP(player.getHP()/2);
			defeat();
			return false;
		}
	}
	
	public Boolean fight(Player player){
		for (int i = player.getSanityStat() ; i > 0 ; i--){
			totalWill += player.will();
			System.out.print("("+(i-1)+" attempts remaining) ");
		}
		System.out.println("Inspired "+totalWill+" total sanity");
		if (totalWill > this.sanityRoll)
			return true;
		else 
			return false;
	}
	
	public void victory(){
		System.out.println("You have successfully defended yourself from "+this.name);
	}
	
	public void defeat(){
		System.out.println();
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("You have been defeated by "+this.name+". You have lost half of your current HP.");
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println();
	}

	public int getSanityRoll() {
		return sanityRoll;
	}

	public void setSanityRoll(int sanityRoll) {
		this.sanityRoll = sanityRoll;
	}

	public int getWill() {
		return will;
	}

	public void setWill(int will) {
		this.will = will;
	}

	public int getTotalWill() {
		return totalWill;
	}

	public void setTotalWill(int totalWill) {
		this.totalWill = totalWill;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
