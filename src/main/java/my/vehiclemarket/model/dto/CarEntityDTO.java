package my.vehiclemarket.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import my.vehiclemarket.model.enums.*;

public class CarEntityDTO {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Brand is required")
    @Size(min = 2, max = 50, message = "Brand must be between 2 and 50 characters")
    private String brand;

    @NotBlank(message = "Model is required")
    @Size(min = 1, max = 50, message = "Model must be between 1 and 50 characters")
    private String model;

    @NotBlank(message = "ImageURL is required")
    @Size(min = 1, max = 50, message = "ImageURL must be between 1 and 50 characters")
    private String imageURL;

    private double fuelConsumption;

    @NotBlank(message = "Description is required")
    @Size(min = 1, max = 200, message = "Description must be between 1 and 200 characters")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;

    private TransmissionTypeEnum transmissionType;

    @NotNull(message = "Car Type cannot be empty")
    private CarTypeEnum carType;

    @NotNull(message = "Engine Type cannot be empty")
    private EngineTypeEnum engineType;

    @NotNull(message = "Horse Power cannot be empty")
    @Positive(message = "Horse Power must be a positive number")
    private int horsePower;

    @NotNull(message = "Year is required")
    @Positive(message = "Year must be positive")
    private int productionYear;

    public CarEntityDTO() {}

    public String getName() {
        return name;
    }

    public CarEntityDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public CarEntityDTO setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getModel() {
        return model;
    }

    public CarEntityDTO setModel(String model) {
        this.model = model;
        return this;
    }

    public String getImageURL() {
        return imageURL;
    }

    public CarEntityDTO setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public CarEntityDTO setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CarEntityDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public CarEntityDTO setPrice(Double price) {
        this.price = price;
        return this;
    }

    public TransmissionTypeEnum getTransmissionType() {
        return transmissionType;
    }

    public CarEntityDTO setTransmissionType(TransmissionTypeEnum transmissionType) {
        this.transmissionType = transmissionType;
        return this;
    }

    public CarTypeEnum getCarType() {
        return carType;
    }

    public CarEntityDTO setCarType(CarTypeEnum carType) {
        this.carType = carType;
        return this;
    }

    public EngineTypeEnum getEngineType() {
        return engineType;
    }

    public CarEntityDTO setEngineType(EngineTypeEnum engineType) {
        this.engineType = engineType;
        return this;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public CarEntityDTO setHorsePower(int horsePower) {
        this.horsePower = horsePower;
        return this;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public CarEntityDTO setProductionYear(int productionYear) {
        this.productionYear = productionYear;
        return this;
    }
}
