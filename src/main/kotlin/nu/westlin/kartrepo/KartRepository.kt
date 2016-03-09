package nu.westlin.kartrepo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import org.springframework.transaction.PlatformTransactionManager
import java.sql.ResultSet
import java.sql.SQLException

@Repository
// jdbcOperations ska vara val och inte var men jag får inte jdbcOperations att sättas automatiskt :(
open class KartRepository @Autowired constructor(var jdbcOperations: JdbcOperations, val transactionManager: PlatformTransactionManager) {

    fun store(user: Driver) {
        val rows: Int = jdbcOperations.update("insert into driver(alias, firstname, lastname) values(?,?,?)",
                user.alias, user.firstname, user.lastname)
        if (rows != 1)
            throw JdbcUpdateAffectedIncorrectNumberOfRowsException("Expected 1 row, got $rows", 0, rows)
    }

    fun load(alias: String): Driver {
        return jdbcOperations.queryForObject("select alias,firstname,lastname from driver where alias=?", UserRowMapper(), alias)
    }

    class UserRowMapper : RowMapper<Driver> {

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
