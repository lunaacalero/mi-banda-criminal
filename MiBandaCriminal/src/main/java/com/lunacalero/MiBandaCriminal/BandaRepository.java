package com.lunacalero.MiBandaCriminal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase BandaRepository, con los métodos del proyecto para  guardar
 * a los miembros en ficheros utilizando ObjectOutputStream y ObjectInputStream.
 * 
 * @author Luna Calero
 */
public class BandaRepository {
	private static final String DIRECTORIO_REFUGIO = "refugio";
	
	/**
	 * Constructor de BandaRepository con el método inicializarRefugio para que siempre cree la carpeta
	 */
	public BandaRepository() {
        inicializarRefugio();
    }
	
	/**
	 * Crea la carpeta "refugio" en caso de que no exista.
	 */
	private void inicializarRefugio() {
		File carpeta = new File(DIRECTORIO_REFUGIO);
		if (!carpeta.exists()) {
			carpeta.mkdirs();
		}
	}
	
	/**
	 * Método para crear la ruta completa del archivo
	 * @param alias
	 * @return ruta del archivo
	 */
	private String obtenerRutaArchivo(String alias) {
		return DIRECTORIO_REFUGIO + File.separator + alias.toLowerCase().trim() + ".dat";
	}
	
	/**
	 * Guarda a un miembro en el archivo
	 * @param miembro
	 * @return boolean dependiendo de si se realizó con éxito la operación
	 * @throws IOException
	 */
	public boolean guardarMiembro(MiembroBanda miembro) throws IOException {
		String ruta = obtenerRutaArchivo(miembro.getAlias());
		File archivo = new File(ruta);
		
		if (archivo.exists()) {
			// Devuelve false para no dejar sobreescribir.
			return false;
		}
		
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
			oos.writeObject(miembro);
			return true;
		}
	}
	
	/**
	 * Busca en el archivo el miembro especificado
	 * @param alias
	 * @return miembro buscado
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public MiembroBanda buscarPorAlias(String alias) throws IOException, ClassNotFoundException {
		String ruta = obtenerRutaArchivo(alias);
		File archivo = new File(ruta);
		
		if (!archivo.exists()) {
			return null;
		}
		
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
			return (MiembroBanda) ois.readObject();
		}
	}
	
	/**
	 * Lee todos los archivos .dat en la carpeta 'refugio' y los carga en una lista.
	 * @return lista de miembros
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public List<MiembroBanda> obtenerTodosLosMiembros() throws IOException, ClassNotFoundException {
        List<MiembroBanda> lista = new ArrayList<>();
        File carpeta = new File(DIRECTORIO_REFUGIO);
        File[] archivos = carpeta.listFiles((dir, name) -> name.toLowerCase().endsWith(".dat"));

        if (archivos != null) {
            for (File f : archivos) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                    MiembroBanda m = (MiembroBanda) ois.readObject();
                    lista.add(m);
                }
            }
        }
        return lista;
    }
	
	/**
	 * Borra el fichero .dat, dejando el alias libre nuevamente.
	 * @param alias
	 */
	public boolean borrarMiembro(String alias) {
		String ruta = obtenerRutaArchivo(alias);
		File archivo = new File(ruta);
		
		if (archivo.exists()) {
			return archivo.delete();
		} else {
			return false;
		}
	}
}
