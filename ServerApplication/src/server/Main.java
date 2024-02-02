/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author husse
 */
public class Main {
    public static MultiClientServer MCS;
    public static NotificationServer NS;
    
    public static void main (String[] args){
    
       MCS = new MultiClientServer();
       NS = new NotificationServer();
       new Thread(() -> MCS.startServer()).start();
       new Thread(() -> NS.startServer()).start();
       
       
    }
}
