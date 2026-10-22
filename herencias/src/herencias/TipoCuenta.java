/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package herencias;

/**
 *
 * @author ANGILI PALACIOS
 */
public class TipoCuenta {
  public static final TipoCuenta AHORRO =
  new TipoCuenta("Ahorro", 0.03);

   public static final TipoCuenta CHEQUES =
       new TipoCuenta("Cheque", 0.00);

    public static final TipoCuenta PLAZO =
     new TipoCuenta("Plazo", 0.05);

    private final String nombre;
    private final double tasa;

    private TipoCuenta(String nombre, double tasa) {
            this.nombre = nombre;
        this.tasa = tasa;
    }

    public double tasa() {
        return tasa;
    }

    @Override
    public String toString() {
        return nombre;
    }
  
}
