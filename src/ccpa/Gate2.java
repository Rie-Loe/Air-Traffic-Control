package ccpa;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Gate2 {
    
    int min = 1;
    int max = 51;
    int passenger2;
    int newP2;
    int TP2;
    
    public void enter2 (Plane P, Semaphore gB, Semaphore pB, Semaphore Tg) {
        
        try {
            //Acquire permission to dock at Gate 2
            gB.acquire(1);
            System.out.println(P.getName() + "     : " + P.getName() + " passengers disembarked, waiting for Bus 2.");
            
            //Simulate delay in waiting for bus
            int a = new Random().nextInt(2);
            Thread.sleep(a*1000);
            
            
            //Drop off passengers for the first time
                enterBus2(P, pB);
            //Simulate delay in refilling plane supplies
                P.refillSupplies(P);
            //Take up new passengers for the second time
                enterPlane2(P, pB);
                
        } catch (InterruptedException ex) {}
        
    }
    
    //Function to drop off passengers for the first time
    public int enterBus2 (Plane P, Semaphore pB) {
        try {
            int a = new Random().nextInt(2);
            Thread.sleep(a*1000);
            
            //Generate passengers to take up plane seats
            passenger2 = Passenger.Passenger(min, max);
            if (passenger2 > 0 && passenger2 < 51) {
                pB.acquire(passenger2);
                System.out.println(P.getName() + "     : " + passenger2 + " passengers from " + P.getName() + " entered Bus 2.");
            }
            
            int b = new Random().nextInt(2);
            Thread.sleep(b*1000);
            
            //Drop off passengers to release plane seats
            if (passenger2 > 0) {
                System.out.println ("Bus Driver 2  : Bus 2 is full with " + passenger2 + " passengers, departing airport.");
                pB.release(passenger2);
            }
            
        } catch (InterruptedException ex) {}
        
        //Total up served passengers
        TP2 += passenger2;
        return TP2;
    }
    
    //Function to take up new passengers for the second time
    public int enterPlane2 (Plane P, Semaphore pB) {
        try {
            int a = new Random().nextInt(2);
            Thread.sleep(a*1000);
            
            //Generate passengers to take up plane seats
            newP2 = Passenger.Passenger(min, max);
            if (newP2 > 0 && newP2 < 51) {
                pB.acquire(newP2);
                System.out.println(P.getName() + "     : " + newP2 + " passengers have embarked onto " + P.getName());
                System.out.println (P.getName() + "     : Preparing to take off with " + newP2 + " passengers... ");
                pB.release(newP2);
            }
            
        } catch (InterruptedException ex) {}
        
        //Total up served passengers
        TP2 += newP2;
        return TP2;
    }
    
    //Return total served passengers of Gate 2
    public int getPassenger2 () {
        return this.TP2;
    }
    
}
