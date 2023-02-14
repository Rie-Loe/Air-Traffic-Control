package ccpa;

import java.util.Random;

public class Passenger {
    int min = 1;
    int max = 51;
    int totalP;
    
    int total1;
    int total2;
    int total3;
    int total4;
    
    Gate1 G1;
    Gate2 G2;
    Gate3 G3;
    Gate4 G4;
    
    public Passenger (Gates G) {
        G1 = G.getG1();
        G2 = G.getG2();
        G3 = G.getG3();
        G4 = G.getG4();
    };
    
    static public int Passenger (int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
    
    public int totalPassenger () {
        total1 = G1.getPassenger1();
        total2 = G2.getPassenger2();
        total3 = G3.getPassenger3();
        total4 = G4.getPassenger4();
        totalP = total1 + total2 + total3 + total4;
        return totalP;
    }
}
