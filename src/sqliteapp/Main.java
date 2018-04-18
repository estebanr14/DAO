/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqliteapp;

import sqliteapp.Persona.Persona;
import java.util.List;
import sqliteapp.DAO.PersonaDAO_SQLite;

/**
 *
 * @author EstebanRestrepo
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {     
       
 
  PersonaDAO_SQLite conectorBD = new PersonaDAO_SQLite();
  conectorBD.conectarBD("exampleDB");
  
  Persona p = new Persona("0005","gjgjgj");
  
  //List<Persona> personas = conectorBD.readPersonas();
 //conectorBD.createPersona(p);
 //System.out.println( conectorBD.readPersona("000585").getNombre());
  //conectorBD.deletePersona(p);
 
 
  
  conectorBD.cerrarConexion();
  
            

    }
    
}
