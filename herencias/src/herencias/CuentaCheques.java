
package herencias;
/**
 *
 * @author ANGILI PALACIOS
 */
public class CuentaCheques extends CuentaBancaria {

    private double limiteSobregiro;

    public CuentaCheques(int numero, String cliente,String moneda, double limiteSobregiro) {
        super(numero, cliente, moneda, TipoCuenta.CHEQUES);
        if (limiteSobregiro < 0 || !Double.isFinite(limiteSobregiro)) {
            throw new IllegalArgumentException(
                    "El limite de sobregiro no puede ser negativo");
        }
        this.limiteSobregiro = limiteSobregiro;
    }

    
    public CuentaCheques(int numero, String cliente,double limiteSobregiro) {
        this(numero, cliente, "Lps", limiteSobregiro);
    }

    public void girarCheque(double monto) {
        if (monto <= 0 || !Double.isFinite(monto)) {
            throw new IllegalArgumentException(
                    "El monto del cheque debe ser mayor que cero");
        }

        if (monto > saldo + limiteSobregiro) {
            throw new IllegalStateException(
                    "Fondos insuficientes para girar el cheque");
        }

        saldo -= monto;

        registrarMovimiento(
                "CHEQUE -" + monto + " -> saldo: " + saldo);
    }

    public double getLimiteSobregiro() {
        return limiteSobregiro;
    }

    @Override
    public void registrarIntereses() {
     
    }

    @Override
    public void quienSoy() {
        System.out.println("Soy una CuentaCheques");
    }

    @Override
    public String toString() {

        return super.toString()+ "  Límite de sobregiro: "+ String.format("%.2f", limiteSobregiro)+ "  Sobregiro disponible: "+ String.format("%.2f", limiteSobregiro + Math.min(saldo, 0));
    }
}
