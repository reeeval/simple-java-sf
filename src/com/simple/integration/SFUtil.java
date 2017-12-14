package com.simple.integration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.simple.integration.model.Contact;
import com.simple.model.User;

public class SFUtil {
	
	private static final String PREFIX_MID_URL = "/services/data/v41.0/sobjects/";
	private static final String OBJECT_API_NAME = "Contact";
	
	public static InsertResponse doInsertContact(AccessTokenResponse atr, User user) {
		InsertResponse insertResponse = null;
		
		try {
			URL url = new URL(atr.instance_url 
					+ PREFIX_MID_URL
					+ OBJECT_API_NAME);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", atr.token_type + " " + atr.access_token);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Contact contact = new Contact();
			contact.setFirstName(user.getFirstName());
			contact.setLastName(user.getLastName());
			contact.setBirthdate(sdf.format(user.getDob()));
			contact.setEmail(user.getEmail());
			contact.setLeadSource("Web");
			contact.setDescription("Created from outside salesforce");
			contact.setContact_Ext_Id__c(String.valueOf(user.getUserId()));
			
			Gson gson = new Gson();
			String jsonString = gson.toJson(contact);
			System.out.println("jsonString: " + jsonString);
			
			OutputStream os = conn.getOutputStream();
			os.write(jsonString.getBytes());
			os.flush();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String output;
			StringBuilder sb = new StringBuilder();
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			String outputResult = sb.toString();
			conn.disconnect();
			
			insertResponse = gson.fromJson(outputResult, InsertResponse.class);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return insertResponse;
	}
	
	public static InsertResponse doUpsertContact(AccessTokenResponse atr, User user) {
		InsertResponse insertResponse = null;
		String extField = "Contact_Ext_Id__c";
		allowMethods("PATCH");
		
		try {
			URL url = new URL(atr.instance_url
						+ PREFIX_MID_URL
						+ OBJECT_API_NAME
						+ "/" + extField
						+ "/" + user.getUserId());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("PATCH");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", atr.token_type + " " + atr.access_token);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Contact contact = new Contact();
			contact.setFirstName(user.getFirstName());
			contact.setLastName(user.getLastName());
			contact.setBirthdate(sdf.format(user.getDob()));
			contact.setEmail(user.getEmail());
			Gson gson = new Gson();
			String jsonString = gson.toJson(contact);
			System.out.println("jsonString: " + jsonString);
			
			OutputStream os = conn.getOutputStream();
			os.write(jsonString.getBytes());
			os.flush();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String output;
			StringBuilder sb = new StringBuilder();
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			String outputResult = sb.toString();
			conn.disconnect();
			
			insertResponse = gson.fromJson(outputResult, InsertResponse.class);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return insertResponse;
	}
	
	public static void doDeleteContact(AccessTokenResponse atr, int userId) {
		String extField = "Contact_Ext_Id__c";
		
		try {
			URL url = new URL(atr.instance_url
					+ PREFIX_MID_URL
					+ OBJECT_API_NAME
					+ "/" + extField
					+ "/" + userId);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("DELETE");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", atr.token_type + " " + atr.access_token);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String output;
			StringBuilder sb = new StringBuilder();
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			conn.disconnect();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void allowMethods(String... methods) {
		try {
			Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);
			methodsField.setAccessible(true);
			
			String[] oldMethods = (String[]) methodsField.get(null);
			Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
			methodsSet.addAll(Arrays.asList(methods));
			String[] newMethods = methodsSet.toArray(new String[0]);
			methodsField.set(null/*static field*/, newMethods);
		}
		catch (NoSuchFieldException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
