package ccpa;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Plane extends Thread {
    Semaphore permit;
    String PName;
    Gates G;
    GateCheck C;
    
    Gate1 G1;
    Gate2 G2;
    Gate3 G3;
    Gate4 G4;
    
    int pFD;
    int pMC;
    int pFS;
    int pM;
    int pN;
    
    int tP;
    
    int flightDelay = 1;
    int medicalCrisis = 1;
    int fuelShortages = 1;
    int malfunctions = 1;
    
    
    public Plane (String PName, Semaphore permit, Gates G, GateCheck C, Gate1 G1, Gate2 G2, Gate3 G3, Gate4 G4) {
        this.setName(PName);
        this.permit = permit;
        this.G = G;
        this.C = C;
        this.G1 = G1;
        this.G2 = G2;
        this.G3 = G3;
        this.G4 = G4;
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
            
            int D = new Random().nextInt(5);
            if (D==0) {
                System.out.println(P.getName() + "     : Experienced turbulence. ");
                if (flightDelay==1) {
                    System.out.println("Airport Staff : " + P.getName() + " experienced turbulence, flight is delayed. ");
                    Thread.sleep(3000);
                    pFD +=1;
                    P.setPriority(Thread.MIN_PRIORITY);
                }
                
            } else if (D==1) {
                System.out.println(P.getName() + "     : Passenger medical crisis. ");
                if (medicalCrisis==1) {
                    System.out.println("ATC           : " + P.getName() + " experienced passenger medical crisis, landing is prioritized. ");
                    pMC +=1;
                    P.setPriority(Thread.MAX_PRIORITY);
                }

            } else if (D==2) {
                System.out.println(P.getName() + "     : Experienced fuel shortages. ");
                if (fuelShortages==1) {
                    System.out.println("ATC           : " + P.getName() + " experienced fuel shortages, landing is prioritized. ");
                    pFS +=1;
                    P.setPriority(Thread.MAX_PRIORITY);
                }

            } else if (D==3) {
                System.out.println(P.getName() + "     : Experienced mechanical malfunctions. ");
                if (malfunctions==1) {
                    System.out.println("ATC           : " + P.getName() + " experienced mechanical malfunctions, landing is prioritized. ");
                    pM +=1;
                    P.setPriority(Thread.MAX_PRIORITY);
                }

            } else {
                System.out.println(P.getName() + "     : Requesting to land on runway. ");
                pN +=1;
                Thread.sleep(1000);
                P.setPriority(Thread.NORM_PRIORITY);
                }
            
            } catch (Exception e) {}
        
            tP = pFD + pMC + pFS + pM + pN;
            return tP;
        }
    
    //Function to simulate delay in plane refilling supplies
    public void refillSupplies(Plane P) {
        try {
            System.out.println(P.getName() + "     : Refilling supplies and fuel... ");
            int a = new Random().nextInt(3);
            Thread.sleep(a*1000);
        } catch (Exception e) {};
    }
    
    //Return total served planes
    public int totalPlane() {
        return this.tP;
    }
    
}
