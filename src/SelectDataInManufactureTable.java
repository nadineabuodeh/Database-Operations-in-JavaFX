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

public class SelectDataInManufactureTable extends Application {
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
		Label label = new Label("Select From Manufacture Table");
		label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));

		Label buildingLabel = new Label("Name : ");
		buildingLabel.setFont(Font.font("Times New Roman", 20));
		TextField nameTextField = new TextField();
		HBox hboxBuilding = new HBox();
		hboxBuilding.getChildren().addAll(buildingLabel, nameTextField);
		hboxBuilding.setSpacing(10);

		Label streetLabel = new Label("Type : ");
		streetLabel.setFont(Font.font("Times New Roman", 20));
		TextField typeTextField = new TextField();
		HBox hboxStreet = new HBox();
		hboxStreet.getChildren().addAll(streetLabel, typeTextField);
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
		insertDataVbox.getChildren().addAll(hboxBuilding, hboxStreet, hboxCity, hboxCountry);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(3.5);
		pane.setVgap(3.5);
		pane.setAlignment(Pos.CENTER_LEFT);
		pane.add(label, 30, 10);
		pane.add(insertDataVbox, 40, 10);

		Button buttonSelect = new Button("Select Data");
		buttonSelect.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		buttonSelect.setStyle("-fx-background-radius: 20");
		Button buttonBackToAddress = new Button("Back To Manufacture Table");
		buttonBackToAddress.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		buttonBackToAddress.setStyle("-fx-background-radius: 20");

		HBox hb = new HBox();
		hb.getChildren().addAll(buttonSelect, buttonBackToAddress);
		hb.setSpacing(20);
		pane.add(hb, 32, 30);

		buttonSelect.setOnAction(e -> {
			String sql = "select * from manufacture";
			String name = nameTextField.getText();
			String type = typeTextField.getText();
			String city = cityTextField.getText();
			String country = countryTextField.getText();
			if (name.isEmpty() && type.isEmpty() && city.isEmpty() && country.isEmpty()) {
				sql = "select * from manufacture";
			} else {
				sql += " WHERE ";
				if (!(name.isEmpty())) {
					sql += "name = '" + name + "' AND ";
				}
				if (!(type.isEmpty())) {
					sql += "type = '" + type + "' AND ";
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

			ManufactureTableScreen manufacture = new ManufactureTableScreen();
			Stage adressStage = new Stage();
			try {
				manufacture.start(adressStage);
				manufacture.buildData(sql);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			primaryStage.close();
		});

		buttonBackToAddress.setOnAction(e -> {

			ManufactureTableScreen manufacture = new ManufactureTableScreen();
			Stage adressStage = new Stage();
			try {
				manufacture.start(adressStage);
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

	public List<String> nameList = new ArrayList<>();
	public List<String> typeList = new ArrayList<>();
	public List<String> cityList = new ArrayList<>();
	public List<String> CountryList = new ArrayList<>();
	public Set<String> setListname;
	public Set<String> setListType;
	public Set<String> setListCity;
	public Set<String> setListCountry;
	public void data() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cars", "root", "");

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("select * from manufacture");

			while (rs.next()) {
				nameList.add(rs.getString("name"));
				typeList.add(rs.getString("type"));
				cityList.add(rs.getString("city"));
				CountryList.add(rs.getString("country"));
			}
			setListCity = new LinkedHashSet<String>(cityList);
			setListType = new LinkedHashSet<String>(typeList);
			setListCountry = new LinkedHashSet<String>(CountryList);
			setListname = new LinkedHashSet<String>(nameList);


		} catch (Exception w) {
			w.printStackTrace();
			System.out.println("Error on Building Data");
		}
	}

}
