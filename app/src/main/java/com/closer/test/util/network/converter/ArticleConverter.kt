package com.closer.test.util.network.converter

import com.closer.test.util.model.Article
import com.google.gson.*
import java.lang.reflect.Type

class ArticleConverter: JsonDeserializer<List<Article>> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): List<Article> {
        json ?: throw JsonParseException("Content is null")
        if (!json.isJsonObject) throw JsonParseException("Content not parsable")

        val jsonObject = json.asJsonObject
        val items = jsonObject.get("items").asJsonArray

        val gson = Gson()

        return items.map { gson.fromJson(it.toString(), Article::class.java) }
    }
}
