public class CarTester {
    public static void main(String[] args) {
        Car car1 = new Car();
        Car car2 = new Car("Red", "ABC123", "CH001", "Toyota", 2020);
        Car car3 = new Car("Blue", "XYZ789", "CH002", "Honda", 2021);
        Car car4 = new Car("Black", "LMN456", "CH003", "Ford", 2019);
        Car car5 = new Car("White", "DEF111", "CH004", "Tesla", 2022);

        car1.displayInfo();
        car2.displayInfo();
        car3.displayInfo();
        car4.displayInfo();
        car5.displayInfo();
    }
}
