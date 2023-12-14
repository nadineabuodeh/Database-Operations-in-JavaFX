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

public class SelectDataInCustomerTable extends Application {
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
		Label label = new Label("Select From Customer Table");
		label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
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

		insertDataVbox.setSpacing(10);
		fullPane.setSpacing(80);
		insertDataVbox.getChildren().addAll(hboxId, hboxBuilding, hboxStreet, hboxCity, hboxCountry);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(3.5);
		pane.setVgap(3.5);
		pane.setAlignment(Pos.CENTER_LEFT);
		pane.add(label, 30, 10);
		pane.add(insertDataVbox, 40, 10);

		Button buttonSelect = new Button("Select Data");
		buttonSelect.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		buttonSelect.setStyle("-fx-background-radius: 20");
		Button buttonBackToAddress = new Button("Back To Customer Table");
		buttonBackToAddress.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		buttonBackToAddress.setStyle("-fx-background-radius: 20");

		HBox hb = new HBox();
		hb.getChildren().addAll(buttonSelect, buttonBackToAddress);
		hb.setSpacing(20);
		pane.add(hb, 32, 30);

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
			System.out.println(sql);

			CustomerTableScreen customer = new CustomerTableScreen();
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

			CustomerTableScreen customer = new CustomerTableScreen();
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

	public List<Integer> idList = new ArrayList<>();
	public List<String> f_nameList = new ArrayList<>();
	public List<String> l_nameList = new ArrayList<>();
	public List<Integer> addressList = new ArrayList<>();
	public List<String> jobList = new ArrayList<>();
	public Set<Integer> setListaddress;
	public Set<String> setListf_name;
	public Set<String> setListl_name;
	public Set<String> setListjob;
	public void data() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cars", "root", "");

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("select * from customer");

			while (rs.next()) {
				idList.add(rs.getInt("id"));
				f_nameList.add(rs.getString("l_name"));
				l_nameList.add(rs.getString("f_name"));
				addressList.add(rs.getInt("address"));
				jobList.add(rs.getString("job"));
			}
			setListaddress = new LinkedHashSet<Integer>(addressList);
			setListf_name = new LinkedHashSet<String>(f_nameList);
			setListjob = new LinkedHashSet<String>(jobList);
			setListl_name = new LinkedHashSet<String>(l_nameList);

			StringBuilder result = new StringBuilder();
			for (int i = 0; i < idList.size(); i++) {
				result.append("ID: ").append(idList.get(i)).append(", building: ").append(f_nameList.get(i))
						.append(", street: ").append(l_nameList.get(i)).append(", city: ").append(addressList.get(i))
						.append(", country: ").append(jobList.get(i)).append("\n");
			}
			System.out.println(result);

		} catch (Exception w) {
			w.printStackTrace();
			System.out.println("Error on Building Data");
		}
	}

}
