package com.example.demo.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "bike_order")
public class BikeOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    private int orderId;

    @Column(name = "order_price", nullable = false)
    private int orderPrice;

    //FOREIGN KEY, MUST BE DELETED LATER!
    @Column(name = "bike_id", nullable = false)
    private int bikeId;

    //BIKE RELATIONSHIP/ANNOTATION
    //@OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "bike_id", referencedColumnName = "order_id")
    //private Bike bike;


    // Constructor 1
    public BikeOrder() {}

    // Constructor 2

    public BikeOrder(int orderId, int bikeId, int orderPrice) {
        this.orderId = orderId;
        this.bikeId = bikeId;
        this.orderPrice = orderPrice;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getBikeId() {
        return bikeId;
    }

    public void setBikeId(int bikeId) {
        this.bikeId = bikeId;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }
}
