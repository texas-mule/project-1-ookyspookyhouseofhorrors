package com.revature.project1.entities;

import java.io.BufferedReader;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.json.*;

public class HttpRequest{
	URL url;
	HttpURLConnection connection;
	
	
	public HttpRequest(String URL, String conMethod){
		try{
			url = new URL("http://localhost:8081"+URL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(30000);
			connection.setRequestMethod(conMethod);
			connection.setRequestProperty("Content-Type", "application/json");
		} catch (IOException e) {
			e.printStackTrace(); 
		}
	}
	
	public void addParameters(Map<String,String> parameters){
		try {
			connection.setDoOutput(true);
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(ParamStringBuilder.getParamString(parameters));
			out.flush();
			out.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public void setHeaderJson(){
		connection.setRequestProperty("Content-Type", "application/json");
	}
	
	public String getHeaderField(){
		return connection.getHeaderField("Content-Type");
	}
	
	public String getResponse(){
		StringBuffer content = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			content = new StringBuffer();
			while ((inputLine = br.readLine()) != null){
				content.append(inputLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content.toString();
	}
	
	public JSONArray getJSONResponse(){
		JSONArray jArray = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			while ((inputLine = br.readLine()) != null){
				jArray = new JSONArray(inputLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jArray;
	}
	
	public void close(){
		connection.disconnect();
	}
	
	public int getStatus(){
		try {
			return connection.getResponseCode();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
}
