package application;

import model_base_de_datos.*;
import java.sql.*;
import java.sql.DriverManager;
import java.util.ArrayList;

import installation_bakery_managment_system.*;	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;


public class Main extends Application {
	//Declaring variables needed for this program.
	Pane contenedor;
	Scene miEscena;
	//Declaring menu variables
	MenuBar myMenu;
	Menu mnuFile, mnuEmployees, mnuBakeryProducts;
	//Declaring MenuItem for File Menu
	MenuItem mnuItmSave, mnuItmExit;
	Menu mnuExport;
	//Declaring MenuItem as SubItems of mnuItmExport
	MenuItem mnuItmExportToCsv, mnuItmExportToTxt;
	//Declaring MenuItem for Employees Menu
	MenuItem mnuItmOpenEmployeesTab, mnuItmAddNewEmployee, mnuItmChangeEmployeesProperty, mnuItmDeleteAnEmployee, mnuItmShowFormerEmployees;
	
	//Declaring MenuItem for Products Menu
	
	//Declaring TableView and corresponding Label for showing the employees.
	Label lblEmployees;
	TableView<String> tblEmployees;
	TableColumn tblClmnId, tblClmnFirstName, tblClmnLastName;
	
	//Declaring buttons to Employees tab
	Button btnAddNewEmployee;
	
	public void start(Stage myStage) {
		try {
			//Initializing menu variables.
			myMenu = new MenuBar();
			mnuFile = new Menu("File");
			mnuEmployees = new Menu("Employees");
			mnuBakeryProducts = new Menu("Products");
			
			//Adding Menu to MenuBar
			myMenu.getMenus().addAll(mnuFile, mnuEmployees, mnuBakeryProducts);
			
			//Initializing MenuItem variables
			mnuItmSave = new MenuItem("Save");
			mnuItmExit = new MenuItem("Exit");
			mnuExport = new Menu("Export");
			
			//Adding MenuItem to Menu
			mnuFile.getItems().addAll(mnuItmSave, mnuItmExit, mnuExport);
			
			//Creating and Adding submenus to Export
			mnuItmExportToCsv = new MenuItem("Export to csv");
			mnuItmExportToTxt = new MenuItem("Export to txt");
			mnuExport.getItems().addAll(mnuItmExportToCsv, mnuItmExportToTxt);
			
			//Creating and adding MenuItem to Employees
			mnuItmOpenEmployeesTab = new MenuItem("Open");
				mnuItmOpenEmployeesTab.setOnAction(e -> bakeryManagmentSystemsEmployeesViewCreator());
			mnuItmAddNewEmployee = new MenuItem("Add new employee");
			mnuItmChangeEmployeesProperty = new MenuItem("Change information about an employee");
			mnuItmDeleteAnEmployee = new MenuItem("Delete an employee");
			mnuItmShowFormerEmployees = new MenuItem("Show former employees");
			mnuEmployees.getItems().addAll(mnuItmOpenEmployeesTab, mnuItmAddNewEmployee, mnuItmChangeEmployeesProperty, mnuItmDeleteAnEmployee, mnuItmShowFormerEmployees);			
			
			
			//Creating the frame
			contenedor = new Pane();
			//Adding the main variables to the Pane item, that is contenedor.
			contenedor.getChildren().addAll(myMenu);
			miEscena = new Scene(contenedor);
			myStage.setTitle("Bakery Managment System");
			myStage.setMinWidth(1000);
			myStage.setMinHeight(700);
			myStage.setScene(miEscena);
			myStage.show();
			
			//Installation of DataBase on the computer
			try {
				//Checking if database already exists
				ArrayList<String> list = new ArrayList<String>();
				String data_base_name = "Bakery_Managment_System";
				Connection myConnect = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "1234");
				System.out.println("Conexion establecida");
				DatabaseMetaData dbm = myConnect.getMetaData();
				ResultSet rs = dbm.getCatalogs();
				
				while(rs.next()) {
					String listOfDatabases = rs.getString("TABLE_CAT");
					list.add(listOfDatabases);
				}
				if(list.contains(data_base_name)) {
					System.out.println("Database already exists");
					
				}else {
					//Creating Database and tables
					Install_bakery_managment_system install_data_base = new Install_bakery_managment_system();
					install_data_base.createDataBase();
					install_data_base.createTables();
					
				}
				rs.close();
				myConnect.close();
				
			
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			bakeryManagmentSystemsEmployeesViewCreator();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void bakeryManagmentSystemsEmployeesViewCreator() {
		lblEmployees = new Label("Employees:");
			lblEmployees.setFont(new Font("Arial", 20));
			lblEmployees.setTranslateX(15);
			lblEmployees.setTranslateY(30);
		tblEmployees = new TableView<>();
			tblEmployees.setEditable(false);
			tblEmployees.setTranslateX(15);
			tblEmployees.setTranslateY(60);
		//Creating columns for the table tblEmployees.	
		tblClmnId = new TableColumn("Id");
			tblClmnId.setMinWidth(30);
			tblClmnId.setMaxWidth(40);
		tblClmnFirstName = new TableColumn("First Name");
			tblClmnFirstName.setMinWidth(100);
		tblClmnLastName = new TableColumn("Last Name");
			tblClmnLastName.setMinWidth(100);
		
		tblEmployees.getColumns().addAll(tblClmnId, tblClmnFirstName, tblClmnLastName);
			
		//Initializing buttons via self created method
		btnAddNewEmployee = buttonCreator("Add new employee", 15, 600);
		
		contenedor.getChildren().addAll(lblEmployees, tblEmployees, btnAddNewEmployee);
	}
	
	public Button buttonCreator(String btnName, double btnSetTranslateX, double btnSetTranslateY) {
		Button newButton = new Button(btnName);
		newButton.setTranslateX(btnSetTranslateX);
		newButton.setTranslateY(btnSetTranslateY);
		
		return newButton;
	}
	
	
}












