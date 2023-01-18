package installation_bakery_managment_system;
import java.sql.*;

public class Install_bakery_managment_system {
	
	public void createDataBase() {
		String sqlCreateDatabase = "CREATE DATABASE Bakery_Managment_System;";

		try {
			//Creating connection
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "1234");
			//Creating object Statement
			Statement myStmt = myConnection.createStatement();
			//Executing query
			myStmt.executeUpdate(sqlCreateDatabase);
			myConnection.close();
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
	}
	public void createTables() {
		String sqlUseCommend = "USE Bakery_Managment_System";
		String sqlCreateTableEntities = "CREATE TABLE Entities( "
									  + "entity_id varchar(10), "
									  + "entity_name varchar(30), "
									  + "city varchar(40), "
									  + "street varchar(40), "
									  + "street_number varchar(10), "
									  + "zip_code varchar(10),"
									  + "PRIMARY KEY (entity_id) "
									  + ");";
		String sqlCreateTablePositions = "CREATE TABLE Positions( "
								  + "position_name varchar(30), "
								  + "entity_id varchar(10) REFERENCES Entities(entity_id) "
								  + ");";
		String sqlCreateTableEntities_costs = "CREATE TABLE Entities_costs( "
								  + "entity_id varchar(10) REFERENCES Entities(entity_id), "
								  + "rental numeric(8,2), "
								  + "utilities numeric(8,2), "
								  + "employees_salaries_sum numeric(8,2), "
								  + "income numeric(8,2), "
								  + "profit numeric(8,2),"
								  + "month varchar(2),"
								  + "year varchar(4) "
								  + ");";
		String sqlCreateTableEmployees = "CREATE TABLE Employees( "
								  + "empl_id varchar(10), "
								  + "first_name varchar(30), "
								  + "last_name varchar(30), "
								  + "salary numeric(8,2), "
								  + "start_date date, "
								  + "end_date date, "
								  + "entity_id varchar(10) REFERENCES Entities(entity_id), "
								  + "PRIMARY KEY (empl_id) "
								  + ");";
		String sqlCreateTableEmployees_contact_info = "CREATE TABLE Employees_contact_info( "
								  + "empl_id varchar(10) REFERENCES Employees(empl_id), "
								  + "email varchar(40), "
								  + "phone varchar(20), "
								  + "city varchar(40), "
								  + "street varchar(40), "
								  + "street_number varchar(10), "
								  + "zip_code varchar(10) "
								  + ");";
		String sqlCreateTableEmergency_contact = "CREATE TABLE Emergency_contact( "
								  + "empl_id varchar(10) REFERENCES Employees(empl_id), "
								  + "phone varchar(20), "
								  + "city varchar(40), "
								  + "street varchar(40), "
								  + "street_number varchar(10), "
								  + "zip_code varchar(10) "
								  + ");";
		
		try {
			//Creating connection
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Bakery_Managment_System", "root", "1234");
			//Creating object Statement
			Statement myStmt = myConnection.createStatement();
			//Executing query
			myStmt.executeUpdate(sqlUseCommend);
			myStmt.executeUpdate(sqlCreateTableEntities);
			myStmt.executeUpdate(sqlCreateTablePositions);
			myStmt.executeUpdate(sqlCreateTableEntities_costs);
			myStmt.executeUpdate(sqlCreateTableEmployees);
			myStmt.executeUpdate(sqlCreateTableEmployees_contact_info);
			myStmt.executeUpdate(sqlCreateTableEmergency_contact);
			
			
			myConnection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
}
