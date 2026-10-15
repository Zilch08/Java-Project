package com.trident.job;
import org.springframework.data.jpa.repository.*;import org.springframework.data.repository.query.Param;import java.util.List;
public interface JobRepository extends JpaRepository<Job,Long>{
 @Query("select j from Job j where j.active=true and (:q is null or lower(j.title) like lower(concat('%',:q,'%')) or lower(j.companyName) like lower(concat('%',:q,'%')) or lower(j.skills) like lower(concat('%',:q,'%'))) and (:loc is null or lower(j.location) like lower(concat('%',:loc,'%'))) and (:type is null or lower(j.type)=lower(:type)) and (:mode is null or lower(j.workMode)=lower(:mode)) order by j.createdAt desc")
 List<Job> search(@Param("q")String q,@Param("loc")String loc,@Param("type")String type,@Param("mode")String mode);
 List<Job> findByRecruiterEmailIgnoreCaseOrderByCreatedAtDesc(String email);
}