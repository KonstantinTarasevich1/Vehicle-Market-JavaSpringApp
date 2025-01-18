package my.vehiclemarket.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@MappedSuperclass
public abstract class BaseVehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Column(unique = true, nullable = false)
    private String name;

    @NotBlank(message = "Brand is required")
    @Size(min = 2, max = 50, message = "Brand must be between 2 and 50 characters")
    @Column(nullable = false)
    private String brand;

    @NotBlank(message = "Model is required")
    @Size(min = 1, max = 50, message = "Model must be between 1 and 50 characters")
    @Column(nullable = false)
    private String model;

    @ManyToOne(optional = false)
    private UserEntity owner;

    @NotBlank(message = "ImageURL is required")
    @Size(min = 1, max = 50, message = "ImageURL must be between 1 and 50 characters")
    @Column(nullable = false, name = "image_URL")
    private String imageURL;

    @Column(name = "fuel_consumption")
    private double fuelConsumption;

    @NotBlank(message = "Description is required")
    @Size(min = 1, max = 200, message = "Description must be between 1 and 200 characters")
    @Column(nullable = false)
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    @Column(nullable = false)
    private double price;

    @Column(nullable = false, name = "days_active")
    private int daysActive;

    @NotNull(message = "Year is required")
    @Positive(message = "Year must be positive")
    @Column(nullable = false, name = "production_year")
    private int productionYear;


    public long getId() {
        return id;
    }

    public BaseVehicleEntity setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public BaseVehicleEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public BaseVehicleEntity setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getModel() {
        return model;
    }

    public BaseVehicleEntity setModel(String model) {
        this.model = model;
        return this;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public BaseVehicleEntity setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BaseVehicleEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public BaseVehicleEntity setPrice(double price) {
        this.price = price;
        return this;
    }

    public int getDaysActive() {
        return daysActive;
    }

    public void setDaysActive(int daysActive) {
        this.daysActive = daysActive;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public BaseVehicleEntity setProductionYear(int productionYear) {
        this.productionYear = productionYear;
        return this;
    }
}