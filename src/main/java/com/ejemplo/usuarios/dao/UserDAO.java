package com.ejemplo.usuarios.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ejemplo.usuarios.model.User;

public class UserDAO {

	private String jdbcURL = "jdbc:mysql://localhost:3306/usuarioscruddemo";
	private String jdbcUsername = "root";
	private String jdbcPassword = "";
	
	private static final String INSERT_USERS_SQL = "INSERT INTO users" + " (username,pass,rango) VALUES " + "(? , ? , ?);";	
	private static final String SELECT_USER_BY_ID = "select id,username,pass,rango from users where id =?";
	private static final String SELECT_ALL_USERS = "select * from users";
	private static final String DELETE_USERS_SQL = "delete from users where id = ?;";
	private static final String UPDATE_USERS_SQL = "update users set username = ?,pass= ?, rango =? where id = ?;";
	
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
	//CREAR USER
	public void insertUser(User user) throws SQLException {
		try(Connection conn = getConnection();
				PreparedStatement preparedStat = conn.prepareStatement(INSERT_USERS_SQL)){
			preparedStat.setString(1, user.getUsername()); 
			preparedStat.setString(2, user.getPass());
			preparedStat.setInt(3, user.getRango());
			preparedStat.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//MODIF USER
	public boolean updateUser(User user) throws SQLException {
		boolean usuarioActualizado;
		try(Connection conn = getConnection();
				PreparedStatement preparedStat = conn.prepareStatement(UPDATE_USERS_SQL)){
			preparedStat.setString(1, user.getUsername()); 
			preparedStat.setString(2, user.getPass());
			preparedStat.setInt(3, user.getRango());
			preparedStat.setInt(4, user.getId());
			usuarioActualizado = preparedStat.executeUpdate() > 0;
		}
		return usuarioActualizado;
	}
	
	//SEL USER
	public User selectUser(int id) throws SQLException {
		User user = null;
		try(Connection conn = getConnection();
				PreparedStatement preparedStat = conn.prepareStatement(SELECT_USER_BY_ID);){
			preparedStat.setInt(1, id);
			System.out.println(preparedStat);
			
			ResultSet rs = preparedStat.executeQuery();
			
			while(rs.next()) {
				String username = rs.getString("username");
				String pass = rs.getString("pass");
				int rango = rs.getInt("rango");
				user = new User(id,username,pass,rango);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	//TODOS LOS USERS
	public List<User> selectAllUser() throws SQLException {
		List<User> users = new ArrayList<>();
		try(Connection conn = getConnection();
				PreparedStatement preparedStat = conn.prepareStatement(SELECT_ALL_USERS);){
			System.out.println(preparedStat);
			
			ResultSet rs = preparedStat.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String pass = rs.getString("pass");
				int rango = rs.getInt("rango");
				users.add(new User(id,username,pass,rango));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	//BORRAR USER
	public boolean deleteUser(int id) throws SQLException{
		boolean usuarioBorrado;
		try(Connection conn = getConnection();
				PreparedStatement preparedStat = conn.prepareStatement(DELETE_USERS_SQL);){
			preparedStat.setInt(1,id);
			usuarioBorrado = preparedStat.executeUpdate() > 0;
		}
		return usuarioBorrado;
	}
	
	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
	
}
