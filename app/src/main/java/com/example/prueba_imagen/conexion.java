package com.example.prueba_imagen;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;

public class conexion {

    public Connection conexionBD(){
        Connection conexion= null;
        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.0.4;databaseName=prueba_imagen;user=sa;password=rubikok;");
        }catch (Exception e){
        }
        return conexion;
    }

}
