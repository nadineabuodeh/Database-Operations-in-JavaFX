
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
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class Car_PartTable extends Application {

	private ObservableList<ObservableList> data;

	private TableView tableview;

	public static void main(String[] args) {
		launch(args);
	}

	public void buildData(String sql) {
		data = FXCollections.observableArrayList();
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cars", "root", "");

			Statement stmt = con.createStatement();
			
			if(sql == null) {
				sql = "select * from car_part";
			}
			
			ResultSet rs = stmt.executeQuery(sql);
			
			tableview.getColumns().clear();
			tableview.getItems().clear();
			
			for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
				final int j = i;
				TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
				col.setCellValueFactory(
						new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
							public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
								return new SimpleStringProperty(param.getValue().get(j).toString());
							}
						});

				tableview.getColumns().addAll(col);
				System.out.println("Column [" + i + "] ");
			}

			while (rs.next()) {
				ObservableList<String> row = FXCollections.observableArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					row.add(rs.getString(i));
				}
				System.out.println("Row [1] added " + row);
				data.add(row);

			}

			tableview.setItems(data);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data");
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		tableview = new TableView();
		buildData(null);
		HBox hbox = new HBox();
		Label label = new Label("Car_Part Table");
		label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
		Button Search = new Button("Search");
		Search.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		Search.setStyle("-fx-background-radius: 20");

		Button Insert = new Button("Insert");
		Insert.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		Insert.setStyle("-fx-background-radius: 20");

		Button Delete = new Button("Delete");
		Delete.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		Delete.setStyle("-fx-background-radius: 20");
		hbox.getChildren().addAll(Search, Insert, Delete);
		hbox.setSpacing(20);
		hbox.setAlignment(Pos.CENTER);
		
		HBox h = new HBox();
		Button back = new Button("Home");
		back.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		back.setStyle("-fx-background-radius: 20");
		
		Button reset = new Button("Reset");
		reset.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		reset.setStyle("-fx-background-radius: 20");
		
		h.getChildren().addAll(back, reset);
		h.setSpacing(40);
		h.setAlignment(Pos.CENTER);
		VBox fullPane = new VBox();
		fullPane.setAlignment(Pos.CENTER);
		fullPane.getChildren().addAll(label, hbox, tableview,h);
		fullPane.setSpacing(50);
		tableview.setMaxWidth(180);
		tableview.setMaxHeight(200);
		HBox h1 = new HBox();
		h1.getChildren().addAll(fullPane, Select());
		Search.setOnAction(e ->{
			h1.getChildren().clear();
			h1.getChildren().addAll(fullPane,Select());
		});
		Insert.setOnAction(e ->{
			h1.getChildren().clear();
			h1.getChildren().addAll(fullPane,Insert());
		});
		Delete.setOnAction(e ->{
			h1.getChildren().clear();
			h1.getChildren().addAll(fullPane,Delete());
		});

		Image introduction = new Image(new File("colorbackground.png").toURI().toString());
		BackgroundImage bImg = new BackgroundImage(introduction, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT, new BackgroundSize(250, 250, false, false, false, true));
		h1.setBackground(new Background(bImg));
		h1.setSpacing(50);
		h1.setAlignment(Pos.CENTER);
		
		back.setOnAction(e -> {
			IntroScreen insertAddress = new IntroScreen();
			Stage adressStage = new Stage();
			try {
				insertAddress.start(adressStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			primaryStage.close();
		});
		reset.setOnAction(e -> {
			buildData(null);
		});	
		Scene scene = new Scene(h1, 1200, 700);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public GridPane Select() {
		GridPane pane = new GridPane();
		VBox fullPane = new VBox();
		Image introduction = new Image(new File("colorbackground.png").toURI().toString());
		BackgroundImage bImg = new BackgroundImage(introduction, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT, new BackgroundSize(250, 250, false, false, false, true));
		pane.setBackground(new Background(bImg));
		VBox insertDataVbox = new VBox();
		data();
		Label buildingLabel = new Label("Car : ");
		buildingLabel.setFont(Font.font("Times New Roman", 20));
		TextField carTextField = new TextField();
		HBox hboxBuilding = new HBox();
		hboxBuilding.getChildren().addAll(buildingLabel, carTextField);
		hboxBuilding.setSpacing(10);

		Label cityLabel = new Label("Part : ");
		cityLabel.setFont(Font.font("Times New Roman", 20));
		Spinner<Integer> partSpinner = new Spinner<Integer>();
		partSpinner.setEditable(true);
		HBox hboxCity = new HBox();
		hboxCity.getChildren().addAll(cityLabel, partSpinner);
		hboxCity.setSpacing(10);


		insertDataVbox.setSpacing(10);
		fullPane.setSpacing(80);
		insertDataVbox.getChildren().addAll(hboxBuilding, hboxCity);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(3.5);
		pane.setVgap(3.5);
		pane.setAlignment(Pos.CENTER_LEFT);
		pane.add(insertDataVbox, 0,0);

		Button buttonSelect = new Button("Select Data");
		buttonSelect.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		buttonSelect.setStyle("-fx-background-radius: 20");
		HBox hb = new HBox();
		hb.getChildren().addAll(buttonSelect);
		hb.setSpacing(20);
		pane.add(hb, 0,10);

		buttonSelect.setOnAction(e -> {
			String sql = "select * from car_part";
			
			String car = carTextField.getText();
			String part = partSpinner.getEditor().getText();
			
			if (car.isEmpty() && part.isEmpty()) {
				sql = "select * from car_part";
			} else {
				boolean partN = true;
				for (int i = 0; i < part.length(); i++) {
					if (!(Character.isDigit(part.charAt(i)))) {
						partN = false;
					}
				}
				if (!partN) {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setContentText("The Process does not work Because The part must be numeric");
					alert.showAndWait();
				} else {
				sql += " WHERE ";
				if (!(car.isEmpty())) {
					sql += "car = '" + car + "' AND ";
				}
				if (!(part.isEmpty())) {
					sql += "part = '" + part + "' AND ";
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
			buildData(sql);}
		});
		return pane;
	}
	public GridPane Insert() {
		GridPane pane = new GridPane();
		VBox fullPane = new VBox();
		Image introduction = new Image(new File("colorbackground.png").toURI().toString());
		BackgroundImage bImg = new BackgroundImage(introduction, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT, new BackgroundSize(250, 250, false, false, false, true));
		pane.setBackground(new Background(bImg));
		VBox insertDataVbox = new VBox();
		dataOriginCar();
		dataOriginPart();
		Label idLabel = new Label("Car :");
		idLabel.setFont(Font.font("Times New Roman", 20));
		ComboBox comboBoxIdCar = new ComboBox(FXCollections.observableArrayList(setListCarOrigin));
		HBox hboxId = new HBox();
		hboxId.getChildren().addAll(idLabel, comboBoxIdCar);
		hboxId.setSpacing(10);

		Label buildingLabel = new Label("Part : ");
		buildingLabel.setFont(Font.font("Times New Roman", 20));
		ComboBox comboBoxIdPart = new ComboBox(FXCollections.observableArrayList(setListPartOrigin));
		HBox hboxBuilding = new HBox();
		hboxBuilding.getChildren().addAll(buildingLabel, comboBoxIdPart);
		hboxBuilding.setSpacing(10);

		insertDataVbox.setSpacing(10);
		fullPane.setSpacing(80);
		insertDataVbox.getChildren().addAll(hboxId, hboxBuilding);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(3.5);
		pane.setVgap(3.5);
		pane.setAlignment(Pos.CENTER_LEFT);
		pane.add(insertDataVbox, 0,0);

		Button buttonInsert = new Button("Insert Data");
		buttonInsert.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		buttonInsert.setStyle("-fx-background-radius: 20");
		
		HBox hb = new HBox();
		hb.getChildren().addAll(buttonInsert);
		hb.setSpacing(20);
		pane.add(hb, 0,10);
		buttonInsert.setOnAction(e -> {

			try {
				Class.forName("com.mysql.jdbc.Driver");

				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cars", "root", "");

				Statement stmt = con.createStatement();
				data();
				String car = "";
				if (comboBoxIdCar.getValue() == null) {
					comboBoxIdCar.getPromptText();
				} else {
					car = comboBoxIdCar.getSelectionModel().getSelectedItem().toString();
				}
				String part = "";
				if (comboBoxIdPart.getValue() == null) {
					comboBoxIdPart.getPromptText();
				} else {
					part = comboBoxIdPart.getSelectionModel().getSelectedItem().toString();
				}
				if (car.isEmpty() || part.isEmpty()) {

				} 
				else {
					String sql = "INSERT INTO car_part VALUES ('" + car + "','" + part + "')";
					stmt.executeUpdate(sql);
					buildData(null);
				} } catch (Exception e1) {
						e1.printStackTrace();
					}
					});
		return pane;
	}
	
	public GridPane Delete() {
		GridPane pane = new GridPane();
		VBox fullPane = new VBox();
		Image introduction = new Image(new File("colorbackground.png").toURI().toString());
		BackgroundImage bImg = new BackgroundImage(introduction, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT, new BackgroundSize(250, 250, false, false, false, true));
		pane.setBackground(new Background(bImg));
		VBox insertDataVbox = new VBox();
		data();
		Label idLabel = new Label("Car :");
		idLabel.setFont(Font.font("Times New Roman", 20));
		ComboBox comboBoxIdCar = new ComboBox(FXCollections.observableArrayList(setListCar));
		HBox hboxId = new HBox();
		hboxId.getChildren().addAll(idLabel, comboBoxIdCar);
		hboxId.setSpacing(10);

		Label buildingLabel = new Label("Part : ");
		buildingLabel.setFont(Font.font("Times New Roman", 20));
		ComboBox comboBoxIdPart = new ComboBox(FXCollections.observableArrayList(setListpart));
		HBox hboxBuilding = new HBox();
		hboxBuilding.getChildren().addAll(buildingLabel, comboBoxIdPart);
		hboxBuilding.setSpacing(10);

		insertDataVbox.setSpacing(10);
		fullPane.setSpacing(80);
		insertDataVbox.getChildren().addAll(hboxId, hboxBuilding);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(3.5);
		pane.setVgap(3.5);
		pane.setAlignment(Pos.CENTER_LEFT);
		pane.add(insertDataVbox, 0, 0);
		tableview.setMaxWidth(200);
		tableview.setMaxHeight(200);

		Button buttonSelect = new Button("Delete Data");
		buttonSelect.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		buttonSelect.setStyle("-fx-background-radius: 20");
		HBox hb = new HBox();
		hb.getChildren().addAll(buttonSelect);
		hb.setSpacing(20);
		pane.add(hb, 0,10);

		buttonSelect.setOnAction(e -> {
			try {
				Class.forName("com.mysql.jdbc.Driver");

				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cars", "root", "");

				Statement stmt = con.createStatement();
				/*
				 * DELETE FROM table_name WHERE some_column = some_value
				 */
				String sql = "delete from car_part ";
				String car = "";
				if (comboBoxIdCar.getValue() == null) {
					comboBoxIdCar.getPromptText();
				} else {
					car = comboBoxIdCar.getSelectionModel().getSelectedItem().toString();
				}
				String part = "";
				if (comboBoxIdPart.getValue() == null) {
					comboBoxIdPart.getPromptText();
				} else {
					part = comboBoxIdPart.getSelectionModel().getSelectedItem().toString();
				}
				if (part.isEmpty() && car.isEmpty() ) {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setContentText("The Process does not work Because You did not enter the car or part to delete.");
					alert.showAndWait();
				} else if(part.isEmpty()){
					sql += "where car = '" + car + "';";
					System.out.println(sql);
					stmt.executeUpdate(sql);
				}
				else if(car.isEmpty()){
					sql += "where part = '" + part + "';";
					System.out.println(sql);
					stmt.executeUpdate(sql);
				}
				else {
					sql += "where car = '" + car + "' AND " + " part = '" + part + "' ;";
					System.out.println(sql);
					stmt.executeUpdate(sql);
				}
				buildData(null);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		return pane;
	}
	
	public List<String> car = new ArrayList<>();
	public List<Integer> part = new ArrayList<>();
	public Set<String> setListpart;
	public Set<String> setListCar;
	public void data() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cars", "root", "");

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("select * from car_part");
			car.clear();
			part.clear();
			while (rs.next()) {
				car.add(rs.getString("car"));
				part.add(rs.getInt("part"));
			}
			setListCar = new LinkedHashSet(car);
			setListpart = new LinkedHashSet(part);
		} catch (Exception w) {
			w.printStackTrace();
			System.out.println("Error on Building Data");
		}
	}
	public List<String> carOrigin = new ArrayList<>();
	public Set<String> setListCarOrigin;
	public void dataOriginCar() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cars", "root", "");

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("select * from car");
			carOrigin.clear();
			while (rs.next()) {
				carOrigin.add(rs.getString("name"));
			}
			setListCarOrigin = new LinkedHashSet(carOrigin);
		} catch (Exception w) {
			w.printStackTrace();
			System.out.println("Error on Building Data");
		}
	}
	public List<String> partOrigin = new ArrayList<>();
	public Set<String> setListPartOrigin;
	public void dataOriginPart() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cars", "root", "");

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("select * from device");
			partOrigin.clear();
			while (rs.next()) {
				partOrigin.add(rs.getString("no"));
			}
			setListPartOrigin = new LinkedHashSet(partOrigin);
		} catch (Exception w) {
			w.printStackTrace();
			System.out.println("Error on Building Data");
		}
	}

}
