package ccpa;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Gate1 {
    
    int min = 1;
    int max = 51;
    int passenger1;
    int newP1;
    int TP1;
    
    public void enter1 (Plane P, Semaphore gA, Semaphore pA, Semaphore Tg) {
    
        try {
            //Acquire permission to dock at Gate 1
            gA.acquire(1);
            System.out.println(P.getName() + "     : " + P.getName() + " passengers disembarked, waiting for Bus 1.");
            
            //Simulate delay in waiting for bus
            int a = new Random().nextInt(3);
            Thread.sleep(a*1000);
            
            //Drop off passengers for the first time
                enterBus1(P, pA);
            //Simulate delay in refilling plane supplies
                P.refillSupplies(P);
            //Take up new passengers for the second time
                enterPlane1(P, pA);
            
        } catch
            (InterruptedException ex) {}
        
    }
    
    //Function to drop off passengers for the first time
    public int enterBus1 (Plane P, Semaphore pA) {
        try {
            int a = new Random().nextInt(2);
            Thread.sleep(a*1000);
            
            //Generate passengers to take up plane seats
            passenger1 = Passenger.Passenger(min, max);
            if (passenger1 > 0 && passenger1 < 51) {
                pA.acquire(passenger1);
                System.out.println(P.getName() + "     : " + passenger1 + " passengers from " + P.getName() + " entered Bus 1.");
            }
            
            int b = new Random().nextInt(2);
            Thread.sleep(b*1000);
            
            //Drop off passengers to release plane seats
            if (passenger1 > 0) {   
                System.out.println ("Bus Driver 1  : Bus 1 is full with " + passenger1 + " passengers, departing airport.");
                pA.release(passenger1);
            } 
            
        } catch (InterruptedException ex) {}
        
        //Total up served passengers
        TP1 += passenger1;
        return TP1;
    }
    
    //Function to take up new passengers for the second time
    public int enterPlane1 (Plane P, Semaphore pA) {
        try {
            int a = new Random().nextInt(2);
            Thread.sleep(a*1000);
            
            //Generate passengers to take up plane seats
            newP1 = Passenger.Passenger(min, max);
            if (newP1 > 0 && newP1 < 51) {
                pA.acquire(newP1);
                System.out.println(P.getName() + "     : " + newP1 + " passengers have embarked onto " + P.getName());
                System.out.println (P.getName() + "     : Preparing to take off with " + newP1 + " passengers... ");
                pA.release(newP1);
            }
            
        } catch (InterruptedException ex) {}
        
        //Total up served passengers
        TP1 += newP1;
        return TP1;
    }
    
    //Return total served passengers of Gate 1
    public int getPassenger1 () {
        return this.TP1;
    }
       
}
