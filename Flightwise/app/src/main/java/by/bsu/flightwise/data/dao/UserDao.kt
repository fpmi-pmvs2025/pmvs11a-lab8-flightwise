package by.bsu.flightwise.data.dao

import by.bsu.flightwise.data.entity.User

interface UserDao {
    fun insert(user: User): Long
    fun getById(id: Long): User?
    fun update(user: User): Int
    fun delete(id: Long): Int
    fun getAll(): List<User>

    fun findByUsername(username: String): User?
}