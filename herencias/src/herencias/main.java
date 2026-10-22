package herencias;

import java.util.Scanner;

public class main {

    private static final Scanner ns = new Scanner(System.in);
    private static final Banco banco = new Banco();

    public static void main(String[] args) {

        int opcion = -1;

        while (opcion != 8) {

            
            System.out.println("1. Crear cuenta");
            System.out.println("2. Depositar");
            System.out.println("3. Retirar");
            System.out.println("4. Transferir");
            System.out.println("5. Aplicar intereses");
            System.out.println("6. Ver reporte y saldo total");
            System.out.println("7. Ver historial");
            System.out.println("8. Salir");
            System.out.println("9. Girar cheque");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(ns.nextLine());

                switch (opcion) {

                    case 1:
                        crearCuenta();
                        break;

                    case 2:
                        depositar();
                        break;

                    case 3:
                        retirar();
                        break;

                    case 4:
                        transferir();
                        break;

                    case 5:
                        banco.aplicarInteresesGlobal();
                        System.out.println("Proceso de intereses finalizado.");
                        break;

                    case 6:
                        banco.generarReporte();
                        break;

                    case 7:
                        verHistorial();
                        break;

                    case 8:
                        System.out.println("Hasta pronto.");
                        break;

                    case 9:
                        girarCheque();
                        break;

                    default:
                        System.out.println("Seleccione una opción válida.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Debe introducir un número válido.");

            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void crearCuenta() {

        System.out.println("\nTIPO DE CUENTA");
        System.out.println("1. Ahorro");
        System.out.println("2. Cheques");
        System.out.println("3. Plazo fijo");

        int tipoSeleccionado = leerEntero("Seleccione el tipo: ");
        int numero = leerEntero("Número de cuenta: ");

        System.out.print("Nombre del cliente: ");
        String cliente = ns.nextLine();

        System.out.print("Moneda (por ejemplo Lps): ");
        String moneda = ns.nextLine();

        CuentaBancaria cuenta;

        switch (tipoSeleccionado) {

            case 1:
                cuenta = new CuentaAhorro(numero, cliente, moneda);
                break;

            case 2:
                double limite = leerDouble("Límite de sobregiro: ");
                cuenta = new CuentaCheques(numero, cliente, moneda, limite);
                break;

            case 3:
                CuentaPlazoFijo plazo = new CuentaPlazoFijo(numero, cliente, moneda);

                int anio = leerEntero("Año de vencimiento: ");
                int mes = leerEntero("Mes de vencimiento (1-12): ");
                int dia = leerEntero("Día de vencimiento: ");

                plazo.setPlazo(anio, mes, dia);
                cuenta = plazo;
                break;

            default:
                throw new IllegalArgumentException("Tipo de cuenta no válido");
        }

        banco.agregarCuenta(cuenta);

        System.out.println("Cuenta creada correctamente.");
        System.out.println(cuenta);
    }

    private static void depositar() {

        int numero = leerEntero("Número de cuenta: ");
        double monto = leerDouble("Monto a depositar: ");

        CuentaBancaria cuenta = banco.buscarPorNumero(numero);

        cuenta.depositar(monto);

        System.out.println("Depósito realizado.");
        System.out.println(cuenta);
    }

    private static void retirar() {

        int numero = leerEntero("Número de cuenta: ");
        double monto = leerDouble("Monto a retirar: ");

        CuentaBancaria cuenta = banco.buscarPorNumero(numero);

        if (cuenta.retirar(monto)) {
            System.out.println("Retiro realizado.");
        } else {
            System.out.println("Retiro no realizado. Saldo o intereses insuficientes.");
        }

        System.out.println(cuenta);
    }

    private static void transferir() {

        int origen = leerEntero("Número de cuenta de origen: ");
        int destino = leerEntero("Número de cuenta de destino: ");
        double monto = leerDouble("Monto a transferir: ");

        banco.transferir(origen, destino, monto);

        System.out.println("Transferencia realizada.");
    }

    private static void verHistorial() {

        int numero = leerEntero("Número de cuenta: ");

        CuentaBancaria cuenta = banco.buscarPorNumero(numero);

        cuenta.imprimirHistorial();
    }

    private static void girarCheque() {

        int numero = leerEntero("Número de cuenta: ");

        CuentaBancaria cuenta = banco.buscarPorNumero(numero);

        if (!(cuenta instanceof CuentaCheques)) {
            throw new IllegalArgumentException("La cuenta seleccionada no es de cheques");
        }

        double monto = leerDouble("Monto del cheque: ");

        ((CuentaCheques) cuenta).girarCheque(monto);

        System.out.println("Cheque girado correctamente.");
        System.out.println(cuenta);
    }

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        return Integer.parseInt(ns.nextLine());
    }

    private static double leerDouble(String mensaje) {
        System.out.print(mensaje);
        return Double.parseDouble(ns.nextLine());
    }
}