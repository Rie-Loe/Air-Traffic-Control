package ccpa;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Plane extends Thread {
    Semaphore permit;
    Semaphore refill;
    String PName;
    Gates G;
    GateCheck C;
    
    Gate1 G1;
    Gate2 G2;
    
    int pFS;
    int pN;
    int tP;
    
    int fuelShortages = 1;
    
    
    public Plane (String PName, Semaphore permit, Gates G, GateCheck C, Gate1 G1, Gate2 G2, Semaphore refill) {
        this.setName(PName);
        this.permit = permit;
        this.G = G;
        this.C = C;
        this.G1 = G1;
        this.G2 = G2;
        this.refill = refill;
    }
    
    public void run() {
        
        try {
            //Landing airport
            priority(this);
            G.enterRunway (this, permit);
            if (permit.availablePermits()==0) {
                System.out.println("ATC           : Runway is occupied, please wait in queue.");
                Thread.sleep(5000);
            }
            permit.release();
            
        } catch(Exception e) {}
    }
    
    //Function to allocate different scenarios + priorities to Planes
    synchronized public int priority (Plane P) {
        
        try {
            int A = new Random().nextInt(4);
            Thread.sleep(A*1000);
            
            int D = new Random().nextInt(2);
            if (D==0) {
                System.out.println(P.getName() + "     : Experienced fuel shortages. ");
                if (fuelShortages==1) {
                    System.out.println("ATC           : " + P.getName() + " experienced fuel shortages, landing is prioritized. ");
                    Thread.sleep(1000);
                    pFS +=1;
                    P.setPriority(Thread.MAX_PRIORITY);
                }
                
            } else {
                System.out.println(P.getName() + "     : Requesting to land on runway. ");
                pN +=1;
                Thread.sleep(3000);
                P.setPriority(Thread.MIN_PRIORITY);
                }
            
            } catch (Exception e) {}
        
            tP = pFS + pN;
            return tP;
        }
    
    //Function to simulate delay in plane refilling supplies
    public void refillSupplies(Plane P) {
            if (refill.availablePermits()==1) {
                System.out.println("Refill ATC    : Available Refuelling Truck count is " + refill.availablePermits());
                System.out.println("Refill ATC    : Refuelling Truck please tend to " + P.getName());
                
                try {
                    refill.acquire(1);
                } catch (InterruptedException ex) {}
                
                System.out.println(P.getName() + "     : Refilling supplies and fuel... ");
                int a = new Random().nextInt(3);
                try {
                    Thread.sleep(a*1000);
                } catch (InterruptedException ex) {}
                
            } else {
                    System.out.println("Refill ATC    : Available Refuelling Truck count is " + refill.availablePermits());
                    System.out.println("Refill ATC    : " + P.getName() + " please wait for Refuelling Truck.");
                    refill.release();
                    
                    if (refill.availablePermits()==1) {
                            System.out.println("Refill ATC    : Available Refuelling Truck count is " + refill.availablePermits());
                            System.out.println("Refill ATC    : Refuelling Truck please tend to " + P.getName());
                            
                            try {
                                refill.acquire(1);
                            } catch (InterruptedException ex) {}
                            
                            System.out.println(P.getName() + "     : Refilling supplies and fuel... ");
                            int a = new Random().nextInt(3);
                            
                            try {
                                Thread.sleep(a*1000);
                            } catch (InterruptedException ex) {}
                    }
            }
    }
    
    //Return total served planes
    public int totalPlane() {
        return this.tP;
    }
    
}
