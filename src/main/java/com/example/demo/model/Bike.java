package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Bike {
    private int bikeId;
    private String type;
    private String state;
    private String brand;
    private String frameSize;
    private int price;
    private Collection<OrderBike> orderBikesByBikeId;

    public Bike(String type, String state, String brand, String frameSize, int price) {
        this.type = type;
        this.state = state;
        this.brand = brand;
        this.frameSize = frameSize;
        this.price = price;
    }

    public Bike(){}

    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    @Id
    @Column(name = "bike_id", nullable = false)
    public int getBikeId() {
        return bikeId;
    }

    public void setBikeId(int bikeId) {
        this.bikeId = bikeId;
    }

    @Basic
    @Column(name = "type", nullable = false, length = 45)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "state", nullable = false, length = 45)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Basic
    @Column(name = "brand", nullable = false, length = 45)
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Basic
    @Column(name = "frame_size", nullable = false, length = 45)
    public String getFrameSize() {
        return frameSize;
    }

    public void setFrameSize(String frameSize) {
        this.frameSize = frameSize;
    }

    @Basic
    @Column(name = "price", nullable = false)
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bike bike = (Bike) o;

        if (bikeId != bike.bikeId) return false;
        if (price != bike.price) return false;
        if (type != null ? !type.equals(bike.type) : bike.type != null) return false;
        if (state != null ? !state.equals(bike.state) : bike.state != null) return false;
        if (brand != null ? !brand.equals(bike.brand) : bike.brand != null) return false;
        if (frameSize != null ? !frameSize.equals(bike.frameSize) : bike.frameSize != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bikeId;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (frameSize != null ? frameSize.hashCode() : 0);
        result = 31 * result + price;
        return result;
    }

    @JsonBackReference
    @OneToMany(mappedBy = "bikeByBikeId")
    public Collection<OrderBike> getOrderBikesByBikeId() {
        return orderBikesByBikeId;
    }

    public void setOrderBikesByBikeId(Collection<OrderBike> orderBikesByBikeId) {
        this.orderBikesByBikeId = orderBikesByBikeId;
    }


    // METHODS FOR SORTING (.sort)

    public int compareToByType(Object bike) {
        return (((Bike) bike).getType()).compareTo(this.getType());
    }

    public int compareToByState(Object bike) {
        return (((Bike) bike).getState()).compareTo(this.getState());
    }

    public int compareToByBrand(Object bike) {
        return (((Bike) bike).getBrand()).compareTo(this.getBrand());
    }

    public int compareToByFrameSize(Object bike) {
        return (((Bike) bike).getFrameSize()).compareTo(this.getFrameSize());
    }

    public int compareToByPrice(Object bike) {
        int number1 = this.getPrice();
        int number2 = ((Bike) bike).getPrice();
        // return (((Bike) bike).getPrice()).(this.getPrice());
        return Integer.compare(number1, number2);
    }

    public int compareToById(Object bike) {
        int number1 = this.getBikeId();
        int number2 = ((Bike) bike).getBikeId();
        // return (((Bike) bike).getPrice()).(this.getPrice());
        return Integer.compare(number1, number2);
    }

}
