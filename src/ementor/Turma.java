/*
 Em face do acima expendido, deve-se criar uma classe Turma contendo os atributos código da
turma, nome da turma e campo capaz de guardar os alunos vinculados a cada Turma cadastrada,
essa associando-se com a classe Aluno pelo matrícula (aluno) e código de turma (turma). Os
métodos devem ser aqueles capazes de gerenciar as turmas a serem criadas conjuntamente a
seus alunos vinculados.
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
    
    public void adicionarAluno(Aluno aluno){
        this.aluno.add(aluno);
    }
    
    public void removerAluno(Aluno aluno){
        this.aluno.remove(aluno);
    }
    
    public void imprimeTurma(){
       
    }
    
}