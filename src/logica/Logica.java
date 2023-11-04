package logica;

import bean.Profesor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Logica {

    public Logica() {
    }

    public void cargarProfesoresEnArrayLists(ArrayList<Profesor> profesoresTiempoCompleto, ArrayList<Profesor> profesoresCatedra, ArrayList<Profesor> profesoresOcasional) throws ParseException {
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Formato de fecha
            BufferedReader br = new BufferedReader(new FileReader("./datos/profesores.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 9) { // Asegurarse de que hay 9 campos en cada línea
                    String tipoContrato = parts[8];
                    LocalDate fechaNacimiento = LocalDate.parse(parts[7], dateFormatter);
                    Profesor profesor = new Profesor(parts[0], parts[1], parts[2], parts[3], parts[4],
                            Integer.parseInt(parts[5]), Integer.parseInt(parts[6]), fechaNacimiento, tipoContrato);

                    // Agregar el profesor a los ArrayList correspondientes
                    if (tipoContrato.contains("Tiempo Completo")) {
                        profesoresTiempoCompleto.add(profesor);
                    }
                    if (tipoContrato.contains("Cátedra")) {
                        profesoresCatedra.add(profesor);
                    }
                    if (tipoContrato.contains("Ocasional")) {
                        profesoresOcasional.add(profesor);
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }//CIERRE DEL METODO

}//CIERRE DEL LA CLASE
