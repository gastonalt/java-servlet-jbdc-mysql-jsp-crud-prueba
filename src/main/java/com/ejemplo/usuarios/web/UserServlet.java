package com.ejemplo.usuarios.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ejemplo.usuarios.dao.UserDAO;
import com.ejemplo.usuarios.model.User;

@WebServlet("/")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
       
    public UserServlet() {
    	this.userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	this.doGet(request, response);
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		try {			
			switch (action) {
			case "/new":
				showAgregarForm(request, response);
				break;
			case "/insert":
				insertUser(request, response);
				break;
			case "/delete":
				deleteUser(request, response);
				break;
			case "/edit":
				showEditarForm(request, response);
				break;
			case "/update":
				updateUser(request, response);
				break;
			default:
				listUser(request, response);
				break;
			}
		} catch(SQLException ex) {
			throw new ServletException(ex);
		}
		
	}
	
	//mostrar formulario para agregar usuario
	private void showAgregarForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		dispatcher.forward(request, response);
	}
	
	//agregar usuario con los parametros de la url
	private void insertUser (HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		String username = request.getParameter("username");
		String pass = request.getParameter("pass");
		int rango = Integer.parseInt(request.getParameter("rango"));
		User newUser = new User(username, pass, rango);
		userDAO.insertUser(newUser);
		response.sendRedirect("list");
	}
	
	//borrar un usuario
	private void deleteUser (HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		int id = Integer.parseInt(request.getParameter("id"));
		userDAO.deleteUser(id);
		response.sendRedirect("list");
	}
	
	//mostrar formulario para editar el usuario
	private void showEditarForm (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		int id = Integer.parseInt(request.getParameter("id"));
		User existingUser = userDAO.selectUser(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		request.setAttribute("user", existingUser);
		dispatcher.forward(request, response);
	}
	
	//modificar usuario
	private void updateUser (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		int id = Integer.parseInt(request.getParameter("id"));
		String username = request.getParameter("username");
		String pass = request.getParameter("pass");
		int rango = Integer.parseInt(request.getParameter("rango"));
		
		User modificado = new User(id,username,pass,rango);
		userDAO.updateUser(modificado);
		response.sendRedirect("list");
	}
	
	//Listar todos los usuarios
	private void  listUser (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		List<User> listaUsers = userDAO.selectAllUser();
		request.setAttribute("listUser", listaUsers);
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
		dispatcher.forward(request, response);
	}
}
