package br.com.intercambiolivros.dao;

import br.com.intercambiolivros.config.ApiSingleton;

public abstract class ApiDAO {

    protected final ApiSingleton api;
    protected final String token;

    protected ApiDAO(String token) {
        this.api = ApiSingleton.getInstance();
        this.token = token;
    }
}