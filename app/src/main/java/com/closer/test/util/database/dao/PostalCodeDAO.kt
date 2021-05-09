package com.closer.test.util.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.closer.test.util.model.PostalCode
import com.closer.test.util.model.PostalCodeFTS

@Dao
interface PostalCodeDAO {

@Query("""
            SELECT postal_code.*
            FROM postal_code 
            JOIN postal_code_fts ON postal_code.id = postal_code_fts.rowid
            WHERE postal_code_fts MATCH :query
    """)
    suspend fun searchFTS(query: String): List<PostalCode>

    @Query(" SELECT * FROM postal_code ")
    suspend fun getAll(): List<PostalCode>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg postalCode: PostalCode)

    @Query("DELETE FROM postal_code")
    suspend fun deleteAll()

}