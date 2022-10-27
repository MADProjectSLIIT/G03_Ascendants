package com.example.petpal.ui.Gigs;

import java.util.ArrayList;
import java.util.Arrays;

public class Gigs {
    private String UserId;
    private String title;
    private ArrayList<String> service;
    private ArrayList<String> typeOfPet;
    private int noOfPets;
    private String size;
    private int noOfDays;
    private String travelDistance;
    private int charge;
    private String location;
    private String description;


    public Gigs(String userId, String title, ArrayList<String> service, ArrayList<String> typeOfPet, int noOfPets, String size, int noOfDays, String travelDistance, int charge, String location, String description) {
        UserId = userId;
        this.title = title;
        this.service = service;
        this.typeOfPet = typeOfPet;
        this.noOfPets = noOfPets;
        this.size = size;
        this.noOfDays = noOfDays;
        this.travelDistance = travelDistance;
        this.charge = charge;
        this.location = location;
        this.description = description;
    }

    public Gigs() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public ArrayList<String> getService() {
        return service;
    }

    public void setService(ArrayList<String> service) {
        this.service = service;
    }

    public ArrayList<String> getTypeOfPet() {
        return typeOfPet;
    }

    public void setTypeOfPet(ArrayList<String> typeOfPet) {
        this.typeOfPet = typeOfPet;
    }

    public int getNoOfPets() {
        return noOfPets;
    }

    public void setNoOfPets(int noOfPets) {
        this.noOfPets = noOfPets;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(int noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getTravelDistance() {
        return travelDistance;
    }

    public void setTravelDistance(String travelDistance) {
        this.travelDistance = travelDistance;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
