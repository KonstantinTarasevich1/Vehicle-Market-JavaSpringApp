package my.vehiclemarket.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import my.vehiclemarket.model.enums.RolesEnum;

@Entity
@Table(name = "roles")
public class UserRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private RolesEnum role;


    public long getId() {
        return id;
    }

    public UserRoleEntity setId(long id) {
        this.id = id;
        return this;
    }

    public RolesEnum getRole() {
        return role;
    }

    public UserRoleEntity setRole(RolesEnum role) {
        this.role = role;
        return this;
    }
}
