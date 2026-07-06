/*
Tocante a subclasse Egresso, filha de Aluno, deve conter os atributos de Profissão Atual, Faixa
Salarial, Curso Anterior, Curso Atual. Os métodos são todos aqueles inerentes a de aluno,
lembrando dos atributos adicionais. Em outros termos, aplicar a sobrescrita.
 */
package ementor;

/**
 *
 * @author aluno
 */
public class Egresso extends Aluno {
    private String ProfissaoAtual;
    private String FaixaSalarial;
    private String CursoAnterior;
    private String CursoAtual;
           
  
    
    @Override
  public void setDados( String nome, String data, long cpf, String contato, String rua, String bairro, String cidade, String estado, long Matricula, int Periodo, float notas[] ) {
        super.SetDados(nome, data, cpf, contato, rua, bairro, cidade, estado, Matricula, Periodo, notas[]);
        
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
    
    public long getTurma(){
        return this.CodigoTurma;
    }
   
    public void imprimeDados(){
        System.out.println("Nome: "+this.Nome);
        System.out.println("CPF: "+this.CPF);
        System.out.println("Data Nascimento: "+this.DataNascimento);
        System.out.println("Matricula: "+this.Matricula);
        System.out.println("Periodo: "+this.Periodo);
        System.out.println("Rua: "+this.Rua);
        System.out.println("Bairro: "+this.Bairro);
        System.out.println("Cidade: "+this.Cidade);
        System.out.println("Estado: "+this.Estado);
        
        for(int i = 0; i <= 10; i++)
            System.out.println("Nota "+i+": "+this.Notas[i]);
    }
    
    
}
