package co.edu.unbosque.controller;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;


public class AplMain {

    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.DOWN);
        try {
            File archivo = new File("src/archivo-1.txt");
            // formatting the decimals to 2 places
            df.setRoundingMode(RoundingMode.DOWN);
            double s = archivo.length();
            String size = df.format(archivo.length());
            double kb = s / 1000;
            double megabytes = s / (1024 * 1024);
            String mb = df.format(megabytes);

            Scanner scanner = new Scanner(archivo);

            JOptionPane.showMessageDialog(null, "El tamaño del archivo es: " + size + " bytes");
            JOptionPane.showMessageDialog(null, "El tamaño del archivo es: " + kb + " Kilobytes");
            JOptionPane.showMessageDialog(null, "El tamaño del archivo es: " + mb + " Megabytes");
            scanner.close();
            //Archivo CSV de Salida
            String archivoCsv = "src/archivo-1.csv";
            try (BufferedReader lector = new BufferedReader(new FileReader(archivo));
                 FileWriter writer = new FileWriter(archivoCsv)) {
                // Leer cada línea del archivo de texto y escribir en el archivo CSV
                String linea;
                while ((linea = lector.readLine()) != null) {
                    // Dividir la línea en campos utilizando el separador adecuado
                    String[] campos = linea.split(",");
                    // Escribir los campos en el archivo CSV
                    for (int i = 0; i < campos.length; i++) {
                        writer.append(campos[i]);
                        if (i < campos.length - 1) {
                            writer.append(",");
                        }
                    }
                    writer.append("\n");
                }
                JOptionPane.showMessageDialog(null, "El archivo se ha convertido a CSV correctamente.");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al leer o escribir el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "El archivo no se encontró.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        // Crear un objeto File para representar el archivo CSV
        File archivo = new File("src/archivo-1.csv");

        // Crear un objeto Scanner para leer el archivo CSV
        Scanner scanner;
        try {
            scanner = new Scanner(archivo);
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no existe.");
            return;
        }

        // Crear un objeto HashMap para almacenar los contadores por género
        Map<String, Integer> contadoresGenero = new HashMap<>();
        Map<String, Integer> contadoresZona = new HashMap<>();
        Map<String, Integer> contadoresMes = new HashMap<>();
        Map<String, Integer> contadoresDepartamento = new HashMap<>();
        Map<String, Integer> contadoresEdad = new HashMap<>();
        int totalDelitos = 0;

        // Leer cada línea del archivo CSV y contar los delitos por género
        while (scanner.hasNextLine()) {
            String linea = scanner.nextLine();
            String[] campos = linea.split(",");

            if (campos.length == 13) {
//                System.out.println(campos[5]);
                String genero = campos[8];
                String zona = campos[7];
                String mes = campos[2].split("/")[0];
                String departamento = campos[0];
                String edad = campos[11];
                // Contar delitos por género
                Integer contadorGenero = contadoresGenero.getOrDefault(genero, 0);
                contadoresGenero.put(genero, contadorGenero + 1);

                // Contar delitos por zona
                Integer contadorZona = contadoresZona.getOrDefault(zona, 0);
                contadoresZona.put(zona, contadorZona + 1);

                // Contar delitos por mes
                Integer contadorMes = contadoresMes.getOrDefault(mes, 0);
                contadoresMes.put(mes, contadorMes + 1);

                // Contar delitos por departamento
                Integer contadorDepartamento = contadoresDepartamento.getOrDefault(departamento, 0);
                contadoresDepartamento.put(departamento, contadorDepartamento + 1);

                // Contar delitos por edad
                Integer contadorEdad = contadoresEdad.getOrDefault(edad, 0);
                contadoresEdad.put(edad, contadorEdad + 1);

                totalDelitos++;
            }
        }

        // Calcular el porcentaje de delitos por género y mostrar los resultados
        System.out.println("Delitos por género");
        for (String genero : contadoresGenero.keySet()) {
            int cantidad = contadoresGenero.get(genero);
            double porcentaje = (double) cantidad / totalDelitos * 100;
            String percent= df.format(porcentaje);
            System.out.println("Género: " + genero + ", cantidad de delitos: " + cantidad + ", porcentaje: " + percent + "%");
        }
        System.out.println("-----------------------------------------------------------");

        System.out.println("Delitos por zona");
        for (String zona : contadoresZona.keySet()) {
            int cantidad = contadoresZona.get(zona);
            double porcentaje = (double) cantidad / totalDelitos * 100;
            String percent= df.format(porcentaje);
            System.out.println("Zona: " + zona + ", cantidad de delitos: " + cantidad + ", porcentaje: " + percent + "%");
        }
        System.out.println("-----------------------------------------------------------");
        System.out.println("Delitos por mes");
        for (String mes : contadoresMes.keySet()) {
            int cantidad = contadoresMes.get(mes);
            double porcentaje = (double) cantidad / totalDelitos * 100;
            String percent= df.format(porcentaje);
            System.out.println("Mes: " + mes + ", cantidad de delitos: " + cantidad + ", porcentaje: " + percent + "%");
        }
        System.out.println("-----------------------------------------------------------");
        System.out.println("Delitos por departamento");
        for (String departamento : contadoresDepartamento.keySet()) {
            int cantidad = contadoresDepartamento.get(departamento);
            double porcentaje = (double) cantidad / totalDelitos * 100;
            String percent= df.format(porcentaje);
            System.out.println("Departamento: " + departamento + ", cantidad de delitos: " + cantidad + ", porcentaje: " + percent + "%");
        }
        System.out.println("-----------------------------------------------------------");
        System.out.println("Delitos por edad");
        for (String edad : contadoresEdad.keySet()) {
            int cantidad = contadoresEdad.get(edad);
            double porcentaje = (double) cantidad / totalDelitos * 100;
            String percent= df.format(porcentaje);
            System.out.println("Edad: " + edad + ", cantidad de delitos: " + cantidad + ", porcentaje: " + percent + "%");
        }
        System.out.println("-----------------------------------------------------------");
        JOptionPane.showMessageDialog(null,"Total de delitos: " + totalDelitos);
        System.out.println("Total de delitos: " + totalDelitos);



        scanner.close();



    }
}





