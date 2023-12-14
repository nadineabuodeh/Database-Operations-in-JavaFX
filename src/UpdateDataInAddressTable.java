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

public class UpdateDataInAddressTable extends Application {
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
		Label label = new Label("Update in Address Table");
		label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
		Label idLabel = new Label("ID :");
		idLabel.setFont(Font.font("Times New Roman", 20));
		ComboBox comboBoxId = new ComboBox(FXCollections.observableArrayList(idList));

		HBox hboxId = new HBox();
		hboxId.getChildren().addAll(idLabel, comboBoxId);
		hboxId.setSpacing(10);

		Label buildingLabel = new Label("Building : ");
		buildingLabel.setFont(Font.font("Times New Roman", 20));
		Spinner<Integer> BuildingSpinner = new Spinner<Integer>();
		BuildingSpinner.setEditable(true);
		HBox hboxBuilding = new HBox();
		hboxBuilding.getChildren().addAll(buildingLabel, BuildingSpinner);
		hboxBuilding.setSpacing(10);

		Label streetLabel = new Label("Street : ");
		streetLabel.setFont(Font.font("Times New Roman", 20));
		TextField streetTextField = new TextField();
		HBox hboxStreet = new HBox();
		hboxStreet.getChildren().addAll(streetLabel, streetTextField);
		hboxStreet.setSpacing(10);

		Label cityLabel = new Label("City : ");
		cityLabel.setFont(Font.font("Times New Roman", 20));
		TextField cityTextField = new TextField();
		HBox hboxCity = new HBox();
		hboxCity.getChildren().addAll(cityLabel, cityTextField);
		hboxCity.setSpacing(10);

		Label countryLabel = new Label("Country : ");
		countryLabel.setFont(Font.font("Times New Roman", 20));
		TextField countryTextField = new TextField();
		HBox hboxCountry = new HBox();
		hboxCountry.getChildren().addAll(countryLabel, countryTextField);
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
		Button buttonBackToAddress = new Button("Back To Address Table");
		buttonBackToAddress.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		buttonBackToAddress.setStyle("-fx-background-radius: 20");

		HBox hb = new HBox();
		hb.getChildren().addAll(buttonUpdate, buttonBackToAddress);
		hb.setSpacing(20);
		pane.add(hb, 32, 30);
		buttonUpdate.setOnAction(e -> {
			String sql = "UPDATE address";
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
			String building = BuildingSpinner.getEditor().getText();
			String street = streetTextField.getText();
			String city = cityTextField.getText();
			String country = countryTextField.getText();
			if (id.isEmpty() && building.isEmpty() && street.isEmpty() && city.isEmpty() && country.isEmpty()) {
				sql = "select * from address";
			} else {
				sql += " SET ";
				if (!(building.isEmpty())) {
					sql += "buidling = " + building + " , ";
				}
				if (!(street.isEmpty())) {
					sql += "street = '" + street + "' , ";
				}
				if (!(city.isEmpty())) {
					sql += "city = '" + city + "' , ";
				}
				if (!(country.isEmpty())) {
					sql += "country = '" + country + "' , ";
				}
				if(id.isEmpty()) {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setContentText("The Process does not work Because You did not enter the id to change.");
					alert.showAndWait();
					sql = "select * from address";
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
			
			AddressTableScreen address = new AddressTableScreen();
			Stage adressStage = new Stage();
			try {
				address.start(adressStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			primaryStage.close();
		});

		buttonBackToAddress.setOnAction(e -> {

			AddressTableScreen address = new AddressTableScreen();
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

			ResultSet rs = stmt.executeQuery("select * from address");

			while (rs.next()) {
				idList.add(rs.getInt("id"));
			}

		} catch (Exception w) {
			w.printStackTrace();
			System.out.println("Error on Building Data");
		}
	}

}
