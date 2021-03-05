/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author joop
 */
public class conectDB {
    
    public Connection conDB()
    {
        Connection connect=null;
         try{
              
              Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");//Loading Driver
              connect= DriverManager.getConnection("jdbc:ucanaccess://C://GT/GestionStock.accdb");//Establishing Connection
              System.out.println("Connected Successfully");
 
          }catch(Exception e){
              System.out.println("Error in connection");
 
          }
         return connect;
    }
}
