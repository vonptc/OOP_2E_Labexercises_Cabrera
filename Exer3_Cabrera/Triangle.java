public class Triangle extends Shape {
    float base;
    float height;
    
    public Triangle(float base, float height) {
        this.base = base;
        this.height = height;
        calculateArea();
    }
    
    public void calculateArea() {
        area = 0.5f * base * height;
    }
    
    public void displayInfo() {
        System.out.println("Triangle - Base: " + base + ", Height: " + height);
    }
}