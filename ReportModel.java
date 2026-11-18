package report;

public class ReportModel {

    public static class ServiceRow {
        private int sno;
        private String service;
        private int orders;
        private double revenue;
        private double percentage;

        public ServiceRow(int sno, String service, int orders, double revenue, double percentage) {
            this.sno = sno;
            this.service = service;
            this.orders = orders;
            this.revenue = revenue;
            this.percentage = percentage;
        }

        public int getSno() { return sno; }
        public String getService() { return service; }
        public int getOrders() { return orders; }
        public double getRevenue() { return revenue; }
        public double getPercentage() { return percentage; }
    }

    public static class OrderSummaryRow {
        private String date;
        private int orderid;
        private String customer;
        private double amount;
        private String status;

        public OrderSummaryRow(String date, int orderid, String customer, double amount, String status) {
            this.date = date;
            this.orderid = orderid;
            this.customer = customer;
            this.amount = amount;
            this.status = status;
        }

        public String getDate() { return date; }
        public int getOrderid() { return orderid; }
        public String getCustomer() { return customer; }
        public double getAmount() { return amount; }
        public String getStatus() { return status; }
    }
}
