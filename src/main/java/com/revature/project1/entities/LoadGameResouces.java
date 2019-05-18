package com.revature.project1.entities;

import java.util.ArrayList;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

public class LoadGameResouces {
	
	String randomRooms = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\"><Body><getRandomRoomArray xmlns=\"http://webservice.myapp/\"/></Body></Envelope>";
	
	
	public ArrayList<Room> getRandomRooms() throws SOAPException{
		SOAPConnectionFactory factory = SOAPConnectionFactory.newInstance();
		SOAPConnection connection = factory.createConnection();
		String request = this.randomRooms;
		String endpoint = "http://localhost:8082/ArrayGameResouces";
		//SOAPMessage response = connection.call(request, endpoint);
		System.out.println("RESPONSE FROM SOAP REQ: ");
		ArrayList<Room> roomArray = new ArrayList<Room>();
		return roomArray;
	}
}
