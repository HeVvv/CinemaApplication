package com.example.user.cinemaapplication.Classes;

/**
 * Created by User on 11.11.2017.
 */

public class AuditoriumsClass {

    private static AuditoriumsClass mainAudit;

    public static AuditoriumsClass getMainAudit() {
        return mainAudit;
    }
    public static void setUser(AuditoriumsClass mainAudit) {
        AuditoriumsClass.mainAudit = mainAudit;
    }

    private int id;
    private int number;
    private String name;
    private String acronym;
    private int theater;
    private double width;
    private double height;
    private int rowsCount;
    private int seatsCount;
    private String logo;
    private String color;
    private String theaterName;
    public int getId() {
        return id;
    }
    public void setTheaterName(String theaterName){this.theaterName = theaterName;}
    public String getTheaterName(){return theaterName;}
    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public int getTheater() {
        return theater;
    }

    public void setTheater(int theater) {
        this.theater = theater;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public void setRowsCount(int rowsCount) {
        this.rowsCount = rowsCount;
    }

    public int getSeatsCount() {
        return seatsCount;
    }

    public void setSeatsCount(int seatsCount) {
        this.seatsCount = seatsCount;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
