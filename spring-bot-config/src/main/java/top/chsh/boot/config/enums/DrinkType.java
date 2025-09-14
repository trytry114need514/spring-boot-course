package top.chsh.boot.config.enums;

public enum DrinkType {
    COFFEE("咖啡", 15.0),
    TEA("茶", 10.0),
    JUICE("果汁", 12.5);

    private final String type;
    private final double price;

    // 增加了一个新的参数来接收价格
    DrinkType(String type, double price){
        this.type = type;
        this.price = price;
    }

    public String getType(){
        return type;
    }

    public double getPrice(){
        return price;
    }
}