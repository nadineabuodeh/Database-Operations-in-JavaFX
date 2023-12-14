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

public class SelectDataInDeviceTable extends Application {
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
		Label label = new Label("Select From Device Table");
		label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
		Label idLabel = new Label("Number :");
		idLabel.setFont(Font.font("Times New Roman", 20));
		Spinner<Integer> numberSpinner = new Spinner<Integer>();
		numberSpinner.setEditable(true);
		HBox hboxId = new HBox();
		hboxId.getChildren().addAll(idLabel, numberSpinner);
		hboxId.setSpacing(10);

		Label buildingLabel = new Label("Name : ");
		buildingLabel.setFont(Font.font("Times New Roman", 20));
		TextField nameTextField = new TextField();
		HBox hboxBuilding = new HBox();
		hboxBuilding.getChildren().addAll(buildingLabel, nameTextField);
		hboxBuilding.setSpacing(10);

		Label streetLabel = new Label("Price : ");
		streetLabel.setFont(Font.font("Times New Roman", 20));
		Spinner<Integer> priceSpinner = new Spinner<Integer>();
		priceSpinner.setEditable(true);
		HBox hboxStreet = new HBox();
		hboxStreet.getChildren().addAll(streetLabel, priceSpinner);
		hboxStreet.setSpacing(10);

		Label cityLabel = new Label("Weight : ");
		cityLabel.setFont(Font.font("Times New Roman", 20));
		Spinner<Integer> weightSpinner = new Spinner<Integer>();
		weightSpinner.setEditable(true);
		HBox hboxCity = new HBox();
		hboxCity.getChildren().addAll(cityLabel, weightSpinner);
		hboxCity.setSpacing(10);

		Label countryLabel = new Label("Made : ");
		countryLabel.setFont(Font.font("Times New Roman", 20));
		TextField madeTextField = new TextField();
		HBox hboxCountry = new HBox();
		hboxCountry.getChildren().addAll(countryLabel, madeTextField);
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
		Button buttonBackToAddress = new Button("Back To Device Table");
		buttonBackToAddress.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		buttonBackToAddress.setStyle("-fx-background-radius: 20");

		HBox hb = new HBox();
		hb.getChildren().addAll(buttonSelect, buttonBackToAddress);
		hb.setSpacing(20);
		pane.add(hb, 32, 30);

		buttonSelect.setOnAction(e -> {
			String sql = "select * from device";
			String no = numberSpinner.getEditor().getText();
			String name = nameTextField.getText();
			String price = priceSpinner.getEditor().getText();
			String weight = weightSpinner.getEditor().getText();
			String made = madeTextField.getText();
			if (no.isEmpty() && name.isEmpty() && price.isEmpty() && weight.isEmpty() && made.isEmpty()) {
				sql = "select * from device";
			} else {
				sql += " WHERE ";
				if (!(no.isEmpty())) {
					sql += "no = " + no + " AND ";
				}
				if (!(name.isEmpty())) {
					sql += "name = '" + name + "' AND ";
				}
				if (!(price.isEmpty())) {
					sql += "price = " + price + " AND ";
				}
				if (!(weight.isEmpty())) {
					sql += "weight = " + weight + " AND ";
				}
				if (!(made.isEmpty())) {
					sql += "made = '" + made + "' AND ";
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

			DeviceTableScreen customer = new DeviceTableScreen();
			Stage adressStage = new Stage();
			try {
				customer.start(adressStage);
				customer.buildData(sql);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			primaryStage.close();
		});

		buttonBackToAddress.setOnAction(e -> {

			DeviceTableScreen customer = new DeviceTableScreen();
			Stage adressStage = new Stage();
			try {
				customer.start(adressStage);
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

	public List<Integer> numberList = new ArrayList<>();
	public List<String> nameList = new ArrayList<>();
	public List<Double> priceList = new ArrayList<>();
	public List<Double> weightList = new ArrayList<>();
	public List<String> madeList = new ArrayList<>();
	public Set<String> setListname;
	public Set<Double> setListprice;
	public Set<Double> setListweight;
	public Set<String> setListmade;
	public void data() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cars", "root", "");

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("select * from device");

			while (rs.next()) {
				numberList.add(rs.getInt("no"));
				nameList.add(rs.getString("name"));
				priceList.add(rs.getDouble("price"));
				weightList.add(rs.getDouble("weight"));
				madeList.add(rs.getString("made"));
			}
			setListweight = new LinkedHashSet<Double>(weightList);
			setListprice = new LinkedHashSet<Double>(priceList);
			setListmade = new LinkedHashSet<String>(madeList);
			setListname = new LinkedHashSet<String>(nameList);

		} catch (Exception w) {
			w.printStackTrace();
			System.out.println("Error on Building Data");
		}
	}

}
