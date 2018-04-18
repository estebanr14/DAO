/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqliteapp.DAO;

import java.util.List;
import sqliteapp.Persona.Persona;

/**
 *
 * @author EstebanRestrepo
 */
public interface IPersonaDAO {
    
    //CRUD
    public void createPersona(Persona persona);
    public List<Persona> readPersonas();
    public Persona readPersona(String id);
    public void updatePersona(Persona persona);
    public void deletePersona(Persona persona);
    
    
    
}
