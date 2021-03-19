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
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author joop
 */
public class GestionProduit {
    
    conectDB db=new conectDB();
    Connection con=db.conDB();
    ResultSet rs=null;
    PreparedStatement st;
    
    public int addProduit(int cat,String n,int qte,String unite)
    {
        int a=0;
        try {
            String sql="insert into Produit(idcategorie,nomProduit,quantite,uniteMesure)values(?,?,?,?)";//("+cat+",'"+n+ "',"+qte+",'"+unite+"')";
            st=con.prepareStatement(sql);
            st.setInt(1,cat);
            st.setString(2,n);
            st.setInt(3,qte);
            st.setString(4,unite);
            int v=st.executeUpdate();
            if(v>0)
            {
                 a=1;
            }
               
        } catch (SQLException ex) {
            System.out.println("Erreur ==> "+ex.getMessage());
        }
        return a;
    }
    
    public void remplirCombox(JComboBox comb)
    {
        try {
            String sql="Select * from categorie";
         st=con.prepareStatement(sql);
            rs=st.executeQuery();
            while(rs.next())
            {
                    comb.addItem(rs.getString("Libele"));
                    comb.setSelectedIndex(rs.getInt("idCategorie"));
            }
        } catch (Exception e) {
        }
    }
    public int addCategorie(String l)
    {
         int a=0;
         
        try {
            boolean isExist=verifiCategorie(l);
          if(isExist==false)
          {
            String sql="insert into Categorie(Libele)values(?)";//("+cat+",'"+n+ "',"+qte+",'"+unite+"')";
            st=con.prepareStatement(sql);
            st.setString(1,l);
            int v=st.executeUpdate();
            if(v>0)
            {
                 a=1;
            }
          }
          else
          {
              a=-1;
          }
               
        } catch (SQLException ex) {
            System.out.println("Erreur ==> "+ex.getMessage());
        }
        return a;
    }
    
    public boolean verifiCategorie(String name)
    {
        boolean value=false;
          try {
            String sql="Select * from categorie";
         st=con.prepareStatement(sql);
            rs=st.executeQuery();
            while(rs.next())
            {
               if(rs.getString("Libele").toLowerCase().equals(name.toLowerCase()))
                   value=true;
            }
        } catch (Exception e) {
        }
        return value;
    }
    
    public void remplirTab(JTable tab,String p,int pu,int qte,int pt,String j)
    {
        String tbData[]={p,""+pu,""+qte,""+pt,j};
        DefaultTableModel model=(DefaultTableModel)tab.getModel();
        model.addRow(tbData);

    }
}
