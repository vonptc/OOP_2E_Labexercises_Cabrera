
public class AirTransportation extends Transportation{
    private double wingspan;

    public AirTransportation(String name, int capacity, double wingspan){
        super(name, capacity, 0.0);
        this.wingspan = wingspan;
    }
    
}
