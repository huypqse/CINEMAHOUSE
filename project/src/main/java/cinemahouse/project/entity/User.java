package cinemahouse.project.entity;

import cinemahouse.project.entity.status.Account;
import cinemahouse.project.entity.status.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "user", indexes = {
        @Index(name = "idx_user_email", columnList = "email"),
        @Index(name = "idx_user_username", columnList = "username"),
        @Index(name = "idx_user_email_username", columnList = "email, username")
})
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends AbstractEntity<Long>  {

    @Column(name = "username", nullable = false, length = 50)
    String username;
    @Column(name= "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    Gender gender;
    @Column(name = "phone_number", nullable = false, length = 15)
    String phoneNumber;
    @Column(name = "dob")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    LocalDate birthDate;
    @Column(name = "otp", length = 6)
    String otp;
    @Column(name = "otp_expiry_date")
    LocalDateTime otpExpiryDate;
    @Column(name = "email", nullable = false, unique = true, length = 100)
    @NotNull
    @Email
    String email;
    @Column(name = "password", nullable = false, length = 255)
    String password;
    @Column(name= "account", nullable = false)
    @Enumerated(EnumType.STRING)
    Account account;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "comment_id")
    Comment comment;


}
