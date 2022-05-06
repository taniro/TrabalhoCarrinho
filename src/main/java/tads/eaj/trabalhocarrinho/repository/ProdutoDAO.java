package tads.eaj.trabalhocarrinho.repository;

import tads.eaj.trabalhocarrinho.model.Produto;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public List<Produto> listarProdutos() {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Produto> listaDeProdutos = new ArrayList<>();
        try {
            connection = ConectaBanco.getConnection();
            stmt = connection.prepareStatement("select * from produto");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setPreco(rs.getFloat("preco"));
                listaDeProdutos.add(p);
            }
            connection.close();
        } catch (SQLException | URISyntaxException ignored) {
        }

        return listaDeProdutos;
    }

    public List<Produto> listarProdutosPorId(int id) {

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Produto> listaDeProdutos = new ArrayList<>();

        try {
            connection = ConectaBanco.getConnection();
            stmt = connection.prepareStatement("select * from produto where id=?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setPreco(rs.getFloat("preco"));
                listaDeProdutos.add(p);
            }
            connection.close();
        } catch (SQLException | URISyntaxException ignored) {
        }
        return listaDeProdutos;
    }

    public void cadastrarProduto(Produto p) {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = ConectaBanco.getConnection();

            stmt = connection.prepareStatement(
                    "insert into produto (nome, descricao, preco) values (?,?,?)");

            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getDescricao());
            stmt.setFloat(3, p.getPreco());
            stmt.executeUpdate();
            connection.close();

        } catch (SQLException | URISyntaxException ex) {
            // response.getWriter().append("Connection Failed! Check output console");
        }
    }
}