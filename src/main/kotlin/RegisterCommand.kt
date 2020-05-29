import discord4j.core.event.domain.message.MessageCreateEvent
import reactor.core.publisher.Mono

class RegisterCommand : ICommand {
    override fun getCommandName() = "!register"

    override fun execute(event: MessageCreateEvent): Mono<Void> {
        val message = event.message
        val author = message.author.get()
        val userId = author.id
        val channel = message.channel.block()!!
        val memberStr = message.authorAsMember.block()?.displayName ?: author.tag
        Storage.registerUser(channel.id, userId)
        return message.channel
            .flatMap { it.createMessage("Пользователь $memberStr зарегистрирован для игры в мафию!") }
            .then()
    }
}