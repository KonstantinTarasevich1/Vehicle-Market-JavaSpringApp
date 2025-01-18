package my.vehiclemarket.model.dto;

import my.vehiclemarket.model.enums.TruckTypeEnum;

public class TruckSummaryDTO {
    private Long id;
    private String name;
    private String brand;
    private String model;
    private double price;
    private int productionYear;
    private TruckTypeEnum truckType;
    private int loadCapacity;

    public TruckSummaryDTO() {}

    public TruckSummaryDTO(Long id, String name, String brand, String model, double price, int productionYear,
                           TruckTypeEnum truckType, int loadCapacity) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.productionYear = productionYear;
        this.truckType = truckType;
        this.loadCapacity = loadCapacity;
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
    public TruckTypeEnum getTruckType() { return truckType; }
    public void setTruckType(TruckTypeEnum truckType) { this.truckType = truckType; }
    public int getLoadCapacity() { return loadCapacity; }
    public void setLoadCapacity(int loadCapacity) { this.loadCapacity = loadCapacity; }
}
