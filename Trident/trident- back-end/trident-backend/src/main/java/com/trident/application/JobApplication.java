package com.trident.application;
import com.trident.job.Job;import com.trident.user.AppUser;import jakarta.persistence.*;import java.time.LocalDateTime;
@Entity @Table(name="job_applications",uniqueConstraints=@UniqueConstraint(columnNames={"candidate_id","job_id"}))
public class JobApplication {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;@ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="candidate_id",nullable=false) private AppUser candidate;@ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="job_id",nullable=false) private Job job;private String status="SUBMITTED";@Column(length=2000) private String coverLetter;private LocalDateTime appliedAt=LocalDateTime.now();
 public Long getId(){return id;}public AppUser getCandidate(){return candidate;}public void setCandidate(AppUser v){candidate=v;}public Job getJob(){return job;}public void setJob(Job v){job=v;}public String getStatus(){return status;}public void setStatus(String v){status=v;}public String getCoverLetter(){return coverLetter;}public void setCoverLetter(String v){coverLetter=v;}public LocalDateTime getAppliedAt(){return appliedAt;}
}