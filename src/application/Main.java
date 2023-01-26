package application;

import model_base_de_datos.*;
import entity.*;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import installation_bakery_managment_system.*;	
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import employees.*;
import java.util.regex.*;

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
	TableView tblEmployees;
	ObservableList<ObservableList> employeesList;
	TableColumn tblClmnId, tblClmnFirstName, tblClmnLastName;
	
	//Declaring buttons to Employees tab
	Button btnAddNewEmployee, btnDeleteEmployee, btnShowAllInformationAboutTheEmployees, btnEmployeesContactInformation;
	
	
	//Declaring boolean values for see which view is on
	boolean employeesOn = false;
	boolean entitiesOn = false;
	//Declaring boolean values for see which has been created first time
	boolean employeesCreated = false;
	boolean entitiesCreated = false;
	
	//Declaring variables for Entities view
	MenuItem entityOpen;
	Label lblEntities;
	ObservableList<ObservableList> entityData;
	TableView tblEntities;
	TableColumn tblClmnEntityId, tblClmnEntityName, tblClmnEntityStreet;
	Button btnAddNewEntity;
	Button btnAddNewEntityCosts, btnAddNewEntityPosition;
	Button btnShowAllInformationAboutTheEntities;
	Button btnShowFullAddress, btnShowPositions;
	Button btnHideFullAddress, btnHideCosts, btnHidePositions;
	ArrayList<Label> temporaryAddressLabelArrayList = new ArrayList<>();
	TableView tblPositions;
	ObservableList<ObservableList> positionsList;
	
	//btnShowAllEntities;
	//ArrayList<Entity> entitiesCollection = new ArrayList<>();
	
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
	
	//Elements for Add Entity Costs window
	Pane addNewEntityCostsContenedor;
	Scene addNewEntityCostsScene;
	Stage addNewEntityCostsStage;
	Label lblEntityIdCosts, lblRental, lblUtilities, lblEmployeesSalariesSum;
	Label lblIncome, lblProfit;
	Label lblMonth, lblYear;
	TextField txtFldEntityIdCosts, txtFldRental, txtFldUtilities, txtFldEmployeesSalariesSum;
	TextField txtFldIncome, txtFldProfit;
	TextField txtFldMonth, txtFldYear;
	Button btnSendCosts, btnCancelCosts, btnRefreshProfit;
	
	//Elements for Add Entity Position window
	Pane addNewEntityPositionContenedor;
	Scene addNewEntityPositionScene;
	Stage addNewEntityPositionStage;
	Label lblEntityPositionName, lblEntityIdPositions;
	TextField txtFldEntityPositionName, txtFldEntityIdPosition;
	Button btnSendPosition, btnCancelPosition;
	
	
	//Elements for new TableView on Entities tab
	
	/***********************
	 Employee View Variables
	****************/
	
	//Declaring variables for addNewEmployee view
	Pane addNewEmployeeContenedor;
	Scene addNewEmployeeScene;
	Stage addNewEmployeeStage;
	Label lblEmplIdAddNewEmpl, lblEntityIdAddNewEmpl, lblFirstName, lblLastName, lblSalary, lblStartDate, lblEndDate;
	TextField txtFldEmplIdAddNewEmpl, txtFldEntityIdAddNewEmpl, txtFldFirstName, txtFldLastName, txtFldStartDate, txtFldEndDate, txtFldSalary;
	Button btnSendNewEmployee, btnCancelNewEmployee;
	DatePicker startDate, endDate;
		
	//Declaring variables for DeleteEmployee view	
	Pane deleteEmployeeContenedor;
	Scene deleteEmployeeScene;
	Stage deleteEmployeeStage;
	Label lblConfirmEmployeeDelete, lblAchtungEmployeeDelete;
	Button btnConfirmEmployeeDelete, btnCancelEmployeeDelete;
	
	//Declaring variables for Add Employee Information View
	Label lblEmplId, lblEmplEmail, lblEmplPhone, lblEmplCity, lblEmplStreet, lblEmplStreetNumber, lblEmplZipCode;
	Label warningLabel;
	TextField txtFldEmplId, txtFldEmplEmail, txtFldEmplPhone, txtFldEmplCity, txtFldEmplStreet, txtFldEmplStreetNumber, txtFldEmplZipCode;
	Stage addEmployeeContactInformationStage;
	Scene addEmployeeContactInformationScene;
	Pane addEmployeeContactInformationContenedor;
	Button btnSendEmployeeContactInformation, btnCancelEmployeeContactInformation;
	
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
				mnuItmExit.setOnAction(e -> myStage.close());
			mnuExport = new Menu("Export");
			
			//Adding MenuItem to Menu
			mnuFile.getItems().addAll(mnuItmSave, mnuItmExit, mnuExport);
			
			//Creating and Adding submenus to Export
			mnuItmExportToCsv = new MenuItem("Export to csv");
				mnuItmExportToCsv.setOnAction(e -> exportEmployeesToCsv());
			mnuItmExportToTxt = new MenuItem("Export to txt");
				mnuItmExportToTxt.setOnAction(e -> exportEmployeesToTxt());
			mnuExport.getItems().addAll(mnuItmExportToCsv, mnuItmExportToTxt);
			
			//Creating and adding MenuItem to Employees
			mnuItmOpenEmployeesTab = new MenuItem("Open");
				mnuItmOpenEmployeesTab.setOnAction(e -> bakeryManagmentSystemsEmployeesViewCreator());
			mnuItmAddNewEmployee = new MenuItem("Add new employee");
				mnuItmAddNewEmployee.setOnAction(e -> addNewEmployeeWindow());
			//mnuItmChangeEmployeesProperty = new MenuItem("Change information about an employee");
			mnuItmDeleteAnEmployee = new MenuItem("Delete an employee");
				mnuItmDeleteAnEmployee.setOnAction(e -> deleteEmployee());
			//mnuItmShowFormerEmployees = new MenuItem("Show former employees");
			mnuEmployees.getItems().addAll(mnuItmOpenEmployeesTab, mnuItmAddNewEmployee, /*mnuItmChangeEmployeesProperty,*/mnuItmDeleteAnEmployee/*, mnuItmShowFormerEmployees*/);			
			
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
			btnAddNewEmployee = buttonCreatorHelper("Add new employee", 15, 480, 130, 20);
				btnAddNewEmployee.setOnAction(e -> addNewEmployeeWindow());
			btnDeleteEmployee = buttonCreatorHelper("Delete Employee", 15, 510, 120, 20);
				btnDeleteEmployee.setOnAction(e -> deleteEmployee());
			btnShowAllInformationAboutTheEmployees = buttonCreatorHelper("Show All Information", 15, 540, 120, 20);
				btnShowAllInformationAboutTheEmployees.setOnAction(e -> showAllInformationAboutEmployees());	
			btnEmployeesContactInformation = buttonCreatorHelper("Add contact info", 140, 480, 120, 20);
				btnEmployeesContactInformation.setOnAction(e -> addEmployeesContactInformation());
				
				
			contenedorEmployees = new Pane();
			
			contenedorEmployees.getChildren().addAll(lblEmployees, tblEmployees, btnAddNewEmployee, btnDeleteEmployee, btnShowAllInformationAboutTheEmployees, btnEmployeesContactInformation);
			
			employeesCreated = true;
		}
		
		showEmployees();
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
				
			tblPositions = new TableView<>();
				tblPositions.setEditable(false);
				tblPositions.setTranslateX(300);
				tblPositions.setTranslateY(60);
				//tblPositions.setMaxWidth(110);
				tblPositions.setVisible(false);
				
			
			//Initializing buttons via self created method
			btnAddNewEntity = buttonCreatorHelper("Add new entity", 15, 480, 120, 20);
				btnAddNewEntity.setOnAction(e -> addNewEntityWindow());
				
			btnAddNewEntityCosts = buttonCreatorHelper("Add costs", 140, 480, 120, 20);
				btnAddNewEntityCosts.setOnAction(e -> addNewEntityCostsWindow());
				
			btnAddNewEntityPosition = buttonCreatorHelper("Add new position", 265, 480, 120, 20);
				btnAddNewEntityPosition.setOnAction(e -> addNewEntityPositionWindow());
			
			btnShowAllInformationAboutTheEntities = buttonCreatorHelper("Show All Information", 15, 510, 120, 20);
				btnShowAllInformationAboutTheEntities.setOnAction(e -> showAllInformationAboutTheEntities());
			
				
			//btnShowFullAddress, btnShowCosts, btnShowPositions
			btnShowFullAddress = buttonCreatorHelper("Show Address", 15, 540, 120, 20);
				btnShowFullAddress.setOnAction(e -> showFullEntityAddress());
				btnHideFullAddress = buttonCreatorHelper("Hide Address", 15, 540, 120, 20);
				btnHideFullAddress.setOnAction(e -> hideFullEntityAddress());
				btnHideFullAddress.setVisible(false);
			btnShowPositions = buttonCreatorHelper("Show Positions", 140, 540, 120, 20);
				btnShowPositions.setOnAction(e -> showPositions());
			btnHidePositions = buttonCreatorHelper("Hide Positions", 140, 540, 120, 20);
				btnHidePositions.setVisible(false);
				btnHidePositions.setOnAction(e -> hidePositions());
			
			contenedorEntities = new Pane();
			contenedorEntities.getChildren().addAll(lblEntities, tblEntities, btnAddNewEntity,
											btnAddNewEntityCosts, btnAddNewEntityPosition,
											btnShowAllInformationAboutTheEntities,
											btnShowFullAddress, btnShowPositions,
											btnHideFullAddress, tblPositions, btnHidePositions);
			
			
			entitiesCreated = true;
		}
		
		allEntitiesShowMethod();
		
		entitiesOn = true;
		myStage.setTitle("Bakery Managment System - Entities");
		contenedorPrincipal.setCenter(contenedorEntities);
		
	}
	
	//Add windows creation
	
	//Add new entity window creation
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
			btnSendNewEntity.setStyle("-fx-font: 14 Euphorigenic; -fx-base: #05F81B");
			btnSendNewEntity.setOnAction(e -> newEntitySendButton());
		btnCancelNewEntity = buttonCreatorHelper("Cancel", 320, 110, 70, 20);
			btnCancelNewEntity.setStyle("-fx-font: 14 Euphorigenic; -fx-base: #FF0500");
			btnCancelNewEntity.setOnAction(e -> addNewEntityStage.close());
		
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
	
	//Add new entity costs window
	public void addNewEntityCostsWindow() {
		try {
			String entityId = tblEntities.getSelectionModel().getSelectedItem().toString().substring(1, 3);
			System.out.println("Entity id is: " + entityId);
			
			/*
			 * lblEntityIdCosts, lblRental, lblUtilities, lblEmployeesSalariesSum, 
			   lblIncome, lblProfit
				 txtFldEntityIdCosts, txtFldRental, txtFldUtilities, txtFldEmployeesSalariesSum,
				 txtFldIncome, txtFldProfit
					btnSendCosts, btnCancelCosts
			 * 
			 */
			
			//Creating all the Labels
			lblEntityIdCosts = labelCreatorHelper("Entity id:", 25, 25);
			lblRental = labelCreatorHelper("Rental:", 175, 25);
			lblUtilities = labelCreatorHelper("Utilities:", 25, 100);
			lblEmployeesSalariesSum = labelCreatorHelper("Sum of salaries:", 175, 100);
			lblIncome = labelCreatorHelper("Income:", 25, 185);
			lblProfit = labelCreatorHelper("Profit:", 175, 185);
			lblMonth = labelCreatorHelper("Month:", 325, 25); 
			lblYear = labelCreatorHelper("Year:", 400, 25);
					
			//Creating all the TextFields
			txtFldEntityIdCosts = textFieldCreatorHelper(25, 45, 100, 35);
				txtFldEntityIdCosts.setText(entityId);
				txtFldEntityIdCosts.setEditable(false);
			txtFldRental = textFieldCreatorHelper(175, 45, 100, 35);
				txtFldRental.setText("0.00");
				txtFldRental.setOnMouseExited(e -> refreshProfit());
			txtFldUtilities = textFieldCreatorHelper(25, 120, 100, 35);
				txtFldUtilities.setText("0.00");
				txtFldUtilities.setOnMouseExited(e -> refreshProfit());
			txtFldEmployeesSalariesSum = textFieldCreatorHelper(175, 120, 100, 35);
				txtFldEmployeesSalariesSum.setText("0.00");
				txtFldEmployeesSalariesSum.setOnMouseExited(e -> refreshProfit());
			txtFldIncome = textFieldCreatorHelper(25, 205, 100, 35);
				txtFldIncome.setText("0.00");
				txtFldIncome.setOnMouseExited(e -> refreshProfit());
			txtFldProfit = textFieldCreatorHelper(175, 205, 100, 35);
				txtFldProfit.setOnMouseExited(e -> refreshProfit());
				
			txtFldMonth = textFieldCreatorHelper(365, 20, 30, 15);
				txtFldMonth.setText("01");
			txtFldYear = textFieldCreatorHelper(430, 20, 50, 15);
				txtFldYear.setText("2023");
			
			//Creating the buttons
			btnSendCosts = buttonCreatorHelper("Send", 320, 80, 70, 20);
				btnSendCosts.setStyle("-fx-font: 14 Euphorigenic; -fx-base: #05F81B");
				btnSendCosts.setOnAction(e -> newEntityCostsSendButton());
			btnCancelCosts = buttonCreatorHelper("Cancel", 320, 110, 70, 20);
				btnCancelCosts.setStyle("-fx-font: 14 Euphorigenic; -fx-base: #FF0500");
				btnCancelCosts.setOnAction(e -> addNewEntityCostsStage.close());
			btnRefreshProfit = buttonCreatorHelper("Refresh", 280, 205, 75, 35);
				btnRefreshProfit.setOnAction(e -> refreshProfit());
			
			
			addNewEntityCostsContenedor = new Pane();
			addNewEntityCostsContenedor.getChildren().addAll(lblEntityIdCosts, lblRental, lblUtilities, 
					lblEmployeesSalariesSum, lblIncome, lblProfit, txtFldEntityIdCosts, txtFldRental, 
					txtFldUtilities, txtFldEmployeesSalariesSum,txtFldIncome, txtFldProfit,
					btnSendCosts, btnCancelCosts, btnRefreshProfit,
					lblMonth, lblYear, txtFldMonth, txtFldYear);
			
			addNewEntityCostsScene = new Scene(addNewEntityCostsContenedor);
			addNewEntityCostsStage = new Stage();
			addNewEntityCostsStage.setTitle("Add costs to the selected entity");
			addNewEntityCostsStage.setMinWidth(500);
			addNewEntityCostsStage.setMinHeight(300);
			addNewEntityCostsStage.setScene(addNewEntityCostsScene);
			addNewEntityCostsStage.show();
			
			
			
		}catch(Exception e) {
			System.out.println("Nothing selected, please try again.");
		}
	

	
		
	}
	public void addNewEntityPositionWindow() {

		try {
			String entityId = tblEntities.getSelectionModel().getSelectedItem().toString().substring(1, 3);
			System.out.println("Entity id is: " + entityId);
			
		/*
		Label lblPositionId, lblEntityPositionName, lblEntityIdPositions;
		TextField txtFldPositionId, txtFldEntityPositionName, txtFldEntityIdPosition;
		Button btnSendPosition, btnCancelPosition;
		*/
			//Labels
			lblEntityIdPositions = labelCreatorHelper("Entity id:", 25, 25);
			lblEntityPositionName = labelCreatorHelper("Position:", 25, 100);
			
			//TextFields
			txtFldEntityIdPosition = textFieldCreatorHelper(25, 45, 100, 35);
				txtFldEntityIdPosition.setText(entityId);
				txtFldEntityIdPosition.setEditable(false);
			
			txtFldEntityPositionName = textFieldCreatorHelper(25, 120, 100, 35);
			
			
			//Buttons
			btnSendPosition = buttonCreatorHelper("Send", 320, 80, 70, 20);
				btnSendPosition.setStyle("-fx-font: 14 Euphorigenic; -fx-base: #05F81B");
				btnSendPosition.setOnAction(e -> newEntityPositionSendButton());
			btnCancelPosition = buttonCreatorHelper("Cancel", 320, 110, 70, 20);
				btnCancelPosition.setStyle("-fx-font: 14 Euphorigenic; -fx-base: #FF0500");
				btnCancelPosition.setOnAction(e -> addNewEntityPositionStage.close());
			
		
		
			addNewEntityPositionContenedor = new Pane();
			addNewEntityPositionContenedor.getChildren().addAll(lblEntityPositionName, lblEntityIdPositions,
						txtFldEntityPositionName, txtFldEntityIdPosition,
						btnSendPosition, btnCancelPosition);
		
			addNewEntityPositionScene = new Scene(addNewEntityPositionContenedor);
			addNewEntityPositionStage = new Stage();
			addNewEntityPositionStage.setTitle("Add new position to the selected entity");
			addNewEntityPositionStage.setMinWidth(500);
			addNewEntityPositionStage.setMinHeight(300);
			addNewEntityPositionStage.setScene(addNewEntityPositionScene);
			addNewEntityPositionStage.show();
			
		}catch (Exception e) {
			System.out.println("Nothing selected, please try again.");
		}
		
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
	
	//Methods having something in common with data base
	
	//New Entity Send Button
	public void newEntitySendButton() {
		/*
		txtFldEntityId, txtFldEntityName, txtFldEntityCity, txtFldEntityStreet, txtFldEntityStreetNumber, 
		txtFldEntityZipCode
		*/
		Entity newEntity = new Entity();
		newEntity.setEntity_id(txtFldEntityId.getText());
		newEntity.setEntity_name(txtFldEntityName.getText());
		newEntity.setEntity_city(txtFldEntityCity.getText());
		newEntity.setEntity_street(txtFldEntityStreet.getText());
		newEntity.setEntity_street_number(txtFldEntityStreetNumber.getText());
		newEntity.setEntity_zip_code(txtFldEntityZipCode.getText());
		
		newEntity.addNewEntity();
		
		allEntitiesShowMethod();
		
		addNewEntityStage.close();
	}
	
	//New Entity Costs Send Button
	public void newEntityCostsSendButton(){
		
		/*
		 * txtFldEntityIdCosts, txtFldRental, txtFldUtilities, txtFldEmployeesSalariesSum,
				 txtFldIncome, txtFldProfit
		 */
		
		try {
		//For now creating a new Entity object, then I want to have a reference to the
		// objects that already have been created.
		//I will try to do it via Serialization and a .txt file or making the objects again
		// restoring the parameters from database.
		
			Entity newEntity = new Entity();
			newEntity.setEntity_id(txtFldEntityIdCosts.getText());
			newEntity.setEntity_rental(Double.parseDouble(txtFldRental.getText()));
			newEntity.setEntity_utilities(Double.parseDouble(txtFldUtilities.getText()));
			newEntity.setEntity_employees_salary_sum(Double.parseDouble(txtFldEmployeesSalariesSum.getText()));
			newEntity.setEntity_income(Double.parseDouble(txtFldIncome.getText()));
			newEntity.setEntity_profit(Double.parseDouble(txtFldProfit.getText()));
			newEntity.setCosts_Month(txtFldMonth.getText());
			newEntity.setCosts_Year(txtFldYear.getText());
			
			newEntity.addCostsToEntity();
			addNewEntityCostsStage.close();
			
		}catch(Exception e) {
			System.out.println("The values are not compatible");
		}
		
		
		
	}
	
	//All Entities Show Method
	public void allEntitiesShowMethod() {
		//The following statement removes the data before so no duplicates appearing
		// after opening the table again.
		tblEntities.getColumns().clear();
		/*
		ObservableList<ObservableList> entityData;
		TableView<Entity> tblEntities;
		TableColumn tblClmnEntityId, tblClmnEntityName, tblClmnEntityStreet;
		 */
		entityData = FXCollections.observableArrayList();
		
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
			//String sql = "SELECT entity_name FROM Entities;";
			String sql = "SELECT entity_id AS id, entity_name AS Entity, street AS Street FROM Entities;";
			
			//Executing query
			ResultSet rs = myStmt.executeQuery(sql);
			
			/***************************************************
			 * TABLE COLUMN ADDED DYNAMICALLY *
			 *********************************************/
			
			for(int i=0 ; i<rs.getMetaData().getColumnCount() ; i++) {
				//We are using non property style for making dynamic table
				final int j = i;
				TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
				col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>(){
					public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
						return new SimpleStringProperty(param.getValue().get(j).toString());
					}
				});
				tblEntities.getColumns().addAll(col);
				//System.out.println("Column ["+i+"] ");
			}
			
			/*********************************
			 * DATA ADDED TO OBSERVABLELIST *
			 *********************************/
			
			while(rs.next()) {
				//Iterate Row
				ObservableList<String> row = FXCollections.observableArrayList();
				for(int i=1 ; i<=rs.getMetaData().getColumnCount() ; i++) {
					//Iterate Column
					row.add(rs.getString(i));
				}
				//System.out.println("Row added " + row );
				entityData.add(row);	
			}
			
			tblEntities.setItems(entityData);
			
			rs.close();
			myConnection.close();
			
			//Adding scroll
			tblEntities.setMaxWidth(241);
			tblEntities.setMaxHeight(400);
				
			
		}catch(Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	public void refreshProfit() {
		try {
		Double rentalRefresh = Double.parseDouble(txtFldRental.getText());
		Double utilitiesRefresh = Double.parseDouble(txtFldUtilities.getText());
		Double employees_salary_sumRefresh = Double.parseDouble(txtFldEmployeesSalariesSum.getText());
		Double incomeRefresh = Double.parseDouble(txtFldIncome.getText());
		
		//Double profit = Double.parseDouble(txtFldProfit.getText());
		Double profitRefresh;
		profitRefresh = incomeRefresh - rentalRefresh - utilitiesRefresh - employees_salary_sumRefresh;
		Math.round(profitRefresh);
		
		String profitRefreshString;
		profitRefreshString = profitRefresh.toString();
		
		txtFldProfit.setText(profitRefreshString);
		
		}catch(Exception e) {
			System.out.println("Not valid numeric type");
		}
	}
	
	
	public void showAllInformationAboutTheEntities() {
		//The following statement removes the data before so no duplicates appearing
				// after opening the table again.
				tblEntities.getColumns().clear();
				/*
				ObservableList<ObservableList> entityData;
				TableView<Entity> tblEntities;
				TableColumn tblClmnEntityId, tblClmnEntityName, tblClmnEntityStreet;
				 */
				entityData = FXCollections.observableArrayList();
				
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
					//String sql = "SELECT entity_name FROM Entities;";
					String sql = "SELECT * FROM Entities JOIN Entities_costs "
							+ "ON Entities.entity_id = Entities_costs.entity_id;";
					
					//Executing query
					ResultSet rs = myStmt.executeQuery(sql);
					
					/***************************************************
					 * TABLE COLUMN ADDED DYNAMICALLY *
					 *********************************************/
					
					for(int i=0 ; i<rs.getMetaData().getColumnCount() ; i++) {
						//We are using non property style for making dynamic table
						final int j = i;
						TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
						col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>(){
							public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
								return new SimpleStringProperty(param.getValue().get(j).toString());
							}
						});
						tblEntities.getColumns().addAll(col);
						//System.out.println("Column ["+i+"] ");
					}
					
					/*********************************
					 * DATA ADDED TO OBSERVABLELIST *
					 *********************************/
					
					while(rs.next()) {
						//Iterate Row
						ObservableList<String> row = FXCollections.observableArrayList();
						for(int i=1 ; i<=rs.getMetaData().getColumnCount() ; i++) {
							//Iterate Column
							row.add(rs.getString(i));
						}
						//System.out.println("Row added " + row );
						entityData.add(row);	
					}
					
					tblEntities.setItems(entityData);
					
					rs.close();
					myConnection.close();
						
					//Adding scroll
					tblEntities.setMaxWidth(500);
					tblEntities.setMaxHeight(400);
					
					
				}catch(Exception e) {
					System.err.println(e.getClass().getName() + ": " + e.getMessage());
					System.exit(0);
				}
	}
	
	//New Entity Position Send Button
	public void newEntityPositionSendButton() {
		
		try {
			//For now creating a new Entity object, then I want to have a reference to the
			// objects that already have been created.
			//I will try to do it via Serialization and a .txt file or making the objects again
			// restoring the parameters from database.
			
			//TextField txtFldPositionId, txtFldEntityPositionName, txtFldEntityIdPosition
			
				Entity newEntity = new Entity();
				newEntity.setEntity_id(txtFldEntityIdPosition.getText());
				newEntity.setEntity_position_name(txtFldEntityPositionName.getText());
				
				newEntity.addPositionToEntity();
				addNewEntityPositionStage.close();
				
			}catch(Exception e) {
				System.out.println("The values are not compatible");
			}
		
	}
	
	public void showFullEntityAddress() {
		Label newLabel;
		try {
			String entityId = tblEntities.getSelectionModel().getSelectedItem().toString().substring(1, 3);
			System.out.println("Entity id is: " + entityId);
			
			
			
			Entity newEntity = new Entity();
			newEntity.setEntity_id(entityId);
			
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Bakery_Managment_System", "root", "1234");
			Statement myStmt = myConnection.createStatement();
			
			String sql = "SELECT street, street_number, city, zip_code "
					+ "FROM Entities "
					+ "WHERE Entities.entity_id = '" + entityId + "';";
			
			ResultSet myResultSet = myStmt.executeQuery(sql);
			//Going trough myResultSet
			while(myResultSet.next()) {
				String result;
				
				result = myResultSet.getString("street") + " " + myResultSet.getString("street_number") + ", " + myResultSet.getString("city") + " " + myResultSet.getString("zip_code");
				
				System.out.println(result);
				
				
				newLabel = new Label(result);
					newLabel.setTranslateX(350);
					newLabel.setTranslateY(100);
					
				temporaryAddressLabelArrayList.add(newLabel);
				
				contenedorEntities.getChildren().add(newLabel);
				
				btnShowFullAddress.setVisible(false);
				btnHideFullAddress.setVisible(true);
					
			}	
			//myStmt.executeUpdate(sql);
			myResultSet.close();
			myConnection.close();
			
		}catch(Exception e) {
			System.out.println("Nothing selected, please try again.");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
	}
	public void hideFullEntityAddress() {
		
		//temporaryAddressLabelArrayList.get(0);
		contenedorEntities.getChildren().remove(temporaryAddressLabelArrayList.get(0));
		temporaryAddressLabelArrayList.remove(0);
		
		btnShowFullAddress.setVisible(true);
		btnHideFullAddress.setVisible(false);
	}
	public void showPositions() {
		/*
		 TableView tblPositions;
		 ObservableList<ObservableList> positionsList;
		 */
		
		String position_entityId = tblEntities.getSelectionModel().getSelectedItem().toString().substring(1, 3);
		System.out.println("Entity id is: " + position_entityId);
		
		tblPositions.getColumns().clear();
		positionsList = FXCollections.observableArrayList();
		
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
			//String sql = "SELECT entity_name FROM Entities;";
			String sql = "SELECT position_name FROM Positions WHERE entity_id='" + position_entityId + "';";
			
			//Executing query
			ResultSet rs = myStmt.executeQuery(sql);
			
			/***************************************************
			 * TABLE COLUMN ADDED DYNAMICALLY *
			 *********************************************/
			
			for(int i=0 ; i<rs.getMetaData().getColumnCount() ; i++) {
				//We are using non property style for making dynamic table
				final int j = i;
				TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
				col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>(){
					public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
						return new SimpleStringProperty(param.getValue().get(j).toString());
					}
				});
				tblPositions.getColumns().addAll(col);
				//System.out.println("Column ["+i+"] ");
			}
			
			
			/*********************************
			 * DATA ADDED TO OBSERVABLELIST *
			 *********************************/
			
			while(rs.next()) {
				//Iterate Row
				ObservableList<String> row = FXCollections.observableArrayList();
				for(int i=1 ; i<=rs.getMetaData().getColumnCount() ; i++) {
					//Iterate Column
					row.add(rs.getString(i));
				}
				//System.out.println("Row added " + row );
				positionsList.add(row);	
			}
			
			tblPositions.setItems(positionsList);
			
			rs.close();
			myConnection.close();
				
			tblPositions.setVisible(true);
			btnShowPositions.setVisible(false);
			btnHidePositions.setVisible(true);
			
			//Adding scroll
			tblPositions.setMaxWidth(102);
			tblPositions.setMaxHeight(400);
			
			
		}catch(Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
		
		
	}
	
	public void hidePositions() {
		tblPositions.setVisible(false);
		btnShowPositions.setVisible(true);
		btnHidePositions.setVisible(false);
		
	}
	
	/*******************************************
	
	EMPLOYEES VIEW BELOW
	
	********************************************/
	
	public void addNewEmployeeWindow() {
		
		/*
		 
		 lblEmplIdAddNewEmpl, lblEntityIdAddNewEmpl, lblFirstName, lblLastName, lblStartDate, lblEndDate, lblSalary
		 txtFldEmplIdAddNewEmpl, txtFldEntityIdAddNewEmpl, txtFldFirstName, txtFldLastName, txtFldStartDate, txtFldEndDate, txtFldSalary
		 btnSendNewEmployee, btnCancelNewEmployee
		 
		 */
		
		
		
		//Creating all the Labels
		lblEmplIdAddNewEmpl = labelCreatorHelper("Empl id:", 25, 25);
		lblEntityIdAddNewEmpl = labelCreatorHelper("Entity id:", 175, 25);
		lblFirstName = labelCreatorHelper("First Name:", 25, 100);
		lblLastName = labelCreatorHelper("Last Name:", 175, 100);
		lblStartDate = labelCreatorHelper("Start Date:", 25, 185);
		lblEndDate = labelCreatorHelper("End Date:", 175, 185);
		lblSalary = labelCreatorHelper("Salary:", 325, 185);
		
		//Creating all the TextFields
		txtFldEmplIdAddNewEmpl = textFieldCreatorHelper(25, 45, 100, 35);
		txtFldEntityIdAddNewEmpl = textFieldCreatorHelper(175, 45, 100, 35);
		txtFldFirstName = textFieldCreatorHelper(25, 120, 100, 35);
		txtFldLastName = textFieldCreatorHelper(175, 120, 100, 35);
		//txtFldStartDate = textFieldCreatorHelper(25, 205, 100, 35);
		//txtFldEndDate = textFieldCreatorHelper(175, 205, 100, 35);
		txtFldSalary = textFieldCreatorHelper(325, 205, 100, 35);
		
		startDate = new DatePicker();
			startDate.setTranslateX(25);
			startDate.setTranslateY(205);
			startDate.setMaxWidth(100);
			startDate.setMaxHeight(35);
		endDate = new DatePicker();
			endDate.setTranslateX(175);
			endDate.setTranslateY(205);
			endDate.setMaxWidth(100);
			endDate.setMaxHeight(35);
			
		//Creating buttons to send and cancel
		//btnSendNewEntity, btnCancelNewEntity
		btnSendNewEmployee = buttonCreatorHelper("Send", 320, 80, 70, 20);
			btnSendNewEmployee.setStyle("-fx-font: 14 Euphorigenic; -fx-base: #05F81B");
			btnSendNewEmployee.setOnAction(e -> newEmployeeSendButton());
		btnCancelNewEmployee = buttonCreatorHelper("Cancel", 320, 110, 70, 20);
			btnCancelNewEmployee.setStyle("-fx-font: 14 Euphorigenic; -fx-base: #FF0500");
			btnCancelNewEmployee.setOnAction(e -> addNewEmployeeStage.close());
		
		
		
		
		addNewEmployeeContenedor = new Pane();
		addNewEmployeeContenedor.getChildren().addAll( lblEmplIdAddNewEmpl, lblEntityIdAddNewEmpl, 
								lblFirstName, lblLastName, lblStartDate, lblEndDate, lblSalary,
								txtFldEmplIdAddNewEmpl, txtFldEntityIdAddNewEmpl, txtFldFirstName, 
								txtFldLastName, /*txtFldStartDate, txtFldEndDate, */txtFldSalary,
								btnSendNewEmployee, btnCancelNewEmployee,
								startDate, endDate);
		addNewEmployeeScene = new Scene(addNewEmployeeContenedor);
		addNewEmployeeStage = new Stage();
		addNewEmployeeStage.setTitle("Add new employee");
		addNewEmployeeStage.setMinWidth(500);
		addNewEmployeeStage.setMinHeight(300);
		addNewEmployeeStage.setScene(addNewEmployeeScene);
		addNewEmployeeStage.show();
		
		
	}
	
	public void newEmployeeSendButton() {
		/*
		txtFldEmplIdAddNewEmpl, txtFldEntityIdAddNewEmpl, txtFldFirstName, txtFldLastName, txtFldStartDate, txtFldEndDate, txtFldSalary
		startDate, endDate
		*/
		
		Employee newEmployee = new Employee();
		newEmployee.setEmpl_id(txtFldEmplIdAddNewEmpl.getText());
		newEmployee.setEntity_id(txtFldEntityIdAddNewEmpl.getText());
		newEmployee.setFirst_name(txtFldFirstName.getText());
		newEmployee.setLast_name(txtFldLastName.getText());
		/***************************
		 Trying to get the date from the DatePicker
		 ***************************/
		
		newEmployee.setStart_date(startDate.getValue());
		newEmployee.setEnd_date(endDate.getValue());
		
		/***************************
		 End of getting the date from the DatePicker
		 ***************************/
		
		newEmployee.setSalary(Double.parseDouble(txtFldSalary.getText()));
		
		//Adding to database
		newEmployee.addNewEmployee();

		
		showEmployees();
		
		addNewEmployeeStage.close();
	}
	
	//Method for showing all the employees in the tableview
	public void showEmployees() {
		
		/*
		 TableView tblEmployees;
		 ObservableList<ObservableList> employeesList;
		 */
		
		//The following statement removes the data before so no duplicates appearing
		// after opening the table again.
		tblEmployees.getColumns().clear();
		
		employeesList = FXCollections.observableArrayList();
		
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
			//String sql = "SELECT entity_name FROM Entities;";
			String sql = "SELECT empl_id AS id, first_name, last_name FROM Employees;";
			
			//Executing query
			ResultSet rs = myStmt.executeQuery(sql);
			
			/***************************************************
			 * TABLE COLUMN ADDED DYNAMICALLY *
			 *********************************************/
			
			for(int i=0 ; i<rs.getMetaData().getColumnCount() ; i++) {
				//We are using non property style for making dynamic table
				final int j = i;
				TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
				col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>(){
					public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
						return new SimpleStringProperty(param.getValue().get(j).toString());
					}
				});
				tblEmployees.getColumns().addAll(col);
				//System.out.println("Column ["+i+"] ");
			}
			
			/*********************************
			 * DATA ADDED TO OBSERVABLELIST *
			 *********************************/
			
			while(rs.next()) {
				//Iterate Row
				ObservableList<String> row = FXCollections.observableArrayList();
				for(int i=1 ; i<=rs.getMetaData().getColumnCount() ; i++) {
					//Iterate Column
					row.add(rs.getString(i));
				}
				//System.out.println("Row added " + row );
				employeesList.add(row);	
			}
			
			tblEmployees.setItems(employeesList);
			
			rs.close();
			myConnection.close();
			
			//Adding scroll
			tblEmployees.setMaxWidth(241);
			tblEmployees.setMaxHeight(400);
				
			
		}catch(Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
		
	}
	
	//Method for show the delete an employee window with the confirmation action
	public void deleteEmployee() {
		
		/*
		Pane deleteEmployeeContenedor;
		Scene deleteEmployeeScene;
		Stage deleteEmployeeStage;
		Label lblConfirmEmployeeDelete;
		Button btnConfirmEmployeeDelete, btnCancelEmployeeDelete;
		*/
		try {
			String employee_emplId = tblEmployees.getSelectionModel().getSelectedItem().toString().substring(1, 3);
			System.out.println("Employee id is: " + employee_emplId);
		
			lblConfirmEmployeeDelete = labelCreatorHelper("Confirm the elimination of the selected employee: ", 20, 20);
			Label lblSelectedEmployee = labelCreatorHelper(employee_emplId, 145, 40);
			lblAchtungEmployeeDelete = labelCreatorHelper("Please, note this change cannot be undone!", 20, 60);
		
			btnConfirmEmployeeDelete = buttonCreatorHelper("Send", 20, 90, 70, 20);
				btnConfirmEmployeeDelete.setStyle("-fx-font: 14 Euphorigenic; -fx-base: #05F81B");
				btnConfirmEmployeeDelete.setOnAction(e -> deleteSelectedEmployee());
			btnCancelEmployeeDelete = buttonCreatorHelper("Cancel", 170, 90, 70, 20);
				btnCancelEmployeeDelete.setStyle("-fx-font: 14 Euphorigenic; -fx-base: #FF0500");
				btnCancelEmployeeDelete.setOnAction(e -> deleteEmployeeStage.close());
		
			deleteEmployeeContenedor = new Pane();
				deleteEmployeeContenedor.getChildren().addAll(lblConfirmEmployeeDelete, lblAchtungEmployeeDelete, btnConfirmEmployeeDelete, btnCancelEmployeeDelete, lblSelectedEmployee);
			deleteEmployeeScene = new Scene(deleteEmployeeContenedor);
			deleteEmployeeStage = new Stage();
				deleteEmployeeStage.setTitle("Delete the selected employee");
				deleteEmployeeStage.setMinWidth(300);
				deleteEmployeeStage.setMinHeight(200);
				deleteEmployeeStage.setScene(deleteEmployeeScene);
				deleteEmployeeStage.show();
		}catch(Exception e) {
			System.out.println("Nothing selected, please try again.");
		}
		
	}
	
	//Method for delete the selected employee once confirmed
	public void deleteSelectedEmployee() {
		try {
			String employee_emplId = tblEmployees.getSelectionModel().getSelectedItem().toString().substring(1, 3);
			System.out.println("Employee id is: " + employee_emplId);
			
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Bakery_Managment_System", "root", "1234");
			Statement myStmt = myConnection.createStatement();
			
			String sql = "DELETE FROM Employees WHERE Employees.empl_id = '" + employee_emplId + "';";
			
			myStmt.execute("USE Bakery_Managment_System;");
			myStmt.execute(sql);
			
			System.out.println("The selected employee has been deleted successfully!");
			
			myStmt.close();
			myConnection.close();
			
			showEmployees();
			deleteEmployeeStage.close();
			
		}catch(Exception e) {
			System.out.println("The selected employee couldn't be deleted. Please try again!");
			e.printStackTrace();
		}
		
	}
	
	public void exportEmployeesToTxt() {
		
		try {
			FileWriter writer = new FileWriter("Employees.txt");
			
			//writer.write(tblEmployees.getItems().get(1).toString() + "\n");
	
			for(Object row : tblEmployees.getItems()) {
				
				writer.write(row.toString() + "\n");
				
			}
			
			writer.close();
			
		}catch(IOException ex) {
			ex.printStackTrace();
			
		}
		
	}
	
	public void exportEmployeesToCsv() {
		
		try {
			FileWriter writer = new FileWriter("Employees.csv");
			
			//writer.write(tblEmployees.getItems().get(1).toString() + "\n");
	
			for(Object row : tblEmployees.getItems()) {
				
				writer.write(row.toString() + "\n");
				
			}
			
			writer.close();
			
		}catch(IOException ex) {
			ex.printStackTrace();
			
		}
		
	}
	
	
	public void showAllInformationAboutEmployees() {
		//The following statement removes the data before so no duplicates appearing
		// after opening the table again.
		tblEmployees.getColumns().clear();
		employeesList = FXCollections.observableArrayList();
		
		try {
			//Creating connection
			Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Bakery_Managment_System", "root", "1234");
			//Creating object statement
			Statement myStmt = myConnection.createStatement();
			//Preparing a sql instruction
			String sql = "SELECT * FROM Employees e JOIN Employees_contact_info einf\n"
					+ "ON e.empl_id = einf.empl_id;";
			
			//Executing query
			ResultSet rs = myStmt.executeQuery(sql);
			
			/***************************************************
			 * TABLE COLUMN ADDED DYNAMICALLY *
			 *********************************************/
			
			for(int i=0 ; i<rs.getMetaData().getColumnCount() ; i++) {
				//We are using non property style for making dynamic table
				final int j = i;
				TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
				col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>(){
					public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
						return new SimpleStringProperty(param.getValue().get(j).toString());
					}
				});
				tblEmployees.getColumns().addAll(col);
				//System.out.println("Column ["+i+"] ");
			}
			
			/*********************************
			 * DATA ADDED TO OBSERVABLELIST *
			 *********************************/
			
			while(rs.next()) {
				//Iterate Row
				ObservableList<String> row = FXCollections.observableArrayList();
				for(int i=1 ; i<=rs.getMetaData().getColumnCount() ; i++) {
					//Iterate Column
					row.add(rs.getString(i));
				}
				//System.out.println("Row added " + row );
				employeesList.add(row);	
			}
			
			tblEmployees.setItems(employeesList);
			
			rs.close();
			myConnection.close();
				
			//Adding scroll
			tblEmployees.setMaxWidth(500);
			tblEmployees.setMaxHeight(400);
			
			
		}catch(Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	
	public void addEmployeesContactInformation() {
		
		try {
			String employee_emplId = tblEmployees.getSelectionModel().getSelectedItem().toString().substring(1, 3);
			System.out.println("Employee id is: " + employee_emplId);
			
			/*
			lblEmplId, lblEmplEmail, lblEmplPhone, lblEmplCity, lblEmplZipCode, lblEmplStreet, lblEmplStreetNumber
			txtFldEmplId, txtFldEmplEmail, txtFldEmplPhone, txtFldEmplCity, txtFldEmplZipCode, txtFldEmplStreet, txtFldEmplStreetNumber
			*/
			
			
			// x 150; y 75
			
			//Creating all the Labels
			lblEmplId = labelCreatorHelper("Empl id:", 25, 25);
			lblEmplEmail = labelCreatorHelper("Email:", 25, 100);
			lblEmplPhone = labelCreatorHelper("Phone number: ", 225, 100);
			lblEmplCity = labelCreatorHelper("City:", 25, 175);
			lblEmplZipCode = labelCreatorHelper("Zip Code:", 225, 175);
			lblEmplStreet = labelCreatorHelper("Street:", 25, 250);
			lblEmplStreetNumber = labelCreatorHelper("Street Number:", 225, 250);
			
			warningLabel = labelCreatorHelper("Wrong Email", 70, 100);
				//warningLabel.setStyle("-fx-font: 14 Euphorigenic; -fx-base: #FF0500");
				warningLabel.setTextFill(Color.web("#FF0500"));
				warningLabel.setVisible(false);
			
			
			//Creating all the TextFields
			txtFldEmplId = textFieldCreatorHelper(25, 45, 35, 35);
				txtFldEmplId.setText(employee_emplId);
				txtFldEmplId.setEditable(false);
				txtFldEmplId.setFocusTraversable(false);
				txtFldEmplId.setStyle("-fx-font: 14 Euphorigenic; -fx-base: #C6C1C1");
			txtFldEmplEmail = textFieldCreatorHelper(25, 120, 150, 35);
				txtFldEmplEmail.setText("@");
				//txtFldEmplEmail.requestFocus();
			txtFldEmplPhone = textFieldCreatorHelper(225, 120, 150, 35);
				txtFldEmplPhone.setText("+48");
			txtFldEmplCity = textFieldCreatorHelper(25, 195, 150, 35);
			txtFldEmplZipCode = textFieldCreatorHelper(225, 195, 150, 35);
			txtFldEmplStreet = textFieldCreatorHelper(25, 270, 150, 35);
			txtFldEmplStreetNumber = textFieldCreatorHelper(225, 270, 150, 35);
			
			
			//Creating buttons to send and cancel
			//btnSendEmployeeContactInformation, btnCancelEmployeeContactInformation
			btnSendEmployeeContactInformation = buttonCreatorHelper("Send", 100, 320, 70, 20);
				btnSendEmployeeContactInformation.setStyle("-fx-font: 14 Euphorigenic; -fx-base: #05F81B");
				btnSendEmployeeContactInformation.setOnAction(e -> employeeContactInformationSendButton());
			btnCancelEmployeeContactInformation = buttonCreatorHelper("Cancel", 200, 320, 70, 20);
				btnCancelEmployeeContactInformation.setStyle("-fx-font: 14 Euphorigenic; -fx-base: #FF0500");
				btnCancelEmployeeContactInformation.setOnAction(e -> addEmployeeContactInformationStage.close());
			
			addEmployeeContactInformationContenedor = new Pane();
				addEmployeeContactInformationContenedor.getChildren().addAll(lblEmplId, lblEmplEmail, lblEmplPhone, lblEmplCity, lblEmplZipCode, lblEmplStreet, lblEmplStreetNumber,
								txtFldEmplId, txtFldEmplEmail, txtFldEmplPhone, txtFldEmplCity, txtFldEmplZipCode, txtFldEmplStreet, txtFldEmplStreetNumber,
								btnSendEmployeeContactInformation, btnCancelEmployeeContactInformation, warningLabel);
			addEmployeeContactInformationScene = new Scene(addEmployeeContactInformationContenedor);
			addEmployeeContactInformationStage = new Stage();
			addEmployeeContactInformationStage.setTitle("Add Contact Information to the following employee: " + employee_emplId);
			addEmployeeContactInformationStage.setMinWidth(430);
			addEmployeeContactInformationStage.setMinHeight(400);
			addEmployeeContactInformationStage.setScene(addEmployeeContactInformationScene);
			addEmployeeContactInformationStage.show();
			
			
		}catch (Exception e) {
			System.out.println("Nothing selected, please try again.");
			e.printStackTrace();
		}
		
	}
	
	
	public void employeeContactInformationSendButton() {
		/*
		txtFldEmplId, txtFldEmplEmail, txtFldEmplPhone, txtFldEmplCity, txtFldEmplZipCode, txtFldEmplStreet, txtFldEmplStreetNumber
		*/
		
		
		//String validEmail = txtFldEmplEmail.getText();
		if(isValid(txtFldEmplEmail.getText())) {
			Employee newEmpl = new Employee();
			newEmpl.setEmpl_id(txtFldEmplId.getText());
			
			newEmpl.setEmail(txtFldEmplEmail.getText());
			
			newEmpl.setPhone_number(txtFldEmplPhone.getText());
			newEmpl.setCity(txtFldEmplCity.getText());
			newEmpl.setZip_code(txtFldEmplZipCode.getText());
			newEmpl.setStreet(txtFldEmplStreet.getText());
			newEmpl.setStreet_number(txtFldEmplStreetNumber.getText());
			
			
			newEmpl.addEmployeeContactInformation();
			
			showEmployees();
			
			addEmployeeContactInformationStage.close();
			
			
			
		}else {
			warningLabel.setVisible(true);
			System.out.println("Wrong email");
		}
		
		
		/*
		
		Employee newEmpl = new Employee();
		newEmpl.setEmpl_id(txtFldEmplId.getText());
		
		newEmpl.setEmail(txtFldEmplEmail.getText());
		
		newEmpl.setPhone_number(txtFldEmplPhone.getText());
		newEmpl.setCity(txtFldEmplCity.getText());
		newEmpl.setZip_code(txtFldEmplZipCode.getText());
		newEmpl.setStreet(txtFldEmplStreet.getText());
		newEmpl.setStreet_number(txtFldEmplStreetNumber.getText());
		
		
		newEmpl.addEmployeeContactInformation();
		
		showEmployees();
		
		addEmployeeContactInformationStage.close();
		
		*/
	}
	
	public static boolean isValid(String email) {
		//try {
			//String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\\\.[a-zA-Z0-9_+&*-]"
			//				+ "+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,7}$";
			
			//String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
			
			String emailRegex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
			
		
			Pattern pat = Pattern.compile(emailRegex);
			if(email == null) {
				return false;
			}else {
				System.out.println(pat.matcher(email).matches());
				return pat.matcher(email).matches();
			}
		//}catch(Exception e) {
		//	System.out.println("Wrong email");
		//	return false;
	//	}
		
		
	}
	
	
}












