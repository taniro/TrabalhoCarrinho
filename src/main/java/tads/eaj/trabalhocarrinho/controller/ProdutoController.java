package tads.eaj.trabalhocarrinho.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import tads.eaj.trabalhocarrinho.repository.ProdutoDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ProdutoController {

    @GetMapping("/listaProdutos")
    public void doListAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var writer = response.getWriter();
        writer.println("<html><body>");
        writer.println("<table>");
        var produtoDao = new ProdutoDAO();
        var listaProdutos = produtoDao.listarProdutos();
        for (var p:listaProdutos ){
            writer.println("<tr>");
            writer.println("<td>");
            writer.println(p.getNome());
            writer.println("</td>");
            writer.println("<td>");
            writer.println(p.getDescricao());
            writer.println("</td>");
            writer.println("<td>");
            writer.println(p.getPreco());
            writer.println("</td>");
            writer.println("<td>");
            writer.println("<a href='/adicionarCarrinho?id="+p.getId()+"'>Adicionar</a>");
            writer.println("</td>");
            writer.println("</tr>");
        }
        writer.println("</table>");
        writer.println("</body></html>");
    }
}
