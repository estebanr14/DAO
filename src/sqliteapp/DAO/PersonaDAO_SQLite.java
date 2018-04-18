
package sqliteapp.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sqliteapp.Persona.Persona;

/**
 *
 * @author EstebanRestrepo
 */
public class PersonaDAO_SQLite  implements IPersonaDAO {
    
    private String ruta = "jdbc:sqlite:"; //Ruta de la base de datos
    private String controlador = "org.sqlite.JDBC"; //Controlador de SQLite3
    private Connection conector=null; // Conector de la base de datos
    private List<Persona> personas= new ArrayList<>(); // Lista de personas en la tabla
    private boolean actualizacion = false; //Verdadero si se ha hecho alguna actualización de datos en la base datos.
    
    
    public void conectarBD(String nombreBD){
        ruta = ruta.concat(nombreBD+".sqlite"); // Contatena el nombre de la base de datos con la ruta
          try{
          Class.forName(this.controlador); //Cargar el controlador de la base de datos
          conector = DriverManager.getConnection(ruta); //Se realiza la conexión a la base de datos
          if(conector!=null){
            System.out.println("Conexión realizada correctamente - Ruta de base de datos: " + ruta);
          }
          } catch (ClassNotFoundException ex) {
            System.out.println("Error en la conexión a la base de datos");
          }catch (SQLException ex) {
             ex.printStackTrace();
        }
    }//Realiza la conexión con la base de datos del parámetro
    
    public void cerrarConexion(){
        try{
            conector.close();
            System.out.println("Cierre de conexión exitosa");
        } catch(SQLException e){
            System.out.println("Error al cerrar conexión");
        }
    } //Cerrar la conexión con la base de datos

    //Implementación del CRUD de la interfaz
    @Override
    public void createPersona(Persona persona) {
        String sentencia= "INSERT INTO personas (id, nombre) VALUES (?,?)"; //Sentencia SQL de inserción
        try{
             PreparedStatement st = conector.prepareStatement(sentencia);   //Prepara la variable para una sentencia SQL
             st.setString(1, persona.getId()); //Inserta en el primer VALUES de la consulta el valor del id Persona
             st.setString(2, persona.getNombre()); //Inserta en el segundo VALUES de la consulta el valor del nombre Persona
             st.execute(); // ejecuta la sentencia SQL
             System.out.println("Inserción Exitosa");
             actualizacion = true;
        } catch (SQLException ex) {
            System.out.println("Error en la inserción "+sentencia);        }
    }

    @Override
    public List<Persona> readPersonas() {
        //Si actualización es TRUE hay que volver a traer la lista de personas ya que hay actualizaciones en la lista
        if(actualizacion=true){
                String sql = "SELECT * FROM personas";
                ResultSet resultado = null;  //Instancia el resultado de la consulta SQL
                Persona personaTemp=new Persona("","");;
                try{
                    PreparedStatement st = conector.prepareStatement(sql); //Prepara la variable para una consulta SQL
                    resultado = st.executeQuery();  // "resultado" almacena el resultado arrojado por la consulta
                    personas.clear();//Limpia la lista
                    while(resultado.next()){
                        personaTemp.setId(resultado.getString("id"));
                        personaTemp.setNombre(resultado.getString("nombre"));
                        personas.add(personaTemp);       
                    }
                    System.out.println("Consulta de lectura realizada exitosamente");
                    actualizacion = false;
                } catch (SQLException ex) {
                    System.out.println("Error en sentencia: "+sql);
                }
        }
        return personas;
    }

    @Override
    public Persona readPersona(String id) {
      ResultSet resultado;
      Persona persona=new Persona("","");;
      String sql = "SELECT * FROM personas WHERE id=?";
      try{
      PreparedStatement st = conector.prepareStatement(sql);
      st.setString(1, id);
      resultado = st.executeQuery();
      persona.setId(resultado.getString("id"));
      persona.setNombre(resultado.getString("nombre"));
          System.out.println("Consulta exitosa");
      }catch (SQLException e){
          System.out.println("Error en la consulta: "+sql );
      }
      return persona;
    }

    @Override
    public void updatePersona(Persona persona) {
        String sql = "UPDATE personas SET nombre= ?  WHERE id = ?";
        try{
            PreparedStatement st = conector.prepareStatement(sql);
            st.setString(1, persona.getNombre());
            st.setString(2, persona.getId());
            st.execute();
            System.out.println("Se actualizó correctamente el registro");
            actualizacion = true;
        }catch (SQLException e){
            System.out.println("Error en la sentencia " +sql);
        }
    }

    @Override
    public void deletePersona(Persona persona) {
        String sql = "DELETE FROM personas WHERE id=?";
        try{
              PreparedStatement st = conector.prepareStatement(sql);
              st.setString(1, persona.getId());
              st.execute();
              System.out.println("Se eeliminó el registro correctamente");
              actualizacion=true;
        } catch (SQLException e){
            System.out.println("Error al ejecutar: "+sql);
        }
    }
    
}
