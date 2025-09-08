public class Car {
    String color;
    String plateNo;
    String chassisNo;
    String brand;
    int year;

    public Car() {
        this.color = "No Color";
        this.plateNo = "No Plate Number";
        this.chassisNo = "No Chassis No Yet!";
        this.brand = "No Brand";
        this.year = 0;
    }

    public Car(String color, String plateNo, String chassisNo, String brand, int year) {
        this.color = color;
        this.plateNo = plateNo;
        this.chassisNo = chassisNo;
        this.brand = brand;
        this.year = year;
    }

    public void displayInfo() {
        String info = "";
        info += "Color: " + this.color;
        info += "\nPlate No: " + this.plateNo;
        info += "\nChassis No: " + this.chassisNo;
        info += "\nBrand: " + this.brand;
        info += "\nYear: " + this.year;
        System.out.println(info);
        System.out.println("----------------------");
    }
}
