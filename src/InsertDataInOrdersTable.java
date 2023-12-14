import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class InsertDataInOrdersTable extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane pane = new GridPane();
		VBox fullPane = new VBox();
		Image introduction = new Image(new File("colorbackground.png").toURI().toString());
		BackgroundImage bImg = new BackgroundImage(introduction, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT, new BackgroundSize(250, 250, false, false, false, true));
		pane.setBackground(new Background(bImg));
		VBox insertDataVbox = new VBox();
		Label label = new Label("Insert Into Orders Table");
		label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
		Label idLabel = new Label("ID :");
		idLabel.setFont(Font.font("Times New Roman", 20));
		Spinner<Integer> idSpinner = new Spinner<Integer>();
		idSpinner.setEditable(true);
		HBox hboxId = new HBox();
		hboxId.getChildren().addAll(idLabel, idSpinner);
		hboxId.setSpacing(10);

		Label buildingLabel = new Label("Date : ");
		buildingLabel.setFont(Font.font("Times New Roman", 20));
		Spinner<Integer> dateSpinner = new Spinner<Integer>();
		dateSpinner.setEditable(true);
		HBox hboxBuilding = new HBox();
		hboxBuilding.getChildren().addAll(buildingLabel, dateSpinner);
		hboxBuilding.setSpacing(10);
		
		dataCustomer();
		dataCar();
		
		Label streetLabel = new Label("Customer : ");
		streetLabel.setFont(Font.font("Times New Roman", 20));
		ComboBox comboBoxCustomer = new ComboBox(FXCollections.observableArrayList(customerList));
		HBox hboxStreet = new HBox();
		hboxStreet.getChildren().addAll(streetLabel, comboBoxCustomer);
		hboxStreet.setSpacing(10);

		Label cityLabel = new Label("Car : ");
		cityLabel.setFont(Font.font("Times New Roman", 20));
		ComboBox comboBoxCar = new ComboBox(FXCollections.observableArrayList(carList));
		HBox hboxCity = new HBox();
		hboxCity.getChildren().addAll(cityLabel, comboBoxCar);
		hboxCity.setSpacing(10);
		
		insertDataVbox.setSpacing(10);
		fullPane.setSpacing(80);
		insertDataVbox.getChildren().addAll(hboxId, hboxBuilding, hboxStreet, hboxCity);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(3.5);
		pane.setVgap(3.5);
		pane.setAlignment(Pos.CENTER_LEFT);
		pane.add(label, 30, 10);
		pane.add(insertDataVbox, 50, 10);

		Button buttonInsert = new Button("Insert Data");
		buttonInsert.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		buttonInsert.setStyle("-fx-background-radius: 20");
		Button buttonBackToAddress = new Button("Back To Orders Table");
		buttonBackToAddress.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		buttonBackToAddress.setStyle("-fx-background-radius: 20");

		HBox hb = new HBox();
		hb.getChildren().addAll(buttonInsert, buttonBackToAddress);
		hb.setSpacing(20);
		pane.add(hb, 32, 30);

		buttonBackToAddress.setOnAction(e -> {
			OrdersTableScreen address = new OrdersTableScreen();
			Stage adressStage = new Stage();
			try {
				address.start(adressStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			primaryStage.close();
		});

		buttonInsert.setOnAction(e -> {

			try {
				Class.forName("com.mysql.jdbc.Driver");

				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cars", "root", "");

				Statement stmt = con.createStatement();
				data();
				String id = idSpinner.getEditor().getText();
				String date = dateSpinner.getEditor().getText();
				String customer = "";
				if (comboBoxCustomer.getValue() == null) {
					comboBoxCustomer.getPromptText();
				} else {
					customer = comboBoxCustomer.getSelectionModel().getSelectedItem().toString();
				}
				String car = "";
				if (comboBoxCar.getValue() == null) {
					comboBoxCar.getPromptText();
				} else {
					car = comboBoxCar.getSelectionModel().getSelectedItem().toString();
				}
				
				if (id.isEmpty() || date.isEmpty() || customer.isEmpty() || car.isEmpty()) {

				} else {
					if (idList.contains(Integer.parseInt(id))) {
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setTitle("Warning");
						alert.setContentText("The Process does not work Because You enter a duplicate key.");
						alert.showAndWait();
					}
					else {
					String sql = "INSERT INTO orders VALUES ('" + id + "','" + date + "','" + customer + "','" + car
							+ "')";
					int rowsAffected = stmt.executeUpdate(sql);
					if (rowsAffected > 0) {
						System.out.println("Data inserted successfully!");
					}
					else {
					}
					}
				}
			} catch (Exception w) {
				w.printStackTrace();
				System.out.println("Error on Building Data");
			}

			OrdersTableScreen address = new OrdersTableScreen();
			Stage adressStage = new Stage();
			try {
				address.start(adressStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			primaryStage.close();
		});

		Scene scene = new Scene(pane, 1400, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public List<Integer> idList = new ArrayList<>();

	public void data() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cars", "root", "");

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("select * from orders");

			while (rs.next()) {
				idList.add(rs.getInt("id"));
			}
			System.out.println(idList.contains(1111));
		} catch (Exception w) {
			w.printStackTrace();
			System.out.println("Error on Building Data");
		}
	}
	public List<Integer> customerList = new ArrayList<>();

	public void dataCustomer() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cars", "root", "");

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("select * from customer");

			while (rs.next()) {
				customerList.add(rs.getInt("id"));
			}
		} catch (Exception w) {
			w.printStackTrace();
			System.out.println("Error on Building Data");
		}
	}
	public List<String> carList = new ArrayList<>();

	public void dataCar() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cars", "root", "");

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("select * from car");

			while (rs.next()) {
				carList.add(rs.getString("name"));
			}
		} catch (Exception w) {
			w.printStackTrace();
			System.out.println("Error on Building Data");
		}
	}
	

}
