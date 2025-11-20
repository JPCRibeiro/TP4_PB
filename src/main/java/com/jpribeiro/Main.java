package com.jpribeiro;

import com.jpribeiro.DAO.Database;
import com.jpribeiro.controller.GeralController;
import com.jpribeiro.controller.JogoController;
import com.jpribeiro.controller.UsuarioController;
import io.javalin.Javalin;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Database.init();

        Javalin app = Javalin.create().start(7000);

        new UsuarioController(app);
        new JogoController(app);
        new GeralController(app);
    }
}