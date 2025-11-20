package com.jpribeiro.service;

import com.jpribeiro.DAO.BibliotecaDAO;
import com.jpribeiro.DAO.UsuarioDAO;
import com.jpribeiro.model.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {
    @Mock
    private UsuarioDAO usuarioDAO;

    @Mock
    private BibliotecaDAO bibliotecaDAO;

    @InjectMocks
    private UsuarioService service;

    @Test
    void deveCriarUsuarioEAdicionarJogos() throws SQLException {
        String nome = "Teste";
        String email = "teste@email.com";
        List<String> jogosIdsStr = Arrays.asList("10", "20");

        when(usuarioDAO.add(any(Usuario.class))).thenReturn(99);

        service.addUsuario(nome, email, jogosIdsStr);

        verify(usuarioDAO, times(1)).add(any(Usuario.class));

        verify(bibliotecaDAO, times(1)).addJogosAoUsuario(eq(99), eq(Arrays.asList(10, 20)));
    }

    @Test
    void deveIgnorarIdsInvalidosAoAdicionarJogos() throws SQLException {
        List<String> jogosIdsStr = Arrays.asList("10", "XYZ");

        when(usuarioDAO.add(any(Usuario.class))).thenReturn(50);

        service.addUsuario("Teste", "t@t.com", jogosIdsStr);

        verify(bibliotecaDAO).addJogosAoUsuario(eq(50), eq(Collections.singletonList(10)));
    }

    @Test
    void naoDeveChamarBibliotecaSeListaForVazia() throws SQLException {
        List<String> listaVazia = Collections.emptyList();
        when(usuarioDAO.add(any(Usuario.class))).thenReturn(1);

        service.addUsuario("Teste", "t@t.com", listaVazia);

        verify(usuarioDAO).add(any(Usuario.class));
        verify(bibliotecaDAO, never()).addJogosAoUsuario(anyInt(), anyList());
    }
}