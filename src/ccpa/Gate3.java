package ccpa;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Gate3 {
    
    int min = 1;
    int max = 51;
    int passenger3;
    int newP3;
    int TP3;
    
    public void enter3 (Plane P, Semaphore gC, Semaphore pC, Semaphore Tg) {
        
        try {
            //Acquire permission to dock at Gate 3
            gC.acquire(1);
            System.out.println(P.getName() + "     : " + P.getName() + " passengers disembarked, waiting for Bus 3.");
            
            //Simulate delay in waiting for bus
            int a = new Random().nextInt(2);
            Thread.sleep(a*1000);

            //Drop off passengers for the first time
                enterBus3(P, pC);
            //Simulate delay in refilling plane supplies
                P.refillSupplies(P);
            //Take up new passengers for the second time
                enterPlane3(P, pC);
            
        } catch (InterruptedException ex) {}
            
    }
    
    //Function to drop off passengers for the first time
    public int enterBus3 (Plane P, Semaphore pC) {
        try {
            int a = new Random().nextInt(2);
            Thread.sleep(a*1000);
            
            //Generate passengers to take up plane seats
            passenger3 = Passenger.Passenger(min, max);
            if (passenger3 > 0 && passenger3 < 51) {
                pC.acquire(passenger3);
                System.out.println(P.getName() + "     : " + passenger3 + " passengers from " + P.getName() + " entered Bus 3.");
            }
            
            int b = new Random().nextInt(2);
            Thread.sleep(1000 + (b*1000));
            
            //Drop off passengers to release plane seats
            if (passenger3 > 0) {
                System.out.println ("Bus Driver 3  : Bus 3 is full with " + passenger3 + " passengers, departing airport.");
                pC.release(passenger3);
            }
            
        } catch (InterruptedException ex) {}
        
        //Total up served passengers
        TP3 += passenger3;
        return TP3;
    }
    
    //Function to take up new passengers for the second time
    public int enterPlane3 (Plane P, Semaphore pC) {
        try {
            int a = new Random().nextInt(3);
            Thread.sleep(a*1000);
            
            //Generate passengers to take up plane seats
            newP3 = Passenger.Passenger(min, max);
            if (newP3 > 0 && newP3 < 51) {
                pC.acquire(newP3);
                System.out.println(P.getName() + "     : " + newP3 + " passengers have embarked onto " + P.getName());
                System.out.println (P.getName() + "     : Preparing to take off with " + newP3 + " passengers... ");
                pC.release(newP3);
            }
            
        } catch (InterruptedException ex) {}
        
        //Total up served passengers
        TP3 += newP3;
        return TP3;
    }
    
    //Function to drop off passengers for the second time
    public int undock3 (Plane P, Semaphore pC) {
        try {
            int a = new Random().nextInt(3);
            Thread.sleep(a*1000);
            
            //Drop off passengers to release plane seats
            if (newP3 > 0) {   
                System.out.println (P.getName() + "     : " + newP3 + " passengers from " + P.getName() + " entered Bus 3. ");
                System.out.println ("Bus Driver 3  : Bus 3 is full with " + newP3 + " passengers, departing airport.");
                pC.release(newP3);                  
            } 
            
        } catch (InterruptedException ex) {}
        
        //Total up served passengers
        TP3 += newP3;
        return TP3;
    }
    
    //Return total served passengers of Gate 3
    public int getPassenger3 () {
        return this.TP3;
    }
}
