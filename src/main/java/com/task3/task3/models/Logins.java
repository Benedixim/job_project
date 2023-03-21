package com.task3.task3.models;

import jakarta.persistence.*;

@Entity
@Table
public class Logins {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idlogins;

    private String Application;
    private String AppAccountName;
    private boolean IsActive;
    private String JobTitle;
    private String Department;

    public Long getIdlogins() {
        return idlogins;
    }

    public void setIdlogins(Long id) {
        this.idlogins = id;
    }

    public String getApplication() {
        return Application;
    }

    public String getAppAccountName() {
        return AppAccountName;
    }

    public boolean getisActive() {
        return IsActive;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public String getDepartment() {
        return Department;
    }


}