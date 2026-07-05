/*
A Subclasse Professor, filha de Pessoa, deve possuir os atributos de Data de Admissão
e Salário Bruto. Os métodos a serem implementados para essa subclasse são: 
Construtor padrão; construtor para inicializar todos os atributos; setDados para 
inicializar os atributos depois de criados os objetos; getDataAdmissao e 
getSalarioBruto para recuperar esses atributos; imprimir dados; e calcular salário 
líquido (de forma simplificada considerar desconto de 14% de INSS para todas as 
faixas de salário e 22,5% de IRPF para salários maiores ou iguais que R$ 5.000,00, 
os salários menores que o valor citado deve ter desconto apenas de INSS)
 */
package ementor; 

import java.io.Serializable;


public class Professor extends Pessoa implements Serializable {
    
   private String DataAdmissao;
   private double SalarioBruto;
   
   public Professor (){
        super();
        this.DataAdmissao = "";
        this.SalarioBruto = 0.00; 
   }

     public Professor(String nome, String data, long cpf, String contato, String DataAdmissao, double SalarioBruto) {
        super(nome, data, cpf, contato);
        this.DataAdmissao = DataAdmissao;
        this.SalarioBruto = SalarioBruto;
    }
   
    public void SetDados(String DataAdmissao, double SalarioBruto, String nome, String data, long cpf, String contato) {
        super.SetDados(nome, data, cpf, contato);
        this.DataAdmissao = DataAdmissao;
        this.SalarioBruto = SalarioBruto;
    }
    
    public String getDataAdmissao(){
        return this.DataAdmissao;
    }
    
    public double getSalarioBruto(){
        return this.SalarioBruto;
    }
   
    public double SalarioLiquido(double salBruto){
        return (salBruto-(salBruto*0.14+salBruto*0.225));
    }
}
