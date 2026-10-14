package com.lunacalero.MiBandaCriminal;

import java.util.List;
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
}
