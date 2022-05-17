package tads.eaj.trabalhocarrinho.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import tads.eaj.trabalhocarrinho.repository.ConectaBanco;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Controller
public class PopulaBanco {

    @GetMapping("/popula")
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = ConectaBanco.getConnection();
            stmt = con.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS produto (id SERIAL PRIMARY KEY, nome VARCHAR(55), descricao VARCHAR(55), preco FLOAT)");
            stmt.execute();

            stmt = con.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS usuario (id SERIAL PRIMARY KEY, nome VARCHAR (55), senha VARCHAR (55), adm BOOLEAN)");
            stmt.execute();

            stmt = con
                    .prepareStatement("insert into produto (nome, descricao, preco) values\n"
                            + "('Mesa', 'Qualquer', '23.0'),\n"
                            + "('Caneta', 'Qualquer', '52.0'),\n"
                            + "('Cadeira', 'Qualquer', '10.0'),\n"
                            + "('TV', 'Dachshund', '75.0'),\n"
                            + "('Monitor', 'Qualquer', '110.0'),\n"
                            + "('Computador', 'Qualquer', '20.0')");
            stmt.execute();

            stmt = con
                    .prepareStatement("insert into usuario(nome, senha, adm) values\n" +
                            "('taniro', '123', true),\n" +
                            "('maria', '456', false)");
            stmt.execute();
            con.close();

            response.getWriter().println("ok");

            HttpSession s = request.getSession();
            s.setAttribute("login", 1);

        } catch (SQLException | URISyntaxException ex) {
            response.getWriter().println(ex);
        }
    }
}
