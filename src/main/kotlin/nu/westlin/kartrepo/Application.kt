package nu.westlin.kartrepo

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
open class Application {

    @Bean
    open fun init(repository: JdbcKartRepository) = CommandLineRunner {
        repository.store(Driver("pwestlin", "Peter", "Westlin"))
        repository.store(Driver("awestlin", "Adam", "Westlin"))
        repository.store(Driver("fwestlin", "Felix", "Westlin"))
        repository.store(Driver("rpeterson", "Ronnie", "Peterson"))
    }

}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
