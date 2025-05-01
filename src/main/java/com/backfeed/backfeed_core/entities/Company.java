package com.backfeed.backfeed_core.entities;

import com.backfeed.backfeed_core.enums.CompanyType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

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
    private User po;

    @OneToMany(mappedBy = "company")
    private Set<PlaceholderClient> clients;

    @OneToMany(mappedBy = "company")
    private Set<User> users;

    private LocalDateTime createdAt;
    private CompanyType companyType;

    public Company() {
    }

    public Company(Integer id, String name, String address, String city, LocalDateTime createdAt, CompanyType companyType) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
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

    public User getPo() {
        return po;
    }

    public void setPo(User po) {
        this.po = po;
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
