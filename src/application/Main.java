package application;

import model_base_de_datos.*;
import entity.*;
import java.sql.*;
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
	ObservableList<ObservableList> entityData;
	TableView tblEntities;
	TableColumn tblClmnEntityId, tblClmnEntityName, tblClmnEntityStreet;
	Button btnAddNewEntity;
	Button btnAddNewEntityCosts, btnAddNewEntityPosition;
	Button btnShowAllInformationAboutTheEntities;
	Button btnShowFullAddress, btnShowCosts, btnShowPositions;
	Button btnHideFullAddress, btnHideCosts, btnHidePositions;
	ArrayList<Label> temporaryAddressLabelArrayList = new ArrayList<>();
	
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
				
			/*	
			//Creating columns for the table tblEmployees.	
			tblClmnEntityId = new TableColumn("Entity Id");
				tblClmnEntityId.setMinWidth(30);
				tblClmnEntityId.setMaxWidth(40);
			tblClmnEntityName = new TableColumn("Entity Name");
				tblClmnEntityName.setMinWidth(100);
				
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
			/*	
			tblClmnEntityStreet = new TableColumn("Street");
				tblClmnEntityStreet.setMinWidth(100);
			*/	
				
			
			//tblEntities.getColumns().addAll(tblClmnEntityId, tblClmnEntityName, tblClmnEntityStreet);
			
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
			btnShowCosts = buttonCreatorHelper("Show Costs", 140, 540, 120, 20);
				//btnShowCosts.setOnAction(e -> );
			btnShowPositions = buttonCreatorHelper("Show Positions", 265, 540, 120, 20);
				
			
				

			
			contenedorEntities = new Pane();
			contenedorEntities.getChildren().addAll(lblEntities, tblEntities, btnAddNewEntity,
											btnAddNewEntityCosts, btnAddNewEntityPosition,
											btnShowAllInformationAboutTheEntities,
											btnShowFullAddress, btnShowCosts, btnShowPositions,
											btnHideFullAddress);
			
			
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
	
}












