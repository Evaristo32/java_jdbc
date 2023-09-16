import java.sql.*;

public class Main {

    // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"
    private static final String URL_DE_CONEXAO_MYSQL = "jdbc:mysql://localhost:3306/loja_virtual?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private static final String USUARIO_DE_ACESSO = "evaristo";
    private static final String SENHA_DO_USUARIO = "123456";

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        try (
                /**Etapa 1: Construindo um objeto 'Conexão' de banco de dados chamado 'conexao'**/
                Connection conexao = DriverManager.getConnection(URL_DE_CONEXAO_MYSQL, USUARIO_DE_ACESSO, SENHA_DO_USUARIO);   // For MySQL only

                /** Etapa 2: Construa um objeto 'Statement' chamado 'stmt' dentro da Conexão criada**/
                Statement stmt = conexao.createStatement();) {

                /** Etapa 3: escreva uma string de consulta SQL. Execute a consulta SQL através da 'Instrução'.
                 O resultado da consulta é retornado em um objeto 'ResultSet' chamado 'rset'. **/
                String query = "INSERT INTO `Produto` (id, nome, descricao) VALUES ('1','TV 50', 'Televisão');";

                PreparedStatement ps = conexao.prepareStatement(query);
                ps.execute();

                String selectTabelaProduto = "SELECT id, nome, descricao FROM loja_virtual.Produto;";
                System.out.println("The SQL statement is: " + selectTabelaProduto + "\n"); // Echo For debugging

                ResultSet rset = stmt.executeQuery(selectTabelaProduto);

                /** Step 4: Process the 'ResultSet' by scrolling the cursor forward via next().
                 For each row, retrieve the contents of the cells with getXxx(columnName).**/
                System.out.println("The records selected are:");

                while (rset.next()) {   // Repeatedly process each row
                    String nome = rset.getString("nome");  // retrieve a 'String'-cell in the row
                    String descricao = rset.getString("descricao");  // retrieve a 'String'-cell in the row
                    System.out.println(nome + ", " + descricao);
                }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}