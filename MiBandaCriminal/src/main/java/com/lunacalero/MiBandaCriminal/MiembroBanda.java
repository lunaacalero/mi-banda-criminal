package com.lunacalero.MiBandaCriminal;

import java.io.Serializable;

/**
 * Clase abstracta MiembroBanda
 * 
 * @author Luna Calero
 */
public abstract class MiembroBanda implements Serializable {
	private String alias;
	private int puntosVida;
	private int nivelBuscado;
	private double porcentajeBotin;
	
	public MiembroBanda() {
		super();
	}


	public MiembroBanda(String alias, int puntosVida, int nivelBuscado, double porcentajeBotin) {
		super();
		this.alias = alias;
		this.puntosVida = puntosVida;
		setNivelBuscado(nivelBuscado);
		this.porcentajeBotin = porcentajeBotin;
	}


	public String getAlias() {
		return alias;
	}


	public void setAlias(String alias) {
		this.alias = alias;
	}


	public int getPuntosVida() {
		return puntosVida;
	}


	public void setPuntosVida(int puntosVida) {
		this.puntosVida = puntosVida;
	}


	public int getNivelBuscado() {
		return nivelBuscado;
	}


	public void setNivelBuscado(int nivelBuscado) {
	    if (nivelBuscado >= 1 && nivelBuscado <= 5) {
	        this.nivelBuscado = nivelBuscado;
	    } else {
	        System.out.println("El nivel buscado debe estar entre 1 y 5.");
	    }
	}


	public double getPorcentajeBotin() {
		return porcentajeBotin;
	}


	public void setPorcentajeBotin(double porcentajeBotin) {
		this.porcentajeBotin = porcentajeBotin;
	}


	@Override
    public String toString() {
        return "Alias: " + alias + " | Vida: " + puntosVida + 
               " HP | Estrellas: " + nivelBuscado + "★ | Botín: " + porcentajeBotin + "%";
    }
}
