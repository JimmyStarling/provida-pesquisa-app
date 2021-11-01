package com.jimmystarling.providapesquisasatisfacao.data

class LoginRepository(private val dao: LoginDatabaseDao) {

    val users = dao.getAllUsers()

    suspend fun insert(user: PesquisadorEntity) {
        return dao.insert(user)
    }

    suspend fun getUserName(userName: String):PesquisadorEntity?{
        return dao.getUsername(userName)
    }
}