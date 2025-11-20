package com.jpribeiro.controller;

import com.jpribeiro.service.BibliotecaService;
import com.jpribeiro.service.JogoService;
import com.jpribeiro.service.UsuarioService;
import com.jpribeiro.view.BibliotecaView;
import com.jpribeiro.view.HomeView;
import io.javalin.Javalin;

public class GeralController {
    private final BibliotecaService service = new BibliotecaService();
    private final UsuarioService usuarioService = new UsuarioService();
    private final JogoService jogoService = new JogoService();

    public GeralController(Javalin app) {
        app.get("/", ctx -> ctx.html(HomeView.renderHome()));

        app.get("/usuarios/{id}/biblioteca", ctx -> {
            int usuarioId = Integer.parseInt(ctx.pathParam("id"));

            var usuario = usuarioService.findUsuarioById(usuarioId);
            var jogos = service.listarJogosDoUsuario(usuarioId);
            var todosJogos = jogoService.findAllJogos();

            ctx.html(BibliotecaView.render(usuario, jogos, todosJogos));
        });
    }
}
