package my.vehiclemarket.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class MotorcycleEntityDTO {

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

    @NotNull(message = "Horse Power is required")
    @Positive(message = "Horse power must be positive")
    private int horsePower;

    public MotorcycleEntityDTO() {
    }

    public String getName() {
        return name;
    }

    public MotorcycleEntityDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public MotorcycleEntityDTO setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getModel() {
        return model;
    }

    public MotorcycleEntityDTO setModel(String model) {
        this.model = model;
        return this;
    }

    public String getImageURL() {
        return imageURL;
    }

    public MotorcycleEntityDTO setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public MotorcycleEntityDTO setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public MotorcycleEntityDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public MotorcycleEntityDTO setPrice(Double price) {
        this.price = price;
        return this;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public MotorcycleEntityDTO setProductionYear(int productionYear) {
        this.productionYear = productionYear;
        return this;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public MotorcycleEntityDTO setHorsePower(int horsePower) {
        this.horsePower = horsePower;
        return this;
    }
}
