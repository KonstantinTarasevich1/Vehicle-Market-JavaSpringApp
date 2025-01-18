package my.vehiclemarket.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import my.vehiclemarket.model.enums.BoatTypeEnum;

public class BoatDetailsDTO {

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

    @NotNull(message = "Production year is required")
    @Positive(message = "Production year must be positive")
    private int productionYear;

    @NotNull(message = "Boat Type is required")
    private BoatTypeEnum boatType;

    @NotBlank(message = "Owner phone is required")
    @Size(min = 1, max = 50, message = "Owner phone must be between 1 and 50 characters")
    private String ownerPhone;

    public BoatDetailsDTO() {
    }

    public BoatDetailsDTO(Long id, String name, String brand, String model, String imageURL, double fuelConsumption, String description, Double price, int productionYear, BoatTypeEnum boatType, String ownerPhone) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.imageURL = imageURL;
        this.fuelConsumption = fuelConsumption;
        this.description = description;
        this.price = price;
        this.productionYear = productionYear;
        this.boatType = boatType;
        this.ownerPhone = ownerPhone;
    }

    public Long getId() {
        return id;
    }

    public BoatDetailsDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public BoatDetailsDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public BoatDetailsDTO setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getModel() {
        return model;
    }

    public BoatDetailsDTO setModel(String model) {
        this.model = model;
        return this;
    }

    public String getImageURL() {
        return imageURL;
    }

    public BoatDetailsDTO setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public BoatDetailsDTO setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BoatDetailsDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public BoatDetailsDTO setPrice(Double price) {
        this.price = price;
        return this;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public BoatDetailsDTO setProductionYear(int productionYear) {
        this.productionYear = productionYear;
        return this;
    }

    public BoatTypeEnum getBoatType() {
        return boatType;
    }

    public BoatDetailsDTO setBoatType(BoatTypeEnum boatType) {
        this.boatType = boatType;
        return this;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public BoatDetailsDTO setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
        return this;
    }
}
