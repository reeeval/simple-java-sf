package com.simple.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.simple.dao.UserDao;
import com.simple.model.User;

public class UserController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static String INSERT_OR_EDIT = "/user.jsp";
	private static String LIST_USER = "/listUser.jsp";
	private static final String DELETE = "delete";
	private static final String EDIT = "edit";
	private static final String ALLUSER = "listUser";
	private UserDao dao;
	
	public UserController() {
		super();
		dao = new UserDao();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forward = "";
		String action = request.getParameter("action");
		
		if (action.equalsIgnoreCase(DELETE)) {
			int userId = Integer.parseInt(request.getParameter("userId"));
			dao.deleteUser(userId);
			forward = LIST_USER;
			request.setAttribute("users", dao.getAllUser());
		}
		else if (action.equalsIgnoreCase(EDIT)) {
			forward = INSERT_OR_EDIT;
			int userId = Integer.parseInt(request.getParameter("userId"));
			User user = dao.getUserById(userId);
			request.setAttribute("user", user);
		}
		else if (action.equalsIgnoreCase(ALLUSER)) {
			forward = LIST_USER;
			request.setAttribute("users", dao.getAllUser());
		}
		else {
			forward = INSERT_OR_EDIT;
		}
		
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = new User();
		user.setFirstName(request.getParameter("firstName"));
		user.setLastName(request.getParameter("lastName"));
		try {
			Date dob = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("dob"));
			user.setDob(dob);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		user.setEmail(request.getParameter("email"));
		String userId = request.getParameter("userid");
		if (userId == null || userId.isEmpty()) {
			dao.addUser(user);
		}
		else {
			user.setUserId(Integer.parseInt(userId));
			dao.updateUser(user);
		}
		
		RequestDispatcher view = request.getRequestDispatcher(LIST_USER);
		request.setAttribute("users", dao.getAllUser());
		view.forward(request, response);
	}
}
