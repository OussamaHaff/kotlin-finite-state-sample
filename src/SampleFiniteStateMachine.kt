fun main() {
    var actualState: State = Disconnected()
    println(actualState)

    actualState = actualState.consumeAction(Action.Login())
    println(actualState)
}

interface State {

    fun consumeAction(action: Action): State

}

sealed class Action {
    class Login : Action()
    class Logout : Action()
    class Refresh : Action()
}

class Disconnected : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.Login -> Connected()
            else -> this
        }
    }
}

class Connected : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.Logout -> Disconnected()
            is Action.Refresh -> Actualized()
            is Action.Login -> this
        }
    }
}

class Actualized : State {
    override fun consumeAction(action: Action): State {
        return when (action) {
            is Action.Logout -> Disconnected()
            is Action.Login -> this
            is Action.Refresh -> Actualized()
        }
    }
}