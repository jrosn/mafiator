import discord4j.common.util.Snowflake
import mu.KotlinLogging
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArraySet

private val logger = KotlinLogging.logger {}

object Storage {
    private val registeredUsers: MutableMap<Snowflake, CopyOnWriteArraySet<Snowflake>> = ConcurrentHashMap()
    private val rules: MutableMap<Snowflake, Rule> = ConcurrentHashMap()

    fun registerUser(channel: Snowflake, user: Snowflake) {
        registeredUsers
            .computeIfAbsent(channel) { CopyOnWriteArraySet() }
            .add(user)
        logger.info { "For channel $channel registered user $user" }
    }

    fun unregisterUser(channel: Snowflake, user: Snowflake) {
        registeredUsers[channel]?.remove(user)
        logger.info { "For channel $channel unregistered user $user" }
    }

    fun unregisterAll(channel: Snowflake) {
        registeredUsers.remove(channel)
        logger.info { "For channel $channel unregistered all users" }
    }

    fun getRegisteredUsers(channel: Snowflake): Set<Snowflake> {
        return registeredUsers[channel] ?: emptySet()
    }

    fun removeUser(channel: Snowflake, user: Snowflake) {
        val members = registeredUsers[channel]
        members?.remove(user)
        logger.info { "For channel $channel removed user $user" }
    }

    fun setRule(channel: Snowflake, rule: Rule) {
        rules[channel] = rule
        logger.info { "For channel $channel set rules $rule" }
    }

    fun getRule(channel: Snowflake): Rule {
        return rules[channel] ?: Rule(mapOf(Pair("Мафия", 2), Pair("Обычный", 0)))
    }
}