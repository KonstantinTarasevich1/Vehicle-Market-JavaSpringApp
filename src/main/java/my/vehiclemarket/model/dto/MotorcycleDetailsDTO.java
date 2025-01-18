package my.vehiclemarket.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class MotorcycleDetailsDTO {

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

    @NotNull(message = "Production Year is required")
    @Positive(message = "Production Year must be positive")
    private int productionYear;

    @NotNull(message = "Horse Power is required")
    @Positive(message = "Horse Power must be positive")
    private int horsePower;

    @NotBlank(message = "Owner Phone is required")
    @Size(min = 1, max = 50, message = "Owner Phone must be between 1 and 50 characters")
    private String ownerPhone;

    public MotorcycleDetailsDTO() {
    }

    public MotorcycleDetailsDTO(Long id, String name, String brand, String model, String imageURL, double fuelConsumption, String description, Double price, int productionYear, int horsePower, String ownerPhone) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.imageURL = imageURL;
        this.fuelConsumption = fuelConsumption;
        this.description = description;
        this.price = price;
        this.productionYear = productionYear;
        this.horsePower = horsePower;
        this.ownerPhone = ownerPhone;
    }

    public Long getId() {
        return id;
    }

    public MotorcycleDetailsDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public MotorcycleDetailsDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public MotorcycleDetailsDTO setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getModel() {
        return model;
    }

    public MotorcycleDetailsDTO setModel(String model) {
        this.model = model;
        return this;
    }

    public String getImageURL() {
        return imageURL;
    }

    public MotorcycleDetailsDTO setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public MotorcycleDetailsDTO setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public MotorcycleDetailsDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public MotorcycleDetailsDTO setPrice(Double price) {
        this.price = price;
        return this;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public MotorcycleDetailsDTO setProductionYear(int productionYear) {
        this.productionYear = productionYear;
        return this;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public MotorcycleDetailsDTO setHorsePower(int horsePower) {
        this.horsePower = horsePower;
        return this;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public MotorcycleDetailsDTO setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
        return this;
    }
}
