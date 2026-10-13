package com.lunacalero.MiBandaCriminal;

/**
 * Clase PilotoHuida, especialización de MiembroBanda.
 * 
 * @author Luna Calero
 */
public class PilotoHuida extends MiembroBanda {
	// Obligatorios
	private String vehiculoFavorito;
	private int nivelEvasion;

	// Nuevos
	private boolean tieneHelicoptero;
	private double velocidadMaximaKmH;

	public PilotoHuida() {
		super();
	}

	public PilotoHuida(String alias, int puntosVida, int nivelBuscado, double porcentajeBotin, String vehiculoFavorito, int nivelEvasion, boolean tieneHelicoptero, double velocidadMaximaKmH) {
		super(alias, puntosVida, nivelBuscado, porcentajeBotin);

		this.vehiculoFavorito = vehiculoFavorito;
		this.nivelEvasion = nivelEvasion;
		this.tieneHelicoptero = tieneHelicoptero;
		this.velocidadMaximaKmH = velocidadMaximaKmH;
	}

	public String getVehiculoFavorito() {
		return vehiculoFavorito;
	}

	public void setVehiculoFavorito(String vehiculoFavorito) {
		this.vehiculoFavorito = vehiculoFavorito;
	}

	public int getNivelEvasion() {
		return nivelEvasion;
	}

	public void setNivelEvasion(int nivelEvasion) {
		this.nivelEvasion = nivelEvasion;
	}

	public boolean isTieneHelicoptero() {
		return tieneHelicoptero;
	}

	public void setTieneHelicoptero(boolean tieneHelicoptero) {
		this.tieneHelicoptero = tieneHelicoptero;
	}

	public double getVelocidadMaximaKmH() {
		return velocidadMaximaKmH;
	}

	public void setVelocidadMaximaKmH(double velocidadMaximaKmH) {
		this.velocidadMaximaKmH = velocidadMaximaKmH;
	}

	@Override
	public String toString() {
		return super.toString() + " | [Piloto] Vehículo: " + vehiculoFavorito + ", Evasión: " + nivelEvasion
				+ ", Helicóptero: " + (tieneHelicoptero ? "Sí" : "No") + ", Velocidad Máxima: " + velocidadMaximaKmH;
	}

}
