import discord4j.core.event.domain.message.MessageCreateEvent
import reactor.core.publisher.Mono

class PingCommand : ICommand {
    override fun getCommandName(): String {
        return "!ping"
    }

    override fun execute(event: MessageCreateEvent): Mono<Void> {
        return event.message.channel
            .flatMap{ channel -> channel.createMessage("Pong!") }
            .then()
    }
}