import java.io.File;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class IntroScreen extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Background
		GridPane pane = new GridPane();
		Image introduction = new Image(new File("cars.png").toURI().toString());
		BackgroundImage bImg = new BackgroundImage(introduction, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT, new BackgroundSize(250, 250, false, false, false, true));
		pane.setBackground(new Background(bImg));
		// buttons
		Button addressTable = new Button("Address Table");
		addressTable.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
		addressTable.setStyle("-fx-background-radius: 20");
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(3.5);
		pane.setVgap(3.5);
		pane.setAlignment(Pos.CENTER_LEFT);

		addressTable.setOnAction(e -> {
			AddressTable address = new AddressTable();
			Stage adressStage = new Stage();
			try {
				address.start(adressStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			primaryStage.close();
		});

		Button carTable = new Button("Car Table");
		carTable.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
		carTable.setStyle("-fx-background-radius: 20");

		carTable.setOnAction(e -> {
			CarTable address = new CarTable();
			Stage adressStage = new Stage();
			try {
				address.start(adressStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			primaryStage.close();
		});

		Button car_PartTable = new Button("Car_Part Table");
		car_PartTable.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
		car_PartTable.setStyle("-fx-background-radius: 20");

		car_PartTable.setOnAction(e -> {
			Car_PartTable address = new Car_PartTable();
			Stage adressStage = new Stage();
			try {
				address.start(adressStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			primaryStage.close();
		});

		Button customerTable = new Button("Customer Table");
		customerTable.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
		customerTable.setStyle("-fx-background-radius: 20");

		customerTable.setOnAction(e -> {
			CustomerTable address = new CustomerTable();
			Stage adressStage = new Stage();
			try {
				address.start(adressStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			primaryStage.close();
		});

		Button deviceTable = new Button("Device Table");
		deviceTable.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
		deviceTable.setStyle("-fx-background-radius: 20");

		deviceTable.setOnAction(e -> {
			DeviceTable address = new DeviceTable();
			Stage adressStage = new Stage();
			try {
				address.start(adressStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			primaryStage.close();
		});

		Button ManufactureTable = new Button("Manufacture Table");
		ManufactureTable.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
		ManufactureTable.setStyle("-fx-background-radius: 20");

		ManufactureTable.setOnAction(e -> {
			ManufactureTable address = new ManufactureTable();
			Stage adressStage = new Stage();
			try {
				address.start(adressStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			primaryStage.close();
		});

		Button OrdersTable = new Button("Orders Table");
		OrdersTable.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
		OrdersTable.setStyle("-fx-background-radius: 20");

		OrdersTable.setOnAction(e -> {
			OrdersTable address = new OrdersTable();
			Stage adressStage = new Stage();
			try {
				address.start(adressStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			primaryStage.close();
		});

		VBox vbox1 = new VBox();
		vbox1.getChildren().addAll(addressTable, carTable, car_PartTable,customerTable, deviceTable, ManufactureTable,OrdersTable);
		vbox1.setSpacing(30);
		pane.add(vbox1, 330, 10);
		Scene introScene = new Scene(pane, 250, 250);
		primaryStage.setTitle("Welcome");
		primaryStage.setScene(introScene);
		primaryStage.show();
		primaryStage.setFullScreen(true);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
