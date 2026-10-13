package com.lunacalero.MiBandaCriminal;

/**
 * Clase EspecialistaArmas, especialización de MiembroBanda.
 * 
 * @author Luna Calero
 */
public class EspecialistaArmas extends MiembroBanda {

    // Obligatorios
    private String armaPrincipal;
    private int precisionTiro;

    // Nuevos
    private String tipoCalibre; // Ej: "9mm", "7.62mm", "5.56mm"
    private boolean manejaExplosivos;

    public EspecialistaArmas() {
        super();
    }

    public EspecialistaArmas(String alias, int puntosVida, int nivelBuscado, double porcentajeBotin, String armaPrincipal, int precisionTiro, String tipoCalibre, boolean manejaExplosivos) {
        super(alias, puntosVida, nivelBuscado, porcentajeBotin);
        
        this.armaPrincipal = armaPrincipal;
        this.precisionTiro = precisionTiro;
        this.tipoCalibre = tipoCalibre;
        this.manejaExplosivos = manejaExplosivos;
    }

    public String getArmaPrincipal() {
        return armaPrincipal;
    }

    public void setArmaPrincipal(String armaPrincipal) {
        this.armaPrincipal = armaPrincipal;
    }

    public int getPrecisionTiro() {
        return precisionTiro;
    }

    public void setPrecisionTiro(int precisionTiro) {
        this.precisionTiro = precisionTiro;
    }

    public String getTipoCalibre() {
        return tipoCalibre;
    }

    public void setTipoCalibre(String tipoCalibre) {
        this.tipoCalibre = tipoCalibre;
    }

    public boolean isManejaExplosivos() {
        return manejaExplosivos;
    }

    public void setManejaExplosivos(boolean manejaExplosivos) {
        this.manejaExplosivos = manejaExplosivos;
    }

    @Override
    public String toString() {
        return super.toString() + " | [Especialista Armas] Arma: " + armaPrincipal + 
               ", Precisión: " + precisionTiro + "%, Calibre: " + tipoCalibre + 
               ", Explosivos: " + (manejaExplosivos ? "Sí" : "No");
    }
}