package vista;

import bean.Profesor;
import java.awt.Dimension;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import logica.Logica;

public class Menu {

    ArrayList<Profesor> profesoresTiempoCompleto = new ArrayList<>();
    ArrayList<Profesor> profesoresCatedra = new ArrayList<>();
    ArrayList<Profesor> profesoresOcasional = new ArrayList<>();

    public void menu() throws ParseException {

        Logica logica = new Logica();

        logica.cargarProfesoresEnArrayLists(profesoresTiempoCompleto, profesoresCatedra, profesoresOcasional);

        ArrayList<Profesor> profesores = new ArrayList<>();
        HashSet<Profesor> profesoresSet = new HashSet<>();
        profesoresSet.addAll(profesoresTiempoCompleto);
        profesoresSet.addAll(profesoresCatedra);
        profesoresSet.addAll(profesoresOcasional);
        profesores.addAll(profesoresSet);

        while (true) {

            String opcion = JOptionPane.showInputDialog(
                    "Menú de Profesores\n"
                    + "1. Listar y contar los profesores de tiempo completo solamente\n"
                    + "2. Listar y contar los profesores de cátedra solamente\n"
                    + "3. Listar y contar los profesores ocasionales solamente\n"
                    + "4. Listar y contar el total de profesores\n"
                    + "5. Listar y contar los profesores de tiempo completo y cátedra\n"
                    + "6. Listar y contar los profesores ocasionales y cátedra\n"
                    + "7. Listar y contar profesores que tengan las 3 condiciones (cátedra, completo y ocasional)\n"
                    + "8. Cantidad de hombres y mujeres por cada tipo de contrato\n"
                    + "9. Listar y contar profesores por facultad\n"
                    + "10. Listar y contar profesores de tiempo completo y ocasional\n"
                    + "11. Ingresar un nuevo profesor\n"
                    + "0. Salir\n"
                    + "Seleccione una opción:"
            );

            switch (opcion) {
                case "1":
                    ArrayList<Profesor> profesoresTiempoCompletoCopia = new ArrayList<>(profesoresTiempoCompleto);
                    profesoresTiempoCompletoCopia.removeAll(profesoresCatedra);
                    profesoresTiempoCompletoCopia.removeAll(profesoresOcasional);
                    listarArrayList(profesoresTiempoCompletoCopia);
                    String message1 = "\nNúmero de profesores de Tiempo Completo que no están en Cátedra ni en Ocasional: " + profesoresTiempoCompletoCopia.size();
                    JOptionPane.showMessageDialog(null, message1);
                    break;
                case "2":
                    ArrayList<Profesor> profesoresCatedraCopia = new ArrayList<>(profesoresCatedra);
                    profesoresCatedraCopia.removeAll(profesoresTiempoCompleto);
                    profesoresCatedraCopia.removeAll(profesoresOcasional);
                    listarArrayList(profesoresCatedraCopia);
                    String message2 = "\nNúmero de profesores de Cátedra que no están en Tiempo Completo ni en Ocasional: " + profesoresCatedraCopia.size();
                    JOptionPane.showMessageDialog(null, message2);
                    break;
                case "3":
                    ArrayList<Profesor> profesoresOcasionalCopia = new ArrayList<>(profesoresOcasional);
                    profesoresOcasionalCopia.removeAll(profesoresCatedra);
                    profesoresOcasionalCopia.removeAll(profesoresTiempoCompleto);
                    listarArrayList(profesoresOcasionalCopia);
                    String message3 = "\nNúmero de profesores de Ocasional que no están en Cátedra ni en Tiempo Completo: " + profesoresOcasionalCopia.size();
                    JOptionPane.showMessageDialog(null, message3);
                    break;
                case "4":
                    listarArrayList(profesores);
                    String message4 = "\nNúmero total de profesores: " + profesores.size();
                    JOptionPane.showMessageDialog(null, message4);
                    break;
                case "5":
                    profesoresTiempoCompleto.retainAll(profesoresCatedra);
                    listarArrayList(profesoresTiempoCompleto);
                    String message5 = "\nNúmero total de profesores de tiempo completo y a la vez de cátedra: " + profesoresTiempoCompleto.size();
                    JOptionPane.showMessageDialog(null, message5);
                    break;
                case "6":
                    profesoresOcasional.retainAll(profesoresCatedra);
                    listarArrayList(profesoresOcasional);
                    String message6 = "\nNúmero total de profesores ocasionales y a la vez de cátedra: " + profesoresOcasional.size();
                    JOptionPane.showMessageDialog(null, message6);
                    break;
                case "7":
                    profesoresTiempoCompleto.retainAll(profesoresCatedra);
                    profesoresTiempoCompleto.retainAll(profesoresOcasional);
                    listarArrayList(profesoresTiempoCompleto);
                    String message7 = "\nNúmero total de profesores que tienen los 3 tipos de contrato: " + profesoresTiempoCompleto.size();
                    JOptionPane.showMessageDialog(null, message7);
                    break;
                case "8":
                    CantidadSexos(profesoresTiempoCompleto, profesoresCatedra, profesoresOcasional);
                    break;
                case "9":
                    CantidadFacultad(profesores);
                    break;
                case "10":
                    profesoresTiempoCompleto.retainAll(profesoresOcasional);
                    listarArrayList(profesoresTiempoCompleto);
                    String message8 = "\nNúmero total de profesores de tiempo completo y ocasionales: " + profesoresTiempoCompleto.size();
                    JOptionPane.showMessageDialog(null, message8);
                    break;
                case "11":
                    Profesor profe = validarProfesor();
                    ingresarNuevoProfesor(profe);
                    break;
                case "0":
                    System.exit(0);
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida. Intente nuevamente.");
            }

        }//CIERRE DEL MENU

    }//CIERRE DEL METODO

    public void ingresarNuevoProfesor(Profesor nuevoProfesor) throws ParseException {

        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaNacimientoFormatted = nuevoProfesor.getFechaNacimiento().format(dateFormatter); // Formatea la fecha
            PrintWriter writer = new PrintWriter(new FileWriter("./datos/profesores.txt", true));
            writer.println(nuevoProfesor.getCedula() + ", " + nuevoProfesor.getNombreCompleto() + ", " + nuevoProfesor.getSexo() + ", "
                    + nuevoProfesor.getFacultad() + ", " + nuevoProfesor.getTitulo() + ", " + nuevoProfesor.getAsignaturasDictadas() + ", "
                    + nuevoProfesor.getHorasDictadasPorSemana() + ", " + fechaNacimientoFormatted + ", " + nuevoProfesor.getTipoProfesor());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar el nuevo profesor en el archivo.");
        }
        JOptionPane.showMessageDialog(null, "Nuevo profesor ingresado con éxito.");

    }//CIERRE DEL METODO

    public Profesor validarProfesor() throws ParseException {
        String cedula;
        String nombreCompleto;
        String sexo;
        String facultad;
        String titulo;
        int asignaturasDictadas;
        int horasDictadasPorSemana;
        String tipoProfesor;

        // Validacion cedula
        do {
            cedula = JOptionPane.showInputDialog("Ingrese la cédula del profesor:");
        } while (!cedula.matches("\\d{8,}"));

        // Validacion nombre
        do {
            nombreCompleto = JOptionPane.showInputDialog("Ingrese el nombre completo del profesor:");
        } while (!nombreCompleto.matches("^[a-zA-Z ]+$"));

        // Validacion sexo
        sexo = (String) JOptionPane.showInputDialog(null, "Seleccione el sexo del profesor:",
                "Sexo", JOptionPane.QUESTION_MESSAGE, null, new String[]{"Masculino", "Femenino"}, "Masculino");

        // Validacin facultad
        facultad = (String) JOptionPane.showInputDialog(null, "Seleccione la facultad del profesor:",
                "Facultad", JOptionPane.QUESTION_MESSAGE, null, new String[]{"Ingeniería", "Deportes", "Comunicación", "Administración", "Idiomas", "Ciencias Básicas"}, "Ingeniería");

        // Validacion titulo
        titulo = (String) JOptionPane.showInputDialog(null, "Seleccione el título del profesor:",
                "Título", JOptionPane.QUESTION_MESSAGE, null, new String[]{"Pregrado", "Especialista", "Maestría", "Doctorado"}, "Pregrado");

        // asignaturas dictadas
        do {
            String input = JOptionPane.showInputDialog("Ingrese la cantidad de asignaturas que dicta:");
            if (input.matches("^(?:[1-9]|10)$")) {
                asignaturasDictadas = Integer.parseInt(input);
                break;
            }
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido en el rango de 1 a 10.");
        } while (true);

        // horas dictadas por semana
        do {
            String input = JOptionPane.showInputDialog("Ingrese la cantidad de horas dictadas por semana:");
            if (input.matches("^(?:[1-9]|1[0-9]|20)$")) {
                horasDictadasPorSemana = Integer.parseInt(input);
                break;
            }
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido en el rango de 1 a 20.");
        } while (true);

        // fecha
        String fechaNacimientoStr;
        LocalDate fechaNacimiento = null;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        do {
            fechaNacimientoStr = JOptionPane.showInputDialog("Ingrese la fecha de nacimiento (formato: dd/MM/yyyy):");
            try {
                fechaNacimiento = LocalDate.parse(fechaNacimientoStr, dateFormatter);
            } catch (DateTimeParseException e) {
                fechaNacimiento = null;
                JOptionPane.showMessageDialog(null, "La fecha de nacimiento no es válida. Debe tener el formato dd/MM/yyyy.");
            }
        } while (fechaNacimiento == null);

        JOptionPane.showMessageDialog(null, "La fecha de nacimiento es válida: " + fechaNacimiento.format(dateFormatter));

        // tipo de profesor
        tipoProfesor = (String) JOptionPane.showInputDialog(null, "Seleccione el tipo de contrato:",
                "Contrato", JOptionPane.QUESTION_MESSAGE, null, new String[]{"Tiempo Completo", "Cátedra", "Ocasional", "Tiempo Completo-Cátedra", "Ocasional-Cátedra", "Tiempo Completo-Ocasional", "Tiempo Completo-Cátedra-Ocasional"}, "Tiempo Completo");

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

        return nuevoProfesor;

    }//CIERRE DEL METODO

    public void CantidadSexos(ArrayList<Profesor> profesoresTiempoCompleto, ArrayList<Profesor> profesoresCatedra, ArrayList<Profesor> profesoresOcasional) {

        Set<Profesor> hombresTiempoCompleto = new HashSet<>();
        Set<Profesor> mujeresTiempoCompleto = new HashSet<>();
        Set<Profesor> hombresCatedra = new HashSet<>();
        Set<Profesor> mujeresCatedra = new HashSet<>();
        Set<Profesor> hombresOcasional = new HashSet<>();
        Set<Profesor> mujeresOcasional = new HashSet<>();

        for (Profesor profesor : profesoresTiempoCompleto) {
            if (profesor.getSexo().equalsIgnoreCase("Masculino")) {
                hombresTiempoCompleto.add(profesor);
            } else if (profesor.getSexo().equalsIgnoreCase("Femenino")) {
                mujeresTiempoCompleto.add(profesor);
            }
        }

        for (Profesor profesor : profesoresCatedra) {
            if (profesor.getSexo().equalsIgnoreCase("Masculino")) {
                hombresCatedra.add(profesor);
            } else if (profesor.getSexo().equalsIgnoreCase("Femenino")) {
                mujeresCatedra.add(profesor);
            }
        }

        for (Profesor profesor : profesoresOcasional) {
            if (profesor.getSexo().equalsIgnoreCase("Masculino")) {
                hombresOcasional.add(profesor);
            } else if (profesor.getSexo().equalsIgnoreCase("Femenino")) {
                mujeresOcasional.add(profesor);
            }
        }
        mostrarResultados("Profesores de Tiempo Completo:", hombresTiempoCompleto, mujeresTiempoCompleto);
        mostrarResultados("Profesores de Cátedra:", hombresCatedra, mujeresCatedra);
        mostrarResultados("Profesores Ocasionales:", hombresOcasional, mujeresOcasional);

    }//CIERRE DEL METODO

    public void CantidadFacultad(ArrayList<Profesor> profesores) {
        
        Set<Profesor> ingenieria = new HashSet<>();
        Set<Profesor> deportes = new HashSet<>();
        Set<Profesor> comunicacion = new HashSet<>();
        Set<Profesor> administracion = new HashSet<>();
        Set<Profesor> idiomas = new HashSet<>();
        Set<Profesor> cienciasBasicas = new HashSet<>();

        for (Profesor profesor : profesores) {
            String facultad = profesor.getFacultad();
            if(facultad.equals("Ingeniería")) {
                ingenieria.add(profesor);
            } else if (facultad.equals("Deportes")) {
                deportes.add(profesor);
            } else if (facultad.equals("Comunicación")) {
                comunicacion.add(profesor);
            } else if (facultad.equals("Administración")) {
                administracion.add(profesor);
            } else if (facultad.equals("Idiomas")) {
                idiomas.add(profesor);
            } else if (facultad.equals("Ciencias Básicas")) {
                cienciasBasicas.add(profesor);
            }
        }

        listarHashSet("Ingeniería", ingenieria);
        listarHashSet("Deportes", deportes);
        listarHashSet("Comunicación", comunicacion);
        listarHashSet("Administración", administracion);
        listarHashSet("Idiomas", idiomas);
        listarHashSet("Ciencias Básicas", cienciasBasicas);
        
    }//CIERRE DEL METODO

    public void listarHashSet(String nombreFacultad, Set<Profesor> facultadSet) {
        
        StringBuilder message = new StringBuilder("Facultad: " + nombreFacultad + "\n\n");

        for (Profesor profesor : facultadSet) {
            message.append("Cédula: ").append(profesor.getCedula()).append("\n");
            message.append("Nombre: ").append(profesor.getNombreCompleto()).append("\n");
            message.append("\n");
        }

        message.append("Cantidad de Profesores en " + nombreFacultad + ": " + facultadSet.size());

        JOptionPane.showMessageDialog(null, message.toString());
        
    }//CIERRE DEL METODO

    private static void mostrarResultados(String titulo, Set<Profesor> hombres, Set<Profesor> mujeres) {

        StringBuilder mensaje = new StringBuilder(titulo + "\n");
        mensaje.append("Hombres: ").append(hombres.size()).append("\n");
        mensaje.append("Mujeres: ").append(mujeres.size());

        JOptionPane.showMessageDialog(null, mensaje.toString());

    }//CIERRE DEL METODO

    public void listarArrayList(ArrayList<Profesor> lista) {
        StringBuilder message = new StringBuilder("Lista de profesores:\n");

        for (Profesor profesor : lista) {
            message.append("Cédula: ").append(profesor.getCedula()).append("\n");
            message.append("Nombre: ").append(profesor.getNombreCompleto()).append("\n");
            message.append("Sexo: ").append(profesor.getSexo()).append("\n");
            message.append("Facultad: ").append(profesor.getFacultad()).append("\n");
            message.append("Título: ").append(profesor.getTitulo()).append("\n");
            message.append("Asignaturas Dictadas: ").append(profesor.getAsignaturasDictadas()).append("\n");
            message.append("Horas Dictadas Por Semana: ").append(profesor.getHorasDictadasPorSemana()).append("\n");
            message.append("Fecha de Nacimiento: ").append(profesor.getFechaNacimiento()).append("\n");
            message.append("Tipo de Profesor: ").append(profesor.getTipoProfesor()).append("\n\n");
        }

        JTextArea textArea = new JTextArea(message.toString());
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        scrollPane.setPreferredSize(new Dimension(400, 400));

        JOptionPane.showMessageDialog(null, scrollPane);

    }//CIERRE DEL METODO

}//CIERRE DE LA CLASE
