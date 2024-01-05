
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

public class CustomerTable extends Application {

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

			if (sql == null) {
				sql = "select * from customer";
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
		Label label = new Label("Customer Table");
		label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
		Button Search = new Button("Search");
		Search.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		Search.setStyle("-fx-background-radius: 20");

		Button Insert = new Button("Insert");
		Insert.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		Insert.setStyle("-fx-background-radius: 20");

		Button Update = new Button("Update");
		Update.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		Update.setStyle("-fx-background-radius: 20");

		Button Delete = new Button("Delete");
		Delete.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		Delete.setStyle("-fx-background-radius: 20");
		hbox.getChildren().addAll(Search, Insert, Update, Delete);
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
		fullPane.getChildren().addAll(label, hbox, tableview, h);
		fullPane.setSpacing(50);
		tableview.setMaxWidth(390);
		tableview.setMaxHeight(200);
		HBox h1 = new HBox();
		h1.getChildren().addAll(fullPane, Select());
		Search.setOnAction(e -> {
			h1.getChildren().clear();
			h1.getChildren().addAll(fullPane, Select());
		});
		Insert.setOnAction(e -> {
			h1.getChildren().clear();
			h1.getChildren().addAll(fullPane, Insert());
		});
		Update.setOnAction(e -> {
			h1.getChildren().clear();
			h1.getChildren().addAll(fullPane, Update());
		});
		Delete.setOnAction(e -> {
			h1.getChildren().clear();
			h1.getChildren().addAll(fullPane, Delete());
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
		primaryStage.setTitle("Customer Table");
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
		Label idLabel = new Label("ID :");
		idLabel.setFont(Font.font("Times New Roman", 20));
		Spinner<Integer> idSpinner = new Spinner<Integer>();
		idSpinner.setEditable(true);
		HBox hboxId = new HBox();
		hboxId.getChildren().addAll(idLabel, idSpinner);
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

		Label cityLabel = new Label("Address : ");
		cityLabel.setFont(Font.font("Times New Roman", 20));
		Spinner<Integer> addressSpinner = new Spinner<Integer>();
		addressSpinner.setEditable(true);
		HBox hboxCity = new HBox();
		hboxCity.getChildren().addAll(cityLabel, addressSpinner);
		hboxCity.setSpacing(10);

		Label countryLabel = new Label("Job : ");
		countryLabel.setFont(Font.font("Times New Roman", 20));
		TextField JobTextField = new TextField();
		HBox hboxCountry = new HBox();
		hboxCountry.getChildren().addAll(countryLabel, JobTextField);
		hboxCountry.setSpacing(10);

		VBox vLabel = new VBox();
		vLabel.getChildren().addAll(idLabel,buildingLabel,streetLabel,cityLabel,countryLabel);
		vLabel.setSpacing(20);
		VBox vText = new VBox();
		vText.getChildren().addAll(idSpinner,f_nameTextField, l_nameTextField, addressSpinner, JobTextField);
		vText.setSpacing(10);
		HBox hbox = new HBox();
		hbox.getChildren().addAll(vLabel,vText);
		hbox.setSpacing(10);
		
		insertDataVbox.setSpacing(10);
		fullPane.setSpacing(80);
		insertDataVbox.getChildren().addAll(hboxId, hboxBuilding, hboxStreet, hboxCity, hboxCountry);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(3.5);
		pane.setVgap(3.5);
		pane.setAlignment(Pos.CENTER_LEFT);
		pane.add(hbox, 0, 0);

		Button buttonSelect = new Button("Select Data");
		buttonSelect.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		buttonSelect.setStyle("-fx-background-radius: 20");

		HBox hb = new HBox();
		hb.getChildren().addAll(buttonSelect);
		hb.setSpacing(20);
		pane.add(hb, 0, 10);

		buttonSelect.setOnAction(e -> {
			String sql = "select * from customer";
			String id = idSpinner.getEditor().getText();
			String f_name = f_nameTextField.getText();
			String l_name = l_nameTextField.getText();
			String address = addressSpinner.getEditor().getText();
			String job = JobTextField.getText();
			if (id.isEmpty() && f_name.isEmpty() && l_name.isEmpty() && address.isEmpty() && job.isEmpty()) {
				sql = "select * from customer";
			} else {
				boolean idN = true;
				boolean addressN = true;
				for (int i = 0; i < id.length(); i++) {
					if (!(Character.isDigit(id.charAt(i)))) {
						idN = false;
					}
				}
				for (int i = 0; i < address.length(); i++) {
					if (!(Character.isDigit(address.charAt(i)))) {
						addressN = false;
					}
				}
				if (!idN) {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setContentText("The Process does not work Because The id must be numeric");
					alert.showAndWait();
				} else if (!addressN) {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setContentText("The Process does not work Because The address must be numeric");
					alert.showAndWait();
				} else {
					sql += " WHERE ";
					if (!(id.isEmpty())) {
						sql += "id = '" + id + "' AND ";
					}
					if (!(f_name.isEmpty())) {
						sql += "f_name = '" + f_name + "' AND ";
					}
					if (!(l_name.isEmpty())) {
						sql += "l_name = '" + l_name + "' AND ";
					}
					if (!(address.isEmpty())) {
						sql += "address = '" + address + "' AND ";
					}
					if (!(job.isEmpty())) {
						sql += "job = '" + job + "' AND ";
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
				buildData(sql);
			}
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
		Label idLabel = new Label("ID :");
		idLabel.setFont(Font.font("Times New Roman", 20));

		Spinner<Integer> idSpinner = new Spinner<Integer>();
		idSpinner.setEditable(true);
		HBox hboxId = new HBox();
		hboxId.getChildren().addAll(idLabel, idSpinner);
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

		VBox vLabel = new VBox();
		vLabel.getChildren().addAll(idLabel,buildingLabel,streetLabel,cityLabel,countryLabel);
		vLabel.setSpacing(20);
		VBox vText = new VBox();
		vText.getChildren().addAll(idSpinner,f_nameTextField, l_nameTextField, comboBoxAddress, JobTextField);
		vText.setSpacing(10);
		HBox hbox = new HBox();
		hbox.getChildren().addAll(vLabel,vText);
		hbox.setSpacing(10);
		
		insertDataVbox.setSpacing(10);
		fullPane.setSpacing(80);
		insertDataVbox.getChildren().addAll(hboxId, hboxBuilding, hboxStreet, hboxCity, hboxCountry);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(3.5);
		pane.setVgap(3.5);
		pane.setAlignment(Pos.CENTER_LEFT);
		pane.add(hbox, 0, 0);

		Button buttonInsert = new Button("Insert Data");
		buttonInsert.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		buttonInsert.setStyle("-fx-background-radius: 20");
		HBox hb = new HBox();
		hb.getChildren().addAll(buttonInsert);
		hb.setSpacing(20);
		pane.add(hb, 0, 10);

		buttonInsert.setOnAction(e -> {
			try {
				Class.forName("com.mysql.jdbc.Driver");

				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cars", "root", "");

				Statement stmt = con.createStatement();
				data();
				String id = idSpinner.getEditor().getText();
				String f_name = f_nameTextField.getText();
				String l_name = l_nameTextField.getText();
				String address = "";
				if (comboBoxAddress.getValue() == null) {
					comboBoxAddress.getPromptText();
				} else {
					address = comboBoxAddress.getSelectionModel().getSelectedItem().toString();
				}
				String job = JobTextField.getText();
				if (id.isEmpty() || f_name.isEmpty() || l_name.isEmpty() || address.isEmpty() || job.isEmpty()) {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setContentText("The Process does not work Because You must to enter all fields");
					alert.showAndWait();
				} else {
					boolean idN = true;
					for (int i = 0; i < id.length(); i++) {
						if (!(Character.isDigit(id.charAt(i)))) {
							idN = false;
						}
					}
					if (idList.contains(id)) {
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setTitle("Warning");
						alert.setContentText("The Process does not work Because You enter a duplicate key.");
						alert.showAndWait();
					} else if (!idN) {
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setTitle("Warning");
						alert.setContentText("The Process does not work Because The id must be numeric");
						alert.showAndWait();
					} else {
						String sql = "INSERT INTO customer VALUES ('" + id + "','" + f_name + "','" + l_name + "','"
								+ address + "','" + job + "')";
						stmt.executeUpdate(sql);
						buildData(null);
						Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
						alert.setTitle("CONFIRMATION");
						alert.setContentText("Insert Complete!");
						alert.showAndWait();
						idSpinner.getEditor().clear();
						f_nameTextField.clear();
						l_nameTextField.clear();
						comboBoxAddress.getSelectionModel().clearSelection();
						JobTextField.clear();
					}
				}
			} catch (Exception w) {
				w.printStackTrace();
				System.out.println("Error on Building Data");
			}
		});
		return pane;
	}

	public GridPane Update() {
		GridPane pane = new GridPane();
		VBox fullPane = new VBox();
		Image introduction = new Image(new File("colorbackground.png").toURI().toString());
		BackgroundImage bImg = new BackgroundImage(introduction, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT, new BackgroundSize(250, 250, false, false, false, true));
		pane.setBackground(new Background(bImg));
		VBox insertDataVbox = new VBox();
		data();
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

		VBox vLabel = new VBox();
		vLabel.getChildren().addAll(idLabel,buildingLabel,streetLabel,cityLabel,countryLabel);
		vLabel.setSpacing(20);
		VBox vText = new VBox();
		vText.getChildren().addAll(comboBoxId,f_nameTextField, l_nameTextField, comboBoxAddress, JobTextField);
		vText.setSpacing(10);
		HBox hbox = new HBox();
		hbox.getChildren().addAll(vLabel,vText);
		hbox.setSpacing(10);
		
		insertDataVbox.setSpacing(10);
		fullPane.setSpacing(80);
		insertDataVbox.getChildren().addAll(hboxId, hboxBuilding, hboxStreet, hboxCity, hboxCountry);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(3.5);
		pane.setVgap(3.5);
		pane.setAlignment(Pos.CENTER_LEFT);
		pane.add(hbox, 0, 0);

		Button buttonUpdate = new Button("Update Data");
		buttonUpdate.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		buttonUpdate.setStyle("-fx-background-radius: 20");

		HBox hb = new HBox();
		hb.getChildren().addAll(buttonUpdate);
		hb.setSpacing(20);
		pane.add(hb, 0, 10);
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
					if (id.isEmpty()) {
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
					stmt.executeUpdate(sql);
					buildData(null);
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setTitle("CONFIRMATION");
					alert.setContentText("Update Complete!");
					alert.showAndWait();
					comboBoxId.getSelectionModel().clearSelection();
					f_nameTextField.clear();
					l_nameTextField.clear();
					comboBoxAddress.getSelectionModel().clearSelection();
					JobTextField.clear();
				}
			} catch (Exception e1) {
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
		Label idLabel = new Label("ID :");
		idLabel.setFont(Font.font("Times New Roman", 20));
		ComboBox comboBoxId = new ComboBox(FXCollections.observableArrayList(idList));

		HBox hboxId = new HBox();
		hboxId.getChildren().addAll(idLabel, comboBoxId);
		hboxId.setSpacing(10);

		insertDataVbox.setSpacing(10);
		fullPane.setSpacing(80);
		insertDataVbox.getChildren().addAll(hboxId);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(3.5);
		pane.setVgap(3.5);
		pane.setAlignment(Pos.CENTER_LEFT);
		pane.add(insertDataVbox, 0, 0);

		Button buttonSelect = new Button("Delete Data");
		buttonSelect.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		buttonSelect.setStyle("-fx-background-radius: 20");
		HBox hb = new HBox();
		hb.getChildren().addAll(buttonSelect);
		hb.setSpacing(20);
		pane.add(hb, 0, 10);

		buttonSelect.setOnAction(e -> {
			try {
				Class.forName("com.mysql.jdbc.Driver");

				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cars", "root", "");

				Statement stmt = con.createStatement();
				/*
				 * DELETE FROM table_name WHERE some_column = some_value
				 */
				String sql = "delete from customer ";
				String id = "";
				if (comboBoxId.getValue() == null) {
					comboBoxId.getPromptText();
				} else {
					id = comboBoxId.getSelectionModel().getSelectedItem().toString();
				}
				if (id.isEmpty()) {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setContentText("The Process does not work Because You did not enter the id to delete.");
					alert.showAndWait();
				} else {
					sql += "where id = '" + id + "';";
					System.out.println(sql);
					stmt.executeUpdate(sql);
					buildData(null);
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setTitle("CONFIRMATION");
					alert.setContentText("Delete Complete!");
					alert.showAndWait();
					comboBoxId.getSelectionModel().clearSelection();
					comboBoxId.getItems().clear();
					data();
					comboBoxId.getItems().addAll(idList);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		return pane;
	}

	public List<Integer> addressList = new ArrayList<>();

	public void dataAddress() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cars", "root", "");

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("select * from address");
			addressList.clear();
			while (rs.next()) {
				addressList.add(rs.getInt("id"));
			}
		} catch (Exception w) {
			w.printStackTrace();
			System.out.println("Error on Building Data");
		}

	}

	public List<String> idList = new ArrayList<>();

	public void data() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cars", "root", "");

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("select * from customer");
			idList.clear();
			while (rs.next()) {
				idList.add(rs.getString("id"));
			}
		} catch (Exception w) {
			w.printStackTrace();
			System.out.println("Error on Building Data");
		}

	}
}
