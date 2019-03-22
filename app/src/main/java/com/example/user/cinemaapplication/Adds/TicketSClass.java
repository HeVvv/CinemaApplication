package com.example.user.cinemaapplication.Adds;


import java.io.IOException;
import java.net.Inet4Address;

public class TicketSClass {

    int salespointsValues;
    String auditoriumsValue;
    int tradingValue;
    int idTicketValue;
    int showValue;
    int  rowValue;
    String prefixValue;
    int seatValue;

    public TicketSClass(String string) throws IOException{
            String[] token = string.split("/");

            this.salespointsValues = Integer.parseInt(token[0]);
            this.auditoriumsValue = token[1];
            this.tradingValue = Integer.parseInt(token[2]);
            this.idTicketValue = Integer.parseInt(token[3]);
            this.showValue = Integer.parseInt(token[4]);
            this.rowValue = Integer.parseInt(token[5]);
            this.prefixValue = token[6];

            this.seatValue = Integer.parseInt(token[7]);
    }

    public int getSalespointsValues() {
        return salespointsValues;
    }

    public void setSalespointsValues(int salespointsValues) { this.salespointsValues = salespointsValues; }

    public String getAuditoriumsValue() {
        return auditoriumsValue;
    }

    public void setAuditoriumsValue(String auditoriumsValue) { this.auditoriumsValue = auditoriumsValue; }

    public int getTradingValue() {
        return tradingValue;
    }

    public void setTradingValue(int tradingValue) {
        this.tradingValue = tradingValue;
    }

    public int getIdTicketValue() {
        return idTicketValue;
    }

    public void setIdTicketValue(int idTicketValue) {
        this.idTicketValue = idTicketValue;
    }

    public int getShowValue() {
        return showValue;
    }

    public void setShowValue(int showValue) {
        this.showValue = showValue;
    }

    public int getRowValue() {
        return rowValue;
    }

    public void setRowValue(int rowValue) {
        this.rowValue = rowValue;
    }

    public String getPrefixValue() {
        return prefixValue;
    }

    public void setPrefixValue(String prefixValue) {
        this.prefixValue = prefixValue;
    }

    public int getSeatValue() {
        return seatValue;
    }

    public void setSeatValue(int seatValue) {
        this.seatValue = seatValue;
    }
}
