package report;

import common.Common;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class ReportController implements Initializable {

    @FXML
    private Button set;
    @FXML
    private Button rep;

    @FXML
    private Label lblTotalOrders;
    @FXML
    private Label lblTotalRevenue;
    @FXML
    private Label lblActiveTailors;
    @FXML
    private Label lblTotalBills;

    @FXML
    private TableView<ReportModel.ServiceRow> topServicesTable;
    @FXML
    private TableColumn<ReportModel.ServiceRow, Integer> colTSno;
    @FXML
    private TableColumn<ReportModel.ServiceRow, String> colTService;
    @FXML
    private TableColumn<ReportModel.ServiceRow, Integer> colTOrders;
    @FXML
    private TableColumn<ReportModel.ServiceRow, Double> colTRevenue;
    @FXML
    private TableColumn<ReportModel.ServiceRow, String> colTPercentage;

    @FXML
    private TableView<ReportModel.OrderSummaryRow> recentOrdersTable;
    @FXML
    private TableColumn<ReportModel.OrderSummaryRow, String> colRDate;
    @FXML
    private TableColumn<ReportModel.OrderSummaryRow, Integer> colROrderId;
    @FXML
    private TableColumn<ReportModel.OrderSummaryRow, String> colRCustomer;
    @FXML
    private TableColumn<ReportModel.OrderSummaryRow, Double> colRAmount;
    @FXML
    private TableColumn<ReportModel.OrderSummaryRow, String> colRStatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ReportService ser = new ReportService();

        int totalOrders = ser.getTotalOrders();
        double totalRevenue = ser.getTotalRevenue();
        int activeTailors = ser.getActiveTailors();
        int totalBills = ser.getTotalBills();

        lblTotalOrders.setText(String.valueOf(totalOrders));
        lblTotalRevenue.setText(String.format("%.2f", totalRevenue));
        lblActiveTailors.setText(String.valueOf(activeTailors));
        lblTotalBills.setText(String.valueOf(totalBills));

        loadTopServices(ser, totalRevenue);
        loadRecentOrders(ser);
    }

    private void loadTopServices(ReportService ser, double totalRevenue) {
        colTSno.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getSno()).asObject());
        colTService.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getService()));
        colTOrders.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getOrders()).asObject());
        colTRevenue.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getRevenue()).asObject());
        colTPercentage.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(String.format("%.1f%%", data.getValue().getPercentage())));

        ArrayList<ReportModel.ServiceRow> list = ser.getTopServicesByRevenue(totalRevenue);
        ObservableList<ReportModel.ServiceRow> items = FXCollections.observableArrayList(list);
        topServicesTable.setItems(items);
    }

    private void loadRecentOrders(ReportService ser) {
        colRDate.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDate()));
        colROrderId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getOrderid()).asObject());
        colRCustomer.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCustomer()));
        colRAmount.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getAmount()).asObject());
        colRStatus.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getStatus()));

        ArrayList<ReportModel.OrderSummaryRow> list = ser.getRecentOrderSummary();
        ObservableList<ReportModel.OrderSummaryRow> items = FXCollections.observableArrayList(list);
        recentOrdersTable.setItems(items);
    }

    @FXML
    private void setbt(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/setting/setting.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void dash(ActionEvent event) {
        Common.openFullScreen("/dashboard/dashboard.fxml");
    }

    @FXML
    private void cust(ActionEvent event) {
        Common.openFullScreen("/customer2/customer2.fxml");
    }

    @FXML
    private void meas(ActionEvent event) {
        Common.openFullScreen("/measurement/meas.fxml");
    }

    @FXML
    private void tail(ActionEvent event) {
        Common.openFullScreen("/tailor/tailor.fxml");
    }

    @FXML
    private void ord(ActionEvent event) {
        Common.openFullScreen("/orders/orders.fxml");
    }

    @FXML
    private void bill(ActionEvent event) {
        Common.openFullScreen("/billing2/billing2.fxml");
    }
}