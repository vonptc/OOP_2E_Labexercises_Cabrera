public class LandTransportation extends Transportation {
    private int wheels;

    public LandTransportation(String name, int capacity, int wheels){
        super(name, capacity, 0.0);
        this.wheels = wheels;
    }
}
