
package herencias;

import java.util.Calendar;

public class CuentaPlazoFijo extends CuentaBancaria {

    private Calendar findePlazo;
    private double intereses;

    public CuentaPlazoFijo(int numero, String cliente, String moneda) {
        super(numero, cliente, moneda, TipoCuenta.PLAZO);
        intereses = 0;
    }

    public void setPlazo(int anio, int mes, int dia) {

        try {
            Calendar fecha = Calendar.getInstance();
            fecha.clear();
            fecha.setLenient(false);
            fecha.set(anio, mes - 1, dia);
            fecha.getTime();

            if (fecha.get(Calendar.YEAR) != anio
                    || fecha.get(Calendar.MONTH) != mes - 1
                    || fecha.get(Calendar.DAY_OF_MONTH) != dia) {
                throw new IllegalArgumentException(
                        "La fecha de vencimiento no es válida");
            }

            findePlazo = fecha;

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Fecha inválida. Revise año, mes y día");
        }
    }

    public boolean vencida() {

        return findePlazo != null
                && !Calendar.getInstance().before(findePlazo);
    }

    public double getIntereses() {
        return intereses;
    }

    @Override
    public boolean retirar(double monto) {

        if (monto <= 0 || !Double.isFinite(monto)) {
            throw new IllegalArgumentException(
                    "El retiro debe ser mayor que cero");
        }

        if (!vencida()) {

            if (monto > intereses) {
                return false;
            }

            intereses -= monto;

            registrarMovimiento("RETIRO DE INTERESES -" + monto + " intereses restantes: " + intereses);

            return true;
        }

        
        if (intereses > 0) {
            saldo += intereses;

            registrarMovimiento( "INTERESES ACUMULADOS +" + intereses + " saldo: " + saldo);
            intereses = 0;
        }
        return super.retirar(monto);
    }

    @Override
    public void registrarIntereses() {
        if (!vencida()) {
            double interesNuevo = saldo * tipo.tasa();
            intereses += interesNuevo;
            if (interesNuevo != 0) {
                registrarMovimiento("INTERESES ACUMULADOS +" + interesNuevo + " -> intereses: " + intereses);
            }

        } else {
            if (intereses > 0) {
                saldo += intereses;

                registrarMovimiento( "INTERESES VENCIDOS +" + intereses+ " -> saldo: " + saldo);
                intereses = 0;
            }

            super.registrarIntereses();
        }
    }

    @Override
    public void reintegrar(double monto) {

        if (monto <= 0 || !Double.isFinite(monto)) {
            throw new IllegalArgumentException(
                    "El reintegro debe ser mayor que cero");
        }

        if (!vencida()) {
            intereses += monto;

            registrarMovimiento(
                    "REVERSA DE INTERESES +" + monto
                    + " -> intereses: " + intereses);
        } else {
            super.reintegrar(monto);
        }
    }

    @Override
    public void quienSoy() {
        System.out.println("Soy una CuentaPlazoFijo");
    }

    @Override
    public String toString() {

        String fecha = (findePlazo == null)
                ? "Sin definir"
                : findePlazo.getTime().toString();

        return super.toString() + " Vencimiento: " + fecha+ " Intereses acumulados: "+ String.format("%.2f", intereses)+ " Vencida: " + vencida();
    }
}
