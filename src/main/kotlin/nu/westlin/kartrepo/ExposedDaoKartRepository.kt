package nu.westlin.kartrepo

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IdTable
import org.jetbrains.exposed.sql.*
import org.springframework.dao.IncorrectResultSizeDataAccessException
import javax.inject.Inject
import javax.sql.DataSource

open class ExposedDaoKartRepository @Inject constructor(val dataSource: DataSource) : KartRepository {

    override fun store(driver: Driver) {
        val db = Database.connect(dataSource)

        db.transaction {
            Drivers.insert {
                it[alias] = driver.alias
                it[firstname] = driver.firstname
                it[lastname] = driver.lastname
            }
        }
    }

    override fun load(alias: String): Driver {
        val db = Database.connect(dataSource)
        var drivers = mutableListOf<Driver>()
        db.transaction {
            Drivers.select {
                (Drivers.alias.eq(alias))
            }.forEach {
                drivers.add(Driver(it[Drivers.alias], it[Drivers.firstname], it[Drivers.lastname]))
            }

            if (drivers.size != 1)
                throw IncorrectResultSizeDataAccessException(1)
        }

        return drivers.first()
    }

    override fun all(): List<Driver> {
        var drivers = mutableListOf<Driver>()

        val db = Database.connect(dataSource)
        db.transaction {
            for (driver in Drivers.selectAll()) {
                //println("${city[Cities.id]}: ${city[Cities.name]}")
                drivers.add(Driver(driver[Drivers.alias], driver[Drivers.firstname], driver[Drivers.lastname]))
            }

        }

        return drivers
    }

    object Drivers : IdTable("driver") {
        val alias = varchar("alias", 20).primaryKey()
        val firstname = varchar("firstname", 36)
        val lastname = varchar("lastname", 36)
    }

    class Driver2(id: EntityID) : Entity(id) {
        companion object : EntityClass<Driver2>(Drivers)

        var alias by Drivers.alias
        var firstname by Drivers.firstname
        var lastname by Drivers.lastname
    }


}

