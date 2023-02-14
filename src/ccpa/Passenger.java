package ccpa;

import java.util.Random;

public class Passenger {
    int min = 1;
    int max = 51;
    int totalP;
    
    int total1;
    int total2;
    
    Gate1 G1;
    Gate2 G2;
    
    public Passenger (Gates G) {
        G1 = G.getG1();
        G2 = G.getG2();
    };
    
    static public int Passenger (int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
    
    public int totalPassenger () {
        total1 = G1.getPassenger1();
        total2 = G2.getPassenger2();
        totalP = total1 + total2;
        return totalP;
    }
}
