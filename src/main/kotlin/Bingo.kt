class Bingo {

    init {
        val jugadores: List<Jugador> = crearJugadores(pedirCantidadJugadores())
    }


    private fun pedirCantidadJugadores(): Int {
        var cantidadJugadores = 0
        while (cantidadJugadores > 3 || cantidadJugadores < 1) {
            println("Introduce la cantidad de jugadores (1-3): ")
            cantidadJugadores = readln().toInt()
        }

        return cantidadJugadores
    }


    private fun crearJugadores(cantidadJugadores: Int): List<Jugador> {
        val listaJugadores = mutableListOf<Jugador>()
        listaJugadores.add(Jugador("GladOS"))

        for (i in 1..cantidadJugadores) {
            println("Introduce el nombre del jugador ${i + 1}: ")
            listaJugadores.add(Jugador(readln()))
        }

        return listaJugadores
    }

}