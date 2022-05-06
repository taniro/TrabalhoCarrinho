package tads.eaj.trabalhocarrinho.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import tads.eaj.trabalhocarrinho.repository.UsuarioDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UsuarioController {

    @PostMapping("/login")
    public void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var login = request.getParameter("login");
        var senha = request.getParameter("senha");

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        switch (usuarioDAO.validarLogin(login, senha)){
            case "user":{
                response.sendRedirect("/userPage");
                break;
            }
            case "admin":{
                response.sendRedirect("/adminPage");
                break;
            }
            default:{
                response.sendRedirect("/index.html");
            }
        }
    }

    @GetMapping("/userPage")
    public void doUserPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println("Página de usuario");

        //verificar se existe a sessão de usuario;
        //se a sessão existe, lista os links disponiveis
        //caso contrario redireciona para o login.html
    }

    @GetMapping("/adminPage")
    public void doAdminPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println("Página de admin");
    }












}
