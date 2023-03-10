package Main;

import ClasesTablas.Clase;
import ClasesTablas.Practico;
import ClasesTablas.Teorico;
import ClasesTablas.Vehiculo;
import ClasesTablas.*;
import bbdd.BD_Autoescuela;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static String usuario;
    static LocalDateTime fechaSesion;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ErrorBaseDatos {
        BD_Autoescuela autoescuela = new BD_Autoescuela("o");

        boolean val = false;
        int filas = 0;

        String dni, nombre, apellidos, fecha_Nac, telefono, correo, clave;
        LocalDate fecha;

        String calle;
        int numero = 0, planta = 0, codigopostal = 0;
        char letra;

        String matricula, marca, modelo;
        int pasada;
        boolean itv;

        String dni_A, dni_T;

        int fallos = 0;
        boolean aprobado;

        int leves = 0, deficientes = 0, graves = 0;

        int ideClase = 0, idPractico = 0, ideTeorico = 0;
        int opcion = 0, opcion1 = 0, opcionbuscar = 0, opcionborrar = 0, opcioninsertar = 0;

        int countFallos = 0; 

        do {

            do {

                System.out.println("Introduzca el dni: (0 para cerrar el programa) ");
                usuario = sc.nextLine();

                if (usuario.equalsIgnoreCase("0")) {
                    System.out.println("[Cerrando el programa...]");
                    System.exit(0);
                }

                if (!validarDni(usuario)) {
                    System.out.println("El DNI introducido no es v??lido. ");
                }

            } while (!validarDni(usuario));

            System.out.println("Introduzca la clave: ");
            clave = sc.nextLine();

            int admin = autoescuela.iniciarSesion(usuario, clave);

            if (admin == 1) {
                fechaSesion = LocalDateTime.now();
                do {
                    menu();
                    opcion1 = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");

                    if (opcion1 < 1 || opcion1 > 4) {
                        System.out.println("La opci??n indicada no es v??lida. ");
                        continue;
                    }

                    if (opcion1 == 1) {
                        menuAdminBuscar();
                        opcionbuscar = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");

                        switch (opcionbuscar) {
                            case 1:
                                sc.nextLine();
                                do {
                                    System.out.println("Introduzca el DNI del alumno: ");
                                    dni = sc.nextLine();
                                    if (!validarDni(dni)) {
                                        System.out.println("El DNI introducido no es v??lido. ");
                                    }
                                } while (!validarDni(dni));

                                Alumno alumno = autoescuela.buscarAlumno(dni);

                                if (alumno == null) {
                                    System.out.println("No existe el alumno");
                                    System.out.println(alumno);
                                } else {
                                    System.out.println(alumno);
                                }

                                break;

                            case 2:
                                sc.nextLine();
                                do {
                                    System.out.println("Introduzca el DNI del trabajador: ");
                                    dni = sc.nextLine();
                                    if (!validarDni(dni)) {
                                        System.out.println("El DNI introducido no es v??lido. ");
                                    }
                                } while (!validarDni(dni));

                                Trabajador trabajador = autoescuela.buscarTrabajador(dni);

                                if (trabajador == null) {
                                    System.out.println("No existe el trabajador");
                                } else {
                                    System.out.println(trabajador);
                                }

                                break;

                            case 3:
                                sc.nextLine();
                                System.out.println("Introduce la matricula del vehiculo");
                                matricula = sc.nextLine();

                                Vehiculo vehiculo = autoescuela.buscarVehiculo(matricula);

                                if (vehiculo == null) {
                                    System.out.println("No existe el vehiculo");
                                } else {
                                    System.out.println(vehiculo);
                                }

                                break;

                            case 4:
                                System.out.println("Introduce el ide de la clase");
                                ideClase = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");

                                Clase clase = autoescuela.buscarClase(ideClase);

                                if (clase == null) {
                                    System.out.println("No existe la clase");
                                } else {
                                    System.out.println(clase);
                                }

                                break;

                            case 5:
                                System.out.println("Introduce el ide del examen teorico");
                                ideTeorico = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");

                                Teorico teorico = autoescuela.buscarExamenTeorico(ideTeorico);

                                if (teorico == null) {
                                    System.out.println("No hay ning??n examen con ese iddentificador");
                                } else {
                                    System.out.println(teorico);
                                }

                                break;

                            case 6:
                                System.out.println("Introduce el ide del examen practico");
                                idPractico = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");

                                Practico practico = autoescuela.buscarExamenPractico(idPractico);

                                if (practico == null) {
                                    System.out.println("No hay ning??n examen con ese iddentificador");
                                } else {
                                    System.out.println(practico);
                                }

                                break;

                            default:

                                System.out.println("La opci??n indicada no es v??lida. ");

                        }
                    }

                    if (opcion1 == 2) {
                        menuAdminInsertar();
                        opcioninsertar = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");

                        switch (opcioninsertar) {
                            case 1:
                                sc.nextLine();
                                do {
                                    System.out.println("Introduce dni:");
                                    dni = sc.nextLine();
                                    val = validarDni(dni);
                                    if (val == false) {
                                        System.out.println("Error... Dni inv??lido");
                                    }
                                } while (val == false);

                                Alumno alumno = autoescuela.buscarAlumno(dni);

                                if (alumno == null) {
                                    do {
                                        System.out.println("Introduce nombre:");
                                        nombre = sc.nextLine();
                                        val = ValidarNombre(nombre);
                                        if (val == false) {
                                            System.out.println("Error... Nombre inv??lido");
                                        }
                                    } while (val == false);

                                    do {
                                        System.out.println("Introduce apellidos:");
                                        apellidos = sc.nextLine();
                                        val = ValidarApellidos(apellidos);
                                        if (val == false) {
                                            System.out.println("Error... Apellidos inv??lidos");
                                        }
                                    } while (val == false);

                                    do {
                                        System.out.println("Introduce fecha de nacimiento en formato AAAA-MM-DD:");
                                        fecha_Nac = sc.nextLine();
                                        fecha = validarFecha(fecha_Nac);
                                        if (fecha == null) {
                                            System.out.println("Error... Fecha inv??lida");
                                        }
                                    } while (fecha == null);

                                    if (mayor18(fecha) == true) {
                                        do {
                                            System.out.println("Introduce tel??fono:");
                                            telefono = sc.nextLine();
                                            val = validarTelefono(telefono);
                                            if (val == false) {
                                                System.out.println("Error... Tel??fono inv??lido");
                                            }
                                        } while (val == false);

                                        do {
                                            System.out.println("Introduce correo");
                                            correo = sc.nextLine();
                                            val = validarCorreo(correo);
                                            if (val == false) {
                                                System.out.println("Error... Correo inv??lido");
                                            }
                                        } while (val == false);

                                        System.out.println("Introduce calle:");
                                        calle = sc.nextLine();

                                        do {
                                            System.out.println("Introduce numero:");
                                            numero = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");
                                            val = validarNumero(numero);
                                            if (val == false) {
                                                System.out.println("Error... N??mero inv??lido");
                                            }
                                        } while (val == false);

                                        do {
                                            System.out.println("Introduce planta:");
                                            planta = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");
                                            val = ValidarPlanta(planta);
                                            if (val == false) {
                                                System.out.println("Error... Planta inv??lida");
                                            }
                                        } while (val == false);

                                        sc.nextLine();

                                        do {
                                            System.out.println("Introduce letra:");
                                            letra = Character.toUpperCase(sc.nextLine().charAt(0));
                                            val = ValidarLetra(letra);
                                            if (val == false) {
                                                System.out.println("Error... letra inv??lida");
                                            }
                                        } while (val == false);

                                        do {
                                            System.out.println("Introduce codigo postal:");
                                            codigopostal = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");
                                            val = validarCodigoPostal(codigopostal);
                                            if (val == false) {
                                                System.out.println("Error... C??digo Postal inv??lido");
                                            }
                                        } while (val == false);

                                        Vector<Examen> examenes = null;

                                        Direccion d1 = new Direccion(calle, numero, planta, letra, codigopostal);
                                        Alumno a1 = new Alumno(dni, nombre, apellidos, fecha, telefono, correo, LocalDate.now(), d1, examenes);

                                        try {
                                            filas = autoescuela.insertar_Alumno(a1);
                                            switch (filas) {
                                                case 1:
                                                    System.out.println("\nAlumno a??adido");
                                                    break;
                                                case 0:
                                                    System.out.println("\nNo a??adido, contacte con sistemas");
                                                    break;
                                            }
                                        } catch (ErrorBaseDatos e) {
                                            System.out.println("Contacte con sistemas:" + e.getMessage());
                                        }
                                    } else {
                                        System.out.println("No es mayor de edad");
                                    }
                                } else {
                                    System.out.println("Ya existe un alumno con ese dni");
                                }
                                break;

                            case 2:
                                sc.nextLine();
                                do {
                                    System.out.println("Introduce dni:");
                                    dni = sc.nextLine();
                                    val = validarDni(dni);
                                    if (val == false) {
                                        System.out.println("Error... Dni inv??lido");
                                    }
                                } while (val == false);

                                do {
                                    System.out.println("Introduce nombre:");
                                    nombre = sc.nextLine();
                                    val = ValidarNombre(nombre);
                                    if (val == false) {
                                        System.out.println("Error... Nombre inv??lido");
                                    }
                                } while (val == false);

                                do {
                                    System.out.println("Introduce apellidos:");
                                    apellidos = sc.nextLine();
                                    val = ValidarApellidos(apellidos);
                                    if (val == false) {
                                        System.out.println("Error... Apellidos inv??lidos");
                                    }
                                } while (val == false);

                                do {
                                    System.out.println("Introduce fecha de nacimiento en formato AAAA-MM-DD:");
                                    fecha_Nac = sc.nextLine();
                                    fecha = validarFecha(fecha_Nac);
                                    if (fecha == null) {
                                        System.out.println("Error... Fecha inv??lida");
                                    }
                                } while (fecha == null);

                                do {
                                    System.out.println("Introduce tel??fono:");
                                    telefono = sc.nextLine();
                                    val = validarTelefono(telefono);
                                    if (val == false) {
                                        System.out.println("Error... Tel??fono inv??lido");
                                    }
                                } while (val == false);

                                do {
                                    System.out.println("Introduce correo");
                                    correo = sc.nextLine();
                                    val = validarCorreo(correo);
                                    if (val == false) {
                                        System.out.println("Error... Correo inv??lido");
                                    }
                                } while (val == false);

                                System.out.println("Introduce calle:");
                                calle = sc.nextLine();

                                do {
                                    System.out.println("Introduce numero:");
                                    numero = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");
                                    val = validarNumero(numero);
                                    if (val == false) {
                                        System.out.println("Error... N??mero inv??lido");
                                    }
                                } while (val == false);

                                do {
                                    System.out.println("Introduce planta:");
                                    planta = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");
                                    val = ValidarPlanta(planta);
                                    if (val == false) {
                                        System.out.println("Error... Planta inv??lida");
                                    }
                                } while (val == false);

                                sc.nextLine();

                                do {
                                    System.out.println("Introduce letra:");
                                    letra = Character.toUpperCase(sc.nextLine().charAt(0));
                                    val = ValidarLetra(letra);
                                    if (val == false) {
                                        System.out.println("Error... letra inv??lida");
                                    }
                                } while (val == false);

                                do {
                                    System.out.println("Introduce codigo postal:");
                                    codigopostal = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");
                                    val = validarCodigoPostal(codigopostal);
                                    if (val == false) {
                                        System.out.println("Error... C??digo Postal inv??lido");
                                    }
                                } while (val == false);

                                boolean Administrador = false;

                                sc.nextLine();

                                do {
                                    System.out.println("Introduce contrase??a:");
                                    clave = sc.nextLine();
                                    val = ValidarContrase??a(clave);
                                    if (val == false) {
                                        System.out.println("Error... Contrase??a inv??lida");
                                    }
                                } while (val == false);

                                Direccion d2 = new Direccion(calle, numero, planta, letra, codigopostal);
                                Trabajador t1 = new Trabajador(dni, nombre, apellidos, fecha, telefono, correo, LocalDate.now(), d2, Administrador, clave);

                                try {
                                    filas = autoescuela.insertar_Trabajador(t1);
                                    switch (filas) {
                                        case 1:
                                            System.out.println("\nTrabajador a??adido");
                                            break;
                                        case 0:
                                            System.out.println("\nNo a??adido, contacte con sistemas");
                                            break;
                                    }
                                } catch (ErrorBaseDatos e) {
                                    System.out.println("Contacte con sistemas:" + e.getMessage());
                                }
                                break;

                            case 3:
                                sc.nextLine();
                                do {
                                    System.out.println("Introduce matr??cula:");
                                    matricula = sc.nextLine();
                                    val = ValidarMatricula(matricula);
                                    if (val == false) {
                                        System.out.println("Error... Matr??cula inv??lida");
                                    }
                                } while (val == false);

                                Vehiculo vehiculo = autoescuela.buscarVehiculo(matricula);

                                if (vehiculo == null) {
                                    do {
                                        System.out.println("Introduce Marca:");
                                        marca = sc.nextLine();
                                        val = ValidarMarca(marca);
                                        if (val == false) {
                                            System.out.println("Error... Marca inv??lida");
                                        }
                                    } while (val == false);

                                    System.out.println("Introduce modelo:");
                                    modelo = sc.nextLine();

                                    do {
                                        System.out.println("?? Tiene la ITV pasada \n1- SI \n2- NO?");
                                        pasada = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");
                                        if (pasada == 1) {
                                            itv = true;
                                        } else {
                                            itv = false;
                                        }
                                        if (pasada < 1 || pasada > 2) {
                                            System.out.println("Opci??n incorrecta...");
                                        }
                                    } while (pasada < 1 || pasada > 2);

                                    LocalDate fecha_M = LocalDate.now();

                                    Vehiculo v1 = new Vehiculo(matricula, marca, modelo, fecha_M, itv);

                                    try {
                                        filas = autoescuela.insertar_Vehiculo(v1);
                                        switch (filas) {
                                            case 1:
                                                System.out.println("\nVeh??culo a??adido");
                                                break;
                                            case 0:
                                                System.out.println("\nNo a??adido, contacte con sistemas");
                                                break;
                                        }
                                    } catch (ErrorBaseDatos e) {
                                        System.out.println("Contacte con sistemas:" + e.getMessage());
                                    }
                                } else {
                                    System.out.println("Ya existe un vehiculo con esa matricula");
                                }

                                break;

                            case 4:
                                sc.nextLine();
                                do {
                                    System.out.println("Introduce dni del alumno que va a realizar la clase:");
                                    dni = sc.nextLine();
                                    val = validarDni(dni);
                                    if (val == false) {
                                        System.out.println("Error... Dni inv??lido");
                                    }
                                } while (val == false);

                                Alumno alumno1 = autoescuela.buscarAlumno(dni);

                                if (alumno1 == null) {
                                    System.out.println("No existe el alumno");
                                } else {
                                    dni_A = dni;

                                    if (alumno1.realizarPractico() == true) {
                                        do {
                                            System.out.println("Introduce dni del trabajador que va a impartir la clase:");
                                            dni = sc.nextLine();
                                            val = validarDni(dni);
                                            if (val == false) {
                                                System.out.println("Error... Dni inv??lido");
                                            }
                                        } while (val == false);

                                        Trabajador trabajador = autoescuela.buscarTrabajador(dni);

                                        if (trabajador == null) {
                                            System.out.println("No existe el trabajador");
                                        } else {
                                            dni_T = dni;

                                            do {
                                                System.out.println("Introduce matr??cula del veh??culo que se va a utilizar:");
                                                matricula = sc.nextLine();
                                                val = ValidarMatricula(matricula);
                                                if (val == false) {
                                                    System.out.println("Error... Matr??cula inv??lida");
                                                }
                                            } while (val == false);

                                            Vehiculo vehicul = autoescuela.buscarVehiculo(matricula);

                                            if (vehicul == null) {
                                                System.out.println("No existe el vehiculo");
                                            } else {
                                                Clase clase = new Clase(0, dni_A, dni_T, LocalDate.now(), matricula);

                                                try {
                                                    filas = autoescuela.insertar_Clase(clase);
                                                    switch (filas) {
                                                        case 1:
                                                            System.out.println("\nClase a??adida");
                                                            break;
                                                        case 0:
                                                            System.out.println("\nNo a??adido, contacte con sistemas");
                                                            break;
                                                    }
                                                } catch (ErrorBaseDatos e) {
                                                    System.out.println("Contacte con sistemas:" + e.getMessage());
                                                }
                                            }
                                        }

                                    } else {
                                        System.out.println("El alumno no ha aprobado el examen teorico");
                                    }
                                }
                                break;

                            case 5:
                                sc.nextLine();
                                do {
                                    System.out.println("Introduce dni del alumno que va a realizar el examen:");
                                    dni = sc.nextLine();
                                    val = validarDni(dni);
                                    if (val == false) {
                                        System.out.println("Error... Dni inv??lido");
                                    }
                                } while (val == false);

                                Alumno alumno2 = autoescuela.buscarAlumno(dni);

                                if (alumno2 == null) {
                                    System.out.println("No existe el alumno");
                                } else {
                                    if (alumno2.realizarPractico() == false) {
                                        do {
                                            System.out.println("Anota cuantos fallos ha tenido el alumno: (hasta un m??ximo de 30)");
                                            fallos = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");
                                            if (fallos > 3) {
                                                aprobado = false;
                                            } else {
                                                aprobado = true;
                                            }
                                            val = Validar_puntuaci??nTeorico(fallos);
                                            if (val == false) {
                                                System.out.println("Error... puntuaci??n inv??lida");
                                            }
                                        } while (val == false);

                                        Teorico teorico = new Teorico(0, LocalDate.now(), aprobado, fallos);

                                        try {
                                            filas = autoescuela.insertar_Examen_Teorico(teorico, dni);
                                            switch (filas) {
                                                case 1:
                                                    System.out.println("\nExamen Teorico a??adido");
                                                    break;
                                                case 0:
                                                    System.out.println("\nNo a??adido, contacte con sistemas");
                                                    break;
                                            }
                                        } catch (ErrorBaseDatos e) {
                                            System.out.println("Contacte con sistemas:" + e.getMessage());
                                        }
                                    } else {
                                        System.out.println("Ya ha aprobado un examen teorico");
                                    }
                                }
                                break;

                            case 6:
                                sc.nextLine();
                                do {
                                    System.out.println("Introduce dni del alumno que va a realizar el examen:");
                                    dni = sc.nextLine();
                                    val = validarDni(dni);
                                    if (val == false) {
                                        System.out.println("Error... Dni inv??lido");
                                    }
                                } while (val == false);

                                Alumno alumno3 = autoescuela.buscarAlumno(dni);

                                if (alumno3 == null) {
                                    System.out.println("No existe el alumno");
                                } else {
                                    if (alumno3.practicoRealizado() == true) {
                                        System.out.println("El alumno ya ha aprobado el examen practico");
                                    } else {
                                        if (alumno3.realizarPractico() == false) {
                                            System.out.println("No ha aprobado el examen teorico");
                                        } else {
                                            if (autoescuela.clasesRealizadas(dni) == false) {
                                                System.out.println("No ha realizado suficiente clases para presentarse al examen");
                                            } else {
                                                dni_A = dni;
                                                do {
                                                    System.out.println("Introduce dni del trabajador que va a estar presente en el examen pr??ctico:");
                                                    dni_T = sc.nextLine();
                                                    val = validarDni(dni_T);
                                                    if (val == false) {
                                                        System.out.println("Error... Dni inv??lido");
                                                    }
                                                } while (val == false);

                                                Trabajador trabajador = autoescuela.buscarTrabajador(dni_T);

                                                if (trabajador == null) {
                                                    System.out.println("No existe ese trabajador");
                                                } else {
                                                    do {
                                                        System.out.println("Introduce matr??cula del veh??culo que se va a utilizar para el examen:");
                                                        matricula = sc.nextLine();
                                                        val = ValidarMatricula(matricula);
                                                        if (val == false) {
                                                            System.out.println("Error... Matr??cula inv??lida");
                                                        }
                                                    } while (val == false);

                                                    Vehiculo vehiculo2 = autoescuela.buscarVehiculo(matricula);

                                                    if (vehiculo2 == null) {
                                                        System.out.println("No existe ese vehiculo");
                                                    } else {
                                                        do {
                                                            System.out.println("Introduce n??mero de faltas leves que ha cometido el alumno:");
                                                            leves = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");
                                                            System.out.println("Introduce n??mero de faltas deficientes que ha cometido el alumno:");
                                                            deficientes = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");
                                                            System.out.println("Introduce n??mero de faltas graves que ha cometido el alumno:");
                                                            graves = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");
                                                            val = Validar_puntuacionPractico(leves, deficientes, graves);
                                                            if (val == false) {
                                                                System.out.println("Error... Puntuaci??n inv??lida");
                                                            }
                                                        } while (val == false);

                                                        aprobado = !(leves >= 10 || deficientes >= 2 || graves >= 1 || (leves >= 5 && deficientes >= 1));

                                                        Practico p1 = new Practico(0, LocalDate.now(), aprobado, leves, deficientes, graves, matricula, dni_T);

                                                        try {
                                                            filas = autoescuela.insertar_Examen_Practico(p1, dni_A);
                                                            switch (filas) {
                                                                case 1:
                                                                    System.out.println("\nExamen Pr??ctico a??adido");
                                                                    break;
                                                                case 0:
                                                                    System.out.println("\nNo a??adido, contacte con sistemas");
                                                                    break;
                                                            }
                                                        } catch (ErrorBaseDatos e) {
                                                            System.out.println("Contacte con sistemas:" + e.getMessage());
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    }
                                }
                                break;

                            default:

                                System.out.println("La opci??n indicada no es v??lida. ");

                        }
                    }

                    if (opcion1 == 3) {
                        menuAdminBorrar();
                        opcionborrar = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");

                        switch (opcionborrar) {
                            case 1:
                                sc.nextLine();
                                do {
                                    System.out.println("Introduzca el DNI del alumno: ");
                                    dni = sc.nextLine();
                                    if (!validarDni(dni)) {
                                        System.out.println("El DNI introducido no es v??lido. ");
                                    }
                                } while (!validarDni(dni));
                                try {
                                    if (verificarOperacion() == 1) {
                                        switch (autoescuela.borrarAlumno(dni)) {
                                            case 0:
                                                System.out.println("El DNI introducido no corresponde al de ning??n alumno. ");
                                                break;
                                            case 1:
                                                System.out.println("El alumno ha sido dado de baja. ");
                                                break;
                                        }
                                    }
                                } catch (ErrorBaseDatos e) {
                                    System.out.println(e.getMessage());
                                }

                                break;

                            case 2:
                                sc.nextLine();
                                do {
                                    System.out.println("Introduzca el DNI del trabajador: ");
                                    dni = sc.nextLine();
                                    if (!validarDni(dni)) {
                                        System.out.println("El DNI introducido no es v??lido. ");
                                    }
                                } while (!validarDni(dni));
                                if (dni.equalsIgnoreCase(usuario)) { 
                                    System.out.println("No es posible dar de baja al trabajador. ");
                                    return;
                                }
                                try {
                                    if (verificarOperacion() == 1) {
                                        if (autoescuela.buscarTrabajador(dni) == null) { 
                                            System.out.println("El DNI introducido no corresponde al de ning??n trabajador. ");
                                            return;
                                        }
                                        if (autoescuela.buscarTrabajador(dni).isAdministrador()) {
                                            int respuesta;
                                            do {
                                                System.out.println("El DNI introducido corresponde al de un Administrador, ??Est?? seguro que desea continuar con la operaci??n?"
                                                        + "\n\t1. S?? "
                                                        + "\n\t2. No ");
                                                respuesta = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");
                                                if (respuesta < 1 || respuesta > 2) {
                                                    System.out.println("La opci??n indicada no es v??lida. ");
                                                }
                                            } while (respuesta < 1 || respuesta > 2);
                                            if (respuesta == 2) {
                                                return;
                                            }
                                        }
                                        switch (autoescuela.borrarTrabajador(dni)) {
                                            case 0:
                                                System.out.println("El DNI introducido no corresponde al de ning??n trabajador. ");
                                                break;
                                            case 1:
                                                System.out.println("El trabajador ha sido dado de baja. ");
                                                break;
                                        }
                                    }
                                } catch (ErrorBaseDatos e) {
                                    System.out.println(e.getMessage());
                                }

                                break;

                            case 3:
                                sc.nextLine();
                                do {
                                    System.out.println("Introduzca la matr??cula del veh??culo: ");
                                    matricula = sc.nextLine();
                                    if (!ValidarMatricula(matricula)) {
                                        System.out.println("La matr??cula introducida no es v??lida. ");
                                    }
                                } while (!ValidarMatricula(matricula));
                                try {
                                    if (verificarOperacion() == 1) {
                                        switch (autoescuela.borrarVehiculo(matricula)) {
                                            case 0:
                                                System.out.println("La matr??cula introducida no corresponde a la de ning??n veh??culo. ");
                                                break;
                                            case 1:
                                                System.out.println("El veh??culo ha sido dado de baja. ");
                                                break;
                                        }
                                    }
                                } catch (ErrorBaseDatos e) {
                                    System.out.println(e.getMessage());
                                }

                                break;

                            case 4:
                                    try {
                                do {
                                    System.out.println("Introduzca el identificador de la clase: ");
                                    ideClase = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");
                                    if (ideClase < 1) {
                                        System.out.println("El identificador introducido no es v??lido. ");
                                    }
                                } while (ideClase < 1);
                                if (verificarOperacion() == 1) {
                                    switch (autoescuela.borrarClase(ideClase)) {
                                        case 0:
                                            System.out.println("El identificador introducido no corresponde al de ninguna clase. ");
                                            break;
                                        case 1:
                                            System.out.println("La clase ha sido dada de baja. ");
                                            break;
                                    }
                                }
                            } catch (ErrorBaseDatos e) {
                                System.out.println(e.getMessage());
                            }

                            break;

                            case 5:
                                    try {
                                do {
                                    System.out.println("Introduzca el identificador del examen te??rico: ");
                                    ideTeorico = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");
                                    if (ideTeorico < 1) {
                                        System.out.println("El identificador introducido no es v??lido. ");
                                    }
                                } while (ideTeorico < 1);
                                if (verificarOperacion() == 1) {
                                    switch (autoescuela.borrarTeorico(ideTeorico)) {
                                        case 0:
                                            System.out.println("El identificador introducido no corresponde al de ning??n examen te??rico. ");
                                            break;
                                        case 1:
                                            System.out.println("El examen pr??ctico ha sido dado de baja. ");
                                            break;
                                    }
                                }
                            } catch (ErrorBaseDatos e) {
                                System.out.println(e.getMessage());
                            }

                            break;

                            case 6:
                                    try {
                                do {
                                    System.out.println("Introduzca el identificador del examen pr??ctico: ");
                                    idPractico = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");
                                    if (idPractico < 1) {
                                        System.out.println("El identificador introducido no es v??lido. ");
                                    }
                                } while (idPractico < 1);
                                if (verificarOperacion() == 1) {
                                    switch (autoescuela.borrarPractico(idPractico)) {
                                        case 0:
                                            System.out.println("El identificador introducido no corresponde al de ning??n examen pr??ctico. ");
                                            break;
                                        case 1:
                                            System.out.println("El examen pr??ctico ha sido dado de baja. ");
                                            break;
                                    }
                                }
                            } catch (ErrorBaseDatos e) {
                                System.out.println(e.getMessage());
                            }

                            break;

                            default:

                                System.out.println("La opci??n indicada no es v??lida. ");

                        }
                    }

                    if (opcion1 == 4) {
                        logSesion(usuario, fechaSesion);
                        countFallos = 0;
                        sc.nextLine();
                    }

                } while (opcion1 != 4);

            } else if (admin == 2) {
                fechaSesion = LocalDateTime.now();
                do {
                    menu();
                    opcion1 = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");

                    if (opcion1 < 1 || opcion1 > 4) {
                        System.out.println("La opci??n indicada no es v??lida. ");
                        continue;
                    }

                    if (opcion1 == 1) {
                        menuProfesorBuscar();
                        opcionbuscar = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");

                        switch (opcionbuscar) {
                            case 1: 
                                sc.nextLine();
                                do {
                                    System.out.println("Introduzca el DNI del alumno: ");
                                    dni = sc.nextLine();
                                    if (!validarDni(dni)) {
                                        System.out.println("El DNI introducido no es v??lido. ");
                                    }
                                } while (!validarDni(dni));

                                Alumno alumno = autoescuela.buscarAlumno(dni);

                                if (alumno == null) {
                                    System.out.println("No existe el alumno");
                                    System.out.println(alumno);
                                } else {
                                    System.out.println(alumno);
                                }

                                break;

                            case 2: 

                                Trabajador trabajador = autoescuela.buscarTrabajador(usuario);
                                System.out.println(trabajador);

                                break;

                            case 3: // LISTAR LA INFORMACI??N DE UN VEH??CULO. (SE PASA LA MATR??CULA DEL VEH??CULO) 
                                sc.nextLine();
                                System.out.println("Introduce la matricula del vehiculo");
                                matricula = sc.nextLine();

                                Vehiculo vehiculo = autoescuela.buscarVehiculo(matricula);

                                if (vehiculo == null) {
                                    System.out.println("No existe el vehiculo");
                                } else {
                                    System.out.println(vehiculo);
                                }

                                break;

                            case 4: 

                                Vector<Clase> clases = autoescuela.buscarClaseProfesor(usuario);

                                if (clases == null || clases.isEmpty()) {
                                    System.out.println("No eres instructor de ninguna clase. ");
                                } else {
                                    for (Clase clase : clases) {
                                        System.out.println(clase.toString());
                                    }
                                }

                                break;

                            case 5: 

                                Vector<Practico> practicos = autoescuela.buscarExamenPracticoProfesor(usuario);

                                if (practicos == null || practicos.isEmpty()) {
                                    System.out.println("No eres examinador de ning??n examen pr??ctico. ");
                                } else {
                                    for (Practico practico : practicos) {
                                        System.out.println(practico.toString());
                                    }
                                }

                                break;

                            default:

                                System.out.println("La opci??n indicada no es v??lida. ");

                        }
                    }

                    if (opcion1 == 2) {
                        menuProfesorInsertar();
                        opcioninsertar = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");

                        switch (opcioninsertar) {
                            case 1:  
                                sc.nextLine();
                                do {
                                    System.out.println("Introduce dni del alumno que va a realizar la clase:");
                                    dni = sc.nextLine();
                                    val = validarDni(dni);
                                    if (val == false) {
                                        System.out.println("Error... Dni inv??lido");
                                    }
                                } while (val == false);

                                Alumno alumno1 = autoescuela.buscarAlumno(dni);

                                if (alumno1 == null) {
                                    System.out.println("No existe el alumno");
                                } else {
                                    dni_A = dni;

                                    if (alumno1.realizarPractico() == true) {

                                        do {
                                            System.out.println("Introduce matr??cula del veh??culo que se va a utilizar:");
                                            matricula = sc.nextLine();
                                            val = ValidarMatricula(matricula);
                                            if (val == false) {
                                                System.out.println("Error... Matr??cula inv??lida");
                                            }
                                        } while (val == false);

                                        Vehiculo vehicul = autoescuela.buscarVehiculo(matricula);

                                        if (vehicul == null) {
                                            System.out.println("No existe el vehiculo");
                                        } else {
                                            Clase clase = new Clase(0, dni_A, usuario, LocalDate.now(), matricula);

                                            try {
                                                filas = autoescuela.insertar_Clase(clase);
                                                switch (filas) {
                                                    case 1:
                                                        System.out.println("\nClase a??adida");
                                                        break;
                                                    case 0:
                                                        System.out.println("\nNo a??adido, contacte con sistemas");
                                                        break;
                                                }
                                            } catch (ErrorBaseDatos e) {
                                                System.out.println("Contacte con sistemas:" + e.getMessage());
                                            }
                                        }

                                    } else {
                                        System.out.println("El alumno no ha aprobado el examen teorico");
                                    }
                                }
                                break;

                            case 2: 
                                sc.nextLine();
                                do {
                                    System.out.println("Introduce dni del alumno que va a realizar el examen:");
                                    dni = sc.nextLine();
                                    val = validarDni(dni);
                                    if (val == false) {
                                        System.out.println("Error... Dni inv??lido");
                                    }
                                } while (val == false);

                                Alumno alumno3 = autoescuela.buscarAlumno(dni);

                                if (alumno3 == null) {
                                    System.out.println("No existe el alumno");
                                } else {
                                    if (alumno3.practicoRealizado() == true) {
                                        System.out.println("El alumno ya ha aprobado el examen practico");
                                    } else {
                                        if (alumno3.realizarPractico() == false) {
                                            System.out.println("No ha aprobado el examen teorico");
                                        } else {
                                            if (autoescuela.clasesRealizadas(dni) == false) {
                                                System.out.println("No ha realizado suficiente clases para presentarse al examen");
                                            } else {
                                                dni_A = dni;

                                                do {
                                                    System.out.println("Introduce matr??cula del veh??culo que se va a utilizar para el examen:");
                                                    matricula = sc.nextLine();
                                                    val = ValidarMatricula(matricula);
                                                    if (val == false) {
                                                        System.out.println("Error... Matr??cula inv??lida");
                                                    }
                                                } while (val == false);

                                                Vehiculo vehiculo2 = autoescuela.buscarVehiculo(matricula);

                                                if (vehiculo2 == null) {
                                                    System.out.println("No existe ese vehiculo");
                                                } else {
                                                    do {
                                                        System.out.println("Introduce n??mero de faltas leves que ha cometido el alumno:");
                                                        leves = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");
                                                        System.out.println("Introduce n??mero de faltas deficientes que ha cometido el alumno:");
                                                        deficientes = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");
                                                        System.out.println("Introduce n??mero de faltas graves que ha cometido el alumno:");
                                                        graves = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");
                                                        val = Validar_puntuacionPractico(leves, deficientes, graves);
                                                        if (val == false) {
                                                            System.out.println("Error... Puntuaci??n inv??lida");
                                                        }
                                                    } while (val == false);

                                                    aprobado = !(leves >= 10 || deficientes >= 2 || graves >= 1 || (leves >= 5 && deficientes >= 1));

                                                    Practico p1 = new Practico(0, LocalDate.now(), aprobado, leves, deficientes, graves, matricula, usuario);

                                                    try {
                                                        filas = autoescuela.insertar_Examen_Practico(p1, dni_A);
                                                        switch (filas) {
                                                            case 1:
                                                                System.out.println("\nExamen Pr??ctico a??adido");
                                                                break;
                                                            case 0:
                                                                System.out.println("\nNo a??adido, contacte con sistemas");
                                                                break;
                                                        }
                                                    } catch (ErrorBaseDatos e) {
                                                        System.out.println("Contacte con sistemas:" + e.getMessage());
                                                    }
                                                }
                                            }
                                        }

                                    }
                                }
                                break;

                            default:

                                System.out.println("La opci??n indicada no es v??lida. ");

                        }
                    }

                    if (opcion1 == 3) {
                        menuProfesorBorrar();
                        opcionborrar = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");

                        switch (opcionborrar) {
                            case 1: 
                                    try {
                                do {
                                    System.out.println("Introduzca el identificador de la clase: ");
                                    ideClase = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");
                                    if (ideClase < 1) {
                                        System.out.println("El identificador introducido no es v??lido. ");
                                    }
                                } while (ideClase < 1);
                                if (verificarOperacion() == 1) {
                                    switch (autoescuela.borrarClaseProfesor(ideClase, usuario)) {
                                        case 0:
                                            System.out.println("Se ha producido un error al intentar dar de baja la clase de la Base de Datos: "
                                                    + "\n\t1. El identificador introducido no corresponde al de ninguna clase. "
                                                    + "\n\t2. La clase indicada no corresponde al usuario que ha iniciado sesi??n. ");
                                            break;
                                        case 1:
                                            System.out.println("La clase ha sido dada de baja. ");
                                            break;
                                    }
                                }
                            } catch (ErrorBaseDatos e) {
                                System.out.println(e.getMessage());
                            }

                            break;

                            case 2: 
                                    try {
                                do {
                                    System.out.println("Introduzca el identificador del examen pr??ctico: ");
                                    idPractico = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");
                                    if (idPractico < 1) {
                                        System.out.println("El identificador introducido no es v??lido. ");
                                    }
                                } while (idPractico < 1);
                                if (verificarOperacion() == 1) {
                                    switch (autoescuela.borrarPracticoProfesor(idPractico, usuario)) {
                                        case 0:
                                            System.out.println("Se ha producido un error al intentar dar de baja el examen pr??ctico de la Base de Datos: "
                                                    + "\n\t1. El identificador introducido no corresponde al de ning??n examen pr??ctico. "
                                                    + "\n\t2. El examen pr??ctico indicado no corresponde al usuario que ha iniciado sesi??n. ");
                                            break;
                                        case 1:
                                            System.out.println("El examen pr??ctico ha sido dado de baja. ");
                                            break;
                                    }
                                }
                            } catch (ErrorBaseDatos e) {
                                System.out.println(e.getMessage());
                            }

                            break;

                            default:

                                System.out.println("La opci??n indicada no es v??lida. ");

                        }
                    }

                    if (opcion1 == 4) {
                        logSesion(usuario, fechaSesion);
                        countFallos = 0; 
                        sc.nextLine();
                    }

                } while (opcion1 != 4);

            } else {

                System.out.println("Las credenciales introducidas no son v??lidas. ");
                countFallos++;

                if (countFallos == 3) {
                    System.out.println("Has fallado demasiadas veces seguidas. [Cerrando el programa...] ");
                }

            }

        } while (countFallos < 3);

    }


    public static void logSesion(String usuario, LocalDateTime fechaSesion) {
        Charset charset = Charset.forName("UTF-8");
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get("loginSession.info"), charset, CREATE, WRITE, APPEND);
            writer.write("Usuario: " + usuario.toUpperCase() + "; Inicio de Sesi??n: " + fechaSesion.toString() + "; Cierre de Sesi??n: " + LocalDateTime.now().toString());
            writer.newLine();
            writer.close();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    public static void menu() {
        System.out.println("\n1. Buscar");
        System.out.println("2. Insertar");
        System.out.println("3. Borrar");
        System.out.println("4. Salir\n");
    }

    public static void menuAdminBuscar() {
        System.out.println("\n1. Buscar alumno");
        System.out.println("2. Buscar trabajador");
        System.out.println("3. Buscar vehiculo");
        System.out.println("4. Buscar clase");
        System.out.println("5. Buscar examen teorico");
        System.out.println("6. Buscar examen practico\n");
    }

    public static void menuAdminInsertar() {
        System.out.println("\n1. Insertar alumno");
        System.out.println("2. Insertar trabajador");
        System.out.println("3. Insertar vehiculo");
        System.out.println("4. Insertar clase");
        System.out.println("5. Insertar examen teorico");
        System.out.println("6. Insertar examen practico\n");
    }

    public static void menuAdminBorrar() {
        System.out.println("\n1. Borrar alumno");
        System.out.println("2. Borrar trabajador");
        System.out.println("3. Borrar vehiculo");
        System.out.println("4. Borrar clase");
        System.out.println("5. Borrar examen teorico");
        System.out.println("6. Borrar examen practico\n");
    }

    public static void menuProfesorBuscar() {
        System.out.println("\n1. Buscar alumno");
        System.out.println("2. Consultar mi informaci??n");
        System.out.println("3. Buscar vehiculo");
        System.out.println("4. Listar clases en las que aparezco como tutor");
        System.out.println("5. Listar ex??menes en los que aparezco como examinador\n");
    }

    public static void menuProfesorInsertar() {
        System.out.println("\n1. Insertar clase");
        System.out.println("2. Insertar examen practico\n");
    }

    public static void menuProfesorBorrar() {
        System.out.println("\n1. Borrar clase");
        System.out.println("2. Borrar examen practico\n");
    }

    public static boolean validarDni(String dni) {
        return dni.matches("[0-9]{8}[a-zA-Z]{1}");
    }

    public static boolean validarTelefono(String telefono) {
        return telefono.matches("[6|9]{1}[0-9]{8}");
    }

    public static LocalDate validarFecha(String fecha) {
        LocalDate f = null;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-LL-dd");
        do {
            try {
                f = LocalDate.parse(fecha, formato);
            } catch (DateTimeParseException e) {

            }
            break;
        } while (true);

        return f;
    }

    public static boolean ValidarMatricula(String matricula) {
        return matricula.matches("[0-9]{4}[a-zA-Z]{3}");
    }

    public static boolean ValidarNombre(String nombre) {
        char letra1 = nombre.charAt(0);
        char letra2 = nombre.toUpperCase().charAt(0);

        if (letra1 != letra2) {
            return false;
        }
        for (int i = 0; i < nombre.length(); i++) {
            if (Character.isDigit((nombre.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    public static boolean ValidarApellidos(String apellidos) {
        char letra1 = apellidos.charAt(0);
        char letra2 = apellidos.toUpperCase().charAt(0);

        if (letra1 != letra2) {
            return false;
        }
        for (int i = 0; i < apellidos.length(); i++) {
            if (Character.isDigit((apellidos.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    public static boolean validarCodigoPostal(int c) {
        return !(c < 10000 || c > 99999);
    }

    public static boolean validarNumero(int n) {
        return !(n < 0 || n > 100);
    }

    public static boolean ValidarPlanta(int p) {
        return !(p < 0 || p > 40);
    }

    public static boolean ValidarLetra(char letra) {
        return !(Character.isDigit(letra) && letra != '0');
    }

    public static boolean ValidarContrase??a(String contrase??a) {
        int cont = 0;
        for (int i = 0; i < contrase??a.length(); i++) {
            cont++;
        }
        return cont >= 8;
    }

    public static boolean Validar_puntuacionPractico(int leve, int deficiente, int grave) {
        return !(leve >= 11 || deficiente >= 3 || grave >= 2);
    }

    public static boolean Validar_puntuaci??nTeorico(int fallos) {
        return fallos <= 30;
    }

    public static boolean validarCorreo(String correo) {
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(correo);

        return mather.find() == true;
    }

    public static boolean ValidarMarca(String marca) {
        for (int i = 0; i < marca.length(); i++) {
            if (Character.isDigit((marca.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    public static int verificarOperacion() throws InputMismatchException {
        int respuesta;
        do {
            System.out.println("El funcionamiento de la Base de Datos puede verse afectado. "
                    + "\n??Est?? seguro que desea continuar con la operaci??n?"
                    + "\n\t1. S?? "
                    + "\n\t2. No ");
            respuesta = leerNumero("La entrada no corresponde con el tipo de dato requerido. Por favor, introduzca un n??mero. ");
            if (respuesta < 1 || respuesta > 2) {
                System.out.println("La opci??n indicada no es v??lida. ");
            }
        } while (respuesta < 1 || respuesta > 2);
        return respuesta;
    }

    public static boolean mayor18(LocalDate fecha) {
        if ((LocalDate.now().getYear() - fecha.getYear()) <= 18) {
            if ((LocalDate.now().getMonthValue() - fecha.getMonthValue()) > 0) {
                return true;
            } else {
                return (LocalDate.now().getDayOfMonth() - fecha.getDayOfMonth()) > 0;
            }
        } else {
            return true;
        }
    }

    public static int leerNumero(String mensaje) {
        while (true) {
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println(mensaje);
            }
        }
    }

}
