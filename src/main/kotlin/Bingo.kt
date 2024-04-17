class Bingo {

    init {
        val jugadores: List<Jugador> = crearJugadores(pedirCantidadJugadores())
    }


    /** Pide al usuario la cantidad de jugadores que forman la partida
     *
     * @return cantidad de jugadores de la partida sin contar la IA
     */
    private fun pedirCantidadJugadores(): Int {
        var cantidadJugadores = 0
        while (cantidadJugadores > 3 || cantidadJugadores < 1) {
            println("Introduce la cantidad de jugadores (1-3): ")
            cantidadJugadores = readln().toInt()
        }

        return cantidadJugadores
    }


    /** Crea una lista con todos los jugadores
     *
     * @param cantidadJugadores Cantidad de jugadores sin contar con la IA
     *
     * @return La lista con todos los jugadores
     */
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