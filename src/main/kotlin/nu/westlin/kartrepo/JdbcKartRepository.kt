package nu.westlin.kartrepo

import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.sql.SQLException
import javax.inject.Inject

@Repository
open class JdbcKartRepository @Inject constructor(var jdbcOperations: JdbcOperations) : KartRepository {

    override fun store(user: Driver) {
        val rows: Int = jdbcOperations.update("insert into driver(alias, firstname, lastname) values(?,?,?)",
                user.alias, user.firstname, user.lastname)
        if (rows != 1)
            throw JdbcUpdateAffectedIncorrectNumberOfRowsException("Expected 1 row, got $rows", 0, rows)
    }

    override fun load(alias: String): Driver {
        return jdbcOperations.queryForObject("select alias,firstname,lastname from driver where alias=?", DriverRowMapper(), alias)
    }

    override fun all(): List<Driver> {
        return jdbcOperations.query<Driver>("select alias,firstname,lastname from driver", DriverRowMapper())
    }

    class DriverRowMapper : RowMapper<Driver> {

        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, rowNum: Int): Driver {
            return Driver(
                    rs.getString("alias"),
                    rs.getString("firstname"),
                    rs.getString("lastname")
            )
        }

    }

}

