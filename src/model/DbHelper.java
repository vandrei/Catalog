/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.*;

/**
 *
 * @author andrei
 */
public class DbHelper {
    
    private String dbName = "CatalogDB.db";
    public Connection sqliteConnection;
 
    //SQLiteDatabase dataBase;
    
    public DbHelper()
    {
        Connection c = null;
        try 
        {
            Class.forName("org.sqlite.JDBC");
            sqliteConnection = DriverManager.getConnection("jdbc:sqlite:data/CatalogDB"/* + dbName*/);
            sqliteConnection.setAutoCommit(false);
        } 
        catch ( Exception e ) 
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
    
    public boolean AuthenticateUser(String username, String password)
    {
        Statement stmt = null;
        try
        {
            stmt = sqliteConnection.createStatement();
            ResultSet rs = stmt.executeQuery("Select * FROM Utilizatori WHERE username='" + username + "' AND password='" + password+"'");
            if (rs.isBeforeFirst())
            {
                rs.next();
                String name = rs.getString("nume");
                System.out.println(name);
            }
            else
            {
                int c = 4;
                System.out.println("nouser");
            }
            
        }
        catch (Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return false;
        }
}
