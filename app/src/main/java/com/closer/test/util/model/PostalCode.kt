package com.closer.test.util.model

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "postal_code",
    indices = [Index(value = ["numero_codigo_postal", "extensao_codigo_postal"], unique = true)]
)
data class PostalCode(

    @ColumnInfo(name = "codigo_distrito")
    val codigoDistrito: Long,

    @ColumnInfo(name = "codigo_concelho")
    val codigoConcelho: Long,

    @ColumnInfo(name = "codigo_localidade")
    val codigoLocalidade: Long,

    @ColumnInfo(name = "nome_localidade")
    val nomeLocalidade: String,

    @ColumnInfo(name = "codigo_arteria")
    val codigoArteria: Long?,

    @ColumnInfo(name = "tipo_arteria")
    val tipoArteria: String?,

    @ColumnInfo(name = "primeira_preposicao")
    val primeiraPreposicao: String?,

    @ColumnInfo(name = "titulo_arteria")
    val tituloArteria: String?,

    @ColumnInfo(name = "segunda_preposicao")
    val segundaPreposicao: String?,

    @ColumnInfo(name = "nome_arteria")
    val nomeArteria: String?,

    @ColumnInfo(name = "local_arteria")
    val localArteria: String?,

    @ColumnInfo(name = "troco")
    val troco: String?,

    @ColumnInfo(name = "porta")
    val porta: String?,

    @ColumnInfo(name = "cliente")
    val cliente: String?,

    @ColumnInfo(name = "numero_codigo_postal")
    val numeroCodigoPostal: Long,

    @ColumnInfo(name = "extensao_codigo_postal")
    val extCodigoPostal: Long,

    @ColumnInfo(name = "designacao_postal")
    val designacaoPostal: String,
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0

    @ColumnInfo(name = "codigo_postal")
    var codigoPostal: String = "$numeroCodigoPostal-$extCodigoPostal"
}


@Entity(tableName = "postal_code_fts")
@Fts4(contentEntity = PostalCode::class, tokenizer = FtsOptions.TOKENIZER_UNICODE61)
data class PostalCodeFTS(

    @PrimaryKey
    @ColumnInfo(name = "rowid")
    val rowId: Long,

    @ColumnInfo(name = "codigo_postal")
    val codigoPostal: String,

    @ColumnInfo(name = "designacao_postal")
    val designacaoPostal: String
)