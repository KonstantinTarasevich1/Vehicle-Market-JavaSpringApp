package my.vehiclemarket.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import my.vehiclemarket.model.enums.CarTypeEnum;
import my.vehiclemarket.model.enums.EngineTypeEnum;
import my.vehiclemarket.model.enums.TransmissionTypeEnum;

public class CarDetailsDTO {

    private Long id;

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
    @Size(min = 1, max = 255, message = "ImageURL must be between 1 and 255 characters")
    private String imageURL;

    @Positive(message = "Fuel consumption must be positive")
    private double fuelConsumption;

    @NotBlank(message = "Description is required")
    @Size(min = 1, max = 200, message = "Description must be between 1 and 200 characters")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotNull(message = "Transmission Type is required")
    private TransmissionTypeEnum transmissionType;

    @NotNull(message = "Car Type is required")
    private CarTypeEnum carType;

    @NotNull(message = "Engine Type is required")
    private EngineTypeEnum engineType;

    @NotNull(message = "Horse Power is required")
    @Positive(message = "Horse Power must be a positive number")
    private int horsePower;

    @NotNull(message = "Production Year is required")
    @Positive(message = "Production Year must be positive")
    private int productionYear;

    @NotBlank(message = "Owner Phone is required")
    @Size(min = 1, max = 50, message = "Owner Phone must be between 1 and 50 characters")
    private String ownerPhone;

    public CarDetailsDTO() {
    }

    public CarDetailsDTO(Long id, String name, String brand, String model, String imageURL, double fuelConsumption, String description, Double price, TransmissionTypeEnum transmissionType, CarTypeEnum carType, EngineTypeEnum engineType, int horsePower, int productionYear, String ownerPhone) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.imageURL = imageURL;
        this.fuelConsumption = fuelConsumption;
        this.description = description;
        this.price = price;
        this.transmissionType = transmissionType;
        this.carType = carType;
        this.engineType = engineType;
        this.horsePower = horsePower;
        this.productionYear = productionYear;
        this.ownerPhone = ownerPhone;
    }

    public Long getId() {
        return id;
    }

    public CarDetailsDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CarDetailsDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public CarDetailsDTO setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getModel() {
        return model;
    }

    public CarDetailsDTO setModel(String model) {
        this.model = model;
        return this;
    }

    public String getImageURL() {
        return imageURL;
    }

    public CarDetailsDTO setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public CarDetailsDTO setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CarDetailsDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public CarDetailsDTO setPrice(Double price) {
        this.price = price;
        return this;
    }

    public TransmissionTypeEnum getTransmissionType() {
        return transmissionType;
    }

    public CarDetailsDTO setTransmissionType(TransmissionTypeEnum transmissionType) {
        this.transmissionType = transmissionType;
        return this;
    }

    public CarTypeEnum getCarType() {
        return carType;
    }

    public CarDetailsDTO setCarType(CarTypeEnum carType) {
        this.carType = carType;
        return this;
    }

    public EngineTypeEnum getEngineType() {
        return engineType;
    }

    public CarDetailsDTO setEngineType(EngineTypeEnum engineType) {
        this.engineType = engineType;
        return this;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public CarDetailsDTO setHorsePower(int horsePower) {
        this.horsePower = horsePower;
        return this;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public CarDetailsDTO setProductionYear(int productionYear) {
        this.productionYear = productionYear;
        return this;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public CarDetailsDTO setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
        return this;
    }
}
