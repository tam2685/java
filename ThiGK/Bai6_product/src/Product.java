import java.io.Serializable;

public class Product implements Serializable {
    private String code;
    private String name;
    private String unit;
    private double price;

    public Product(String code, String name, String unit, double price) {
        this.code = code;
        this.name = name;
        this.unit = unit;
        this.price = price;
    }

    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getUnit() {
        return unit;
    }


    public void setUnit(String unit) {
        this.unit = unit;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
