package ementor;

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
 import java.sql.ResultSet;
import java.util.ArrayList;



public class ConexoesMySQL {
    
    /*Secão de atributos/variaveis iniciais */
    private final String caminho = "localhost"; //Indica que usaremos o server na máquina
    private final String porta = "3306"; //Porta padrão de Conexão do MySQL Server
    private final String nome = "ementor"; //Nome da nossa base de dados
    private final String usuario = "root"; //Usuario padrão do MySQL
    private final String senha = "admin"; // Senha definida no momento da instalação do MySQL
    private final String FusoHorario = "?useTimezone=true&serverTimezone=UTC";
    private final String URL ="jdbc:mysql://"+caminho+":"+porta+"/"+nome+FusoHorario; //Ajusta o fuso horário em relação a sede da Oracle 
    
    public Connection realizaConexaoMySQL(){
        try{
            return DriverManager.getConnection(URL,usuario,senha); //Estabelece a conexão via conector j
            
        }catch(SQLException e){
          JOptionPane.showMessageDialog(null,"Algum imprevisto ocorreu: "+e+"","ERRO",JOptionPane.ERROR_MESSAGE);
          return null;
        }        
    }
    
    /////////////////////////////////////////////////////////////////////
    public void desconectaMySQL(Connection conexao){
        try{
           if (conexao != null) 
             conexao.close();
            
        }catch(SQLException e){
          JOptionPane.showMessageDialog(null,"Algum imprevisto ocorreu: "+e+"","ERRO",JOptionPane.ERROR_MESSAGE);
          
        }   
    }
    //////////////////////////////////////////////////////////////////////
    public void insereDadosNoMySQL(String nome, long CPF, String contato,String Data, long matricula, int periodo){
        Connection conexao = realizaConexaoMySQL();
        String sql_pessoa = "insert into pessoa (CPF, Nome, Telefone, DataNascimento) values (?,?,?,?)";
        String sql_aluno = "insert into aluno (Matricula,CPF_Pessoa,Periodo) values (?,?,?)";
        
        try{//Aqui serão aplicados todos os recursos para efetivar a inserção dos daddo nas Tabelas do MySQL
            PreparedStatement Atuador_pessoa = conexao.prepareStatement(sql_pessoa);
            PreparedStatement Atuador_aluno = conexao.prepareStatement(sql_aluno);
            
            /*Seção para setar os campos no atuador */
            Atuador_pessoa.setLong(1, CPF);//Substitui a primeira interrogação no insert into aluno
            Atuador_pessoa.setString(2, nome);//Substitui a segunda interrogação no insert into aluno
            Atuador_pessoa.setString(3, contato); //Substitui a terceira interrogação no insert into aluno
            Atuador_pessoa.setString(4, Data);//Substitui a última interrogação no insert into aluno
            
            Atuador_pessoa.execute();//Nesse momento estará sendo lançado o comando insert into "fisisicamente" no MySQL
            
            // Idem ao passos anteriores
            Atuador_aluno.setLong(1,matricula);
            Atuador_aluno.setLong(2, CPF);
            Atuador_aluno.setInt(3, periodo);   
            
            Atuador_aluno.execute();
            JOptionPane.showMessageDialog(null,"Cadastro Realizado com Sucesso","Salvar",JOptionPane.INFORMATION_MESSAGE);
          }catch(SQLException e){
          JOptionPane.showMessageDialog(null,"Algum imprevisto ocorreu: "+e+"","ERRO",JOptionPane.ERROR_MESSAGE);
          
        }
        desconectaMySQL(conexao);
    }  
    //////////////////////////////////////////////////////////////////////
    public void atualizaDadosNoMySQL(String matricula, String periodo){
        Connection conexao = realizaConexaoMySQL();
        String sql ="update ementor.pessoa, ementor.aluno set Periodo="+periodo+" where CPF=CPF_Pessoa and Matricula="+matricula+";";
       
        //update ementor.pessoa set pessoa.Nome="Fulado de tal" where pessoa.CPF="12345678910". 
        try{//Aqui serão aplicados todos os recursos para efetivar a inserção dos daddo nas Tabelas do MySQL
            PreparedStatement Atuador = conexao.prepareStatement(sql);  
            
            /*Seção para setar os campos no atuador */          
            Atuador.execute();//Nesse momento estará sendo lançado o comando update "fisisicamente" no MySQL
                     
           JOptionPane.showMessageDialog(null,"Dados Atualizados com Sucesso","Salvar",JOptionPane.INFORMATION_MESSAGE);
          }catch(SQLException e){
          JOptionPane.showMessageDialog(null,"Algum imprevisto ocorreu: "+e+"","ERRO",JOptionPane.ERROR_MESSAGE);
          
        }
        desconectaMySQL(conexao);
    }
    
    ///////////////////////////////////////////////////////////
    public ArrayList<Aluno> recuperaDadosDoMySQL(String tipoOrdenacao){
        Connection conexao = realizaConexaoMySQL();//Estabelece conexão
        ArrayList<Aluno> Academico = new ArrayList();//Cria uma lista chamada Academico do Tipo Aluno
        
        try{            
            String sql_selecao_aluno = "SELECT *FROM ementor.pessoa, ementor.aluno WHERE pessoa.CPF=aluno.CPF_Pessoa ORDER BY "+tipoOrdenacao+";";
            PreparedStatement atuador_selecao_aluno = conexao.prepareStatement(sql_selecao_aluno);
            ResultSet ResultadoSelecao = atuador_selecao_aluno.executeQuery(); //É aqui que fica o resultado da selação do MySQL
            /*Secao para percorrer todas as linhas resultantes da seleção- Logo, deve-se usar um laço de repetição  */
            while(ResultadoSelecao.next()){//Laço de repetição para percorrer todo o conjuto de resultados "ResultSet" trazidos pela Query
                Aluno ObjetoAluno = new Aluno();//Cria objeto aluno
                /*Seção para inserir em cada atributo do objetoAluno os valores dos campos do MySQL */
                ObjetoAluno.CPF = ResultadoSelecao.getLong("CPF");
                ObjetoAluno.Nome = ResultadoSelecao.getString("Nome");
                ObjetoAluno.DataNascimento = ResultadoSelecao.getString("DataNascimento");
                ObjetoAluno.Telefone = ResultadoSelecao.getString("Telefone");
                ObjetoAluno.setMatricula(ResultadoSelecao.getLong("Matricula"));
                ObjetoAluno.setPeriodo(ResultadoSelecao.getInt("Periodo"));
                       
                Academico.add(ObjetoAluno);//Adiciona à Lista o Objeto Atual        
                      
            }
              ResultadoSelecao.close();
              atuador_selecao_aluno.close();                   
                    
             }catch(SQLException e){
              JOptionPane.showMessageDialog(null,"Algum imprevisto ocorreu: "+e+"","ERRO",JOptionPane.ERROR_MESSAGE);
          
        }
        desconectaMySQL(conexao); //Fecha a conexão do Banco de Dados
        return Academico;
    }
    ///////////////////////////////////////////////////////////////////////////
    public Aluno buscaAluno(String matricula){
        Connection conexao = realizaConexaoMySQL();//Estabelece conexão
        Aluno Academico  = new Aluno();//Cria um vetor chamado Academico do Tipo Aluno
        Academico=null;
        try{            
            String sql_selecao_aluno = "SELECT *FROM ementor.pessoa, ementor.aluno WHERE pessoa.CPF=aluno.CPF_Pessoa and aluno.Matricula="+matricula+";";
            PreparedStatement atuador_selecao_aluno = conexao.prepareStatement(sql_selecao_aluno);
            ResultSet ResultadoSelecao = atuador_selecao_aluno.executeQuery(); //É aqui que fica o resultado da selação do MySQL
            /*Secao para percorrer todas as linhas resultantes da seleção- Logo, deve-se usar um laço de repetição  */
           // ResultadoSelecao.previous();
            while(ResultadoSelecao.next()){//Laço de repetição para percorrer todo o conjuto de resultados "ResultSet" trazidos pela Query
                //ResultadoSelecao.first();
                Aluno ObjetoAluno = new Aluno();//Cria objeto aluno
                /*Seção para inserir em cada atributo do objetoAluno os valores dos campos do MySQL */
                ObjetoAluno.CPF = ResultadoSelecao.getLong("CPF");
                ObjetoAluno.Nome = ResultadoSelecao.getString("Nome");
                ObjetoAluno.DataNascimento = ResultadoSelecao.getString("DataNascimento");
                ObjetoAluno.Telefone = ResultadoSelecao.getString("Telefone");
                ObjetoAluno.setMatricula(ResultadoSelecao.getLong("Matricula"));
                ObjetoAluno.setPeriodo(ResultadoSelecao.getInt("Periodo"));
                       
                Academico=ObjetoAluno;//Adiciona ao vetor de Aluno o Objeto Atual        
                      
            }
              ResultadoSelecao.close();
              atuador_selecao_aluno.close();                   
                    
             }catch(SQLException e){
              JOptionPane.showMessageDialog(null,"Algum imprevisto ocorreu: "+e+"","ERRO",JOptionPane.ERROR_MESSAGE);
          
        }
        desconectaMySQL(conexao); //Fecha a conexão do Banco de Dados
        return Academico;
    }
   }
