/*
@author Anderson Cordeiro de Souza, Marcos Vinícius Pimentel Gomes, Dennis Francisco Guimarães de Oliveira Baracho
*/
package ementor;

public class Usuario {

    private String NomeUsuario;
    private String Senha;  
    private int NivelAcesso;
    
    public Usuario (){
        this.NomeUsuario = "";
        this.Senha = "";
        this.NivelAcesso = 1;
    }

    public Usuario(String nomeUsuario, String senha, int nivelAcesso) {
        this.NomeUsuario = nomeUsuario;
        this.Senha = senha;
        this.NivelAcesso = nivelAcesso;
    }
    
    public void SetDados( String nomeUsuario, String senha, int nivelAcesso) {
        this.NomeUsuario = nomeUsuario;
        this.Senha = senha;
        this.NivelAcesso = nivelAcesso;
    }
    
    public String getNomeUsuario(){
        return this.NomeUsuario;
    }
    
    public String getSenha(){
        return this.Senha;
    }
  
    public void setNivelAcesso(int nivelAcesso){
        if(nivelAcesso >= 1 && nivelAcesso <= 3){
            this.NivelAcesso = nivelAcesso;
        }
    }
    
    public int getNivelAcesso(){
        return this.NivelAcesso;
    }
}
