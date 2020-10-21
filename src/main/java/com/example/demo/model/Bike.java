package com.example.demo.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name= "bike")
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    private int bikeId;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "frame_size", nullable = false)
    private String frameSize;

    @Column(name = "price", nullable = false)
    private int price;

    //ADDING THE BIKE ORDER ANNOTATION/RELATIONSHIP
    //@OneToOne(mappedBy = "bike")
    //private BikeOrder bikeOrder;

    // Constructor 1
    public Bike() {}

    // Constructor 2
    public Bike(int bikeId, String type, String state, String brand, String frameSize, int price) {
        this.bikeId = bikeId;
        this.type = type;
        this.state = state;
        this.brand = brand;
        this.frameSize = frameSize;
        this.price = price;
    }

    // Getters and Setters
    public int getBikeId() {
        return bikeId;
    }

    public void setBikeId(int bikeId) {
        this.bikeId = bikeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getFrameSize() {
        return frameSize;
    }

    public void setFrameSize(String frameSize) {
        this.frameSize = frameSize;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
