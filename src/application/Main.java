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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;


public class Main extends Application {
	//Declaring variables needed for this program.
	BorderPane contenedorPrincipal;
	Pane contenedor;
	Scene miEscena;
	//Declaring menu variables
	MenuBar myMenu;
	Menu mnuFile, mnuEmployees, mnuEntities, mnuBakeryProducts;
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
	
	//Declaring variables for addNewEmployee view
	Pane addNewEmployeeContenedor;
	Scene addNewEmployeeScene;
	Stage addNewEmployeeStage;
	Label lbl;
	TextField txtFld;
	
	//Declaring boolean values for see which view is on
	boolean employeesOn = false;
	boolean entitiesOn = false;
	//Declaring boolean values for see which has been created first time
	boolean employeesCreated = false;
	boolean entitiesCreated = false;
	
	//Declaring variables for Entities view
	MenuItem entityOpen;
	Label lblEntities;
	TableView<String> tblEntities;
	TableColumn tblClmnEntityId, tblClmnEntityName, tblClmnEntityStreet;
	Button btnAddNewEntity;
	//Declaring variables for addNewEntity view
	Pane addNewEntityContenedor;
	Scene addNewEntityScene;
	Stage addNewEntityStage;
	Label lblEntityId, lblEntityName, lblEntityCity, lblEntityStreet, lblEntityStreetNumber, lblEntityZipCode;
	TextField txtFldEntityId, txtFldEntityName, txtFldEntityCity, txtFldEntityStreet, txtFldEntityStreetNumber, txtFldEntityZipCode;
	Button btnSendNewEntity, btnCancelNewEntity;
	
	//Stage
	Stage myStage;
	
	//New Scene and Pane and other elements for Employees view
	//Scene sceneEmployees;
	Pane contenedorEmployees;
	MenuBar mnuEmplView;
	
	//New Scene and Pane and other elements for Entities view
	//Scene sceneEntities;
	Pane contenedorEntities;
	
	
	public void start(Stage myStage) {
		try {
			this.myStage = myStage;
			
			
			//Initializing menu variables.
			myMenu = new MenuBar();
				myMenu.setTranslateX(0);
				myMenu.setTranslateY(0);
				myMenu.setPrefSize(600, 30);
				
			mnuFile = new Menu("File");
			mnuEmployees = new Menu("Employees");
			mnuEntities = new Menu("Entities");
			mnuBakeryProducts = new Menu("Products");
			
			//Adding Menu to MenuBar
			myMenu.getMenus().addAll(mnuFile, mnuEmployees, mnuEntities, mnuBakeryProducts);
			
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
				mnuItmAddNewEmployee.setOnAction(e -> addNewEmployeeWindow());
			mnuItmChangeEmployeesProperty = new MenuItem("Change information about an employee");
			mnuItmDeleteAnEmployee = new MenuItem("Delete an employee");
			mnuItmShowFormerEmployees = new MenuItem("Show former employees");
			mnuEmployees.getItems().addAll(mnuItmOpenEmployeesTab, mnuItmAddNewEmployee, mnuItmChangeEmployeesProperty, mnuItmDeleteAnEmployee, mnuItmShowFormerEmployees);			
			
			//Adding MenuItem Open to Entity tab
			entityOpen = new MenuItem("Open");
				//entityOpen.setOnAction(e -> bakeryManagmentSystemsEntitiesViewCreator());
				entityOpen.setOnAction(e -> bakeryManagmentSystemsEntitiesViewCreator());
			mnuEntities.getItems().addAll(entityOpen);
			
			
			
			//Creating the frame
			contenedorPrincipal = new BorderPane();
			contenedor = new Pane();
			//Adding the main variables to the Pane item, that is contenedor.
			//contenedor.getChildren().addAll(myMenu);
			contenedorPrincipal.setTop(myMenu);
			contenedorPrincipal.setCenter(contenedor);
			//miEscena = new Scene(contenedor);
			miEscena = new Scene(contenedorPrincipal);
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
			
			mnuEmplView = myMenu;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	//View creators
	
	public void bakeryManagmentSystemsEmployeesViewCreator() {
		
		if(employeesCreated == false) {
			lblEmployees = new Label("Employees:");
				lblEmployees.setFont(new Font("Arial", 20));
				lblEmployees.setTranslateX(15);
				lblEmployees.setTranslateY(30);
				
		
			//
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
			btnAddNewEmployee = buttonCreatorHelper("Add new employee", 15, 600, 150, 20);
				btnAddNewEmployee.setOnAction(e -> addNewEmployeeWindow());
			
			contenedorEmployees = new Pane();
			
			contenedorEmployees.getChildren().addAll(lblEmployees, tblEmployees, btnAddNewEmployee);
			
			employeesCreated = true;
		}
		
		myStage.setTitle("Bakery Managment System - Employees");
		//myStage.setScene(sceneEmployees);
		contenedorPrincipal.setCenter(contenedorEmployees);
		
		employeesOn = true;
		
	}
	
	public void bakeryManagmentSystemsEntitiesViewCreator(){
		
		if(entitiesCreated == false) {
			lblEntities = new Label("Entities:");
				lblEntities.setFont(new Font("Arial", 20));
				lblEntities.setTranslateX(15);
				lblEntities.setTranslateY(30);
		
			tblEntities = new TableView<>();
				tblEntities.setEditable(false);
				tblEntities.setTranslateX(15);
				tblEntities.setTranslateY(60);
			//Creating columns for the table tblEmployees.	
			tblClmnEntityId = new TableColumn("Entity Id");
				tblClmnEntityId.setMinWidth(30);
				tblClmnEntityId.setMaxWidth(40);
			tblClmnEntityName = new TableColumn("Entity Name");
				tblClmnEntityName.setMinWidth(100);
			tblClmnEntityStreet = new TableColumn("Street");
				tblClmnEntityStreet.setMinWidth(100);
		
			tblEntities.getColumns().addAll(tblClmnEntityId, tblClmnEntityName, tblClmnEntityStreet);
			
			//Initializing buttons via self created method
			btnAddNewEntity = buttonCreatorHelper("Add new entity", 15, 600, 120, 20);
				btnAddNewEntity.setOnAction(e -> addNewEntityWindow());
			
			contenedorEntities = new Pane();
			contenedorEntities.getChildren().addAll(lblEntities, tblEntities, btnAddNewEntity);
			
			entitiesCreated = true;
		}
		
		entitiesOn = true;
		myStage.setTitle("Bakery Managment System - Entities");
		contenedorPrincipal.setCenter(contenedorEntities);
		
	}
	
	//Add windows creation
	
	public void addNewEntityWindow() {
		
		/*
		lblEntityId, lblEntityName, lblEntityCity, lblEntityStreet, lblEntityStreetNumber, lblEntityZipCode,
		txtFldEntityId, txtFldEntityName, txtFldEntityCity, txtFldEntityStreet, txtFldEntityStreetNumber, 
		txtFldEntityZipCode
		*/
		
		//Creating all the Labels
		lblEntityId = labelCreatorHelper("Entity id:", 25, 25);
		lblEntityName = labelCreatorHelper("Entity Name:", 175, 25);
		lblEntityCity = labelCreatorHelper("City:", 25, 100);
		lblEntityStreet = labelCreatorHelper("Street:", 25, 185);
		lblEntityStreetNumber = labelCreatorHelper("Street Number:", 175, 185);
		lblEntityZipCode = labelCreatorHelper("Zip Code:", 175, 100);
		
		//Creating all the TextFields
		txtFldEntityId = textFieldCreatorHelper(25, 45, 100, 35);
		txtFldEntityName = textFieldCreatorHelper(175, 45, 100, 35);
		txtFldEntityCity = textFieldCreatorHelper(25, 120, 100, 35);
		txtFldEntityStreet = textFieldCreatorHelper(25, 205, 100, 35);
		txtFldEntityStreetNumber = textFieldCreatorHelper(175, 205, 100, 35);
		txtFldEntityZipCode = textFieldCreatorHelper(175, 120, 100, 35);
		
		//Creating buttons to send and cancel
		//btnSendNewEntity, btnCancelNewEntity
		btnSendNewEntity = buttonCreatorHelper("Send", 320, 80, 70, 20);
			
		btnCancelNewEntity = buttonCreatorHelper("Cancel", 320, 110, 70, 20);
		
		
		addNewEntityContenedor = new Pane();
		addNewEntityContenedor.getChildren().addAll(lblEntityId, lblEntityName, lblEntityCity, lblEntityStreet, lblEntityStreetNumber, lblEntityZipCode,
				txtFldEntityId, txtFldEntityName, txtFldEntityCity, txtFldEntityStreet, txtFldEntityStreetNumber, 
				txtFldEntityZipCode, btnSendNewEntity, btnCancelNewEntity);
		addNewEntityScene = new Scene(addNewEntityContenedor);
		addNewEntityStage = new Stage();
		addNewEntityStage.setTitle("Add new entity");
		addNewEntityStage.setMinWidth(500);
		addNewEntityStage.setMinHeight(300);
		addNewEntityStage.setScene(addNewEntityScene);
		addNewEntityStage.show();
		
	}
	
	public void addNewEmployeeWindow() {
		
		
		
		
		addNewEmployeeContenedor = new Pane();
		addNewEmployeeContenedor.getChildren().addAll();
		addNewEmployeeScene = new Scene(addNewEmployeeContenedor);
		addNewEmployeeStage = new Stage();
		addNewEmployeeStage.setTitle("Add new employee");
		addNewEmployeeStage.setMinWidth(500);
		addNewEmployeeStage.setMinHeight(300);
		addNewEmployeeStage.setScene(addNewEmployeeScene);
		addNewEmployeeStage.show();
		
		
	}
	
	//Helper methods to create the elements with less code usage.
	
	
	public Button buttonCreatorHelper(String btnName, double btnSetTranslateX, double btnSetTranslateY, double btnSetPrefSizeX, double btnSetPrefSizeY) {
		Button newButton = new Button(btnName);
		newButton.setTranslateX(btnSetTranslateX);
		newButton.setTranslateY(btnSetTranslateY);
		newButton.setPrefSize(btnSetPrefSizeX, btnSetPrefSizeY);
		return newButton;
	}
	

	
	public Label labelCreatorHelper(String labelName, int labelX, int labelY) {
		Label newLabel;
		newLabel = new Label(labelName);
		newLabel.setTranslateX(labelX);
		newLabel.setTranslateY(labelY);
		return newLabel;
	}
	public TextField textFieldCreatorHelper(int txtFieldX, int txtFieldY, int prefX, int prefY) {
		TextField newTxtField;
		newTxtField = new TextField();
		newTxtField.setTranslateX(txtFieldX);
		newTxtField.setTranslateY(txtFieldY);
		newTxtField.setPrefSize(prefX, prefY);
		newTxtField.setEditable(true);
		return newTxtField;
	}
	
	
	
}












