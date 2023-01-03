package application;

import model_base_de_datos.*;	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
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
	//Declaring MenuItem for Employees Menu
	
	//Declaring MenuItem for Products Menu
	
	//Declaring TableView and corresponding Label for showing the employees.
	Label lblEmployees;
	TableView<String> tblEmployees;
	TableColumn tblClmnId, tblClmnFirstName, tblClmnLastName;
	
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
			
			//Adding MenuItem to Menu
			mnuFile.getItems().addAll(mnuItmSave, mnuItmExit);
			
			
			
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
			
		contenedor.getChildren().addAll(lblEmployees, tblEmployees);
	}
	
	
}
