class Bingo(
    private val consola: IConsola,
    private val bombo: IBombo,
    private val gestorFichero: IFicheros
) {

    init {
        val jugadores: List<Jugador> = crearJugadores(pedirCantidadJugadores())
    }

    companion object {
        private const val NOMBRE_JUGADOR_RED = "EMMAS_PANCAKE"
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

        if (bombo is IBomboPideBolas) {
            //Pedir num cartones

            listaJugadores.add(Jugador("GladOS", 2))

            for (i in 1..cantidadJugadores) {
                println("Introduce el nombre del jugador ${i + 1}: ")
                listaJugadores.add(Jugador(readln(), 2))
            }
        }
        else {
            listaJugadores.add(Jugador(NOMBRE_JUGADOR_RED, 2))
        }


        return listaJugadores
    }
}