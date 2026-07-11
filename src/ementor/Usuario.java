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
    
    // Função fora dos requisitos mínimos, avaliar usá-la para caso 
    // seja interessante um usuário poder trocar de nível de acesso
    // seria bacana pra mostrar as diferentes interfaces
    public void setNivelAcesso(int nivelAcesso){
        if(nivelAcesso >= 1 && nivelAcesso <= 3){
            this.NivelAcesso = nivelAcesso;
        }
    }
    
    public int getNivelAcesso(){
        return this.NivelAcesso;
    }
}
