package nu.westlin.kartrepo

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.jdbc.core.JdbcOperations
import javax.inject.Inject

@SpringApplicationConfiguration(classes = arrayOf(TestConfiguration::class))
open class JdbcKartRepositoryTest() : AbstractDatabaseTest() {

    val driver = Driver("alias", "firstname", "lastname")

    @Inject
    lateinit var jdbcOperations: JdbcOperations

    @Inject
    lateinit var repository: JdbcKartRepository

    @Test
    fun store() {
        repository.store(driver)

        assertThat(repository.load(driver.alias),
                `is`(jdbcOperations.queryForObject("select alias,firstname,lastname from driver where alias=?", JdbcKartRepository.DriverRowMapper(), driver.alias)))
    }

    @Test
    fun load() {
        jdbcOperations.update(
                "insert into driver(alias, firstname, lastname) values(?,?,?)",
                driver.alias, driver.firstname, driver.lastname)

        assertThat(repository.load(driver.alias), `is`(driver))
    }
}