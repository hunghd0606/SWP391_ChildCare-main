package com.example.SWP_1631.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int roleID;

    @Column(name = "role_name")
    private String roleName;

    public Role(int roleID) {
        this.roleID = roleID;
    }

    public Role() {
    }
}
