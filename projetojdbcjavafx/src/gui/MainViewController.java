package gui;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;
import model.services.SellerService;

public class MainViewController implements Initializable {
	@FXML
	private MenuItem menuItemSeller;
	
	@FXML
	private MenuItem menuItemDepartment;
	
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuItemSellerAction() {
		loadview("/gui/SellerList.fxml", (SellerListController controller)-> {
			controller.setSellerService(new SellerService());
			controller.updateTableView();
		});
	} 
	
	@FXML
	public void onMenuItemDepartmentAction() {
		loadview("/gui/DepartmentList.fxml", (DepartmentListController controller)-> {
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
		});
	} 
	
	@FXML
	public void onMenuItemAboutAction() {
		loadview("/gui/About.fxml", x-> {});
	} 
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		
		
	}
	
	@FXML
	public  synchronized <T> void  loadview(String absoluteName, Consumer<T> InitializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
	        VBox newVbox = loader.load();
	        Scene mainScene  = Main.getMainScene();
	        VBox mainVbox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
	        Node mainMenu = mainVbox.getChildren().get(0);
	        mainVbox.getChildren().clear();
	        mainVbox.getChildren().add(mainMenu);
	        mainVbox.getChildren().addAll(newVbox.getChildren());
	        T controller = loader.getController();
	        InitializingAction.accept(controller);
	       
		}
		catch(IOException e)
		{
		  	Alerts.showAlert("Io Exception","Error load view", e.getMessage(), AlertType.ERROR);
		}
	} 
	
	


}
