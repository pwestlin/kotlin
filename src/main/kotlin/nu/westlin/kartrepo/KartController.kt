package nu.westlin.kartrepo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.web.bind.annotation.*

@RestController
open class KartController @Autowired constructor(val kartRepository: KartRepository, val jdbcOperations: JdbcOperations) {

    init {
        println("jdbcOperations = ${jdbcOperations}")
        println("kartRepository = ${kartRepository}")
        println("kartRepository.jdbcOperations = ${kartRepository.jdbcOperations}")
        // Jag får inte jdbcOperations att sättas automatiskt :(
        kartRepository.jdbcOperations = jdbcOperations
        println("kartRepository.jdbcOperations = ${kartRepository.jdbcOperations}")
    }


    @RequestMapping("/driver")
    fun greeting(@RequestParam(value = "username", defaultValue = "pwestlin") username: String): Driver {
        return kartRepository.load(username)
    }

    @RequestMapping("/drivers")
    fun greeting(): List<Driver> {
        return kartRepository.all()
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun notFoundException(e: NotFoundException): ErrorResource {
        return ErrorResource(HttpStatus.NOT_FOUND.value(), "Could not find resource")
    }

    @ExceptionHandler(IncorrectResultSizeDataAccessException::class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun incorrectResultSizeDataAccessException(e: IncorrectResultSizeDataAccessException): ErrorResource {
        return ErrorResource(HttpStatus.NOT_FOUND.value(), "Could not find resource")
    }
}

class NotFoundException(message: String) : RuntimeException(message)

data class ErrorResource(val status: Int, val message: String)