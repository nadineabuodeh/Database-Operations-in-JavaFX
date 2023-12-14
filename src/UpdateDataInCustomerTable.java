import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

public class UpdateDataInCustomerTable extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane pane = new GridPane();
		VBox fullPane = new VBox();
		Image introduction = new Image(new File("colorbackground.png").toURI().toString());
		BackgroundImage bImg = new BackgroundImage(introduction, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT, new BackgroundSize(250, 250, false, false, false, true));
		pane.setBackground(new Background(bImg));
		VBox insertDataVbox = new VBox();
		data();
		Label label = new Label("Update in Customer Table");
		label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
		Label idLabel = new Label("ID :");
		idLabel.setFont(Font.font("Times New Roman", 20));
		ComboBox comboBoxId = new ComboBox(FXCollections.observableArrayList(idList));

		HBox hboxId = new HBox();
		hboxId.getChildren().addAll(idLabel, comboBoxId);
		hboxId.setSpacing(10);

		Label buildingLabel = new Label("First Name : ");
		buildingLabel.setFont(Font.font("Times New Roman", 20));
		TextField f_nameTextField = new TextField();
		HBox hboxBuilding = new HBox();
		hboxBuilding.getChildren().addAll(buildingLabel, f_nameTextField);
		hboxBuilding.setSpacing(10);

		Label streetLabel = new Label("Last Name : ");
		streetLabel.setFont(Font.font("Times New Roman", 20));
		TextField l_nameTextField = new TextField();
		HBox hboxStreet = new HBox();
		hboxStreet.getChildren().addAll(streetLabel, l_nameTextField);
		hboxStreet.setSpacing(10);

		dataAddress();
		Label cityLabel = new Label("Address : ");
		cityLabel.setFont(Font.font("Times New Roman", 20));
		ComboBox comboBoxAddress = new ComboBox(FXCollections.observableArrayList(addressList));
		HBox hboxCity = new HBox();
		hboxCity.getChildren().addAll(cityLabel, comboBoxAddress);
		hboxCity.setSpacing(10);

		Label countryLabel = new Label("Job : ");
		countryLabel.setFont(Font.font("Times New Roman", 20));
		TextField JobTextField = new TextField();
		HBox hboxCountry = new HBox();
		hboxCountry.getChildren().addAll(countryLabel, JobTextField);
		hboxCountry.setSpacing(10);

		insertDataVbox.setSpacing(10);
		fullPane.setSpacing(80);
		insertDataVbox.getChildren().addAll(hboxId, hboxBuilding, hboxStreet, hboxCity, hboxCountry);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(3.5);
		pane.setVgap(3.5);
		pane.setAlignment(Pos.CENTER_LEFT);
		pane.add(label, 30, 10);
		pane.add(insertDataVbox, 50, 10);

		Button buttonUpdate = new Button("Update Data");
		buttonUpdate.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		buttonUpdate.setStyle("-fx-background-radius: 20");
		Button buttonBackToAddress = new Button("Back To Customer Table");
		buttonBackToAddress.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		buttonBackToAddress.setStyle("-fx-background-radius: 20");

		HBox hb = new HBox();
		hb.getChildren().addAll(buttonUpdate, buttonBackToAddress);
		hb.setSpacing(20);
		pane.add(hb, 32, 30);
		buttonUpdate.setOnAction(e -> {
			String sql = "UPDATE customer";
			try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cars", "root", "");

			Statement stmt = con.createStatement();
			String id = "";
			if (comboBoxId.getValue() == null) {
				comboBoxId.getPromptText();
			} else {
				id = comboBoxId.getSelectionModel().getSelectedItem().toString();
			}
			String f_name = f_nameTextField.getText();
			String l_name = l_nameTextField.getText();
			String address = "";
			if (comboBoxAddress.getValue() == null) {
				comboBoxAddress.getPromptText();
			} else {
				address = comboBoxAddress.getSelectionModel().getSelectedItem().toString();
			}
			String job = JobTextField.getText();
			if (id.isEmpty() && f_name.isEmpty() && l_name.isEmpty() && address.isEmpty() && job.isEmpty()) {
				sql = "select * from customer";
			} else {
				sql += " SET ";
				if (!(f_name.isEmpty())) {
					sql += "f_name = '" + f_name + "' , ";
				}
				if (!(l_name.isEmpty())) {
					sql += "l_name = '" + l_name + "' , ";
				}
				if (!(address.isEmpty())) {
					sql += "address = '" + address + "' , ";
				}
				if (!(job.isEmpty())) {
					sql += "job = '" + job + "' , ";
				}
				if(id.isEmpty()) {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setContentText("The Process does not work Because You did not enter the id to change.");
					alert.showAndWait();
					sql = "select * from customer";
				}
			}
			String[] splitArray = sql.split(" ");
			for (String string : splitArray) {
				System.out.println(string);
			}
			System.out.println("------------------");
			String[] newSQL = new String[splitArray.length - 1];
			if (splitArray[splitArray.length - 1].equalsIgnoreCase(",")) {
				for (int i = 0; i < newSQL.length; i++) {
					newSQL[i] = splitArray[i];
				}
				sql = "";

				for (String string : newSQL) {
					sql += string + " ";
				}
				if (!(id.isEmpty())) {
					sql += "Where id = " + id + ";";
				}
				System.out.println(sql);
				int rowsAffected = stmt.executeUpdate(sql);
				if (rowsAffected > 0) {
					System.out.println("Data inserted successfully!");
				} else {
				}
			}
			System.out.println(sql);
			
			}	catch (Exception e1) {
				e1.printStackTrace();
			}
			
			CustomerTableScreen address = new CustomerTableScreen();
			Stage adressStage = new Stage();
			try {
				address.start(adressStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			primaryStage.close();
		});

		buttonBackToAddress.setOnAction(e -> {

			CustomerTableScreen address = new CustomerTableScreen();
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

	public List<Integer> addressList = new ArrayList<>();

	public void dataAddress() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cars", "root", "");

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("select * from address");

			while (rs.next()) {
				addressList.add(rs.getInt("id"));
			}
		} catch (Exception w) {
			w.printStackTrace();
			System.out.println("Error on Building Data");
		}

	}

	public List<Integer> idList = new ArrayList<>();

	public void data() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cars", "root", "");

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("select * from customer");

			while (rs.next()) {
				idList.add(rs.getInt("id"));
			}
		} catch (Exception w) {
			w.printStackTrace();
			System.out.println("Error on Building Data");
		}

	}

}
