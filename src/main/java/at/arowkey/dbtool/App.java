package at.arowkey.dbtool;

import at.arowkey.dbtool.db.ConnectionManager;
import at.arowkey.dbtool.ui.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class App extends Application {

    private final ConnectionManager connectionManager = new ConnectionManager();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("main-view.fxml"));
        Parent root = loader.load();

        MainController controller = loader.getController();
        controller.setConnectionManager(connectionManager);

        stage.setTitle("Arows DB Tool");
        stage.setScene(new Scene(root, 1000, 650));
        stage.show();
        stage.setAlwaysOnTop(true);
        stage.setAlwaysOnTop(false);
        stage.requestFocus();

        controller.connect();
    }

    @Override
    public void stop() {
        connectionManager.close();
    }

    @SuppressWarnings({"unused", "UnnecessaryModifier"})
    public static void main(String[] args) {
        launch(args);
    }
}
