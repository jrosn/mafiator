import discord4j.core.event.domain.message.MessageCreateEvent
import reactor.core.publisher.Mono

interface ICommand {
    fun getCommandName(): String
    fun execute(event: MessageCreateEvent): Mono<Void>
}