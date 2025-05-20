package com.backfeed.backfeed_core.entities;

import com.backfeed.backfeed_core.enums.CompanyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "company")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}
