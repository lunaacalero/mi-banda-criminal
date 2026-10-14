package com.lunacalero.MiBandaCriminal;

import java.util.Scanner;

public class App {

    private static final Scanner scanner = new Scanner(System.in);
    private static final MetodosApp metodos = new MetodosApp();

    public static void main(String[] args) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- MENÚ BANDA CRIMINAL ---");
            System.out.println("1. Reclutar nuevo miembro");
            System.out.println("2. Liquidar miembro");
            System.out.println("3. Consultar expediente por Alias");
            System.out.println("4. Buscar perfil para misión (Búsqueda avanzada)");
            System.out.println("5. Exportar Banda (XML/JSON)");
            System.out.println("6. Importación Masiva de Datos");
            System.out.println("7. Convertir formato de archivo");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine();

            try {
                switch (opcion) {
                    case "1":
                        metodos.reclutarMiembro();
                        break;
                    case "2":
                        metodos.liquidarMiembro();
                        break;
                    case "3":
                        metodos.consultarExpediente();
                        break;
                    case "4":
                        metodos.buscarPerfilMision();
                        break;
                    case "5":
                    	metodos.exportarBanda();
                        break;
                    case "6":
                    	metodos.importacionMasiva();
                        break;
                    case "7":
                    	metodos.convertirFormato();
                        break;
                    case "8":
                        salir = true;
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error durante la operación: " + e.getMessage());
            }
        }
    }

   
}