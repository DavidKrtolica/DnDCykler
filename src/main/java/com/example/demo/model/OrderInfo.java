package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "order_info", schema = "bikeshopdb")
public class OrderInfo {
    private int orderId;
    private int totalPrice;
    private Collection<OrderBike> orderBikesByOrderId;
    private Customer customerByCustomerId;

    public OrderInfo(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderInfo(){}

    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    @Id
    @Column(name = "order_id", nullable = false)
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "total_price", nullable = false)
    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderInfo orderInfo = (OrderInfo) o;

        if (orderId != orderInfo.orderId) return false;
        if (totalPrice != orderInfo.totalPrice) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId;
        result = 31 * result + totalPrice;
        return result;
    }


    @OneToMany(mappedBy = "orderInfoByOrderId")
    public Collection<OrderBike> getOrderBikesByOrderId() {
        return orderBikesByOrderId;
    }

    public void setOrderBikesByOrderId(Collection<OrderBike> orderBikesByOrderId) {
        this.orderBikesByOrderId = orderBikesByOrderId;
    }

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    public Customer getCustomerByCustomerId() {
        return customerByCustomerId;
    }

    public void setCustomerByCustomerId(Customer customerByCustomerId) {
        this.customerByCustomerId = customerByCustomerId;
    }
}
