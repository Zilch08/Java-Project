package com.trident.user;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
@Entity @Table(name="users")
public class AppUser {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true) private String email;
 @JsonIgnore @Column(nullable=false) private String password;
 @Column(nullable=false) private String fullName;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private Role role;
 private String headline; @Column(length=2000) private String bio; private String skills; private String education; private String experience; private String resumeUrl; private String companyName; private String companyWebsite;
 private LocalDateTime createdAt=LocalDateTime.now();
 public Long getId(){return id;} public String getEmail(){return email;} public void setEmail(String v){email=v;} public String getPassword(){return password;} public void setPassword(String v){password=v;} public String getFullName(){return fullName;} public void setFullName(String v){fullName=v;} public Role getRole(){return role;} public void setRole(Role v){role=v;} public String getHeadline(){return headline;} public void setHeadline(String v){headline=v;} public String getBio(){return bio;} public void setBio(String v){bio=v;} public String getSkills(){return skills;} public void setSkills(String v){skills=v;} public String getEducation(){return education;} public void setEducation(String v){education=v;} public String getExperience(){return experience;} public void setExperience(String v){experience=v;} public String getResumeUrl(){return resumeUrl;} public void setResumeUrl(String v){resumeUrl=v;} public String getCompanyName(){return companyName;} public void setCompanyName(String v){companyName=v;} public String getCompanyWebsite(){return companyWebsite;} public void setCompanyWebsite(String v){companyWebsite=v;} public LocalDateTime getCreatedAt(){return createdAt;}
}