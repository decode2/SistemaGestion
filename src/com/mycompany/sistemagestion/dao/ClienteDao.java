package com.mycompany.sistemagestion.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mycompany.sistemagestion.models.Cliente;
import com.mysql.cj.util.StringUtils;

public class ClienteDao {
	
	public Connection connect() {
		
		String dbName = "management";
		String dbUser = "root";
		String dbPassword = "mypw";
		String dbHost = "localhost";
		String dbPort = "3306";
		String dbDriver = "com.mysql.cj.jdbc.Driver";
		String connectionUrl = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?useSSL=false";
		
		Connection connection = null;
		
		try {
			Class.forName(dbDriver);
			connection = DriverManager.getConnection(connectionUrl, dbUser, dbPassword);
		}
		catch (Exception ex){
			Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return connection;
	}
	
	public void add(Cliente cliente) {
		
		try {
			Connection connection = this.connect();
			
			String query = "INSERT INTO `clients` (`name`, `lastname`, `phone`, `email`) VALUES ('" + cliente.getNombre() + "', '" + cliente.getApellido() + "', '" + cliente.getTelefono() + "', '" + cliente.getEmail() + "');";
			Statement statement = connection.createStatement();
			
			statement.execute(query);
		}
		catch (Exception ex){
			Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void update(Cliente cliente) {
		
		try {
			Connection connection = this.connect();
			
			String query = "UPDATE `clients` SET `name` = '" + cliente.getNombre() 
			+ "', `lastname` =  '" + cliente.getApellido() 
			+ "', `phone` = '" + cliente.getTelefono() 
			+ "', `email` = '" + cliente.getEmail() 
			+ "' WHERE `clients`.`id` = " + cliente.getId();
			
			Statement statement = connection.createStatement();
			
			statement.execute(query);
		}
		catch (Exception ex){
			Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public List<Cliente> list() {
		
		List<Cliente> listado = new ArrayList<>();
		
		try {
			Connection connection = this.connect();
			
			String query = "SELECT * FROM `clients`";
			Statement statement = connection.createStatement();
			
			ResultSet result = statement.executeQuery(query);
			
			Cliente client;
			while (result.next()) {
				
				client = new Cliente();
				
				client.setId(result.getString("id"));
				client.setNombre(result.getString("name"));
				client.setApellido(result.getString("lastname"));
				client.setTelefono(result.getString("phone"));
				client.setEmail(result.getString("email"));
				
				listado.add(client);
			}
		}
		catch (Exception ex){
			Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return listado;
	}
	
	public void delete(String id) {
		
		try {
			Connection connection = this.connect();
			
			String query = "DELETE FROM `clients` WHERE `clients`.`id` = " + id;
			Statement statement = connection.createStatement();
			
			statement.execute(query);
		}
		catch (Exception ex){
			Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void save(Cliente client) {

		if (StringUtils.isEmptyOrWhitespaceOnly(client.getId())) {
			this.add(client);
		}
		else {
			this.update(client);
		}
		
	}

}
