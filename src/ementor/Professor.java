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
   private boolean isChefia;
   private boolean isCoordenacao;
   
   public Professor (){
        super();
        this.DataAdmissao = "";
        this.SalarioBruto = 0.00;
        this.isChefia = false;
        this.isCoordenacao = false;
   }

     public Professor(String nome, String data, long cpf, String contato, String DataAdmissao, double SalarioBruto, String rua, String bairro, String cidade, String estado, boolean isChefia, boolean isCoordenacao) {
        super(nome, data, cpf, contato, rua, bairro, cidade, estado);
        this.DataAdmissao = DataAdmissao;
        this.SalarioBruto = SalarioBruto;
        this.isChefia = isChefia;
        this.isCoordenacao = isCoordenacao;
    }
   
    public void SetDados(String nome, String data, long cpf, String contato, String rua, String bairro, String cidade, String estado, String DataAdmissao, double SalarioBruto, boolean isChefia, boolean isCoordenacao) {
        super.SetDados(nome, data, cpf, contato, rua, bairro, cidade, estado);
        this.DataAdmissao = DataAdmissao;
        this.SalarioBruto = SalarioBruto;
        this.isChefia = isChefia;
        this.isCoordenacao = isCoordenacao;
    }
    
    public String getDataAdmissao(){
        return this.DataAdmissao;
    }
    
    public double getSalarioBruto(){
        return this.SalarioBruto;
    }
   
    public void setDataAdmissao(String dataAdmissao){
    this.DataAdmissao = dataAdmissao;
}

    public void setSalarioBruto(double salarioBruto){
        this.SalarioBruto = salarioBruto;
    }

    public void setChefia(boolean chefia){
        this.isChefia = chefia;
    }

    public void setCoordenacao(boolean coordenacao){
        this.isCoordenacao = coordenacao;
    }

    public boolean isChefia() {
        return this.isChefia;
    }
    
    public boolean isCoordenacao() {
        return this.isCoordenacao;
    }
    
    public double calcularSalarioBruto() {

        double salario = this.SalarioBruto;

        if (this.isChefia)
            salario *= 1.10;

        if (this.isCoordenacao)
            salario *= 1.20;

        return salario;
    }
    
    public double calcularSalarioLiquido() {
        double salario = calcularSalarioBruto();
        double inss = salario * 0.14;

        if (salario >= 5000) {
            double ir = salario * 0.225;
            return salario - inss - ir;
        }

        return salario - inss;
    }
    
    @Override
    public void imprimeDados(){
        super.imprimeDados();
        System.out.println("Data de Admissão: "+this.DataAdmissao);
        System.out.println("Salário Bruto: R$ "+ calcularSalarioBruto());
        System.out.println("Salário Líquido: R$ "+ calcularSalarioLiquido());
        System.out.println(this.isChefia ? "É Chefia" : "Não é Chefia");
        System.out.println(this.isCoordenacao ? "É Coordenador(a)" : "Não é Coordenador(a)");
    }
}

