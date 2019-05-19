package com.revature.project1.entities;

public class Item {
	private int id;
	private String name;
	private String description;
	private int mightBonus;
	private int sanityBonus;
	
	Item(int id, String name, String description, int mightBonus, int sanityBonus){
		this.id = id;
		this.name = name;
		this.description = description;
		this.mightBonus = mightBonus;
		this.sanityBonus = sanityBonus;
	}
	
	public String getId(){
		return Integer.toString(this.id);
	}
	
	public int getIdInt(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getMightBonus() {
		return mightBonus;
	}

	public int getSanityBonus() {
		return sanityBonus;
	}

	public void getItem(Player player){
		System.out.println("You obtained "+this.name+"! "+this.description);
		System.out.println("This item provides "+
								(mightBonus>=0?"+":"")+mightBonus+" Might and "+
								(sanityBonus>=0?"+":"")+sanityBonus+" Sanity");
		player.addItem(this);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
