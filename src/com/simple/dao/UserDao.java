package com.simple.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.simple.integration.AccessTokenResponse;
import com.simple.integration.GetToken;
import com.simple.integration.InsertResponse;
import com.simple.integration.SFUtil;
import com.simple.model.User;
import com.simple.util.DbUtil;

public class UserDao {
	private Connection connection;
	
	public UserDao() {
		connection = DbUtil.getConnection();
	}
	
	public void addUser(User user) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO users(firstname, lastname, dob, email) VALUES (?,?,?,?)",
							Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setDate(3, new java.sql.Date(user.getDob().getTime()));
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.executeUpdate();
			
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				user.setUserId(rs.getInt(1));
			}
			
			//call API insert to salesforce
			AccessTokenResponse atr = GetToken.doGetToken();
			if (atr != null) {
				InsertResponse ir = SFUtil.doInsertContact(atr, user);
				System.out.println(ir.toString());
			}
			else {
				throw new Exception("empty response from get token salesforce");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateUser(User user) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("UPDATE users SET firstname=?, lastname=?, dob=?, email=? WHERE userid=?");
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setDate(3, new java.sql.Date(user.getDob().getTime()));
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.setInt(5, user.getUserId());
			preparedStatement.executeUpdate();
			
			//call API upsert to salesforce
			AccessTokenResponse atr = GetToken.doGetToken();
			if (atr != null) {
				InsertResponse ir = SFUtil.doUpsertContact(atr, user);
				if (ir != null) System.out.println(ir.toString());
			}
			else {
				throw new Exception("empty response from get token salesforce");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteUser(int userId) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("DELETE FROM users WHERE userid = ?");
			preparedStatement.setInt(1, userId);
			preparedStatement.executeUpdate();
			
			//call API delete to salesforce
			AccessTokenResponse atr = GetToken.doGetToken();
			if (atr != null) {
				SFUtil.doDeleteContact(atr, userId);
			}
			else {
				throw new Exception("empty response from get token salesforce");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<User> getAllUser() {
		List<User> userList = new ArrayList<User>();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM users");
			while (rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt("userid"));
				user.setFirstName(rs.getString("firstname"));
				user.setLastName(rs.getString("lastname"));
				user.setDob(rs.getDate("dob"));
				user.setEmail(rs.getString("email"));
				userList.add(user);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}
	
	public User getUserById(int userId) {
		User user = new User();
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT * FROM users WHERE userid=?");
			preparedStatement.setInt(1, userId);
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				user.setUserId(rs.getInt("userid"));
				user.setFirstName(rs.getString("firstname"));
				user.setLastName(rs.getString("lastname"));
				user.setDob(rs.getDate("dob"));
				user.setEmail(rs.getString("email"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
}
