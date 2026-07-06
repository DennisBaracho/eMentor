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
   
    public boolean isChefia() {
        return this.isChefia;
    }
    
    public boolean isCoordenacao() {
        return this.isCoordenacao;
    }
    
    public double SalBruto(double salBruto, boolean isChefia, boolean isCoordenacao){
        if(isChefia)
            salBruto += salBruto * 0.1;
        
        if(isCoordenacao)
            salBruto += salBruto * 0.2;
        
        return salBruto;
    }
    
    public double SalarioLiquido(double salBruto){
        if (salBruto >= 5000.00)
            return (salBruto-(salBruto*0.14+salBruto*0.225));
        else 
            return (salBruto-(salBruto*0.14));
    }
    
     public void imprimeDados(){
        System.out.println("Nome: "+this.Nome);
        System.out.println("CPF: "+this.CPF);
        System.out.println("Data Nascimento: "+this.DataNascimento);
        System.out.println("Rua: "+this.Rua);
        System.out.println("Bairro: "+this.Bairro);
        System.out.println("Cidade: "+this.Cidade);
        System.out.println("Estado: "+this.Estado);
        System.out.println("Data de Admissão: "+this.DataAdmissao);
        System.out.println("Salario Bruto : R$"+this.SalarioBruto);
        System.out.println(this.isChefia ? "É Chefia" : "Não é Chefia");
        System.out.println(this.isCoordenacao ? "É Coordenador(a)" : "Não é Coordenador(a)");
        System.out.println("Salario Líquido: R$"+this.SalarioLiquido(SalarioBruto));
    }
}

