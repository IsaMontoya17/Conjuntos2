package vista;

import bean.Profesor;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import logica.Logica;

public class Menu {

    ArrayList<Profesor> profesoresTiempoCompleto = new ArrayList<>();
    ArrayList<Profesor> profesoresCatedra = new ArrayList<>();
    ArrayList<Profesor> profesoresOcasional = new ArrayList<>();

    public void menu() throws ParseException {

        Logica logica = new Logica();

        logica.cargarProfesoresEnArrayLists(profesoresTiempoCompleto, profesoresCatedra, profesoresOcasional);

        while (true) {

            String opcion = JOptionPane.showInputDialog(
                    "Menú de Profesores\n"
                    + "1. Listar y contar los profesores de tiempo completo\n"
                    + "2. Listar y contar los profesores de cátedra\n"
                    + "3. Listar y contar los profesores ocasionales\n"
                    + "4. Listar y contar el total de profesores\n"
                    + "5. Listar y contar los profesores de tiempo completo y cátedra\n"
                    + "6. Listar y contar los profesores ocasionales y cátedra\n"
                    + "7. Listar y contar profesores que tengan las 3 condiciones (cátedra, completo y ocasional)\n"
                    + "8. Cantidad de hombres y mujeres por cada tipo de contrato\n"
                    + "9. Listar y contar profesores por facultad\n"
                    + "10. Ingresar un nuevo profesor\n"
                    + "0. Salir\n"
                    + "Seleccione una opción:"
            );

            switch (opcion) {
                case "1":
                    //listarYContarPorTipo(profesoresTiempoCompleto, "Tiempo Completo");
                    break;
                case "2":
                    //listarYContarPorTipo(profesoresCatedra, "Cátedra");
                    break;
                case "3":
                    //listarYContarPorTipo(profesoresOcasional, "Ocasional");
                    break;
                case "4":
                    //listarYContarTodos(profesoresTiempoCompleto, profesoresCatedra, profesoresOcasional);
                    break;
                case "5":
                    //listarYContarPorTipo(profesores, "Tiempo Completo y Cátedra");
                    break;
                case "6":
                    //listarYContarPorTipo(profesores, "Ocasional y Cátedra");
                    break;
                case "7":
                    //listarYContarPorTipo(profesores, "Tiempo Completo, Cátedra y Ocasional");
                    break;
                case "8":
                    //contarPorGeneroYTipo(profesores);
                    break;
                case "9":
                    //listarYContarPorFacultad(profesores);
                    break;
                case "10":
                    ingresarNuevoProfesor();
                    break;
                case "0":
                    System.exit(0);
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida. Intente nuevamente.");
            }

        }//CIERRE DEL MENU

    }//CIERRE DEL METODO

    public void ingresarNuevoProfesor() throws ParseException {
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String cedula = JOptionPane.showInputDialog("Ingrese la cédula del profesor:");
        String nombreCompleto = JOptionPane.showInputDialog("Ingrese el nombre completo del profesor:");
        String sexo = (String) JOptionPane.showInputDialog(null, "Seleccione el sexo del profesor:",
                "Sexo", JOptionPane.QUESTION_MESSAGE, null, new String[]{"Masculino", "Femenino"}, "Masculino");
        String facultad = (String) JOptionPane.showInputDialog(null, "Seleccione la facultad del profesor:",
                "Facultad", JOptionPane.QUESTION_MESSAGE, null, new String[]{"Ingeniería", "Deportes", "Comunicación", "Administración", "Idiomas", "Ciencias Básicas"}, "Ingeniería");
        String titulo = (String) JOptionPane.showInputDialog(null, "Seleccione el título del profesor:",
                "Título", JOptionPane.QUESTION_MESSAGE, null, new String[]{"Pregrado", "Especialista", "Maestría", "Doctorado"}, "Pregrado");
        int asignaturasDictadas = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad de asignaturas que dicta:"));
        int horasDictadasPorSemana = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad de horas dictadas por semana:"));
        LocalDate fechaNacimiento = LocalDate.parse(JOptionPane.showInputDialog("Ingrese la fecha de nacimiento del profesor (formato: dd/MM/yyyy):"), dateFormatter);
        String tipoProfesor = (String) JOptionPane.showInputDialog(null, "Seleccione el tipo de contrato:",
                "Contrato", JOptionPane.QUESTION_MESSAGE, null, new String[]{"Tiempo Completo", "Cátedra", "Ocasional", "Tiempo Completo-Cátedra", "Ocasional-Cátedra", "Tiempo Completo-Ocasional"}, "Tiempo Completo");

        Profesor nuevoProfesor = new Profesor(cedula, nombreCompleto, sexo, facultad, titulo,
                asignaturasDictadas, horasDictadasPorSemana, fechaNacimiento, tipoProfesor);

        if (nuevoProfesor.getTipoProfesor().contains("Tiempo Completo")) {
            profesoresTiempoCompleto.add(nuevoProfesor);
        }
        if (nuevoProfesor.getTipoProfesor().contains("Cátedra")) {
            profesoresCatedra.add(nuevoProfesor);
        }
        if (nuevoProfesor.getTipoProfesor().contains("Ocasional")) {
            profesoresOcasional.add(nuevoProfesor);
        }

        // Actualizar el archivo plano con el nuevo profesor
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("./datos/profesores.txt", true));
            String fechaNacimientoFormatted = fechaNacimiento.format(dateFormatter); // Formatea la fecha
            writer.print(nuevoProfesor.getCedula() + ", " + nuevoProfesor.getNombreCompleto() + ", " + nuevoProfesor.getSexo() + ", "
                    + nuevoProfesor.getFacultad() + ", " + nuevoProfesor.getTitulo() + ", " + nuevoProfesor.getAsignaturasDictadas() + ", "
                    + nuevoProfesor.getHorasDictadasPorSemana() + ", " + fechaNacimientoFormatted + ", " + nuevoProfesor.getTipoProfesor());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar el nuevo profesor en el archivo.");
        }

        JOptionPane.showMessageDialog(null, "Nuevo profesor ingresado con éxito.");
        
    }//CIERRE DEL METODO

}//CIERRE DE LA CLASE
