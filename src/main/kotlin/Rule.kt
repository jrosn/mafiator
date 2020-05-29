data class Rule(val roles: Map<String, Int>) {
    fun residualRole(): String {
        roles.asSequence()
            .forEach { entry ->
                if (entry.value == 0) return entry.key
            }
        return "Пусто"
    }
}

