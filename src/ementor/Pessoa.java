/*Pessoa deve possuir os atributos de Nome, Data de Nascimento, CPF e Telefone. 
Em relação aos métodos, deve ser implementado o método construtor padrão 
e método construtor para inicializar todos os atributos. */
package ementor;

import java.io.Serializable;


public class Pessoa implements Serializable{
    protected String Nome;
    protected String DataNascimento;
    protected long CPF;
    protected String Telefone;
    
    public Pessoa (){
        this.Nome = "";
        this.DataNascimento = "";
        this.CPF = 0;
        this.Telefone = "";
    }
    
    public Pessoa (String nome, String data, long cpf, String contato){
        this.Nome = nome;
        this.DataNascimento = data;
        this.CPF = cpf;
        this.Telefone = contato;
    }
    
    public void SetDados (String nome, String data, long cpf, String contato){
      this.Nome = nome;
      this.DataNascimento = data;
      this.CPF = cpf;
      this.Telefone = contato;
    }
}
