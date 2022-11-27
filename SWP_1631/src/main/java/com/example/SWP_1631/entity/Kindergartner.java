package com.example.SWP_1631.entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Data
@Table(name = "Kindergartner")
public class Kindergartner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kinder_id")
    private int KinderId;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Account account;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "img")
    private String img;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "")
    @Column(name = "dob")
    private Date dob;

    @Column(name = "gender")
    private boolean gender;


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
        return "Kindergartner{" +
                "KinderId=" + KinderId +
                ", account=" + account.toString() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", img='" + img + '\'' +
                ", dob=" + dob +
                ", gender=" + gender +
                '}';
    }
}
