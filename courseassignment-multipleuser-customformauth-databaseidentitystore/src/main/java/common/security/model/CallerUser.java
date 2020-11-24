package common.security.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class CallerUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Size(min = 3, max = 32, message = "Enter a username that contains {min} to {max} characters")
    @Column(length = 32, unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "CallerGroup", joinColumns = {@JoinColumn(name = "username")})
    @Column(name = "groupname", nullable = false)
    private Set<String> groups = new HashSet<>();

    @Transient
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",
            message = "Password value must contain at least 8 characters with at least 1 uppercase letter, 1 lowercase letter, and 1 number",
            groups = {PasswordValidationGroup.class})
    private String plainTextPassword = "Password2015";

    @Transient
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",
            message = "Confirm Password value must contain at least 8 characters with at least 1 uppercase letter, 1 lowercase letter, and 1 number",
            groups = {PasswordValidationGroup.class})
    private String confirmedPlainTextPassword = "Password2015";

    @Version
    private Integer version;
}
