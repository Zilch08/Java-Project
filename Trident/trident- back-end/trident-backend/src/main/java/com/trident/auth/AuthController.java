package com.trident.auth;
import com.trident.security.JwtService;import com.trident.user.*;import jakarta.validation.Valid;import jakarta.validation.constraints.*;import org.springframework.http.HttpStatus;import org.springframework.security.crypto.password.PasswordEncoder;import org.springframework.web.bind.annotation.*;import org.springframework.web.server.ResponseStatusException;import java.util.Map;
@RestController @RequestMapping("/api/auth")
public class AuthController {
 private final UserRepository users;private final PasswordEncoder encoder;private final JwtService jwt;
 public AuthController(UserRepository users,PasswordEncoder encoder,JwtService jwt){this.users=users;this.encoder=encoder;this.jwt=jwt;}
 public record Register(@NotBlank String fullName,@Email @NotBlank String email,@Size(min=8) String password,Role role){}
 public record Login(@Email @NotBlank String email,@NotBlank String password){}
 @PostMapping("/register") @ResponseStatus(HttpStatus.CREATED) public Map<String,Object> register(@Valid @RequestBody Register r){
  if(users.existsByEmailIgnoreCase(r.email()))throw new ResponseStatusException(HttpStatus.CONFLICT,"Email already registered");
  AppUser u=new AppUser();u.setFullName(r.fullName().trim());u.setEmail(r.email().trim().toLowerCase());u.setPassword(encoder.encode(r.password()));u.setRole(r.role()==Role.RECRUITER?Role.RECRUITER:Role.CANDIDATE);users.save(u);return result(u);
 }
 @PostMapping("/login") public Map<String,Object> login(@Valid @RequestBody Login r){AppUser u=users.findByEmailIgnoreCase(r.email()).filter(x->encoder.matches(r.password(),x.getPassword())).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid email or password"));return result(u);}
 private Map<String,Object> result(AppUser u){return Map.of("token",jwt.generate(u),"user",Map.of("id",u.getId(),"fullName",u.getFullName(),"email",u.getEmail(),"role",u.getRole()));}
}