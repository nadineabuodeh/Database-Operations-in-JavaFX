import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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

public class SelectDataInAddressTable extends Application {
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
		Label label = new Label("Select From Address Table");
		label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
		Label idLabel = new Label("ID :");
		idLabel.setFont(Font.font("Times New Roman", 20));
		Spinner<Integer> idSpinner = new Spinner<Integer>();
		idSpinner.setEditable(true);
		HBox hboxId = new HBox();
		hboxId.getChildren().addAll(idLabel, idSpinner);
		hboxId.setSpacing(10);

		Label buildingLabel = new Label("Building : ");
		buildingLabel.setFont(Font.font("Times New Roman", 20));
		Spinner<Integer> buildingSpinner = new Spinner<Integer>();
		buildingSpinner.setEditable(true);
		HBox hboxBuilding = new HBox();
		hboxBuilding.getChildren().addAll(buildingLabel, buildingSpinner);
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

		Button buttonSelect = new Button("Select Data");
		buttonSelect.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		buttonSelect.setStyle("-fx-background-radius: 20");
		Button buttonBackToAddress = new Button("Back To Address Table");
		buttonBackToAddress.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		buttonBackToAddress.setStyle("-fx-background-radius: 20");

		HBox hb = new HBox();
		hb.getChildren().addAll(buttonSelect, buttonBackToAddress);
		hb.setSpacing(20);
		pane.add(hb, 32, 30);

		buttonSelect.setOnAction(e -> {
			String sql = "select * from address";
			String id = idSpinner.getEditor().getText();
			String building = buildingSpinner.getEditor().getText();
			String street = streetTextField.getText();
			String city = cityTextField.getText();
			String country = countryTextField.getText();
			if (id.isEmpty() && building.isEmpty() && street.isEmpty() && city.isEmpty() && country.isEmpty()) {
				sql = "select * from address";
			} else {
				sql += " WHERE ";
				if (!(id.isEmpty())) {
					sql += "id = '" + id + "' AND ";
				}
				if (!(building.isEmpty())) {
					sql += "buidling = '" + building + "' AND ";
				}
				if (!(street.isEmpty())) {
					sql += "street = '" + street + "' AND ";
				}
				if (!(city.isEmpty())) {
					sql += "city = '" + city + "' AND ";
				}
				if (!(country.isEmpty())) {
					sql += "country = '" + country + "' AND ";
				}
			}
			String[] splitArray = sql.split(" ");
			for (String string : splitArray) {
				System.out.println(string);
			}
			System.out.println("------------------");
			String[] newSQL = new String[splitArray.length - 1];
			if (splitArray[splitArray.length - 1].equalsIgnoreCase("And")) {
				for (int i = 0; i < newSQL.length; i++) {
					newSQL[i] = splitArray[i];
				}
				sql = "";

				for (String string : newSQL) {
					sql += string + " ";
				}
			}
			System.out.println(sql);

			AddressTableScreen address = new AddressTableScreen();
			Stage adressStage = new Stage();
			try {
				address.start(adressStage);
				address.buildData(sql);
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
	public List<Integer> buildingList = new ArrayList<>();
	public List<String> streetList = new ArrayList<>();
	public List<String> cityList = new ArrayList<>();
	public List<String> countryList = new ArrayList<>();
	public Set<Integer> setListBuilding;
	public Set<String> setListSreet;
	public Set<String> setListCity;
	public Set<String> setListCountry;

	public void data() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cars", "root", "");

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("select * from address");

			while (rs.next()) {
				idList.add(rs.getInt("id"));
				buildingList.add(rs.getInt("buidling"));
				streetList.add(rs.getString("street"));
				cityList.add(rs.getString("city"));
				countryList.add(rs.getString("country"));
			}
			setListBuilding = new LinkedHashSet<Integer>(buildingList);
			setListSreet = new LinkedHashSet<String>(streetList);
			setListCity = new LinkedHashSet<String>(cityList);
			setListCountry = new LinkedHashSet<String>(countryList);
			
			StringBuilder result = new StringBuilder();
			for (int i = 0; i < idList.size(); i++) {
				result.append("ID: ").append(idList.get(i)).append(", building: ").append(buildingList.get(i))
						.append(", street: ").append(streetList.get(i)).append(", city: ").append(cityList.get(i))
						.append(", country: ").append(countryList.get(i)).append("\n");
			}
			System.out.println(result);

		} catch (Exception w) {
			w.printStackTrace();
			System.out.println("Error on Building Data");
		}
	}

}
