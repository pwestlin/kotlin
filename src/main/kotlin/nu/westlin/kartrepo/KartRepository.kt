package nu.westlin.kartrepo

import org.springframework.jdbc.core.JdbcOperations

interface KartRepository {

    fun store(user: Driver)

    fun load(alias: String): Driver

    fun all(): List<Driver>

}