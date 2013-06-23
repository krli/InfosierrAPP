package es.ulpgc.IST.infosierrapp.maestrodetalle;

import java.io.Serializable;

public class ItemModel implements Serializable {

    public static int maxpos = 0;

    private String pos;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private String descripcion;
    private int X;
    private int Y;
//a??adir fotos y map

    public ItemModel() {
        setNombre("");
        setDireccion("");
        setTelefono("");
        setDescripcion("");
        setEmail("");
        setX(0);
        setY(0);
        setPos();
    }

    public ItemModel(String nombre, String direccion, String telefono, String email, String descripcion, int X, int Y) {
        setNombre(nombre);
        setDireccion(direccion);
        setTelefono(telefono);
        setEmail(email);
        setDescripcion(descripcion);
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
