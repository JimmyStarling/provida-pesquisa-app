package com.jimmystarling.providapesquisasatisfacao.data.database

import android.content.Context
import androidx.room.*
import com.jimmystarling.providapesquisasatisfacao.data.model.PacienteEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisaEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisadorEntity

@Database(entities = arrayOf(PesquisadorEntity::class, PacienteEntity::class, PesquisaEntity::class), version = 2, exportSchema = false)
abstract class ProvidaDatabase : RoomDatabase() {

    abstract fun databaseDao() : DAOAccess

    companion object {

        @Volatile
        private var INSTANCE: ProvidaDatabase? = null

        fun getDataseClient(context: Context) : ProvidaDatabase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, ProvidaDatabase::class.java, "PROVIDA_DATABASE")
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!

            }
        }

    }

}