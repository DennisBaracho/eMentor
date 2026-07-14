/*
 A Subclasse Aluno, filha de Pessoa, deve conter os atributos de Matrícula e Período 
e os métodos: Construtor padrão; construtor para inicializar todos os atributos; 
setDados para inicializar todos os atributos após a criação dos objetos; 
getMatricula e getPeriodo para recuperar esses atributos; e 
outro método para imprimir os dados (preferencialmente utilizar mensagens gráficas de dialogo).
 */
package ementor;

public class Aluno extends Pessoa {
    protected long Matricula;
    protected int Periodo;  
    protected float Notas[];
    protected long Turma;
    
    public Aluno (){
        super();
        this.Matricula = 0;
        this.Periodo = 0;
        this.Notas = new float[10];
        this.Turma = 0;
    }

    public Aluno(String nome, String data, long cpf, String contato, String rua, String bairro, String cidade, String estado, long Matricula, int Periodo, float notas[], long turma) {
        super(nome, data, cpf, contato, rua, bairro, cidade, estado);
        this.Matricula = Matricula;
        this.Periodo = Periodo;
        this.Notas = notas;
        this.Turma = turma;
    }

    public void setDados( String nome, String data, long cpf, String contato, String rua, String bairro, String cidade, String estado, long Matricula, int Periodo, float notas[], long turma ) {
        super.SetDados(nome, data, cpf, contato, rua, bairro, cidade, estado);
        this.Matricula = Matricula;
        this.Periodo = Periodo;
        this.Notas = notas;
        this.Turma = turma;
    }
    
    public void setMatricula(long matricula){
        this.Matricula = matricula;
    }
    
    public void setPeriodo(int periodo){
        this.Periodo = periodo;
    }
    
    public long getMatricula(){
        return this.Matricula;
    }
    
    public int getPeriodo(){
        return this.Periodo;
    }
    
    public float[] getNotas() {
        return Notas;
    }
    
    public void setNotas(float[] notas){
        this.Notas = notas;
    }

    public long getTurma() {
        return this.Turma;
    }

    public void setTurma(long turma) {
        this.Turma = turma;
    }

    
    public void imprimeDados(){
        super.imprimeDados();
        System.out.println("Matricula: "+this.Matricula);
        System.out.println("Periodo: "+this.Periodo);
        
        for(int i = 0; i < 10; i++)
            System.out.println("Nota "+i+": "+this.Notas[i]);
    }
}
