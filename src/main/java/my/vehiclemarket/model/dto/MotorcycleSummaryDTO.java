package my.vehiclemarket.model.dto;

public class MotorcycleSummaryDTO {
    private Long id;
    private String name;
    private String brand;
    private String model;
    private double price;
    private int horsePower;
    private int productionYear;

    public MotorcycleSummaryDTO() {}

    public MotorcycleSummaryDTO(Long id, String name, String brand, String model, double price, int horsePower, int productionYear) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.horsePower = horsePower;
        this.productionYear = productionYear;
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
    public int getHorsePower() { return horsePower; }
    public void setHorsePower(int horsePower) { this.horsePower = horsePower; }
    public int getProductionYear() { return productionYear; }
    public void setProductionYear(int productionYear) { this.productionYear = productionYear; }
}
