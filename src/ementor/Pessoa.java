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
    protected String Rua;
    protected String Bairro;
    protected String Cidade;
    protected String Estado;
    
    public Pessoa (){
        this.Nome = "";
        this.DataNascimento = "";
        this.CPF = 0;
        this.Telefone = "";
        this.Rua = "";
        this.Bairro = "";
        this.Cidade = "";
        this.Estado = "";
    }
    
    public Pessoa (String nome, String data, long cpf, String contato, String rua, String bairro, String cidade, String estado){
        this.Nome = nome;
        this.DataNascimento = data;
        this.CPF = cpf;
        this.Telefone = contato;
        this.Rua = rua;
        this.Bairro = bairro;
        this.Cidade = cidade;
        this.Estado = estado;
    }
    
    public void SetDados (String nome, String data, long cpf, String contato, String rua, String bairro, String cidade, String estado){
      this.Nome = nome;
      this.DataNascimento = data;
      this.CPF = cpf;
      this.Telefone = contato;
      this.Rua = rua;
      this.Bairro = bairro;
      this.Cidade = cidade;
      this.Estado = estado;
    }
        
    public String getRua() {
        return this.Rua;
    }
    
    public String getBairro() {
        return this.Bairro;
    }
    
    public String getCidade() {
        return this.Cidade;
    }
    
    public String getEstado() {
        return this.Estado;
    }
}
