import discord4j.core.event.domain.message.MessageCreateEvent
import reactor.core.publisher.Mono

class UnregisterAllCommand : ICommand {
    override fun getCommandName() = "!unregisterAll"

    override fun execute(event: MessageCreateEvent): Mono<Void> {
        val message = event.message
        val channel = message.channel.block()!!
        Storage.unregisterAll(channel.id)
        return message.channel
            .flatMap { it.createMessage("В игре больше никто не участвует!") }
            .then()
    }
}