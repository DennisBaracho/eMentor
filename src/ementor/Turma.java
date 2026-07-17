/*
@author Anderson Cordeiro de Souza, Marcos Vinícius Pimentel Gomes, Dennis Francisco Guimarães de Oliveira Baracho
*/
package ementor;
import java.util.ArrayList; 

public class Turma {
    private long CodigoTurma;
    private String NomeTurma;
    ArrayList<Aluno> aluno = new ArrayList<>();

    public Turma(){
        this.aluno = new ArrayList<>();
        this.CodigoTurma = 0;
        this.NomeTurma = "";
    }

   public Turma(long codigoTurma, String nomeTurma, Aluno aluno){
    this.CodigoTurma = codigoTurma;
    this.NomeTurma = nomeTurma;
    this.aluno = new ArrayList<>();
    this.aluno.add(aluno);
}
    
    public String getNomeTurma(){
        return this.NomeTurma;
    }
    
    public void setNomeTurma(String nomeTurma){
        this.NomeTurma = nomeTurma;
    }
     
    public void setCodigoTurma(long codigoTurma){
        this.CodigoTurma = codigoTurma;
    }
    
    public long getCodigoTurma(){
        return this.CodigoTurma;
    }
    
    public ArrayList<Aluno> getAlunos(){
        return this.aluno;
    }
    
    public void adicionarAluno(Aluno aluno){
        this.aluno.add(aluno);
    }
    
    public void removerAluno(Aluno aluno){
        this.aluno.remove(aluno);
    }
    
    public void imprimeTurma(){

        System.out.println("Código: " + CodigoTurma);
        System.out.println("Turma: " + NomeTurma);
        System.out.println("Alunos:");
        for(Aluno a : aluno){
            System.out.println(a.getNome()
                    + " - "
                    + a.getMatricula());
        }
    }
}