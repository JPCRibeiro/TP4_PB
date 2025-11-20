package com.jpribeiro.service;

import com.jpribeiro.DAO.BibliotecaDAO;
import com.jpribeiro.model.Jogo;

import java.util.List;

public class BibliotecaService {
    private final BibliotecaDAO bibliotecaDAO = new BibliotecaDAO();

    public List<Jogo> listarJogosDoUsuario(int usuarioId) throws Exception {
        return bibliotecaDAO.findById(usuarioId);
    }
}
