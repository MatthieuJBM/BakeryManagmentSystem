package entity;

import java.sql.*;

public class Entity {
	//Declaring variables
	private String entity_id;
	private String entity_name;
	private String entity_city;
	private String entity_street;
	private String entity_street_number;
	private String entity_zip_code;
	
	//Declaring variables for costs table in database
	private double entity_rental;
	private double entity_utilities;
	private double entity_employees_salary_sum;
	private double entity_income;
	private double entity_profit;
	
	//Declaring variables for positions table in database
	private String entity_position_name;

	public String getEntity_id() {
		return entity_id;
	}

	public void setEntity_id(String entity_id) {
		this.entity_id = entity_id;
	}

	public String getEntity_name() {
		return entity_name;
	}

	public void setEntity_name(String entity_name) {
		this.entity_name = entity_name;
	}

	public String getEntity_city() {
		return entity_city;
	}

	public void setEntity_city(String entity_city) {
		this.entity_city = entity_city;
	}

	public String getEntity_street() {
		return entity_street;
	}

	public void setEntity_street(String entity_street) {
		this.entity_street = entity_street;
	}

	public String getEntity_street_number() {
		return entity_street_number;
	}

	public void setEntity_street_number(String entity_street_number) {
		this.entity_street_number = entity_street_number;
	}

	public String getEntity_zip_code() {
		return entity_zip_code;
	}

	public void setEntity_zip_code(String entity_zip_code) {
		this.entity_zip_code = entity_zip_code;
	}

	public double getEntity_rental() {
		return entity_rental;
	}

	public void setEntity_rental(double entity_rental) {
		this.entity_rental = entity_rental;
	}

	public double getEntity_utilities() {
		return entity_utilities;
	}

	public void setEntity_utilities(double entity_utilities) {
		this.entity_utilities = entity_utilities;
	}

	public double getEntity_employees_salary_sum() {
		return entity_employees_salary_sum;
	}

	public void setEntity_employees_salary_sum(double entity_employees_salary_sum) {
		this.entity_employees_salary_sum = entity_employees_salary_sum;
	}

	public double getEntity_income() {
		return entity_income;
	}

	public void setEntity_income(double entity_income) {
		this.entity_income = entity_income;
	}

	public double getEntity_profit() {
		return entity_profit;
	}

	public void setEntity_profit(double entity_profit) {
		this.entity_profit = entity_profit;
	}

	public String getEntity_position_name() {
		return entity_position_name;
	}

	public void setEntity_position_name(String entity_position_name) {
		this.entity_position_name = entity_position_name;
	}
	
	public void addNewEntity() {
		try {
			//Creating connection
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Bakery_Managment_System", "root", "1234");
			//Creating object statement
			Statement myStmt = myConnection.createStatement();
			//Preparing a sql instruction
			
			/*
			 entity_id, entity_name, entity_city, entity_street, entity_street_number, entity_zip_code,
			 entity_rental, entity_utilities, entity_employees_salary_sum, entity_income, entity_profit,
			 entity_position_name
			 
			 */
			String sql = "INSERT INTO Entities(entity_id, entity_name, city, street, street_number, zip_code) "
					+ "VALUES ('" + this.entity_id + "', '" + this.entity_name + "', '" + this.entity_city + "', '" + this.entity_street + "', '" + this.entity_street_number + "', '" + this.entity_zip_code + "');";
			
			//Executing query
			myStmt.executeUpdate(sql);
			myConnection.close();
			
		}catch(Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
		
	}
	
	//Para eliminar - sirve solamente para una vista mejor al lado derecho.
	/*
	//
	Entity entityExample = new Entity();
	entityExample.setEntity_name("Produkcja");
	ObservableList<Entity> data = FXCollections.observableArrayList(entityExample);
	
	tblClmnEntityName.setCellValueFactory(
			new PropertyValueFactory<Entity, String>("entity_name"));
	
	//tblEntities.setItems(data);	
	//
	 * 
	 */
	
	
	
}
