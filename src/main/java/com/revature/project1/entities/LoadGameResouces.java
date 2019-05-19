package com.revature.project1.entities;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

public class LoadGameResouces {
	
	String randomRooms = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\"><Body><getRandomRoomArray xmlns=\"http://webservice.myapp/\"/></Body></Envelope>";
	
	
	public ArrayList<Event> getRandomEvents(){
		ArrayList<Event> events= new ArrayList<Event>();
		try {
			URL obj = new URL("http://localhost:8082/ArrayGameResouces");
			HttpURLConnection connect = (HttpURLConnection) obj.openConnection();
			connect.setRequestMethod("POST");
			connect.setRequestProperty("Content-Type","application/soap+xml; charset=utf-8");
			String xml = 
					"<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
							"<Body>" +
							"<getRandomEventArray xmlns=\"http://webservice.myapp/\"/>" +
							"</Body>" +
							"</Envelope>";
			connect.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(connect.getOutputStream());
			wr.writeBytes(xml);
			wr.flush();
			wr.close();
			String responseStatus = connect.getResponseMessage();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connect.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
				String[] res = response.toString().split("<return>|</return>|<description>|</description>|<id>|</id>|<name>|</name>|<sanityRoll>|</sanityRoll>",-1);
				System.out.println("RES:");
				int i = 1;
				ArrayList<String> loadedData = new ArrayList<String>();
				while(i<res.length-1){
					if(!res[i].equals("")){
						loadedData.add(res[i]);
					}
					i++;
				}
				i=0;
				while(i<loadedData.size()-3){
					int id = Integer.valueOf(loadedData.get(i+1));
					int sanityRoll = Integer.valueOf(loadedData.get(i+3));
					Event event = new Event(id, loadedData.get(i+2), loadedData.get(i), sanityRoll);
					events.add(event);
					i = i+4;
				}
				
			}
			in.close();
			return events;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	public ArrayList<Item> getRandomItems(){
		ArrayList<Item> items= new ArrayList<Item>();
		try {
			URL obj = new URL("http://localhost:8082/ArrayGameResouces");
			HttpURLConnection connect = (HttpURLConnection) obj.openConnection();
			connect.setRequestMethod("POST");
			connect.setRequestProperty("Content-Type","application/soap+xml; charset=utf-8");
			String xml = 
					"<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
							"<Body>" +
							"<getRandomItemArray xmlns=\"http://webservice.myapp/\"/>" +
							"</Body>" +
							"</Envelope>";
			connect.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(connect.getOutputStream());
			wr.writeBytes(xml);
			wr.flush();
			wr.close();
			String responseStatus = connect.getResponseMessage();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connect.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
				String[] res = response.toString().split("<return>|</return>|<description>|</description>|<id>|</id>|<name>|</name>|<mightBonus>|</mightBonus>|<sanityBonus>|</sanityBonus>",-1);
				int i = 1;
				ArrayList<String> loadedData = new ArrayList<String>();
				while(i<res.length-1){
					if(!res[i].equals("")){
						loadedData.add(res[i]);
					}
					i++;
				}
				i=0;
				while(i<loadedData.size()-4){
					int id = Integer.valueOf(loadedData.get(i+1));
					int mightBonus = Integer.valueOf(loadedData.get(i+2));
					int sanityBonus = Integer.valueOf(loadedData.get(i+4));
					Item item = new Item(id, loadedData.get(i+3), loadedData.get(i), mightBonus, sanityBonus);
					items.add(item);
					i = i+5;
				}
				
			}
			in.close();
			return items;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	public ArrayList<Monster> getRandomMonsters(){
		ArrayList<Monster> monsters= new ArrayList<Monster>();
		try {
			URL obj = new URL("http://localhost:8082/ArrayGameResouces");
			HttpURLConnection connect = (HttpURLConnection) obj.openConnection();
			connect.setRequestMethod("POST");
			connect.setRequestProperty("Content-Type","application/soap+xml; charset=utf-8");
			String xml = 
					"<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
							"<Body>" +
							"<getRandomMonsterArray xmlns=\"http://webservice.myapp/\"/>" +
							"</Body>" +
							"</Envelope>";
			connect.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(connect.getOutputStream());
			wr.writeBytes(xml);
			wr.flush();
			wr.close();
			String responseStatus = connect.getResponseMessage();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connect.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
				String[] res = response.toString().split("<return>|</return>|<description>|</description>|<id>|</id>|<name>|</name>|<mightRoll>|</mightRoll>|<hp>|</hp>",-1);
				System.out.println("RES:");
				int i = 1;
				ArrayList<String> loadedData = new ArrayList<String>();
				while(i<res.length-1){
					if(!res[i].equals("")){
						loadedData.add(res[i]);
					}
					i++;
				}
				i=0;
				while(i<loadedData.size()-4){
					int id = Integer.valueOf(loadedData.get(i+2));
					int hp = Integer.valueOf(loadedData.get(i+1));
					int mightRoll = Integer.valueOf(loadedData.get(i+3));
					Monster monster = new Monster(id, loadedData.get(i+4), loadedData.get(i), hp, mightRoll);
					monsters.add(monster);
					i = i+5;
				}
				
			}
			in.close();
			return monsters;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	public ArrayList<Room> getRandomRooms(){
		ArrayList<Room> rooms= new ArrayList<Room>();
		try {
			URL obj = new URL("http://localhost:8082/ArrayGameResouces");
			HttpURLConnection connect = (HttpURLConnection) obj.openConnection();
			connect.setRequestMethod("POST");
			connect.setRequestProperty("Content-Type","application/soap+xml; charset=utf-8");
			String xml = 
					"<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
							"<Body>" +
							"<getRandomRoomArray xmlns=\"http://webservice.myapp/\"/>" +
							"</Body>" +
							"</Envelope>";
			connect.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(connect.getOutputStream());
			wr.writeBytes(xml);
			wr.flush();
			wr.close();
			String responseStatus = connect.getResponseMessage();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connect.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
				String[] res = response.toString().split("<return>|</return>|<description>|</description>|<id>|</id>|<name>|</name>",-1);
				System.out.println("RES:");
				int i = 1;
				ArrayList<String> loadedData = new ArrayList<String>();
				while(i<res.length-1){
					if(!res[i].equals("")){
						loadedData.add(res[i]);
					}
					i++;
				}
				i=0;
				while(i<loadedData.size()-2){
					int id = Integer.valueOf(loadedData.get(i+1));
					Room room = new Room(id, loadedData.get(i+2), loadedData.get(i));
					rooms.add(room);
					i = i+3;
				}
				
			}
			in.close();
			return rooms;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	
}
