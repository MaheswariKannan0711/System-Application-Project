
package report;

import Database.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReportDAO {

    public int getTotalOrders() {
        int count = 0;

        try {
            Connection con = DBConnection.connect();

            String sql = "SELECT COUNT(*) FROM orders";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(ReportDAO.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

        return count;
    }

    public double getTotalRevenue() {
        double total = 0;

        try {
            Connection con = DBConnection.connect();

            String sql = "SELECT SUM(totalamount) FROM bills";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getDouble(1);

                if (rs.wasNull()) {
                    total = 0;
                }
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(ReportDAO.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

        return total;
    }

    public int getActiveTailors() {
        int count = 0;

        try {
            Connection con = DBConnection.connect();

            String sql = "SELECT COUNT(*) FROM tailorr";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(ReportDAO.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

        return count;
    }

    public int getTotalBills() {
        int count = 0;

        try {
            Connection con = DBConnection.connect();

            String sql = "SELECT COUNT(*) FROM bills";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(ReportDAO.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

        return count;
    }

    public ArrayList<ReportModel.ServiceRow> getTopServicesByRevenue(
            double totalRevenue) {

        ArrayList<ReportModel.ServiceRow> list = new ArrayList<>();

        try {
            Connection con = DBConnection.connect();

            String sql = "SELECT dresstype, COUNT(*) AS orders, "
                    + "SUM(totalamount) AS revenue "
                    + "FROM orders o "
                    + "JOIN bills b ON o.customerid = b.customerid "
                    + "GROUP BY dresstype "
                    + "ORDER BY revenue DESC";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            int sno = 1;

            while (rs.next()) {
                double revenue = rs.getDouble("revenue");

                if (rs.wasNull()) {
                    revenue = 0;
                }

                double percent = totalRevenue > 0
                        ? (revenue / totalRevenue) * 100
                        : 0;

                ReportModel.ServiceRow row =
                        new ReportModel.ServiceRow(
                                sno++,
                                rs.getString("dresstype"),
                                rs.getInt("orders"),
                                revenue,
                                percent
                        );

                list.add(row);
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(ReportDAO.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public ArrayList<ReportModel.OrderSummaryRow> getRecentOrderSummary() {

        ArrayList<ReportModel.OrderSummaryRow> list = new ArrayList<>();

        try {
            Connection con = DBConnection.connect();

            String sql = "SELECT orderdate, orderid, customername, status "
                    + "FROM orders "
                    + "ORDER BY orderdate DESC NULLS LAST "
                    + "LIMIT 10";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                // Handle a null order date safely.
                Date orderDate = rs.getDate("orderdate");

                String date = (orderDate != null)
                        ? orderDate.toString()
                        : "N/A";

                String customerName = rs.getString("customername");
                String status = rs.getString("status");

                // Handle null customer names and statuses.
                if (customerName == null) {
                    customerName = "N/A";
                }

                if (status == null) {
                    status = "N/A";
                }

                ReportModel.OrderSummaryRow row =
                        new ReportModel.OrderSummaryRow(
                                date,
                                rs.getInt("orderid"),
                                customerName,
                                0,
                                status
                        );

                list.add(row);
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(ReportDAO.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

        return list;
    }
}
