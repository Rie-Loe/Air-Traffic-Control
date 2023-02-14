package ccpa;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Gate4 {
    
    int min = 1;
    int max = 51;
    int passenger4;
    int newP4;
    int TP4;
    
    public void enter4 (Plane P, Semaphore gD, Semaphore pD, Semaphore Tg) {
        
        try {
            //Acquire permission to dock at Gate 4
            gD.acquire(1);
            System.out.println(P.getName() + "     : " + P.getName() + " passengers disembarked, waiting for Bus 4.");
            
            //Simulate delay in waiting for bus
            int a = new Random().nextInt(2);
            Thread.sleep(a*1000);
            
            //Drop off passengers for the first time
                enterBus4(P, pD);
            //Simulate delay in refilling plane supplies
                P.refillSupplies(P);
            //Take up new passengers for the second time
                enterPlane4(P, pD);
            
        } catch (InterruptedException ex) {}
    }
    
    //Function to drop off passengers for the first time
    public int enterBus4 (Plane P, Semaphore pD) {
        try {
            int a = new Random().nextInt(2);
            Thread.sleep(a*1000);
            
            //Generate passengers to take up plane seats
            passenger4 = Passenger.Passenger(min, max);
            if (passenger4 > 0 && passenger4 < 51) {
                pD.acquire(passenger4);
                System.out.println(P.getName() + "     : " + passenger4 + " passengers from " + P.getName() + " entered Bus 4.");
            }
            
            int b = new Random().nextInt(2);
            Thread.sleep(b*1000);
            
            //Drop off passengers to release plane seats
            if (passenger4 > 0) {
                System.out.println ("Bus Driver 4  : Bus 4 is full with " + passenger4 + " passengers, departing airport.");
                pD.release(passenger4);
            }
            
        } catch (InterruptedException ex) {}
        
        //Total up served passengers
        TP4 += passenger4;
        return TP4;
    }
    
    //Function to take up new passengers for the second time
    public int enterPlane4 (Plane P, Semaphore pD) {
        try {
            int a = new Random().nextInt(2);
            Thread.sleep(a*1000);
            
            //Generate passengers to take up plane seats
            newP4 = Passenger.Passenger(min, max);
            if (newP4 > 0 && newP4 < 51) {
                pD.acquire(newP4);
                System.out.println(P.getName() + "     : " + newP4 + " passengers have embarked onto " + P.getName());
                System.out.println (P.getName() + "     : Preparing to take off with " + newP4 + " passengers... ");
                pD.release(newP4);
            }
            
        } catch (InterruptedException ex) {}
        
        //Total up served passengers
        TP4 += newP4;
        return TP4;
    }
    
    //Return total served passengers of Gate 4
    public int getPassenger4 () {
        return this.TP4;
    }
}
