package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "order_bike", schema = "heroku_7f91564409e745b")
@IdClass(OrderBikePK.class)
public class OrderBike {
    private int orderId;
    private int bikeId;
    private OrderInfo orderInfoByOrderId;
    private Bike bikeByBikeId;

    @Id
    @Column(name = "order_id", nullable = false)
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Id
    @Column(name = "bike_id", nullable = false)
    public int getBikeId() {
        return bikeId;
    }

    public void setBikeId(int bikeId) {
        this.bikeId = bikeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderBike orderBike = (OrderBike) o;

        if (orderId != orderBike.orderId) return false;
        if (bikeId != orderBike.bikeId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId;
        result = 31 * result + bikeId;
        return result;
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false, insertable = false, updatable = false)
    public OrderInfo getOrderInfoByOrderId() {
        return orderInfoByOrderId;
    }

    public void setOrderInfoByOrderId(OrderInfo orderInfoByOrderId) {
        this.orderInfoByOrderId = orderInfoByOrderId;
    }

    @ManyToOne
    @JoinColumn(name = "bike_id", referencedColumnName = "bike_id", nullable = false, insertable = false, updatable = false)
    public Bike getBikeByBikeId() {
        return bikeByBikeId;
    }

    public void setBikeByBikeId(Bike bikeByBikeId) {
        this.bikeByBikeId = bikeByBikeId;
    }
}
