/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package herencias;

import java.util.Calendar;
import java.util.ArrayList;
/**
 *
 * @author ANGILI PALACIOS
 */
public class CuentaBancaria {
    //atributos
    
    protected int numero;
    protected String cliente;
    protected double saldo;
    protected double tasa;
    protected Calendar creacion;
    protected ArrayList<String> historial;

    public CuentaBancaria(int numero, String cliente,  double tasa) {
        this.numero = numero;
        this.cliente = cliente;
        this.saldo = 500;
        this.tasa = tasa;
        this.creacion = Calendar.getInstance();
        this.historial = new ArrayList<>();
        this.historial.add("Cuenta creada con saldo incial de 500 Lps. ");
    }
    
    public void depositar(double monto){
        try{
        if(monto<=0){
            System.out.println("Insuficiente saldo ");
        }else{
            saldo += monto;
            historial.add("Deposito: "+monto);
        }
        }
            catch(Exception e){
                System.out.println(e.getMessage());
                    
                    }
    }
        public boolean retirar(double monto){
            if(monto>00 && saldo >= monto){
                saldo-=monto;
                historial.add("Retiro: - "+monto);
                return true;
            }
            historial.add("intento de retiro fallido "+monto);
            return false;
        
        
    }
        public void mostrarHistorial(){
            System.out.println("===== HISTORIAL DE "+cliente.toUpperCase()+"=======");
            for(String registro:historial)
               System.out.println(registro);
        }
        
        public double getSaldo(){
            return saldo;
        }
        public String ToStrig(){
            return String.format("Cuenta #%d |%s | $%.2f",numero,cliente,saldo);
        }
        public void tranferir (double monto,CuentaBancaria destino){
            if(this.retirar(monto)){
                destino.retirar(monto);
                this.historial.add("Tranferencia enviada a "+destino.numero + monto);
                
                
            }else{
                System.out.println("Tranferencia fallida");
            }
        }
                
       
       
}
