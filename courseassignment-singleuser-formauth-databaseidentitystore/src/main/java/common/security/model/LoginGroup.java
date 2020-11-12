package common.security.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
@Entity
public class LoginGroup implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message="Enter a group name")
    @Column(length=64, unique=true, nullable=false)
    private String groupname;

    @ManyToMany(mappedBy="groups")
    private List<LoginUser> users;

    @Version
    private Integer version;

    /**
     * https://vladmihalcea.com/the-best-way-to-implement-equals-hashcode-and-tostring-with-jpa-and-hibernate/
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof LoginGroup))
            return false;

        LoginGroup other = (LoginGroup) o;
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