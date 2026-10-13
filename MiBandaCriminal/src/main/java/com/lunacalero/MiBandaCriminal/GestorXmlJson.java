package com.lunacalero.MiBandaCriminal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * Usa Jackson (JSON y XML) con un mixin para la jerarquía de clases.
 * @author Samuel
 */
public class GestorXmlJson {

    public static final String CARPETA_REFUGIO = "refugio";

    private static final ObjectMapper JSON = crearMapperJson();
    private static final XmlMapper XML = crearMapperXml();

    // Configuración de los mappers
    private static ObjectMapper crearMapperJson() {
        ObjectMapper m = new ObjectMapper();
        configurar(m);
        return m;
    }

    private static XmlMapper crearMapperXml() {
        XmlMapper m = new XmlMapper();
        configurar(m);
        return m;
    }

    private static void configurar(ObjectMapper m) {
        m.addMixIn(MiembroBanda.class, MiembroBandaMixin.class);
        m.enable(SerializationFeature.INDENT_OUTPUT);
        m.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        m.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
    
    // Exportar banda
    public static void exportarBanda(Scanner sc) {
        List<MiembroBanda> miembros = cargarMiembrosDelRefugio();
        if (miembros.isEmpty()) {
            System.out.println("No hay miembros en el refugio para exportar.");
            return;
        }

        System.out.print("¿Formato de exportación? (1 = XML, 2 = JSON): ");
        String opcion = sc.nextLine().trim();

        File destino;
        ObjectMapper mapper;
        if (opcion.equals("1")) {
            destino = new File("plantilla.xml");
            mapper = XML;
        } else if (opcion.equals("2")) {
            destino = new File("plantilla.json");
            mapper = JSON;
        } else {
            System.out.println("Opción no válida.");
            return;
        }

        try {
            mapper.writeValue(destino, new PlantillaBanda(miembros));
            System.out.println("Banda exportada correctamente (" + miembros.size() + " miembros) en: "
                    + destino.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error al exportar la banda: " + e.getMessage());
        }
    }

    // Importación masiva
    public static void importacionMasiva(Scanner sc) {
        System.out.print("Ruta del archivo XML o JSON a importar: ");
        File origen = new File(sc.nextLine().trim());

        if (!origen.isFile()) {
            System.out.println("El archivo no existe: " + origen.getPath());
            return;
        }

        List<MiembroBanda> miembros;
        try {
            miembros = leerMiembros(origen);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo (formato incorrecto): " + e.getMessage());
            return;
        }

        asegurarRefugio();

        int importados = 0;
        List<String> conflictos = new ArrayList<>();

        for (MiembroBanda m : miembros) {
            if (m == null || m.getAlias() == null || m.getAlias().trim().isEmpty()) {
                System.out.println("[AVISO] Se ha omitido un miembro sin alias.");
                continue;
            }

            File fichero = ficheroDe(m.getAlias());
            if (fichero.exists()) {
                System.out.println("[CONFLICTO] El alias '" + m.getAlias()
                        + "' ya existe en el refugio. No se importa.");
                conflictos.add(m.getAlias());
                continue;
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichero))) {
                oos.writeObject(m);
                importados++;
            } catch (IOException e) {
                System.out.println("[ERROR] No se pudo guardar '" + m.getAlias() + "': " + e.getMessage());
            }
        }

        System.out.println("\n--- Resumen de la importación ---");
        System.out.println("Miembros importados: " + importados);
        System.out.println("Conflictos por alias duplicado: " + conflictos.size()
                + (conflictos.isEmpty() ? "" : " " + conflictos));
    }

    // Convertir formato
    public static void convertirFormato(Scanner sc) {
        System.out.print("Ruta del archivo XML o JSON a convertir: ");
        File origen = new File(sc.nextLine().trim());

        if (!origen.isFile()) {
            System.out.println("El archivo no existe: " + origen.getPath());
            return;
        }

        String nombre = origen.getName();
        String lower = nombre.toLowerCase();
        boolean esXml = lower.endsWith(".xml");
        boolean esJson = lower.endsWith(".json");
        if (!esXml && !esJson) {
            System.out.println("Formato no soportado. Usa un archivo .xml o .json");
            return;
        }

        String base = nombre.substring(0, nombre.lastIndexOf('.'));
        File destino = new File(origen.getAbsoluteFile().getParentFile(),
                base + (esXml ? ".json" : ".xml"));

        try {
            List<MiembroBanda> miembros = leerMiembros(origen);
            ObjectMapper mapperDestino = esXml ? JSON : XML;
            mapperDestino.writeValue(destino, new PlantillaBanda(miembros));
            System.out.println("Conversión completada (" + miembros.size() + " miembros): "
                    + destino.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error al convertir el archivo (formato incorrecto): " + e.getMessage());
        }
    }

    // Utilidades ==>
    // Lee una lista de miembros desde un fichero .xml o .json.
    private static List<MiembroBanda> leerMiembros(File f) throws IOException {
        String lower = f.getName().toLowerCase();

        if (lower.endsWith(".xml")) {
            PlantillaBanda p = XML.readValue(f, PlantillaBanda.class);
            return p.getMiembros() != null ? p.getMiembros() : new ArrayList<>();
        }

        if (lower.endsWith(".json")) {
            JsonNode raiz = JSON.readTree(f);
            if (raiz.isArray()) {
                MiembroBanda[] array = JSON.treeToValue(raiz, MiembroBanda[].class);
                return new ArrayList<>(Arrays.asList(array));
            }
            PlantillaBanda p = JSON.treeToValue(raiz, PlantillaBanda.class);
            return p.getMiembros() != null ? p.getMiembros() : new ArrayList<>();
        }

        throw new IllegalArgumentException("Formato no soportado. Usa un archivo .xml o .json");
    }

    // Carga todos los .dat de la carpeta refugio (los retirados no se incluyen)
    private static List<MiembroBanda> cargarMiembrosDelRefugio() {
        List<MiembroBanda> lista = new ArrayList<>();
        File carpeta = new File(CARPETA_REFUGIO);
        File[] ficheros = carpeta.listFiles((dir, name) -> name.toLowerCase().endsWith(".dat"));
        if (ficheros == null) {
            return lista;
        }
        Arrays.sort(ficheros, Comparator.comparing(File::getName));

        for (File f : ficheros) {
            try (ObjectInputStream ois = new ObjectInputStream(new java.io.FileInputStream(f))) {
                Object o = ois.readObject();
                if (o instanceof MiembroBanda) {
                    lista.add((MiembroBanda) o);
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("[AVISO] No se pudo leer " + f.getName() + ": " + e.getMessage());
            }
        }
        return lista;
    }

    private static File ficheroDe(String alias) {
        return new File(CARPETA_REFUGIO, alias.trim().toLowerCase() + ".dat");
    }

    private static void asegurarRefugio() {
        File carpeta = new File(CARPETA_REFUGIO);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
    }
}