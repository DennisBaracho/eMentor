/*
@author Anderson Cordeiro de Souza, Marcos Vinícius Pimentel Gomes, Dennis Francisco Guimarães de Oliveira Baracho
*/
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
    
    public String getNome() {
        return Nome;
    }

    public String getDataNascimento() {
        return DataNascimento;
    }

    public long getCPF() {
        return CPF;
    }

    public String getTelefone() {
        return Telefone;
    }       
    
    public void setNome(String nome) {
    this.Nome = nome;
    }

    public void setDataNascimento(String dataNascimento) {
        this.DataNascimento = dataNascimento;
    }

    public void setCPF(long cpf) {
        this.CPF = cpf;
    }

     public void setTelefone(String telefone) {
        this.Telefone = telefone;
    }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
    public void setRua(String rua) {
        this.Rua = rua;
    }

    public void setBairro(String bairro) {
        this.Bairro = bairro;
    }

    public void setCidade(String cidade) {
        this.Cidade = cidade;
    }

    public void setEstado(String estado) {
        this.Estado = estado;
    }
    
    public void imprimeDados() {
        System.out.println("Nome: " + this.Nome);
        System.out.println("CPF: " + this.CPF);
        System.out.println("Data Nascimento: " + this.DataNascimento);
        System.out.println("Telefone: " + this.Telefone);
        System.out.println("Rua: " + this.Rua);
        System.out.println("Bairro: " + this.Bairro);
        System.out.println("Cidade: " + this.Cidade);
        System.out.println("Estado: " + this.Estado);
    }
}
