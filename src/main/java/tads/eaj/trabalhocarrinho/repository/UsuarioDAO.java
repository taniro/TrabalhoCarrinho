package tads.eaj.trabalhocarrinho.repository;

import tads.eaj.trabalhocarrinho.model.Usuario;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public List<Usuario> listarUsuarios() {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuario> usuarios = new ArrayList<>();
        try {
            connection = ConectaBanco.getConnection();
            stmt = connection.prepareStatement("select * from usuario");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setSenha(rs.getString("senha"));
                u.setAdm(rs.getBoolean("adm"));
                usuarios.add(u);
            }
            connection.close();
        } catch (SQLException | URISyntaxException ignored) {
        }

        return usuarios;
    }

    public List<Usuario> listarUsuariosPorId(int id) {

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Usuario> usuarios = new ArrayList<>();

        try {
            connection = ConectaBanco.getConnection();
            stmt = connection.prepareStatement("select * from usuario where id=?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setSenha(rs.getString("senha"));
                u.setAdm(rs.getBoolean("adm"));
                usuarios.add(u);
            }
            connection.close();
        } catch (SQLException | URISyntaxException ignored) {
        }
        return usuarios;
    }

    public void cadastrarUsuario(Usuario u) {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = ConectaBanco.getConnection();

            stmt = connection.prepareStatement(
                    "insert into usuario (nome, senha, admin) values (?,?,?)");

            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getSenha());
            stmt.setBoolean(3, u.isAdm());
            stmt.executeUpdate();
            connection.close();

        } catch (SQLException | URISyntaxException ex) {
            // response.getWriter().append("Connection Failed! Check output console");
        }
    }
    public String validarLogin(String login, String senha) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Usuario> usuarios = new ArrayList<>();

        try {
            connection = ConectaBanco.getConnection();
            stmt = connection.prepareStatement("select * from usuario where nome=?");
            stmt.setString(1, login);

            System.out.println(stmt.toString());
            rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setSenha(rs.getString("senha"));
                u.setAdm(rs.getBoolean("adm"));
                usuarios.add(u);
            }

            connection.close();

            if (!usuarios.isEmpty()){
                for (Usuario u : usuarios) {
                    if (u.getNome().equals(login) && u.getSenha().equals(senha)) {
                        if (u.isAdm()) {
                            return "admin";
                        } else {
                            return "user";
                        }
                    }
                }
            }

        } catch (SQLException | URISyntaxException ignored) {}
        return "no-user";
    }
}
