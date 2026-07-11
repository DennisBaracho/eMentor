/*
Tocante a subclasse Egresso, filha de Aluno, deve conter os atributos de Profissão Atual, Faixa
Salarial, Curso Anterior, Curso Atual. Os métodos são todos aqueles inerentes a de aluno,
lembrando dos atributos adicionais. Em outros termos, aplicar a sobrescrita.
 */
package ementor;

public class Egresso extends Aluno {
    private String ProfissaoAtual;
    private String FaixaSalarial;
    private String CursoAnterior;
    private String CursoAtual;
    
    public Egresso(){
        super();
        
        this.ProfissaoAtual = "";
        this.FaixaSalarial = "";
        this.CursoAnterior = "";
        this.CursoAtual = "";
    }
    
    public Egresso(String nome, String data, long cpf, String contato, String rua, String bairro, String cidade, String estado, long matricula, int periodo, float notas[], long turma, String profissaoAtual, String faixaSalarial, String cursoAnterior, String cursoAtual){
        super(nome, data, cpf, contato, rua, bairro, cidade, estado, matricula, periodo, notas, turma);
        this.ProfissaoAtual = profissaoAtual;
        this.FaixaSalarial = faixaSalarial;
        this.CursoAnterior = cursoAnterior;
        this.CursoAtual = cursoAtual;
    }
    
    public void setDados(String nome, String data, long cpf, String contato, String rua, String bairro, String cidade, String estado, long matricula, int periodo, float notas[], long turma, String profissaoAtual, String faixaSalarial, String cursoAnterior, String cursoAtual) {
        super.setDados(nome, data, cpf, contato, rua, bairro, cidade, estado, matricula, periodo, notas, turma);
        this.ProfissaoAtual = profissaoAtual;
        this.FaixaSalarial = faixaSalarial;
        this.CursoAnterior = cursoAnterior;
        this.CursoAtual = cursoAtual;
    }
    
    public String getCursoAtual(){
        return this.CursoAtual;
    }
    
    public void setCursoAtual(String cursoAtual){
        this.CursoAtual = cursoAtual;
    }
    
    public String getCursoAnterior(){
        return this.CursoAnterior;
    }
    
    public void setCursoAnterior(String cursoAnterior){
        this.CursoAnterior = cursoAnterior;
    }
    
    public String getFaixaSalarial(){
        return this.FaixaSalarial;
    }
    
    public void setFaixaSalarial(String faixaSalarial){
        this.FaixaSalarial = faixaSalarial;
    }
    
    public String getProfissaoAtual(){
        return this.ProfissaoAtual;
    }
    
    public void setProfissaoAtual(String profissaoAtual){
        this.ProfissaoAtual = profissaoAtual;
    }
    
  @Override
    public void imprimeDados() {

    super.imprimeDados();

    System.out.println("Profissão Atual: " + this.ProfissaoAtual);
    System.out.println("Faixa Salarial: " + this.FaixaSalarial);
    System.out.println("Curso Anterior: " + this.CursoAnterior);
    System.out.println("Curso Atual: " + this.CursoAtual);
    }
}
