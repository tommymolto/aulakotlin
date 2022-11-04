package br.edu.infnet.tasks

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categoria_table")
data class Categoria (


        @PrimaryKey(autoGenerate = true)
        var categoriaId: Long = 0L,
        @ColumnInfo(name = "categoria_name")
        var categoriaName: String = "",

    ){
    override fun toString(): String {
        return categoriaName
    }
}
