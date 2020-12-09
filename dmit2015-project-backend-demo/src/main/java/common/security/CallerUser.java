package common.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CallerUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Size(min=3, max=32, message="Enter a username that contains {min} to {max} characters")
    @Column(length=32, unique=true, nullable=false)
    private String username;

    @Column(nullable=false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "CallerGroup", joinColumns = {@JoinColumn(name = "username")})
    @Column(name = "groupname", nullable = false)
    private Set<String> groups = new HashSet<>();

    @Version
    private Integer version;

    /**
     * https://vladmihalcea.com/the-best-way-to-implement-equals-hashcode-and-tostring-with-jpa-and-hibernate/
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof CallerUser))
            return false;

        CallerUser other = (CallerUser) o;
        return Objects.equals(getUsername(), other.getUsername());
    }

    /**
     * https://vladmihalcea.com/the-best-way-to-implement-equals-hashcode-and-tostring-with-jpa-and-hibernate/
     */
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}