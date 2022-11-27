package com.example.SWP_1631.entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Data
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private int accountId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "img")
    private String img;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "")
    @Column(name = "dob")
    private Date dob;

    @Column(name = "gender")
    private boolean gender;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public Date getDob() {
        return dob;
    }

    public void setDob(String dob) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.dob = formatter.parse(dob);
        } catch (Exception e) {
            String sDate = "17/07/2017";
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
            this.dob = date;
        }

    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", img='" + img + '\'' +
                ", dob=" + dob +
                ", gender=" + gender +
                ", role=" + role +
                '}';
    }

    public Account() {
    }

    public Account(int accountId, String firstName, String lastName, String email, String password, String phoneNumber, String address, String img, Date dob, boolean gender, Role role) {
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.img = img;
        this.dob = dob;
        this.gender = gender;
        this.role = role;
    }
}
