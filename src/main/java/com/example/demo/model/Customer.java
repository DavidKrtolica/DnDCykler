package com.example.demo.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    private int customerId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone_nr", nullable = false)
    private String phoneNr;

    @Column(name = "email")
    private String email;

    //ANNOTATION FOR RELATIONSHIP

    public Customer(){}

    public Customer(String firstName, String lastName, String address, String phoneNr, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNr = phoneNr;
        this.email = email;
    }


}
