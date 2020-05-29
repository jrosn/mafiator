import discord4j.core.DiscordClient
import discord4j.core.event.domain.lifecycle.ReadyEvent
import discord4j.core.event.domain.message.MessageCreateEvent
import mu.KotlinLogging
import reactor.core.publisher.Flux

private val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {
    if (args.isEmpty()) return

    val client = DiscordClient.create(args[0]).login().block() ?: return

    client.eventDispatcher.on(ReadyEvent::class.java)
        .subscribe { event: ReadyEvent ->
            val self = event.self
            logger.info { String.format("Logged in as %s", self.tag) }
        }

    val commands = arrayListOf(
        PingCommand(),
        RegisterCommand(),
        StatusCommand(),
        SetRuleCommand(),
        StartCommand(),
        UnregisterAllCommand(),
        UnregisterCommand()
    )

    client.eventDispatcher
        .on(MessageCreateEvent::class.java)
        .flatMap { event ->
            Flux.fromIterable(commands)
                .filter { event.message.content.startsWith(it.getCommandName()) }
                .flatMap { it.execute(event) }
                .next()
        }
        .subscribe()

    client.onDisconnect().block()
}
