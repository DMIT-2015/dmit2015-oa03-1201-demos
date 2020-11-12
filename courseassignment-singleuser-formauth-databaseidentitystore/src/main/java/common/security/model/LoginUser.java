package common.security.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@PasswordMatch(groups = PasswordValidationGroup.class)
@Data
@Entity
public class LoginUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Size(min=3, max=64, message="Enter a username that contains {min} to {max} characters")
    @Column(length=64, unique=true, nullable=false)
    private String username;

    @Column(nullable=false)
    private String password;

    @Transient
    @Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",
            message="Password value must contain at least 8 characters with at least 1 uppercase letter, 1 lowercase letter, and 1 number",
            groups = {PasswordValidationGroup.class})
    private String plainTextPassword = "Password2015";

    @Transient
    @Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",
            message="Confirm Password value must contain at least 8 characters with at least 1 uppercase letter, 1 lowercase letter, and 1 number",
            groups = {PasswordValidationGroup.class})
    private String confirmedPlainTextPassword = "Password2015";

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            name="LoginUserGroup",
            joinColumns={
                    @JoinColumn(name="userid")
            },
            inverseJoinColumns={
                    @JoinColumn(name="groupid")
            }
    )
    private List<LoginGroup> groups = new ArrayList<>();

    @Version
    private Integer version;

    /**
     * https://vladmihalcea.com/the-best-way-to-implement-equals-hashcode-and-tostring-with-jpa-and-hibernate/
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof LoginUser))
            return false;

        LoginUser other = (LoginUser) o;
        return Objects.equals(getId(), other.getId());
    }

    /**
     * https://vladmihalcea.com/the-best-way-to-implement-equals-hashcode-and-tostring-with-jpa-and-hibernate/
     */
    @Override
    public int hashCode() {
        return 31;
    }

}