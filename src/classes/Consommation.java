/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import DAO.conectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.time.ZonedDateTime.now;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author joop
 */
public class Consommation {
    
    conectDB db=new conectDB();
    Connection con=db.conDB();
    ResultSet rs=null;
    PreparedStatement st;
   // public DefaultTableModel model;
    HashMap<Integer, String> map=new HashMap<>();
    public void addConsToTable(JTable tab,String p,int qte,String date)
    {
      
        String tbData[]={p,""+qte,""+date};
       DefaultTableModel model=(DefaultTableModel)tab.getModel();
        model.addRow(tbData);
       
    }
    
    public String getProduit(int idProd)
    {
        String ch="";
        try {
            String sql="Select nomProduit from Produit where idProduit="+idProd;
         st=con.prepareStatement(sql);
           rs=st.executeQuery();
            while(rs.next())
            {
              ch= rs.getString("nomProduit");
                   
            }
           
        } catch (Exception e) {
            System.out.println("Erreur cat get nomProduit");
        }
        return ch;
    }
    
     public void remplirComboxProduit(JComboBox comb)
    {
        try {
            String sql="Select * from Produit";
         st=con.prepareStatement(sql);
            rs=st.executeQuery();
            while(rs.next())
            {
                comb.addItem(rs.getString("nomProduit"));  
               map.put(rs.getInt("idProduit"), rs.getString("nomProduit"));
            }
        } catch (Exception e) {
            System.out.println("Erreur catch remplircomboProduit");
        }
    }
     
     public int getKey(String Produit)
     {
        // System.out.println("Boucle for:");
         int b=0;
        for (Map.Entry e : map.entrySet()) {
            if(e.getValue().equals(Produit))
            {
                b=Integer.parseInt(e.getKey().toString());
            }
         
        }

         return b;
     }
     
     public void insertTabToDB(JTable tab)//HashMap<Integer,String>map
     {
         DefaultTableModel model=(DefaultTableModel)tab.getModel();
         int key=0;
         String p="";
         int qte=-1;
        for (int i = 0; i < model.getRowCount(); i++) {
        try {
            p= model.getValueAt(i, 0).toString();
            qte= Integer.parseInt(model.getValueAt(i, 1).toString());
            key=getKey(p);
            if((key!=0 )&& (!p.equals("")) && (qte!=-1))
            {
                saveConsommation(key,qte);
            }
        } 
        catch(Exception ex)
        {
            System.out.println("catch exception");
        }
          }
     }
     
     public void saveConsommation(int idp,int qte)
     {
        try {
            String q="insert into Consommation(idProduit,quantite,dates)vaalues("+idp+","+qte+ ",'"+now()+"')";
            st=con.prepareStatement(q);
            int v=st.executeUpdate();
            if(v>0)
            {
                System.out.println("insertion reussi");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Consommation.class.getName()).log(Level.SEVERE, null, ex);
        }
         
     }
}
