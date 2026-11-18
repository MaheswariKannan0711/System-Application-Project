package report;

import java.util.ArrayList;

public class ReportService {
    ReportDAO dao = new ReportDAO();

    public int getTotalOrders() {
        return dao.getTotalOrders();
    }

    public double getTotalRevenue() {
        return dao.getTotalRevenue();
    }

    public int getActiveTailors() {
        return dao.getActiveTailors();
    }

    public int getTotalBills() {
        return dao.getTotalBills();
    }

    public ArrayList<ReportModel.ServiceRow> getTopServicesByRevenue(double totalRevenue) {
        return dao.getTopServicesByRevenue(totalRevenue);
    }

    public ArrayList<ReportModel.OrderSummaryRow> getRecentOrderSummary() {
        return dao.getRecentOrderSummary();
    }
}