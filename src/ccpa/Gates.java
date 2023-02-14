package ccpa;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Gates {
    int b;
    int c;
    
    Gate1 G1 = new Gate1();
    Gate2 G2 = new Gate2();
    Gate3 G3 = new Gate3();
    Gate4 G4 = new Gate4();

    public Gate1 getG1() {
        return G1;
    }

    public Gate2 getG2() {
        return G2;
    }

    public Gate3 getG3() {
        return G3;
    }

    public Gate4 getG4() {
        return G4;
    }
   
    Semaphore Tg = new Semaphore(4); //semaphore for all gates
    Semaphore gA = new Semaphore(1); //semaphore for Gate 1
    Semaphore gB = new Semaphore(1); //semaphore for Gate 2
    Semaphore gC = new Semaphore(1); //semaphore for Gate 3
    Semaphore gD = new Semaphore(1); //semaphore for Gate 4
    
    Semaphore pA = new Semaphore(50); //semaphore for plane seats
    Semaphore pB = new Semaphore(50);
    Semaphore pC = new Semaphore(50);
    Semaphore pD = new Semaphore(50);
    
    //Arriving airport
    synchronized public void enterRunway (Plane P, Semaphore permit) {
        try {
            permit.acquire(1);
            System.out.println("ATC           : " + P.getName() + " successfully landed on Runway. ");
            System.out.println(P.getName() + "     : " + P.getName() + " exiting Runway, preparing to dock at Gate. ");
            gateATC(P, permit);
        } catch (Exception e) {}
    }
    
    //Leaving airport
    synchronized public void getRunway (Semaphore permit) {
        try {
            permit.acquire(1);
            System.out.println("ATC           : 1 Plane coast back onto Runway successfully. ");
            System.out.println("ATC           : 1 Plane exiting Runway, leaving airport. ");
        } catch (Exception e) {}
    }
    
    //Assign gates
    synchronized public void gateATC (Plane P, Semaphore permit) {
        System.out.println("Gate ATC      : Checking availability of Gates. ");
        boolean a = true;
        
        //If there is available Gate(s), assign Plane to Gate
        if (Tg.availablePermits()!=0) {
            
            while (a) {
                b = new Random().nextInt(4); 
                P.C.gateCheck = b;
                
                //If assigned Gate is available
                if (gA.availablePermits()==1 && P.C.gateCheck==0) { 
                    System.out.println("Gate ATC      : Available Gates count is " + Tg.availablePermits());
                    System.out.println("Gate ATC      : " + P.getName() + " Please dock at Gate 1.");
                    try {
                        Tg.acquire();
                        G1.enter1(P, gA, pA, Tg);
                        permit.release();
                    } catch (Exception e) {}
                    a = false;

                } else if (gB.availablePermits()==1 && P.C.gateCheck==1) {
                    System.out.println("Gate ATC      : Available Gates count is " + Tg.availablePermits());
                    System.out.println("Gate ATC      : " + P.getName() + " Please dock at Gate 2.");
                    try {
                        Tg.acquire();
                        G2.enter2(P, gB, pB, Tg);
                        permit.release();
                    } catch (Exception e) {}
                    a = false;

                } else if (gC.availablePermits()==1 && P.C.gateCheck==2) {
                    System.out.println("Gate ATC      : Available Gates count is " + Tg.availablePermits());
                    System.out.println("Gate ATC      : " + P.getName() + " Please dock at Gate 3.");
                    try {
                        Tg.acquire();
                        G3.enter3(P, gC, pC, Tg);
                        permit.release();
                    } catch (Exception e) {}
                    a = false;

                } else if (gD.availablePermits()==1 && P.C.gateCheck==3) {
                    System.out.println("Gate ATC      : Available Gates count is " + Tg.availablePermits());
                    System.out.println("Gate ATC      : " + P.getName() + " Please dock at Gate 4.");
                    try {
                        Tg.acquire();
                        G4.enter4(P, gD, pD, Tg);
                        permit.release();
                    } catch (Exception e) {}
                    a = false;
                
                    
                    
                //If assigned Gate is not available yet total available Gate count is not 0
                //Release previous Plane at assigned gate for new Plane
                } else if (gA.availablePermits()==0 && P.C.gateCheck==0) {
                    System.out.println("Gate ATC      : Gate 1 is occupied at the moment, please wait... ");
                    getRunway(permit);
                    gA.release(1);
                    Tg.release(1);
                    if (gA.availablePermits()==1 && P.C.gateCheck==0) {
                        System.out.println ("Gate ATC      : 1 Plane has left Gate 1, Gate 1 is opened. ");
                        System.out.println("Gate ATC      : Available Gates count is " + Tg.availablePermits());
                        System.out.println("Gate ATC      : " + P.getName() + " Please dock at Gate 1.");
                        try {
                            Tg.acquire();
                            G1.enter1(P, gA, pA, Tg);
                            permit.release();
                        } catch (Exception e) {}
                    }
                    a = false;
                    
                } else if (gB.availablePermits()==0 && P.C.gateCheck==1) {
                    System.out.println("Gate ATC      : Gate 2 is occupied at the moment, please wait... ");
                    getRunway(permit);
                    gB.release(1);
                    Tg.release(1);
                    if (gB.availablePermits()==1 && P.C.gateCheck==1) {
                        System.out.println ("Gate ATC      : 1 Plane has left Gate 2, Gate 2 is opened. ");
                        System.out.println("Gate ATC      : Available Gates count is " + Tg.availablePermits());
                        System.out.println("Gate ATC      : " + P.getName() + " Please dock at Gate 2.");
                        try {
                            Tg.acquire();
                            G2.enter2(P, gB, pB, Tg);
                            permit.release();
                        } catch (Exception e) {}
                    }
                    a = false;
                    
                } else if (gC.availablePermits()==0 && P.C.gateCheck==2) {
                    System.out.println("Gate ATC      : Gate 3 is occupied at the moment, please wait... ");
                    getRunway(permit);
                    gC.release(1);
                    Tg.release(1);
                    if (gC.availablePermits()==1 && P.C.gateCheck==2) {
                        System.out.println ("Gate ATC      : 1 Plane has left Gate 3, Gate 3 is opened. ");
                        System.out.println("Gate ATC      : Available Gates count is " + Tg.availablePermits());
                        System.out.println("Gate ATC      : " + P.getName() + " Please dock at Gate 3.");
                        try {
                            Tg.acquire();
                            G3.enter3(P, gC, pC, Tg);
                            permit.release();
                        } catch (Exception e) {}
                    }
                    a = false;
                    
                } else if (gD.availablePermits()==0 && P.C.gateCheck==3) {
                    System.out.println("Gate ATC      : Gate 4 is occupied at the moment, please wait... ");
                    getRunway(permit);
                    gD.release(1);
                    Tg.release(1);
                    if (gD.availablePermits()==1 && P.C.gateCheck==3) {
                        System.out.println ("Gate ATC      : 1 Plane has left Gate 4, Gate 4 is opened. ");
                        System.out.println("Gate ATC      : Available Gates count is " + Tg.availablePermits());
                        System.out.println("Gate ATC      : " + P.getName() + " Please dock at Gate 4.");
                        try {
                            Tg.acquire();
                            G4.enter4(P, gD, pD, Tg);
                            permit.release();
                        } catch (Exception e) {}
                    }
                    a = false;
                    
                } else {
                    System.out.println("ERROR.");
                    a = false;
                }
            }
        
            
            
        //If all Gates are not available
        //Release all Planes at Gates + free up all Gates
        } else if (Tg.availablePermits()==0) {
            System.out.println ("Gate ATC      : All 4 Gates are occupied, please wait... ");
            getRunway(permit);
            gA.release(1);
            if (gA.availablePermits()==1) {
                System.out.println ("Gate ATC      : 1 Plane has left Gate 1, Gate 1 is opened. ");
            }
            
            getRunway(permit);
            gB.release(1);
            if (gB.availablePermits()==1) {
                System.out.println ("Gate ATC      : 1 Plane has left Gate 2, Gate 2 is opened. ");
            }
            
            getRunway(permit);
            gC.release(1);
            if (gC.availablePermits()==1) {
                System.out.println ("Gate ATC      : 1 Plane has left Gate 3, Gate 3 is opened. ");
            }
            
            getRunway(permit);
            gD.release(1);
            if (gD.availablePermits()==1) {
                System.out.println ("Gate ATC      : 1 Plane has left Gate 4, Gate 4 is opened. ");
            }
            
            Tg.release(4);
            System.out.println("Gate ATC      : All 4 Gates are opened. ");
            
            while (a) {
                c = new Random().nextInt(4); 
                P.C.gateCheck = c;
                
                //If assigned Gate is available
                if (gA.availablePermits()==1 && P.C.gateCheck==0) { 
                    System.out.println("Gate ATC      : Available Gates count is " + Tg.availablePermits());
                    System.out.println("Gate ATC      : " + P.getName() + " Please dock at Gate 1.");
                    try {
                        Tg.acquire();
                        G1.enter1(P, gA, pA, Tg);
                        permit.release();
                    } catch (Exception e) {}
                    a = false;

                } else if (gB.availablePermits()==1 && P.C.gateCheck==1) {
                    System.out.println("Gate ATC      : Available Gates count is " + Tg.availablePermits());
                    System.out.println("Gate ATC      : " + P.getName() + " Please dock at Gate 2.");
                    try {
                        Tg.acquire();
                        G2.enter2(P, gB, pB, Tg);
                        //permit.release();
                    } catch (Exception e) {}
                    a = false;

                } else if (gC.availablePermits()==1 && P.C.gateCheck==2) {
                    System.out.println("Gate ATC      : Available Gates count is " + Tg.availablePermits());
                    System.out.println("Gate ATC      : " + P.getName() + " Please dock at Gate 3.");
                    try {
                        Tg.acquire();
                        G3.enter3(P, gC, pC, Tg);
                        permit.release();
                    } catch (Exception e) {}
                    a = false;

                } else if (gD.availablePermits()==1 && P.C.gateCheck==3) {
                    System.out.println("Gate ATC      : Available Gates count is " + Tg.availablePermits());
                    System.out.println("Gate ATC      : " + P.getName() + " Please dock at Gate 4.");
                    try {
                        Tg.acquire();
                        G4.enter4(P, gD, pD, Tg);
                        permit.release();
                    } catch (Exception e) {}
                    a = false;
                
                    
                    
                //If assigned Gate is not available yet total available Gate count is not 0
                //Release previous Plane at assigned gate for new Plane
                } else if (gA.availablePermits()==0 && P.C.gateCheck==0) {
                    System.out.println("Gate ATC      : Gate 1 is occupied at the moment, please wait... ");
                    gA.release(1);
                    Tg.release(1);
                    if (gA.availablePermits()==1 && P.C.gateCheck==0) {
                        System.out.println ("Gate ATC      : 1 Plane has left Gate 1, Gate 1 is opened. ");
                        System.out.println("Gate ATC      : Available Gates count is " + Tg.availablePermits());
                        System.out.println("Gate ATC      : " + P.getName() + " Please dock at Gate 1.");
                        try {
                            Tg.acquire();
                            G1.enter1(P, gA, pA, Tg);
                            permit.release();
                        } catch (Exception e) {}
                    }
                    a = false;
                    
                } else if (gB.availablePermits()==0 && P.C.gateCheck==1) {
                    System.out.println("Gate ATC      : Gate 2 is occupied at the moment, please wait... ");
                    getRunway(permit);
                    gB.release(1);
                    Tg.release(1);
                    if (gB.availablePermits()==1 && P.C.gateCheck==1) {
                        System.out.println ("Gate ATC      : 1 Plane has left Gate 2, Gate 2 is opened. ");
                        System.out.println("Gate ATC      : Available Gates count is " + Tg.availablePermits());
                        System.out.println("Gate ATC      : " + P.getName() + " Please dock at Gate 2.");
                        try {
                            Tg.acquire();
                            G2.enter2(P, gB, pB, Tg);
                            permit.release();
                        } catch (Exception e) {}
                    }
                    a = false;
                    
                } else if (gC.availablePermits()==0 && P.C.gateCheck==2) {
                    System.out.println("Gate ATC      : Gate 3 is occupied at the moment, please wait... ");
                    getRunway(permit);
                    gC.release(1);
                    Tg.release(1);
                    if (gC.availablePermits()==1 && P.C.gateCheck==2) {
                        System.out.println ("Gate ATC      : 1 Plane has left Gate 3, Gate 3 is opened. ");
                        System.out.println("Gate ATC      : Available Gates count is " + Tg.availablePermits());
                        System.out.println("Gate ATC      : " + P.getName() + " Please dock at Gate 3.");
                        try {
                            Tg.acquire();
                            G3.enter3(P, gC, pC, Tg);
                            permit.release();
                        } catch (Exception e) {}
                    }
                    a = false;
                    
                } else if (gD.availablePermits()==0 && P.C.gateCheck==3) {
                    System.out.println("Gate ATC      : Gate 4 is occupied at the moment, please wait... ");
                    getRunway(permit);
                    gD.release(1);
                    Tg.release(1);
                    if (gD.availablePermits()==1 && P.C.gateCheck==3) {
                        System.out.println ("Gate ATC      : 1 Plane has left Gate 4, Gate 4 is opened. ");
                        System.out.println("Gate ATC      : Available Gates count is " + Tg.availablePermits());
                        System.out.println("Gate ATC      : " + P.getName() + " Please dock at Gate 4.");
                        try {
                            Tg.acquire();
                            G4.enter4(P, gD, pD, Tg);
                            permit.release();
                        } catch (Exception e) {}
                    }
                    a = false;
                    
                } else {
                    System.out.println("ERROR.");
                    a = false;
                }
            }
        }
    }
}