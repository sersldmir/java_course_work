package com.example.demo;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "resources")
public class Resource {
    
    @Column(name = "resid")
    private Long resid;

    @Column(name = "name")
    private String resname;

    @Column(name = "type")
    private String type;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "cost")
    private int cost;

    @Column(name = "acdate")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private String acdate;

    @Column(name = "supplier")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier", referencedColumnName = "supid",
    insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    private Supplier supplier;


    protected Resource() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getResid() {
        return resid;
    }

    public void setResid(Long resid) {
        this.resid = resid;
    }

    public String getResname() {
        return resname;
    }

    public void setResname(String resname) {
        this.resname = resname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getAcdate() {
        return acdate;
    }

    public void setAcdate(String acdate) {
        this.acdate = acdate;
    }

    
    

}
