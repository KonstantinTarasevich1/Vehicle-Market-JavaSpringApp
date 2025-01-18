package my.vehiclemarket.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import my.vehiclemarket.model.enums.BoatTypeEnum;

@Entity
@Table(name = "boats")
public class BoatEntity extends BaseVehicleEntity {

    @NotNull(message = "Boat Type cannot be empty")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "boat_type")
    private BoatTypeEnum boatType;

    public BoatTypeEnum getBoatType() {
        return this.boatType;
    }

    public BoatEntity setBoatType(BoatTypeEnum boatType) {
        this.boatType = boatType;
        return this;
    }
}
