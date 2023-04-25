package com.example.demo;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "suppliers")
public class Supplier {

    @Column(name = "supid")
    private Long supid;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @OneToMany(fetch=FetchType.LAZY, targetEntity=Resource.class, mappedBy = "resid", orphanRemoval = false)
    private Set<Resource> resources;

    

    protected Supplier() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getSupid() {
        return supid;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setSupid(Long supid) {
        this.supid = supid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    

    
    

    
}
