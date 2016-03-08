package nu.westlin.kartrepo

import org.springframework.stereotype.Repository

@Repository
class KartRepository {

    var users: MutableMap<String, User> = mutableMapOf()

    fun store(user: User) {
        users.put(user.username, user)
    }

    fun load(username: String) = users.get(username)
}
