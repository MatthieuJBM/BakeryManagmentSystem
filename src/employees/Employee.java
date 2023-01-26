package employees;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDate;

public class Employee implements Employed {
	
	private String empl_id;
	private String entity_id;
	private String first_name;
	private String last_name;
	private LocalDate start_date;
	private LocalDate end_date;
	private double salary;
	private String email;
	private String phone_number;
	private String city;
	private String zip_code;
	private String street;
	private String street_number;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZip_code() {
		return zip_code;
	}
	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getStreet_number() {
		return street_number;
	}
	public void setStreet_number(String street_number) {
		this.street_number = street_number;
	}
	public String getEmpl_id() {
		return empl_id;
	}
	public void setEmpl_id(String empl_id) {
		this.empl_id = empl_id;
	}
	public String getEntity_id() {
		return entity_id;
	}
	public void setEntity_id(String entity_id) {
		this.entity_id = entity_id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public LocalDate getStart_date() {
		return start_date;
	}
	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
	}
	public LocalDate getEnd_date() {
		return end_date;
	}
	public void setEnd_date(LocalDate end_date) {
		this.end_date = end_date;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	public void addNewEmployee() {
		try {
			//Creating connection
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Bakery_Managment_System", "root", "1234");
			//Creating object statement
			Statement myStmt = myConnection.createStatement();
			//Preparing a sql instruction
			
			String sql = "INSERT INTO Employees(empl_id, first_name, last_name, salary, start_date, end_date, entity_id) "
					+ "VALUES ('" + this.empl_id + "', '" + this.first_name + "', '" + this.last_name + "', " + this.salary + ", '" + this.start_date + "', '" + this.end_date + "', '" + this.entity_id + "');";
			
			//Executing query
			myStmt.executeUpdate(sql);
			myConnection.close();
			
		}catch(Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	public void addEmployeeContactInformation() {
		try {
			//Creating connection
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Bakery_Managment_System", "root", "1234");
			//Creating object statement
			Statement myStmt = myConnection.createStatement();
			//Preparing a sql instruction
			
			/*
		
			 
			 */
			String sql = "INSERT INTO Employees_contact_info(empl_id, email, phone, city, street, street_number, zip_code) "
					+ "VALUES ('" + this.empl_id + "', '" + this.email + "', '" + this.phone_number + "', '" + this.city + "', '" + this.street + "', '" + this.street_number + "', '" + this.zip_code + "');";
			
			//Executing query
			myStmt.executeUpdate(sql);
			myConnection.close();
			
		}catch(Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	
	
	
}
