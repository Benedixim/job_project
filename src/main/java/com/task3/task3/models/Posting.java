package com.task3.task3.models;

import jakarta.persistence.*;
import java.sql.Date;


@Entity
public class Posting {


    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Long idpostings;

    private Long mat_Doc;
    private int item;
    private Date doc_Date;
    private Date pstngdate;
    private String material_Description;
    private int quantity;
    private String bun;
    private double amountLC;
    private String crcy;
    private String username;
    private Boolean authdelivery;

    public Posting() {
    }

    public Long getIdpostings() {
        return idpostings;
    }

    public void setIdpostings(Long idpostings) {
        this.idpostings = idpostings;
    }

    public Long getMat_Doc() {
        return mat_Doc;
    }

    public void setMat_Doc(Long mat_Doc) {
        this.mat_Doc = mat_Doc;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public Date getDoc_Date() {
        return doc_Date;
    }

    public void setDoc_Date(Date doc_Date) {
        this.doc_Date = doc_Date;
    }



    public String getMaterial_Description() {
        return material_Description;
    }

    public void setMaterial_Description(String material_Description) {
        this.material_Description = material_Description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBun() {
        return bun;
    }

    public void setBun(String BUn) {
        this.bun = BUn;
    }

    public double getAmountLC() {
        return amountLC;
    }

    public void setAmountLC(double amountLC) {
        this.amountLC = amountLC;
    }

    public String getCrcy() {
        return crcy;
    }

    public void setCrcy(String crcy) {
        this.crcy = crcy;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getPstngdate() {
        return pstngdate;
    }

    public void setPstngdate(Date pstngdate) {
        this.pstngdate = pstngdate;
    }

    public Boolean getAuthdelivery() {
        return authdelivery;
    }

    public void setAuthdelivery(Boolean authdelivery) {
        this.authdelivery = authdelivery;
    }
}
