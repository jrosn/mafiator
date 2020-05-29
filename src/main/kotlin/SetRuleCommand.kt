import discord4j.core.event.domain.message.MessageCreateEvent
import reactor.core.publisher.Mono
import java.util.HashMap

class SetRuleCommand : ICommand {
    override fun getCommandName(): String {
        return "!setRule"
    }

    override fun execute(event: MessageCreateEvent): Mono<Void> {
        val content = event.message.content
        val channel = event.message.channel.block()!!
        return try {
            val roles = HashMap<String, Int>()
            val config = content.split(" ")[1];
            for (c in config.split(";")) {
                val (name, count) = c.split(":")
                roles[name] = Integer.parseInt(count)
            }
            val rule = Rule(roles)
            Storage.setRule(channel.id, rule)
            event.message.channel
                .flatMap{ channel -> channel.createMessage("Правила изменились на: $rule") }
                .then()
        } catch (ex: Exception) {
            event.message.channel
                .flatMap{ channel -> channel.createMessage("Ошибка в формате описания правил! $ex") }
                .then()
        }
    }
}