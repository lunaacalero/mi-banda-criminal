package com.lunacalero.MiBandaCriminal;

/**
 * Clase Hacker, especialización de MiembroBanda.
 * 
 * @author Luna Calero
 */
public class Hacker extends MiembroBanda {

    // Obligatorios
    private int velocidadDesencriptacion;
    private int sistemasInfiltrados;

    // Nuevos
    private String sistemaOperativo;
    private boolean tieneVpnAnonima;

    public Hacker() {
        super();
    }

    public Hacker(String alias, int puntosVida, int nivelBuscado, double porcentajeBotin,int velocidadDesencriptacion, int sistemasInfiltrados, String sistemaOperativo, boolean tieneVpnAnonima) {
        super(alias, puntosVida, nivelBuscado, porcentajeBotin);
        
        this.velocidadDesencriptacion = velocidadDesencriptacion;
        this.sistemasInfiltrados = sistemasInfiltrados;
        this.sistemaOperativo = sistemaOperativo;
        this.tieneVpnAnonima = tieneVpnAnonima;
    }

    public int getVelocidadDesencriptacion() {
        return velocidadDesencriptacion;
    }

    public void setVelocidadDesencriptacion(int velocidadDesencriptacion) {
        this.velocidadDesencriptacion = velocidadDesencriptacion;
    }

    public int getSistemasInfiltrados() {
        return sistemasInfiltrados;
    }

    public void setSistemasInfiltrados(int sistemasInfiltrados) {
        this.sistemasInfiltrados = sistemasInfiltrados;
    }

    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }

    public boolean isTieneVpnAnonima() {
        return tieneVpnAnonima;
    }

    public void setTieneVpnAnonima(boolean tieneVpnAnonima) {
        this.tieneVpnAnonima = tieneVpnAnonima;
    }

    @Override
    public String toString() {
        return super.toString() + " | [Hacker] Vel. Desencriptación: " + velocidadDesencriptacion + 
               " MB/s, Sistemas Infiltrados: " + sistemasInfiltrados + 
               ", OS: " + sistemaOperativo + ", VPN Anónima: " + (tieneVpnAnonima ? "Sí" : "No");
    }
}
