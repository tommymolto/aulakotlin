package br.edu.infnet.tasks

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CategoriaDAO {

    @Insert
    suspend fun insert(categoria: Categoria)

    @Update
    suspend fun update(categoria: Categoria)

    @Delete
    suspend fun delete(categoria: Categoria)

    @Query("SELECT * FROM categoria_table WHERE categoriaId = :key")
    fun get(key: Long): LiveData<Categoria>

    @Query("SELECT * FROM categoria_table ORDER BY categoriaId DESC")
    fun getAll(): LiveData<List<Categoria>>

    @Query("SELECT * FROM categoria_table ORDER BY categoriaId DESC")
    fun getCategorias(): LiveData<List<Categoria>>
}