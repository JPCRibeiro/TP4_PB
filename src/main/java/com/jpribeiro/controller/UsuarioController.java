package com.jpribeiro.controller;

import com.jpribeiro.DAO.BibliotecaDAO;
import com.jpribeiro.model.Jogo;
import com.jpribeiro.service.JogoService;
import com.jpribeiro.service.UsuarioService;
import com.jpribeiro.view.UsuarioView;
import io.javalin.Javalin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UsuarioController {
    private final UsuarioService usuarioService = new UsuarioService();
    private final JogoService jogoService = new JogoService();
    private final BibliotecaDAO bibliotecaDAO = new BibliotecaDAO();

    public UsuarioController(Javalin app) {
        app.get("/usuarios", ctx -> ctx.html(UsuarioView.renderList(usuarioService.findAllUsuarios())));

        app.get("/usuarios/adicionar", ctx ->
                ctx.html(UsuarioView.renderForm(new HashMap<>(), jogoService.findAllJogos()))
        );

        app.post("/usuarios", ctx -> {
            String nome = ctx.formParam("nome");
            String email = ctx.formParam("email");
            List<String> jogosIds = ctx.formParams("jogosIds");

            HashMap<String, Object> model = new HashMap<>();

            try {
                usuarioService.addUsuario(nome, email, jogosIds);
                ctx.redirect("/usuarios");
            } catch (Exception e) {
                model.put("erro", "Erro ao adicionar usuário: " + e.getMessage());
                model.put("nome", nome);
                model.put("email", email);
                List<Integer> selecionados = new ArrayList<>();
                for(String id : jogosIds) selecionados.add(Integer.parseInt(id));

                model.put("biblioteca", selecionados);
                ctx.html(UsuarioView.renderForm(model, jogoService.findAllJogos()));
            }
        });

        app.get("/usuarios/editar/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            HashMap<String, Object> model = new HashMap<>();

            try {
                var usuario = usuarioService.findUsuarioById(id);

                List<Jogo> jogosDoUsuario = bibliotecaDAO.findById(id);
                List<Integer> idsJogos = jogosDoUsuario.stream()
                        .map(Jogo::getId)
                        .toList();

                model.put("biblioteca", idsJogos);
                model.put("id", usuario.getId());
                model.put("nome", usuario.getNome());
                model.put("email", usuario.getEmail());
                ctx.html(UsuarioView.renderForm(model, jogoService.findAllJogos()));
            } catch (Exception e) {
                ctx.result("Erro: " + e.getMessage());
            }
        });

        app.post("/usuarios/editar/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            String nome = ctx.formParam("nome");
            String email = ctx.formParam("email");

            List<Jogo> bibliotecaDoUsuario = bibliotecaDAO.findById(id);
            List<Integer> idsExistentes = bibliotecaDoUsuario.stream()
                    .map(Jogo::getId)
                    .toList();

            HashMap<String, Object> model = new HashMap<>();

            try {
                usuarioService.updateUsuario(id, nome, email);
                ctx.redirect("/usuarios");
            } catch (Exception e) {
                model.put("erro", "Erro ao atualizar usuário: " + e.getMessage());
                model.put("id", id);
                model.put("nome", nome);
                model.put("email", email);
                model.put("biblioteca", idsExistentes);
                ctx.html(UsuarioView.renderForm(model, jogoService.findAllJogos()));
            }
        });

        app.post("/usuarios/excluir/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));

            try {
                usuarioService.deleteUsuario(id);
                ctx.redirect("/usuarios");
            } catch (Exception e) {
                ctx.result("Erro ao excluir usuário: " + e.getMessage());
            }
        });
    }
}
