package com.glr.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Undergraduate
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String school;
    private boolean isAuth;
    private String major;
    private boolean isAbroad;
    private String ability;
    private String service;
    private float price;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getSchool()
    {
        return school;
    }

    public void setSchool(String school)
    {
        this.school = school;
    }

    public boolean isAuth()
    {
        return isAuth;
    }

    public void setAuth(boolean auth)
    {
        isAuth = auth;
    }

    public String getMajor()
    {
        return major;
    }

    public void setMajor(String major)
    {
        this.major = major;
    }

    public boolean isAbroad()
    {
        return isAbroad;
    }

    public void setAbroad(boolean abroad)
    {
        isAbroad = abroad;
    }

    public String getAbility()
    {
        return ability;
    }

    public void setAbility(String ability)
    {
        this.ability = ability;
    }

    public String getService()
    {
        return service;
    }

    public void setService(String service)
    {
        this.service = service;
    }

    public float getPrice()
    {
        return price;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }
}
