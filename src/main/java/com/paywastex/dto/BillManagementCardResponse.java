package com.paywastex.dto;

public class BillManagementCardResponse {
    private String totalBills;
    private String totalPendingPayment;
    private String totalOverdueBills;

    public String getTotalBills() {
        return totalBills;
    }
    public String getTotalPendingPayment() {
        return totalPendingPayment;
    }
    public String getTotalOverdueBills() {
        return totalOverdueBills;
    }

    public void setTotalBills(String totalBills) {
        this.totalBills = totalBills;
    }
    public void setTotalPendingPayment(String totalPendingPayment) {
        this.totalPendingPayment = totalPendingPayment;
    }
    public void setTotalOverdueBills(String totalOverdueBills) {
        this.totalOverdueBills = totalOverdueBills;
    }
}
