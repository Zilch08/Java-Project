package com.trident.profile;
import com.trident.user.*;import org.springframework.http.HttpStatus;import org.springframework.security.core.Authentication;import org.springframework.web.bind.annotation.*;import org.springframework.web.server.ResponseStatusException;
@RestController @RequestMapping("/api/profile")
public class ProfileController {
 private final UserRepository users;public ProfileController(UserRepository users){this.users=users;}
 public record Update(String fullName,String headline,String bio,String skills,String education,String experience,String resumeUrl,String companyName,String companyWebsite){}
 @GetMapping("/me") public AppUser me(Authentication a){return current(a);}
 @PutMapping("/me") public AppUser update(@RequestBody Update r,Authentication a){AppUser u=current(a);if(r.fullName()!=null&&!r.fullName().isBlank())u.setFullName(r.fullName().trim());u.setHeadline(r.headline());u.setBio(r.bio());u.setSkills(r.skills());u.setEducation(r.education());u.setExperience(r.experience());u.setResumeUrl(r.resumeUrl());if(u.getRole()==Role.RECRUITER){u.setCompanyName(r.companyName());u.setCompanyWebsite(r.companyWebsite());}return users.save(u);}
 private AppUser current(Authentication a){return users.findByEmailIgnoreCase(a.getName()).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED));}
}