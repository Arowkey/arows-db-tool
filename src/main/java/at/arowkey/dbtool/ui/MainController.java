package at.arowkey.dbtool.ui;

import at.arowkey.dbtool.config.ConfigException;
import at.arowkey.dbtool.config.ConnectionConfig;
import at.arowkey.dbtool.config.ConnectionConfigLoader;
import at.arowkey.dbtool.db.ConnectionManager;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

public class MainController {

    private static final String[] STATUS_STYLES = {"status-pending", "status-ok", "status-error"};

    @FXML
    private ListView<String> tableList;

    @FXML
    private StackPane contentPane;

    @FXML
    private Label statusLabel;

    private ConnectionManager connectionManager;

    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    /**
     * Loads the config and connects in a background thread so the window stays responsive.
     */
    public void connect() {
        setStatus("Connecting...", "status-pending");

        Task<ConnectionConfig> task = new Task<>() {
            @Override
            protected ConnectionConfig call() throws Exception {
                ConnectionConfig config = ConnectionConfigLoader.load(ConnectionConfigLoader.DEFAULT_PATH);
                connectionManager.connect(config);
                return config;
            }
        };
        task.setOnSucceeded(event -> onConnected(task.getValue()));
        task.setOnFailed(event -> onConnectionFailed(task.getException()));

        Thread thread = new Thread(task, "db-connect");
        thread.setDaemon(true);
        thread.start();
    }

    private void onConnected(ConnectionConfig config) {
        setStatus("Connected to " + config.describe(), "status-ok");
        // Extension point: load the table names into tableList here.
    }

    private void onConnectionFailed(Throwable error) {
        String message = error.getMessage() != null ? error.getMessage() : error.toString();
        setStatus("Not connected: " + message.lines().findFirst().orElse(""), "status-error");

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Connection failed");
        alert.setHeaderText(error instanceof ConfigException
                ? "Problem with the connection config"
                : "Could not connect to the database");
        alert.setContentText(message);
        alert.initOwner(statusLabel.getScene().getWindow());
        alert.show();
    }

    private void setStatus(String text, String styleClass) {
        statusLabel.setText(text);
        statusLabel.getStyleClass().removeAll(STATUS_STYLES);
        statusLabel.getStyleClass().add(styleClass);
    }

    @FXML
    private void onReconnect() {
        connect();
    }

    @FXML
    private void onExit() {
        Platform.exit();
    }

    @FXML
    public void onCreateTable(ActionEvent actionEvent) {
        tableList.getItems().addFirst("<new_table>");
        tableList.getSelectionModel().selectFirst();
    }
}
