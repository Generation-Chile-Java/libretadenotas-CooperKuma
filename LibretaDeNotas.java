import java.util.*;

public class LibretaDeNotas {
    private static final double NOTA_APROBATORIA = 4.0; // Nota mínima para aprobar
    private Map<String, List<Double>> estudiantes;
    private Scanner scanner;

    // Constructor
    public LibretaDeNotas() {
        estudiantes = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    // Método para ingresar los datos de los estudiantes y sus notas
    public void ingresarDatos() {
        // Solicitar la cantidad de alumnos
        System.out.print("Ingrese la cantidad de alumnos: ");
        int cantidadAlumnos;
        try {
            cantidadAlumnos = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Debe ingresar un número.");
            return;
        }

        // Validación de cantidad de alumnos
        if (cantidadAlumnos <= 0) {
            System.out.println("La cantidad de alumnos debe ser mayor que 0.");
            return;
        }

        // Ingresar los datos de los estudiantes
        for (int i = 0; i < cantidadAlumnos; i++) {
            System.out.println("Ingrese el nombre del alumno " + (i + 1) + ": ");
            String nombre = scanner.nextLine();

            // Solicitar la cantidad de notas por alumno
            System.out.println("Ingrese la cantidad de notas para " + nombre + ": ");
            int cantidadNotas;
            try {
                cantidadNotas = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Debe ingresar un número.");
                i--; // Repetir el mismo alumno
                continue;
            }

            // Validación de cantidad de notas
            if (cantidadNotas <= 0) {
                System.out.println("La cantidad de notas debe ser mayor que 0.");
                i--; // Repetir el mismo alumno
                continue;
            }

            List<Double> notas = new ArrayList<>();

            // Ingresar las notas
            for (int j = 0; j < cantidadNotas; j++) {
                double nota;
                boolean notaValida = false;

                do {
                    System.out.print("Ingrese la nota " + (j + 1) + " para " + nombre + " (entre 1 y 7): ");
                    try {
                        nota = Double.parseDouble(scanner.nextLine());
                        if (nota >= 1 && nota <= 7) {
                            notaValida = true;
                            notas.add(nota);
                        } else {
                            System.out.println("La nota debe estar entre 1 y 7. Intente nuevamente.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida. Debe ingresar un número.");
                    }
                } while (!notaValida);
            }

            estudiantes.put(nombre, notas);
        }
    }

    // Método para calcular el promedio de las notas de un estudiante
    private double calcularPromedio(List<Double> notas) {
        if (notas == null || notas.isEmpty()) {
            return 0;
        }

        double suma = 0;
        for (double nota : notas) {
            suma += nota;
        }
        return suma / notas.size();
    }

    // Método para mostrar el menú
    public void mostrarMenu() {
        int opcion;

        do {
            System.out.println("\nMenú de Opciones:");
            System.out.println("1. Mostrar el Promedio de Notas por Estudiante.");
            System.out.println("2. Mostrar si la Nota es Aprobatoria o Reprobatoria por Estudiante.");
            System.out.println("3. Mostrar si la Nota está por Sobre o por Debajo del Promedio del Curso por Estudiante.");
            System.out.println("0. Salir.");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Debe ingresar un número.");
                opcion = -1; // Valor inválido para continuar el bucle
                continue;
            }

            switch (opcion) {
                case 1:
                    mostrarPromedio();
                    break;
                case 2:
                    verificarAprobacion();
                    break;
                case 3:
                    verificarNotaPromedioCurso();
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }

    // Método para mostrar el promedio de notas de cada estudiante
    private void mostrarPromedio() {
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }

        for (Map.Entry<String, List<Double>> entry : estudiantes.entrySet()) {
            String nombre = entry.getKey();
            List<Double> notas = entry.getValue();
            double promedio = calcularPromedio(notas);
            System.out.println(nombre + " - Promedio: " + promedio);
        }
    }

    // Método para verificar si una nota es aprobatoria o reprobatoria
    private void verificarAprobacion() {
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }

        System.out.print("Ingrese el nombre del estudiante: ");
        String nombre = scanner.nextLine();

        if (!estudiantes.containsKey(nombre)) {
            System.out.println("Estudiante no encontrado.");
            return;
        }

        System.out.print("Ingrese la nota para verificar: ");
        double nota;
        try {
            nota = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Debe ingresar un número.");
            return;
        }

        if (nota >= NOTA_APROBATORIA) {
            System.out.println("La nota " + nota + " es Aprobatoria.");
        } else {
            System.out.println("La nota " + nota + " es Reprobatoria.");
        }
    }

    // Método para verificar si la nota está por sobre o por debajo del promedio del curso
    private void verificarNotaPromedioCurso() {
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }

        System.out.print("Ingrese el nombre del estudiante: ");
        String nombre = scanner.nextLine();

        if (!estudiantes.containsKey(nombre)) {
            System.out.println("Estudiante no encontrado.");
            return;
        }

        double promedioCurso = calcularPromedioDelCurso();
        System.out.println("El promedio del curso es: " + promedioCurso);

        System.out.print("Ingrese la nota para comparar: ");
        double nota;
        try {
            nota = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Debe ingresar un número.");
            return;
        }

        if (nota > promedioCurso) {
            System.out.println("La nota " + nota + " está por encima del promedio del curso (" + promedioCurso + ").");
        } else if (nota < promedioCurso) {
            System.out.println("La nota " + nota + " está por debajo del promedio del curso (" + promedioCurso + ").");
        } else {
            System.out.println("La nota " + nota + " es igual al promedio del curso (" + promedioCurso + ").");
        }
    }

    private double calcularPromedioDelCurso() {
        double sumaTotal = 0;
        int cantidadNotas = 0;

        for (List<Double> notas : estudiantes.values()) {
            for (double nota : notas) {
                sumaTotal += nota;
                cantidadNotas++;
            }
        }

        return cantidadNotas > 0 ? sumaTotal / cantidadNotas : 0;
    }

    // Método para cerrar recursos
    public void cerrarRecursos() {
        if (scanner != null) {
            scanner.close();
        }
    }

    public static void main(String[] args) {
        LibretaDeNotas libreta = new LibretaDeNotas();

        try {
            libreta.ingresarDatos();
            libreta.mostrarMenu();
        } finally {
            libreta.cerrarRecursos();
        }
    }
}