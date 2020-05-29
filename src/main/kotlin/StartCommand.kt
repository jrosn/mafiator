import discord4j.core.event.domain.message.MessageCreateEvent
import reactor.core.publisher.Mono
import java.security.SecureRandom
import kotlin.collections.ArrayList

class StartCommand : ICommand {
    override fun getCommandName(): String {
        return "!start"
    }

    override fun execute(event: MessageCreateEvent): Mono<Void> {
        val channel = event.message.channel.block()!!

        val registeredUsers = Storage.getRegisteredUsers(channel.id)
        val rule = Storage.getRule(channel.id)

        val roles = ArrayList<String>()
        for (role in rule.roles) {
            for (i in 0 until role.value) {
                roles.add(role.key)
            }
        }
        val residualRoleCount = if (registeredUsers.size - roles.size < 0) {
            0
        } else {
            registeredUsers.size - roles.size
        }

        for (i in 0 until residualRoleCount) {
            roles.add(rule.residualRole())
        }

        val rnd = SecureRandom()
        for (registeredUser in registeredUsers) {
            val selectedRoleIdx = rnd.nextInt(roles.size)
            val selectedRole = roles[selectedRoleIdx]
            event.client.getUserById(registeredUser)
                .flatMap { it.privateChannel }
                .flatMap { it.createMessage("Ваша роль: $selectedRole") }
                .block();
            roles.remove(selectedRole)
        }

        return Mono.empty();
    }
}