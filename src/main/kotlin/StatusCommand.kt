import discord4j.core.event.domain.message.MessageCreateEvent
import reactor.core.publisher.Mono

class StatusCommand : ICommand {
    override fun getCommandName(): String = "!status"

    override fun execute(event: MessageCreateEvent): Mono<Void> {
        val message = event.message
        val channel = message.channel.block();
        val guild = message.guild.block();

        channel ?: return Mono.empty()
        guild ?: return Mono.empty()

        val registeredMembersStr = ArrayList<String>();
        for (registeredUser in Storage.getRegisteredUsers(channel.id)) {
            val member = guild.getMemberById(registeredUser).block()
            if (member == null) {
                Storage.removeUser(channel.id, registeredUser);
                continue
            }
            registeredMembersStr.add(member.displayName);
        }

        return message.channel
            .flatMap { it.createMessage(
                "Зарегистрированные игроки: $registeredMembersStr\n" +
                "Текущие правила: ${Storage.getRule(channel.id)}"
            ) }
            .then()
    }
}