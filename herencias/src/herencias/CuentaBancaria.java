
package herencias;
/**
 *
 * @author ANGILI PALACIOS
 */
//ANGILI RELINA PALCIOS 6261135
import java.util.Calendar;

public class CuentaBancaria {

    protected int numero;
    protected double saldo;
    protected String cliente;
    protected String moneda;
    protected TipoCuenta tipo;
    protected Calendar apertura;

    protected String[] historial = new String[50];
    protected int cantidadMovimientos = 0;

    public CuentaBancaria(int numero, String cliente, String moneda) {
        this(numero, cliente, moneda, TipoCuenta.CHEQUES);
    }

    public CuentaBancaria(int numero, String cliente,String moneda, TipoCuenta tipo) {
        if (cliente == null || cliente.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío");
        }
        if (moneda == null || moneda.trim().isEmpty()) {
            throw new IllegalArgumentException("La moneda no puede estar vacía");
        }

        if (tipo == null) {
            throw new IllegalArgumentException( "El tipo de cuenta no puede ser nulo");
        }

        this.numero = numero;
        this.cliente = cliente;
        this.moneda = moneda;
        this.tipo = tipo;
        this.saldo = 0;
        this.apertura = Calendar.getInstance();

        registrarMovimiento("cuenta creada su saldo: " + saldo);
    }

    public void depositar(double monto) {

        if (monto <= 0 || !Double.isFinite(monto)) {
            throw new IllegalArgumentException( "El depósito debe ser mayor que cero");
        }
        saldo += monto;
        registrarMovimiento("deposito+" + monto + " su  saldoes: " + saldo);
    }

    public boolean retirar(double monto) {
        if (monto <= 0 || !Double.isFinite(monto)) {
            throw new IllegalArgumentException("El retiro debe ser mayor que cero");
        }
        if (monto > saldo) {
            return false;
        }
        saldo -= monto;
        registrarMovimiento("RETIRO -" + monto + " -> saldo: " + saldo);

        return true;
    }

    public void reintegrar(double monto) {
        if (monto <= 0 || !Double.isFinite(monto)) {
            throw new IllegalArgumentException("El reintegro debe ser mayor que cero");
        }
        saldo += monto;
        registrarMovimiento("REVERSA +" + monto + " saldo: " + saldo);
    }

    public void registrarIntereses() {
        double interes = saldo * tipo.tasa();
        if (interes != 0) {
            saldo += interes;
            registrarMovimiento( "INTERESES +" + interes + " -> saldo: " + saldo);
        }
    }

    public void registrarMovimiento(String linea) {
        try {
            if (cantidadMovimientos >= historial.length) {
                throw new IllegalStateException(
                        "El historial está lleno");
            }
            historial[cantidadMovimientos] = linea;
            cantidadMovimientos++;

        } catch (IllegalStateException e) {
            System.out.println(
                    "Aviso: " + e.getMessage()
                    + ". La operación bancaria se completó, "
                    + "pero no se registró el movimiento.");
        }
    }

    public void imprimirHistorial() {

        System.out.println("hoistorial de "+ cliente.toUpperCase() );
        for (int i = 0; i < cantidadMovimientos; i++) {
            System.out.println(historial[i]);
        }
    }

    public void mostrarHistorial() {
        imprimirHistorial();
    }

    public void quienSoy() {
        System.out.println("CuentaBancaria (clase base)");
    }

    public int getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getCliente() {
        return cliente;
    }

    public String getMoneda() {
        return moneda;
    }

    public TipoCuenta getTipo() {
        return tipo;
    }

    @Override
    public String toString() {

        return String.format( "Cuenta #%d | Cliente: %s | Saldo: %.2f %s" + " | Tipo: %s | Apertura: %s",numero, cliente, saldo, moneda,tipo, apertura.getTime());
    }

    
    public String ToStrig() {
        return toString();
    }
    public void tranferir(double monto, CuentaBancaria destino) {

        try {
            if (destino == null || destino == this) {
                throw new IllegalArgumentException(
                        "La cuenta de destino no es válida");
            }

            if (!retirar(monto)) {
                throw new IllegalStateException(
                        "Saldo insuficiente para transferir");
            }

            try {
                destino.depositar(monto);

            } catch (IllegalArgumentException
                    | IllegalStateException e) {

                reintegrar(monto);
                throw e;
            }

        } catch (IllegalArgumentException
                | IllegalStateException e) {

            System.out.println("Transferencia fallida: "
                    + e.getMessage());
        }
    }
}
