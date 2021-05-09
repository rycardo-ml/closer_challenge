package com.closer.test.util.network.converter;


import com.closer.test.util.model.Article;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Fazendo com Kotlin por algum motivo ele retorna o Tipo como List < extends Emoji> e nao com o
 * tipo especifo assim nao sendo chamado apos a resposta da API
 */
public class TypeTokenCreator {

    public static Type createListArticle() {
        return new TypeToken<List<Article>>() {}.getType();
    }

}
