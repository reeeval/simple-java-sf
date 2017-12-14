package com.simple.integration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

public class GetToken {
	
	private static final String ENV = "test"; //sandbox: test, prod: login
	private static final String PREFIX_URL = "https://" + ENV + ".salesforce.com/services/oauth2/token?grant_type=password";
	private static final String CLIENT_ID = "3MVG959Nd8JMmavR8RGTUq2JrAoKB7zwmdPEaP8lt2yJdzj4IVGz93slZniMt7aNKjci11gPI4Y30VkFxDmPj";
	private static final String CLIENT_SECRET = "2390182050070612017";
	private static final String USERNAME = "";
	private static final String PASSWORD = "";
	
	public static AccessTokenResponse doGetToken() {
		AccessTokenResponse atr = null;
		try {
			URL url = new URL(PREFIX_URL 
					+ "&client_id=" + CLIENT_ID
					+ "&client_secret=" + CLIENT_SECRET
					+ "&username=" + USERNAME
					+ "&password=" + PASSWORD);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String output;
			StringBuilder sb = new StringBuilder();
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			String outputResult = sb.toString();
			Gson gson = new Gson();
			atr = gson.fromJson(outputResult, AccessTokenResponse.class);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return atr;
	}
}
