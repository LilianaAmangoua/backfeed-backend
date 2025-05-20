package com.backfeed.backfeed_core.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Firstname cannot be null.")
    private String firstName;
    @NotNull(message = "Lastname cannot be null.")
    private String lastName;
    @Email(message = "Please insert a valid email address.")
    private String email;

    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-={}\\[\\]:;\"'<>,.?/]).{14,}$",
            message = "Password must contain at least 14 characters, including at least one number and one special character."
    )
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "po")
    private Set<FeedbackAnswer> poComments;

    @OneToMany(mappedBy = "user")
    private Set<Project> projects;

    @OneToMany(mappedBy = "user")
    private Set<Feedback> feedbacks;

    @OneToMany(mappedBy = "supervisor")
    private Set<Hierarchy> supervisorHierarchies;

    @OneToMany(mappedBy = "subordinate")
    private Set<Hierarchy> subordinateHierarchies;

    @OneToMany(mappedBy = "user")
    private Set<Invitation> invitations;

    @OneToMany(mappedBy = "po")
    private Set<Company> createdCompanies;

    @OneToMany(mappedBy = "createdBy")
    private Set<PlaceholderClient> placeholderClients;

}
