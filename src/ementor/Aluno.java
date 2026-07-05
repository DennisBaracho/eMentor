/*
 A Subclasse Aluno, filha de Pessoa, deve conter os atributos de Matrícula e Período 
e os métodos: Construtor padrão; construtor para inicializar todos os atributos; 
setDados para inicializar todos os atributos após a criação dos objetos; 
getMatricula e getPeriodo para recuperar esses atributos; e 
outro método para imprimir os dados (preferencialmente utilizar mensagens gráficas de dialogo).
 */
package ementor;

public class Aluno extends Pessoa {
    private long Matricula;
    private int Periodo;  
    
    public Aluno (){
        super();
        this.Matricula = 0;
        this.Periodo = 0;
    }

    public Aluno(long Matricula, int Periodo) {
        this.Matricula = Matricula;
        this.Periodo = Periodo;
    }

    public Aluno(String nome, String data, long cpf, String contato,long Matricula, int Periodo) {
        super(nome, data, cpf, contato);
        this.Matricula = Matricula;
        this.Periodo = Periodo;
    }
    
    public void setDados( String nome, String data, long cpf, String contato, long Matricula, int Periodo) {
       super.SetDados(nome, data, cpf, contato);
       this.Matricula = Matricula;
       this.Periodo = Periodo;
    }
    
    public void setMatricula(long matricula){
       this.Matricula=matricula;
    }
    
    public void setPeriodo(int periodo){
       this.Periodo=periodo;
    }
    
    public long getMatricula(){
        return this.Matricula;
    }
    
    public int getPeriodo(){
        return this.Periodo;
    }
   
    public void imprimeDados(){
        System.out.println("Nome: "+this.Nome);
        System.out.println("CPF: "+this.CPF);
        System.out.println("Data Nascimento: "+this.DataNascimento);
        System.out.println("Matricula: "+this.Matricula);
        System.out.println("Periodo: "+this.Periodo);
    }
    
    
    
}
