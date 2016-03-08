package nu.westlin.kartrepo

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
class KartController {

    @RequestMapping("/user")
    fun greeting(@RequestParam(value = "username", defaultValue = "pwestlin") username: String, response: HttpServletResponse): User {
        if (username == "pwestlin")
            return User(username, "Peter", "Westlin")
        else
            throw NotFoundException("User $username not found")

    }

    @RequestMapping("/users")
    fun greeting(): List<User> {
        return listOf(User("pwestlin", "Peter", "Westlin"))
    }

    @ExceptionHandler(Exception::class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun handleException(e: NotFoundException, response: HttpServletResponse): ErrorResource {
        return ErrorResource(HttpStatus.NOT_FOUND.value(), "Could not find resource")
    }
}

class NotFoundException(message: String) : RuntimeException(message)

data class ErrorResource(val status: Int, val message: String)