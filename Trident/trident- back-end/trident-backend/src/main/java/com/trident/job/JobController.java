package com.trident.job;
import com.trident.user.*;import jakarta.validation.Valid;import jakarta.validation.constraints.NotBlank;import org.springframework.http.HttpStatus;import org.springframework.security.core.Authentication;import org.springframework.web.bind.annotation.*;import org.springframework.web.server.ResponseStatusException;import java.util.List;
@RestController @RequestMapping("/api/jobs")
public class JobController {
 private final JobRepository jobs;private final UserRepository users;public JobController(JobRepository jobs,UserRepository users){this.jobs=jobs;this.users=users;}
 public record JobRequest(@NotBlank String title,@NotBlank String description,String companyName,String location,String type,String workMode,String salary,String skills){}
 @GetMapping public List<Job> list(@RequestParam(required=false)String q,@RequestParam(required=false)String location,@RequestParam(required=false)String type,@RequestParam(required=false)String workMode){return jobs.search(n(q),n(location),n(type),n(workMode));}
 @GetMapping("/{id}") public Job get(@PathVariable Long id){return jobs.findById(id).filter(Job::isActive).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public Job create(@Valid @RequestBody JobRequest r,Authentication a){AppUser u=user(a);Job j=new Job();copy(r,j);j.setRecruiter(u);if(j.getCompanyName()==null||j.getCompanyName().isBlank())j.setCompanyName(u.getCompanyName()==null?u.getFullName():u.getCompanyName());return jobs.save(j);}
 @PutMapping("/{id}") public Job update(@PathVariable Long id,@Valid @RequestBody JobRequest r,Authentication a){Job j=owned(id,a);copy(r,j);return jobs.save(j);}
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void close(@PathVariable Long id,Authentication a){Job j=owned(id,a);j.setActive(false);jobs.save(j);}
 @GetMapping("/mine") public List<Job> mine(Authentication a){return jobs.findByRecruiterEmailIgnoreCaseOrderByCreatedAtDesc(a.getName());}
 private AppUser user(Authentication a){return users.findByEmailIgnoreCase(a.getName()).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED));}
 private Job owned(Long id,Authentication a){Job j=jobs.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));if(!j.getRecruiter().getEmail().equalsIgnoreCase(a.getName())&&user(a).getRole()!=Role.ADMIN)throw new ResponseStatusException(HttpStatus.FORBIDDEN);return j;}
 private void copy(JobRequest r,Job j){j.setTitle(r.title().trim());j.setDescription(r.description().trim());j.setCompanyName(r.companyName());j.setLocation(r.location());j.setType(r.type());j.setWorkMode(r.workMode());j.setSalary(r.salary());j.setSkills(r.skills());}
 private String n(String s){return s==null||s.isBlank()?null:s.trim();}
}