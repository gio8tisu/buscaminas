/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

/**
 *
 * @author user
 */
public class Celda {
    private boolean activa; //false-->inactivo , true-->"tocado"
    private boolean bandera;
    private final boolean bomba;
    private char minasCerca;
    
    
    public Celda(boolean bomba) {
        this.bomba = bomba;
        this.activa = false;
        if(bomba) {
            minasCerca = '*';
        }
    }
    
    
    public void setActiva() {
        this.activa = true;
    }
    
    public void setActiva(boolean nuevoEstado) {
        this.activa = nuevoEstado;
    }
    
    public boolean isActiva() {
        return activa;
    }

    public boolean isBomba() {
        return bomba;
    }

    public boolean isBandera() {
        return bandera;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }

    public int getMinasCercaInt() {
        return (int) (minasCerca - '0');
    }
    
    public String getMinasCercaString() {
        return " " + minasCerca + " "; //char to String para concatenar
    }
    
    public void setMinasCerca(int minasCerca) {
        this.minasCerca = (char)(minasCerca + '0'); //int to char
    }
    
}
