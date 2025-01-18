package my.vehiclemarket.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import my.vehiclemarket.model.enums.TruckTypeEnum;

public class TruckEntityDTO {

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

    @NotNull(message = "Year is required")
    @Positive(message = "Year must be positive")
    private int productionYear;

    @NotNull(message = "Truck Type is required")
    private TruckTypeEnum truckType;

    @NotNull(message = "Load capacity is required")
    @Positive(message = "Load capacity must be positive")
    private int loadCapacity;

    public TruckEntityDTO() {
    }

    public String getName() {
        return name;
    }

    public TruckEntityDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public TruckEntityDTO setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getModel() {
        return model;
    }

    public TruckEntityDTO setModel(String model) {
        this.model = model;
        return this;
    }

    public String getImageURL() {
        return imageURL;
    }

    public TruckEntityDTO setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public TruckEntityDTO setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TruckEntityDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public TruckEntityDTO setPrice(Double price) {
        this.price = price;
        return this;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public TruckEntityDTO setProductionYear(int productionYear) {
        this.productionYear = productionYear;
        return this;
    }

    public TruckTypeEnum getTruckType() {
        return truckType;
    }

    public TruckEntityDTO setTruckType(TruckTypeEnum truckType) {
        this.truckType = truckType;
        return this;
    }

    public int getLoadCapacity() {
        return loadCapacity;
    }

    public TruckEntityDTO setLoadCapacity(int loadCapacity) {
        this.loadCapacity = loadCapacity;
        return this;
    }
}
