
package herencias;

public class Banco {

    private CuentaBancaria[] cuentas = new CuentaBancaria[20];
    private int cantidadCuentas = 0;

    public void agregarCuenta(CuentaBancaria c) {

        if (c == null) {
            throw new IllegalArgumentException(
                    "La cuenta no puede ser nula");
        }

        if (buscarExistente(c.getNumero(), 0)) {
            throw new IllegalArgumentException(
                    "Ya existe una cuenta con ese número");
        }

        if (cantidadCuentas >= cuentas.length) {
            throw new IllegalStateException(
                    "El banco no puede registrar más cuentas");
        }

        cuentas[cantidadCuentas] = c;
        cantidadCuentas++;
    }

    private boolean buscarExistente(int numero, int indice) {

        if (indice >= cantidadCuentas) {
            return false;
        }

        if (cuentas[indice].getNumero() == numero) {
            return true;
        }

        return buscarExistente(numero, indice + 1);
    }

    public CuentaBancaria buscarPorNumero(int numero) {
        return buscarRecursivo(numero, 0);
    }

    private CuentaBancaria buscarRecursivo(int numero, int indice) {

        if (indice >= cantidadCuentas) {
            throw new IllegalArgumentException(
                    "No existe una cuenta con el número " + numero);
        }

        if (cuentas[indice].getNumero() == numero) {
            return cuentas[indice];
        }

        return buscarRecursivo(numero, indice + 1);
    }

    public void ordenarPorSaldo() {
        ordenarPasada(0);
    }

    private void ordenarPasada(int pasada) {

        if (pasada >= cantidadCuentas - 1) {
            return;
        }

        compararSiguiente(pasada, 0);
        ordenarPasada(pasada + 1);
    }

    private void compararSiguiente(int pasada, int indice) {

        if (indice >= cantidadCuentas - pasada - 1) {
            return;
        }

        if (cuentas[indice].getSaldo()
                > cuentas[indice + 1].getSaldo()) {

            CuentaBancaria auxiliar = cuentas[indice];
            cuentas[indice] = cuentas[indice + 1];
            cuentas[indice + 1] = auxiliar;
        }

        compararSiguiente(pasada, indice + 1);
    }

    public void transferir(int numeroOrigen,
                           int numeroDestino, double monto) {

        if (numeroOrigen == numeroDestino) {
            throw new IllegalArgumentException(
                    "La cuenta de origen y destino deben ser distintas");
        }

        if (monto <= 0 || !Double.isFinite(monto)) {
            throw new IllegalArgumentException(
                    "El monto debe ser mayor que cero");
        }

        CuentaBancaria origen = buscarPorNumero(numeroOrigen);
        CuentaBancaria destino = buscarPorNumero(numeroDestino);

        if (!origen.retirar(monto)) {
            throw new IllegalStateException(
                    "Saldo insuficiente para realizar la transferencia");
        }

        try {
            destino.depositar(monto);

        } catch (IllegalArgumentException
                | IllegalStateException e) {

            origen.reintegrar(monto);
            throw e;
        }
    }

    public void aplicarInteresesGlobal() {

        for (int i = 0; i < cantidadCuentas; i++) {

            try {
                cuentas[i].registrarIntereses();

            } catch (IllegalArgumentException
                    | IllegalStateException e) {

                System.out.println(
                        "No se pudieron aplicar intereses a la cuenta "
                        + cuentas[i].getNumero() + ": "
                        + e.getMessage());
            }
        }
    }

    public double saldoTotalDelBanco() {
        return sumarSaldos(0);
    }

    private double sumarSaldos(int indice) {

        if (indice >= cantidadCuentas) {
            return 0;
        }

        return cuentas[indice].getSaldo()
                + sumarSaldos(indice + 1);
    }

    public void generarReporte() {

        ordenarPorSaldo();

        System.out.println("===== REPORTE DEL BANCO =====");

        for (int i = 0; i < cantidadCuentas; i++) {
            System.out.println(cuentas[i].toString());
        }

        System.out.printf(
                "Saldo total del banco: %.2f%n",
                saldoTotalDelBanco());
    }

    public int getCantidadCuentas() {
        return cantidadCuentas;
    }

    public CuentaBancaria[] getCuentas() {
        CuentaBancaria[] copia =
                new CuentaBancaria[cantidadCuentas];

        for (int i = 0; i < cantidadCuentas; i++) {
            copia[i] = cuentas[i];
        }

        return copia;
    }
}
