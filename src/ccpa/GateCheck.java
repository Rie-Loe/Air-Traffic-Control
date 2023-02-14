package ccpa;

import java.util.concurrent.Semaphore;

public class GateCheck {
    int gateCheck;
    
    Gates G;
    
    Semaphore permit;
    Semaphore Tg;
    Semaphore gA;
    Semaphore gB;
    
    public void setGates (Gates G) {
        this.G = G;
    }
    
    public void setPermit (Semaphore permit) {
        this.permit = permit;
    }
    
    public GateCheck (int gateCheck) {
        this.gateCheck = gateCheck;
    }
    
    //Function to check for remaining Planes + release remaining Planes
    synchronized public void GateRemain (Semaphore Tg, Semaphore gA, Semaphore gB) {
        System.out.println("AIRPORT IS CLOSING SOON!");
        System.out.println("ATC           : Gate ATC Please report Gate statistics. ");
        
        if (Tg.availablePermits()!=0 || Tg.availablePermits() == 0) {
            
            if (gA.availablePermits() == 0) {
                System.out.println("Gate ATC      : 1 plane found in Gate 1.");
                G.getRunway(permit);
                gA.release(1);
                permit.release(1);
                System.out.println("Gate ATC      : 0 plane found in Gate 1. Gate 1 closing now... ");
            }
            else {
                System.out.println("Gate ATC      : 0 plane found in Gate 1. Gate 1 closing now... ");
            }
            
            
            if (gB.availablePermits() == 0) {
                System.out.println("Gate ATC      : 1 plane found in Gate 2.");
                G.getRunway(permit);
                gB.release(1);
                permit.release(1);
                System.out.println("Gate ATC      : 0 plane found in Gate 2. Gate 2 closing now... ");
            }
            else {
                System.out.println("Gate ATC      : 0 plane found in Gate 2. Gate 2 closing now... ");
            }
            
            
            Tg.release(2);
            if (Tg.availablePermits() == 2) {
                System.out.println("Gate ATC      : Total 0 plane found in all Gates.");
            }
        }
    }
}
