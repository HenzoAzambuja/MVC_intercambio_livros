package br.com.intercambiolivros.service;

import br.com.intercambiolivros.model.Perfil;

import java.util.List;

public class PerfilService {

    public List<Perfil> listar() {
        Perfil administrador = new Perfil();
        administrador.setId(1L);
        administrador.setNome("Administrador");

        Perfil cliente = new Perfil();
        cliente.setId(2L);
        cliente.setNome("Cliente");

        return List.of(administrador, cliente);
    }

    public Perfil buscarPorId(Long id) {
        return this.listar().stream()
                .filter(perfil -> perfil.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
