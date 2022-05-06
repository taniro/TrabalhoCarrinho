package tads.eaj.trabalhocarrinho.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import tads.eaj.trabalhocarrinho.model.Produto;
import tads.eaj.trabalhocarrinho.repository.ProdutoDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;


@Controller
public class CarrinhoController {

    @GetMapping("/adicionarCarrinho")
    public void doAdicionarCarrinho(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        var idProduto = request.getParameter("id");
        var produtoDao = new ProdutoDAO();
        var produtos = produtoDao.listarProdutosPorId(Integer.parseInt(idProduto));

        Cookie carrinho = new Cookie("carrinho", "");
        carrinho.setMaxAge(60*60*24*7);

        Cookie[] requestCookies = request.getCookies();
        boolean achouCarrinho = false;

        if (requestCookies != null){
            for (var c:requestCookies){
                if (c.getName().equals("carrinho")){
                    achouCarrinho = true;
                    carrinho = c;
                    break;
                }
            }
        }

        Produto produtoEscolhido = null;

        if (!produtos.isEmpty()) {
            produtoEscolhido = produtos.get(0);
            if (achouCarrinho) {
                String value = carrinho.getValue();
                carrinho.setValue(value + produtoEscolhido.getId() + "|");
            } else {
                carrinho.setValue(produtoEscolhido.getId() + "|");
            }
        }else {
            response.addCookie(carrinho);
            response.getWriter().println("Id inexistente");
        }
        response.addCookie(carrinho);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/listaProdutos");
        dispatcher.forward(request, response);
    }

    @GetMapping("/visualizarCarrinho")
    public void doVisualizarCarrinho(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Cookie carrinho = new Cookie("carrinho", "");
        carrinho.setMaxAge(60*60*24*7);

        Cookie[] requestCookies = request.getCookies();
        boolean achouCarrinho = false;

        if (requestCookies != null){
            for (var c:requestCookies){
                if (c.getName().equals("carrinho")){
                    achouCarrinho = true;
                    carrinho = c;
                    break;
                }
            }
        }

        if (achouCarrinho){
            var writer = response.getWriter();
            writer.println("<html><body>");
            writer.println("<table>");
            var produtoDao = new ProdutoDAO();
            StringTokenizer tokenizer = new StringTokenizer(carrinho.getValue(), "|");
            while (tokenizer.hasMoreTokens() ){
                List<Produto> produtos = produtoDao.listarProdutosPorId(Integer.parseInt(tokenizer.nextToken()));
                if (produtos != null){
                    Produto p = produtos.get(0);
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
                    writer.println("<a href='/removerCarrinho?id="+p.getId()+"'>Remover</a>");
                    writer.println("</td>");
                    writer.println("</tr>");
                }
            }
            writer.println("</table>");
            writer.println("</body></html>");
        }else{
            response.getWriter().println("Carrinho vazio");
        }
    }
}
