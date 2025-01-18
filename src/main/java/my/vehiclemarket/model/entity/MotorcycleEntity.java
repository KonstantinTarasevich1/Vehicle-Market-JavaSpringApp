package my.vehiclemarket.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "motorcycles")
public class MotorcycleEntity extends BaseVehicleEntity {

    @NotNull(message = "Horse Power is required")
    @Positive(message = "Horse power must be positive")
    @Column(nullable = false, name = "horse_power")
    private int horsePower;

    public int getHorsePower() {
        return horsePower;
    }

    public MotorcycleEntity setHorsePower(int horsePower) {
        this.horsePower = horsePower;
        return this;
    }
}
