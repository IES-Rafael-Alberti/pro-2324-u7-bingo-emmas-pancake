class Bingo(
    private val consola: IConsola,
    private val bombo: IBombo,
    private val gestorFichero: IFicheros
) {
    private var finJuego = false
    private var lineaCantada = false
    private var bingoCercaCantado = false
    private val jugadores: List<Jugador> = crearJugadores(Utilidades.preguntarJugadores(consola))

    companion object {
        private const val NOMBRE_JUGADOR_RED = "EMMANUEL_MVP"
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
            val numCartones = Utilidades.preguntarCartones(consola)

            listaJugadores.add(Jugador("GladOS", numCartones))

            for (i in 1..cantidadJugadores) {
                println("Introduce el nombre del jugador ${i + 1}: ")
                listaJugadores.add(Jugador(readln(), numCartones))
            }
        }
        else {
            listaJugadores.add(Jugador(NOMBRE_JUGADOR_RED, 2))
        }

        return listaJugadores
    }


    private fun cambiarGeneracionBolas(jugador: Jugador) {
        if (bombo is IBomboPideBolas) {

            if (jugador.linea && !lineaCantada) {
                bombo.setNumbolas(NumBolas.LINEA)
                lineaCantada = true
            }

            if (jugador.a3Numeros && !bingoCercaCantado) {
                bombo.setNumbolas(NumBolas.A3NUMEROS)
                bingoCercaCantado = true
            }
        }

    }


    fun jugar() {
        val generador = GeneradorVisualCartonInterno()
        while (!finJuego){

            val listaNumeros = bombo.sacarBolas()
            println("$listaNumeros")
            for (num in listaNumeros) {

                for (jugador in jugadores) {
                    jugador.marcarNumero(num)
                    println("${jugador.nombre}")
                    for (carton in jugador.listaCartones) {
                        print(generador.retornarCartonVisual(carton.casillas))
                    }

                    if (!lineaCantada) {
                        cambiarGeneracionBolas(jugador)
                    } else if (!bingoCercaCantado) {
                        cambiarGeneracionBolas(jugador)
                    }

                    if (jugador.bingo) {
                        finJuego = true
                    }

                }

            }

        }
    }

}