package br.unipar;

import java.sql.*;

public class Main {

    private static final String url = "jdbc:postgresql://localhost:5432/Exemplo1";
    private static final String user = "postgres";
    private  static final String password = "admin123";

    public static void main(String[] args) {

        criarTabelaUsuario();

        inserirUsuario("taffe", "12345", "Fabio", "1890-01-01");

        listarTodosUsuarios();

    }

    public static Connection connection() throws SQLException {
        //localhost -> Onde esta o banco
        //5432 -> porta padrão do banco
        return   DriverManager.getConnection(url, user, password );
    }

    public static void criarTabelaUsuario(){
        try {
            Connection conn = connection();

            Statement statement = conn.createStatement();
            String sql = " CREATE TABLE IF NOT EXISTS usuarios ( "
                    + "codigo SERIAL PRIMARY KEY, "
                    + "username VARCHAR(50) UNIQUE NOT NULL, "
                    + "password VARCHAR(300) NOT NULL, "
                    + " nome VARCHAR(50) NOT NULL, "
                    + " nascimento DATE)";

            statement.execute(sql);

            System.out.println("TABELA CRIADA");

        }catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    public static void inserirUsuario(String username, String password, String nome, String dataNascimento){
        try {
            //Abre a conexao
            Connection conn = connection();

            //Prepara a execução de um sql
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO usuarios(username, password, nome, nascimento)"
                            + " VALUES (?, ?, ?, ?)"
            );


            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, nome);
            preparedStatement.setDate(4, java.sql.Date.valueOf(dataNascimento));

            preparedStatement.executeUpdate();

            System.out.println("usuario inserido !");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static void listarTodosUsuarios(){

        try {
            Connection conn = connection();

            Statement statement = conn.createStatement();

            ResultSet result = statement.executeQuery("SELECT * FROM usuarios");

            while (result.next()) {

                System.out.println(result.getString("codigo"));
                System.out.println(result.getString("username"));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
