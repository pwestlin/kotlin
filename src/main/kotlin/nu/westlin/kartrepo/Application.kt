package nu.westlin.kartrepo

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
open class Application {

    @Bean
    open fun init(repository: KartRepository) = CommandLineRunner {
        repository.store(User("pwestlin", "Peter", "Westlin"))
        repository.store(User("awestlin", "Adam", "Westlin"))
    }

}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
