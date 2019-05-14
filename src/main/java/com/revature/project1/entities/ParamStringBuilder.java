package com.revature.project1.entities;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class ParamStringBuilder {
	public static String getParamString(Map<String, String> params) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		
		for (Map.Entry<String, String> entry : params.entrySet()){
			sb.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
			sb.append("=");
			sb.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
			sb.append("&");
		}
		
		String output = sb.toString();
		return output.length() > 0
		          ? output.substring(0, output.length() - 1)
		          : output;
	}
}
