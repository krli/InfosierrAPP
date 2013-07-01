/**
 * 
 */
package es.ulpgc.IST.infosierrapp.datos;

/**
 * @author krlo
 *
 */

/**
 * Contiene la informacion que contendrá cada item del modelo maestro-detalle
 * @author jesus
 *
 */
public class Anuncio {



    public static int maxpos = 0;

    private String pos;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private String web;
    private String descripcion;
    private int X;
    private int Y;
//a??adir fotos 

    public Anuncio() {
        setNombre("");
        setDireccion("");
        setTelefono("");
        setDescripcion("");
        setEmail("");
        setWeb("");
        setX(0);
        setY(0);
        setPos();
    }

    /**
     * Constructor para crear nuevos items. Recibe los campos necesarios
     * @param nombre
     * @param direccion
     * @param telefono
     * @param email
     * @param descripcion
     * @param X
     * @param Y
     */
    public Anuncio(String nombre, String direccion, String telefono, String email, String descripcion, String web, int X, int Y) {
        setNombre(nombre);
        setDireccion(direccion);
        setTelefono(telefono);
        setEmail(email);
        setDescripcion(descripcion);
        setWeb(web);
        setX(X);
        setY(Y);
        setPos();
    }

    public String getPos() {
        return pos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setTelefono(String tel) {
        this.telefono = tel;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }



    public void setEmail(String email) {
        this.email = email;
    }



    public void setPos(int pos) {
        this.pos = "" + pos;
    }


    private void setPos() {
        this.pos = "" + maxpos++;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;

    }

    public String getNombre(){
        return nombre;
    }

    public String getEmail(){
        return email;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion(){
        return descripcion;
    }
    
    public void setWeb(String web) {
        this.web = web;
    }
    
    public String getWeb() {
        return web;
    }
    
    public int getX(){
        return X;
    }
    
    public int getY(){
        return Y;
    }
    
    public void setX(int X) {
        this.X = X;
    }
    
    public void setY(int Y) {
        this.Y = Y;
    }

}
