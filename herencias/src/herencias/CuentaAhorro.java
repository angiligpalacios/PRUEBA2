package herencias;

import java.util.Calendar;

public class CuentaAhorro extends CuentaBancaria {
     private Calendar ultimoUso;

    public CuentaAhorro(int numero, String cliente, String moneda) {
        super(numero, cliente, moneda, TipoCuenta.AHORRO);
        ultimoUso = Calendar.getInstance();
    }

    // Constructor para conservar la forma de creación anterior.
    public CuentaAhorro(int numero, String cliente) {
        this(numero, cliente, "Lps");
    }

    public boolean status() {

        Calendar fechaLimite = Calendar.getInstance();
        fechaLimite.add(Calendar.MONTH, -6);
        return ultimoUso != null && !ultimoUso.before(fechaLimite);
    }

    private void verificarActiva() {

        if (!status()) {
            throw new IllegalStateException(
                    "La cuenta de ahorro está inactiva");
        }
    }

    @Override
    public void depositar(double monto) {
        verificarActiva();
        super.depositar(monto);
        ultimoUso = Calendar.getInstance();
    }

    @Override
    public boolean retirar(double monto) {
        verificarActiva();
        boolean realizado = super.retirar(monto);
        if (realizado) {
            ultimoUso = Calendar.getInstance();
        }
        return realizado;
    }

    @Override
    public void reintegrar(double monto) {
        super.reintegrar(monto);
        ultimoUso = Calendar.getInstance();
    }

    @Override
    public void registrarIntereses() {
        verificarActiva();
        super.registrarIntereses();
    }

    
    public void aplicarIntereses() {
        registrarIntereses();
    }

    @Override
    public void quienSoy() {
        System.out.println("Soy una CuentaAhorro");
    }

    @Override
    public String toString() {

        return super.toString()
                + " Estado: "
                + (status() ? "Activa" : "Inactiva");
    }
}