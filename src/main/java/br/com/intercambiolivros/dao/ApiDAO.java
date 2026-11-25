package br.com.intercambiolivros.dao;

import br.com.intercambiolivros.config.ApiSingleton;
import com.google.gson.Gson;

public abstract class ApiDAO {

    protected final ApiSingleton api;
    protected final Gson gson;

    protected ApiDAO() {
        this.api = ApiSingleton.getInstance();
        this.gson = api.getGson();
    }
}