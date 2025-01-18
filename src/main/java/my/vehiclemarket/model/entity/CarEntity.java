package my.vehiclemarket.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import my.vehiclemarket.model.enums.CarTypeEnum;
import my.vehiclemarket.model.enums.EngineTypeEnum;
import my.vehiclemarket.model.enums.TransmissionTypeEnum;

@Entity
@Table(name = "cars")
public class CarEntity extends BaseVehicleEntity {

    @NotNull(message = "Transmission Type cannot be empty")
    @Enumerated(EnumType.STRING)
    @Column(name = "transmission_type")
    private TransmissionTypeEnum transmissionType;

    @NotNull(message = "Car Type cannot be empty")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "car_type")
    private CarTypeEnum carType;

    @NotNull(message = "Engine Type cannot be empty")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "engine_type")
    private EngineTypeEnum engineType;

    @NotNull(message = "Horse Power is required")
    @Positive(message = "Horse power must be positive")
    @Column(nullable = false, name = "horse_power")
    private int horsePower;

    public TransmissionTypeEnum getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(TransmissionTypeEnum transmissionType) {
        this.transmissionType = transmissionType;
    }

    public CarTypeEnum getCarType() {
        return carType;
    }

    public CarEntity setCarType(CarTypeEnum carType) {
        this.carType = carType;
        return this;
    }

    public EngineTypeEnum getEngineType() {
        return engineType;
    }

    public CarEntity setEngineType(EngineTypeEnum engineType) {
        this.engineType = engineType;
        return this;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public CarEntity setHorsePower(int horsePower) {
        this.horsePower = horsePower;
        return this;
    }

}
