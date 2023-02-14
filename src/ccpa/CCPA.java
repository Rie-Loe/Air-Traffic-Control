package ccpa;

import java.util.concurrent.Semaphore;


public class CCPA {

    public static void main(String[] args) {
        Semaphore permit = new Semaphore(1);
        int totalPass;
        int totalP = 0;
        
        Plane P[] = new Plane[10];
        Gates G = new Gates();
        GateCheck C = new GateCheck(0);
        C.setGates(G);
        C.setPermit(permit);
        Passenger pass = new Passenger(G);
        
        Gate1 G1;
        Gate2 G2;
        Gate3 G3;
        Gate4 G4;
        
        G1 = G.getG1();
        G2 = G.getG2();
        G3 = G.getG3();
        G4 = G.getG4();
        
        //Announce airport is opened
        System.out.println("AIRPORT IS OPENED.");
        
        
        //Creating Planes
        for (int i = 0; i < P.length; i++) {
            P[i] = new Plane("Plane - " + Integer.toString(i), permit, G, C, G1, G2, G3, G4);
        }
        
        //Starting Plane threads
        for (int i = 0; i < P.length; i++) {
        try {
            P[i].start();
        } catch (Exception e) {}
        }
        
        //Ending Plane threads
        for (int i = 0; i < P.length; i++) {
        try {
            P[i].join();
            totalP += P[i].totalPlane();
            P[i] = new Plane("Plane - " + Integer.toString(i), permit, G, C, G1, G2, G3, G4);
        } catch (InterruptedException ex) {}
        }
        
        //Check for remaining Gates
        C.GateRemain(G.Tg, G.gA, G.gB, G.gC, G.gD);
        
        //Print statistics
        totalPass = pass.totalPassenger();
        System.out.println("Total Passengers served: " + totalPass);
        System.out.println("Total Planes served: " + totalP);
        
        //Announce airport is closed
        System.out.println("AIRPORT IS CLOSED.");
    }
}
