package br.edu.infnet.tasks
import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.Executors

@Database(entities = [Task::class, Categoria::class],
    version = 4,
    exportSchema = false,
    )
abstract class TaskDatabase : RoomDatabase() {
    abstract val taskDao: TaskDao
    abstract val categoriaDao: CategoriaDAO
    abstract val visaoTaskDao: VisaoTaskDAO

    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null
        fun getInstance(context: Context): TaskDatabase {
            val MIGRATION_1_2 = object : Migration(1, 2) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("CREATE TABLE `categoria_table` (`categoriaId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `categoria_name` TEXT NOT NULL)")
                }
            }
            val MIGRATION_2_3 = object : Migration(2, 3) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("INSERT INTO categoria_table(categoria_name) VALUES (\"Pessoal\"),(\"Trabalho\"),(\"Outros\");")
                }
            }
            val MIGRATION_3_4 = object : Migration(3, 4) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("ALTER TABLE task_table ADD COLUMN categoriaId INTEGER NULL ;")
                }
            }
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TaskDatabase::class.java,
                        "tasks_database"
                    ).addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
                        .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            //pre-populate data
                            //db.execSQL("INSERT INTO categoria_table(categoria_name) VALUES (\"Pessoal\"),(\"Trabalho\"),(\"Outros\");");
                        }
                    }).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}
