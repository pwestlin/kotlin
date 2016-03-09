package nu.westlin.kartrepo

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import javax.inject.Inject
import javax.sql.DataSource

/**
 * Database configuration.
 */
@Configuration
open class DatabaseConfiguration {

    @Inject
    lateinit protected var dataSource: DataSource

    @Bean
    open fun transactionManager(): PlatformTransactionManager {
        return DataSourceTransactionManager(this.dataSource)
    }

    @Bean
    open fun jdbcOperations(): JdbcOperations {
        return JdbcTemplate(this.dataSource)
    }

}