package com.backfeed.backfeed_core.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

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

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String firstName, String lastName, String email, String password, Role role, Company company) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.company = company;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<FeedbackAnswer> getPoComments() {
        return poComments;
    }

    public void setPoComments(Set<FeedbackAnswer> poComments) {
        this.poComments = poComments;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Set<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(Set<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public Set<Hierarchy> getSupervisorHierarchies() {
        return supervisorHierarchies;
    }

    public void setSupervisorHierarchies(Set<Hierarchy> supervisorHierarchies) {
        this.supervisorHierarchies = supervisorHierarchies;
    }

    public Set<Hierarchy> getSubordinateHierarchies() {
        return subordinateHierarchies;
    }

    public void setSubordinateHierarchies(Set<Hierarchy> subordinateHierarchies) {
        this.subordinateHierarchies = subordinateHierarchies;
    }

    public Set<Invitation> getInvitations() {
        return invitations;
    }

    public void setInvitations(Set<Invitation> invitations) {
        this.invitations = invitations;
    }

    public Set<Company> getCreatedCompanies() {
        return createdCompanies;
    }

    public void setCreatedCompanies(Set<Company> createdCompanies) {
        this.createdCompanies = createdCompanies;
    }
}
