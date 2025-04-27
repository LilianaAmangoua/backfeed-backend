package com.backfeed.backfeed_core.entities;

import com.backfeed.backfeed_core.enums.CompanyType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String address;
    private String city;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User user;

    @OneToOne(mappedBy = "company")
    private User userCompany;

    private LocalDateTime createdAt;
    private CompanyType companyType;

    public Company() {
    }

    public Company(Integer id, String name, String address, String city, User user, User userCompany, LocalDateTime createdAt, CompanyType companyType) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.user = user;
        this.userCompany = userCompany;
        this.createdAt = createdAt;
        this.companyType = companyType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUserCompany() {
        return userCompany;
    }

    public void setUserCompany(User userCompany) {
        this.userCompany = userCompany;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public CompanyType getCompanyType() {
        return companyType;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
