/*
A subclasse Usuário deve possuir os atributos: Nome do Usuario, Senha e Nivel de Acesso (de 1
a 3 – para futuras implementações); com os métodos: construtor padrão, setDados,
getNomoUsuario; getSenha. O objetivo dessa classe é gerenciar os acessos ao ementor-Plus,
sendo assim, devendo para entrar no software informar usuário e senha. Estes previamente
definidos (podendo ser cadastrados diretamente no console do banco de dados pelo comando
insert into Nome da Tabela). Caso o usuário e senha estiverem corretos chama-se uma interface
gráfica contendo o Menu de opções: Cadastrar Aluno, Cadastrar Egresso, Cadastrar Professor;
Listar Aluno; Listar Egresso; Listar Professor; Alterar dados Aluno; Alterar dados Egresso; Alterar
dados Professor. A partir desse ponto outras interfaces gráficas com suas respectivas
funcionalidades serão invocadas.
 */
package ementor;

public class Usuario {
    /*
 A Subclasse Aluno, filha de Pessoa, deve conter os atributos de Matrícula e Período 
e os métodos: Construtor padrão; construtor para inicializar todos os atributos; 
setDados para inicializar todos os atributos após a criação dos objetos; 
getMatricula e getPeriodo para recuperar esses atributos; e 
outro método para imprimir os dados (preferencialmente utilizar mensagens gráficas de dialogo).
 */

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
    public void setNivelAcesso( int nivelAcesso ){
        this.NivelAcesso = nivelAcesso;
    }
}
