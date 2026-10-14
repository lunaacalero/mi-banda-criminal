package com.lunacalero.MiBandaCriminal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.Scanner;

public class MetodosApp {
	
    private static final BandaRepository repo = new BandaRepository();
    private static final Scanner scanner = new Scanner(System.in);
    
	 /**
     * Opción 1 del menú: Reclutar Miembro
     * @author Luna
     * @throws Exception
     */
    public void reclutarMiembro() throws Exception {
        System.out.println("\n--- RECLUTAR MIEMBRO ---");
        System.out.print("Tipo de perfil (1. Piloto | 2. Especialista Armas | 3. Hacker): ");
        String tipo = scanner.nextLine();

        System.out.print("Alias: ");
        String alias = scanner.nextLine();
        System.out.print("Puntos de Vida: ");
        int vida = Integer.parseInt(scanner.nextLine());
        System.out.print("Nivel Buscado (1 a 5 estrellas): ");
        int estrellas = Integer.parseInt(scanner.nextLine());
        System.out.print("Porcentaje de Botín: ");
        double botin = Double.parseDouble(scanner.nextLine());

        MiembroBanda nuevo = null;

        if (tipo.equals("1")) {
            System.out.print("Vehículo Favorito: ");
            String vehiculo = scanner.nextLine();
            System.out.print("Nivel de Evasión: ");
            int evasion = Integer.parseInt(scanner.nextLine());
            System.out.print("¿Tiene Helicóptero? (si/no): ");
            boolean helicoptero = scanner.nextLine().equalsIgnoreCase("si");
            System.out.print("Velocidad Máxima (Km/h): ");
            double velMax = Double.parseDouble(scanner.nextLine());

            nuevo = new PilotoHuida(alias, vida, estrellas, botin, vehiculo, evasion, helicoptero, velMax);

        } else if (tipo.equals("2")) {
            System.out.print("Arma Principal: ");
            String arma = scanner.nextLine();
            System.out.print("Precisión de Tiro (%): ");
            int precision = Integer.parseInt(scanner.nextLine());
            System.out.print("Tipo de Calibre: ");
            String calibre = scanner.nextLine();
            System.out.print("¿Maneja Explosivos? (si/no): ");
            boolean explosivos = scanner.nextLine().equalsIgnoreCase("si");

            nuevo = new EspecialistaArmas(alias, vida, estrellas, botin, arma, precision, calibre, explosivos);

        } else if (tipo.equals("3")) {
            System.out.print("Velocidad de Desencriptación: ");
            int velDes = Integer.parseInt(scanner.nextLine());
            System.out.print("Sistemas Infiltrados: ");
            int sistemas = Integer.parseInt(scanner.nextLine());
            System.out.print("Sistema Operativo: ");
            String os = scanner.nextLine();
            System.out.print("¿Tiene VPN Anónima? (si/no): ");
            boolean vpn = scanner.nextLine().equalsIgnoreCase("si");

            nuevo = new Hacker(alias, vida, estrellas, botin, velDes, sistemas, os, vpn);
        } else {
            System.out.println("Tipo de perfil no válido.");
            return;
        }

        boolean guardado = repo.guardarMiembro(nuevo);
        if (guardado) {
            System.out.println("¡Miembro reclutado y guardado con éxito en refugio!");
        } else {
            System.out.println("Error: Ya existe un personaje guardado con el alias '" + alias + "'.");
        }
    }

    /**
     * Opción 2 del menú: Liquidar miembro
     * @author Luna
     */
    public void liquidarMiembro() {
        System.out.println("\n--- LIQUIDAR MIEMBRO ---");
        System.out.print("Introduce el alias a liquidar: ");
        String alias = scanner.nextLine();

        System.out.print("¿Confirmas retirar a " + alias + "? (si/no): ");
        String confirmacion = scanner.nextLine();

        if (confirmacion.equalsIgnoreCase("si")) {
            boolean borrado = repo.borrarMiembro(alias);
            if (borrado) {
                System.out.println("El miembro '" + alias + "' ha sido retirado y su alias ha quedado libre.");
            } else {
                System.out.println("No se encontró ningún miembro activo con el alias '" + alias + "'.");
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    /**
     * Opción 3 del menú: Consultar expediente
     * @author Luna
     * @throws Exception
     */
    public void consultarExpediente() throws Exception {
        System.out.println("\n--- CONSULTAR EXPEDIENTE ---");
        System.out.print("Introduce el alias del personaje: ");
        String alias = scanner.nextLine();

        MiembroBanda m = repo.buscarPorAlias(alias);
        if (m != null) {
            System.out.println("\nEXPEDIENTE ENCONTRADO:");
            System.out.println(m);
        } else {
            System.out.println("Error: No existe ningún archivo .dat para el alias '" + alias + "' en la carpeta refugio.");
        }
    }

    /**
     * Opción 4 del menú: Buscar perfil
     * @author Luna
     * @throws Exception
     */
    public void buscarPerfilMision() throws Exception {
        System.out.println("\n--- BÚSQUEDA AVANZADA PARA MISIÓN ---");
        System.out.print("Selecciona perfil (1. Piloto | 2. Especialista | 3. Hacker): ");
        String tipoStr = scanner.nextLine();

        System.out.print("Nivel MÁXIMO de búsqueda policial (1 a 5 estrellas): ");
        int maxEstrellas = Integer.parseInt(scanner.nextLine());

        List<MiembroBanda> todos = repo.obtenerTodosLosMiembros();
        int encontrados = 0;

        System.out.println("\nCANDIDATOS ENCONTRADOS:");
        for (MiembroBanda m : todos) {
            boolean coincideTipo = false;

            if (tipoStr.equals("1") && m instanceof PilotoHuida) coincideTipo = true;
            if (tipoStr.equals("2") && m instanceof EspecialistaArmas) coincideTipo = true;
            if (tipoStr.equals("3") && m instanceof Hacker) coincideTipo = true;

            if (coincideTipo && m.getNivelBuscado() <= maxEstrellas) {
                System.out.println("- " + m.getAlias() + " | Vida: " + m.getPuntosVida() + " HP | Estrellas: " + m.getNivelBuscado() + "★");
                encontrados++;
            }
        }

        if (encontrados == 0) {
            System.out.println("No se encontraron miembros que cumplan los criterios.");
        }
    }
    
    
    
    
    
    
    // =====================================================================
    // MÓDULO XML / JSON (opciones 5, 6 y 7 hechas por SAMUEL)
    // =====================================================================

    // Contenedor raíz para el XML/JSON.
    @JacksonXmlRootElement(localName = "plantilla")
    public static class PlantillaBanda {
        @JacksonXmlElementWrapper(localName = "miembros")
        @JacksonXmlProperty(localName = "miembro")
        private List<MiembroBanda> miembros = new ArrayList<>();

        public PlantillaBanda() {
        }

        public PlantillaBanda(List<MiembroBanda> miembros) {
            this.miembros = miembros;
        }

        public List<MiembroBanda> getMiembros() {
            return miembros;
        }

        public void setMiembros(List<MiembroBanda> miembros) {
            this.miembros = miembros;
        }
    }

    // Mixin: indica a Jackson qué subclase crear al leer (sin tocar las clases originales).
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipo")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = PilotoHuida.class, name = "PILOTO"),
            @JsonSubTypes.Type(value = EspecialistaArmas.class, name = "ESPECIALISTA"),
            @JsonSubTypes.Type(value = Hacker.class, name = "HACKER")
    })
    public static abstract class MiembroBandaMixin {
    }

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final XmlMapper XML_MAPPER = new XmlMapper();

    static {
        for (ObjectMapper m : new ObjectMapper[] { JSON_MAPPER, XML_MAPPER }) {
            m.addMixIn(MiembroBanda.class, MiembroBandaMixin.class);
            m.enable(SerializationFeature.INDENT_OUTPUT);
            m.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            m.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
    }

    /**
     * Opción 5 del menú: Exportar Banda (XML/JSON)
     * @author Samuel
     * @throws Exception
     */
    public void exportarBanda() throws Exception {
        System.out.println("\n--- EXPORTAR BANDA ---");

        List<MiembroBanda> miembros = repo.obtenerTodosLosMiembros();
        if (miembros == null || miembros.isEmpty()) {
            System.out.println("No hay miembros en el refugio para exportar.");
            return;
        }

        System.out.print("¿Formato de exportación? (1. XML | 2. JSON): ");
        String opcion = scanner.nextLine().trim();

        File destino;
        ObjectMapper mapper;
        if (opcion.equals("1")) {
            destino = new File("plantilla.xml");
            mapper = XML_MAPPER;
        } else if (opcion.equals("2")) {
            destino = new File("plantilla.json");
            mapper = JSON_MAPPER;
        } else {
            System.out.println("Opción no válida.");
            return;
        }

        try {
            mapper.writeValue(destino, new PlantillaBanda(new ArrayList<>(miembros)));
            System.out.println("Banda exportada correctamente (" + miembros.size() + " miembros) en: "
                    + destino.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error al exportar la banda: " + e.getMessage());
        }
    }

    /**
     * Opción 6 del menú: Importación Masiva de Datos
     * @author Samuel
     * @throws Exception
     */
    public void importacionMasiva() throws Exception {
        System.out.println("\n--- IMPORTACIÓN MASIVA ---");
        System.out.print("Ruta del archivo XML o JSON a importar: ");
        File origen = new File(scanner.nextLine().trim());

        if (!origen.isFile()) {
            System.out.println("El archivo no existe: " + origen.getPath());
            return;
        }

        List<MiembroBanda> miembros;
        try {
            miembros = leerMiembrosDeFichero(origen);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo (formato incorrecto): " + e.getMessage());
            return;
        }

        int importados = 0;
        List<String> conflictos = new ArrayList<>();

        for (MiembroBanda m : miembros) {
            if (m == null || m.getAlias() == null || m.getAlias().trim().isEmpty()) {
                System.out.println("[AVISO] Se ha omitido un miembro sin alias.");
                continue;
            }

            try {
                if (repo.guardarMiembro(m)) {
                    importados++;
                } else {
                    System.out.println("[CONFLICTO] El alias '" + m.getAlias()
                            + "' ya existe en el refugio. No se importa.");
                    conflictos.add(m.getAlias());
                }
            } catch (Exception e) {
                System.out.println("[ERROR] No se pudo guardar '" + m.getAlias() + "': " + e.getMessage());
            }
        }

        System.out.println("\n--- Resumen de la importación ---");
        System.out.println("Miembros importados: " + importados);
        System.out.println("Conflictos por alias duplicado: " + conflictos.size()
                + (conflictos.isEmpty() ? "" : " " + conflictos));
    }

    /**
     * Opción 7 del menú: Convertir formato de archivo (XML <-> JSON)
     * @author Samuel
     * @throws Exception
     */
    public void convertirFormato() throws Exception {
        System.out.println("\n--- CONVERTIR FORMATO ---");
        System.out.print("Ruta del archivo XML o JSON a convertir: ");
        File origen = new File(scanner.nextLine().trim());

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
        File destino = new File(origen.getAbsoluteFile().getParentFile(), base + (esXml ? ".json" : ".xml"));

        try {
            List<MiembroBanda> miembros = leerMiembrosDeFichero(origen);
            ObjectMapper mapperDestino = esXml ? JSON_MAPPER : XML_MAPPER;
            mapperDestino.writeValue(destino, new PlantillaBanda(miembros));
            System.out.println("Conversión completada (" + miembros.size() + " miembros): "
                    + destino.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error al convertir el archivo (formato incorrecto): " + e.getMessage());
        }
    }

    // Auxiliar: lee una lista de miembros desde un fichero .xml o .json.
    private List<MiembroBanda> leerMiembrosDeFichero(File f) throws IOException {
        String lower = f.getName().toLowerCase();

        if (lower.endsWith(".xml")) {
            PlantillaBanda p = XML_MAPPER.readValue(f, PlantillaBanda.class);
            return p.getMiembros() != null ? p.getMiembros() : new ArrayList<>();
        }

        if (lower.endsWith(".json")) {
            JsonNode raiz = JSON_MAPPER.readTree(f);
            if (raiz.isArray()) {
                return new ArrayList<>(Arrays.asList(JSON_MAPPER.treeToValue(raiz, MiembroBanda[].class)));
            }
            PlantillaBanda p = JSON_MAPPER.treeToValue(raiz, PlantillaBanda.class);
            return p.getMiembros() != null ? p.getMiembros() : new ArrayList<>();
        }

        throw new IllegalArgumentException("Formato no soportado. Usa un archivo .xml o .json");
    }
}
