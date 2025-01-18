package my.vehiclemarket.model.dto;

import my.vehiclemarket.model.enums.CarTypeEnum;
import my.vehiclemarket.model.enums.EngineTypeEnum;

public class CarSummaryDTO {
    private Long id;
    private String name;
    private String brand;
    private String model;
    private double price;
    private int productionYear;
    private CarTypeEnum carType;
    private int horsepower;
    private EngineTypeEnum engineType;

    public CarSummaryDTO() {}

    public CarSummaryDTO(Long id, String name, String brand, String model, double price, int productionYear, CarTypeEnum carType, int horsepower, EngineTypeEnum engineType) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.productionYear = productionYear;
        this.carType = carType;
        this.horsepower = horsepower;
        this.engineType = engineType;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getProductionYear() { return productionYear; }
    public void setProductionYear(int productionYear) { this.productionYear = productionYear; }
    public CarTypeEnum getCarType() { return carType; }
    public void setCarType(CarTypeEnum carType) { this.carType = carType; }
    public int getHorsepower() { return horsepower; }
    public void setHorsepower(int horsepower) { this.horsepower = horsepower; }
    public EngineTypeEnum getEngineType() { return engineType; }
    public void setEngineType(EngineTypeEnum engineType) { this.engineType = engineType; }
}
