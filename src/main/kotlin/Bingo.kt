import java.io.File

class Bingo(
    private val consola: IConsola,
    private val bombo: IBombo,
    private val gestorFichero: IFicheros,
    private val fichero: File
) {
    private var finJuego = false
    private var lineaCantada = false
    private var bingoCercaCantado = false
    private val jugadores: List<Jugador> = crearJugadores(Utilidades.preguntarJugadores(consola))

    companion object {
        private const val NOMBRE_JUGADOR_RED = "EMMANUEL_ZZZ"
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


    /** Cambia la cantidad de bolas que se sacan de la pila según lo que haya ocurrido en la partida
     *
     * @param jugador Se usa un jugador para ver si ha hecho linea o está a 3 bolas de acabar
     */
    private fun cambiarGeneracionBolas(jugador: Jugador) {
        if (bombo is IBomboPideBolas) {

            if (jugador.linea && !lineaCantada) {
                bombo.setNumbolas(NumBolas.LINEA)
                lineaCantada = true
            }

            if (jugador.a1Numero && !bingoCercaCantado) {
                bombo.setNumbolas(NumBolas.A1NUMERO)
                bingoCercaCantado = true
            }
        }

    }


    /** Ejecuta una partida de bingo hasta que un jugador complete uno de sus cartones
     */
    fun jugar() {
        val generador = GeneradorVisualCartonInterno()
        while (!finJuego){

            val listaNumeros = bombo.sacarBolas()
            println("$listaNumeros")
            for (num in listaNumeros) {

                for (jugador in jugadores) {
                    jugador.marcarNumero(num)
                    println(jugador.nombre)
                    for (carton in jugador.listaCartones) {
                        print(generador.retornarCartonVisual(carton.casillas))
                    }

                    if (!lineaCantada) {
                        cambiarGeneracionBolas(jugador)
                        gestorFichero.escribir(fichero, "${jugador.nombre} ha hecho línea\n")
                    } else if (!bingoCercaCantado) {
                        cambiarGeneracionBolas(jugador)
                        gestorFichero.escribir(fichero, "${jugador.nombre} a 1 bola\n")
                    }

                    if (jugador.bingo) {
                        gestorFichero.escribir(fichero, "${jugador.nombre} ha ganado!\n")
                        finJuego = true
                    }

                }

            }

        }
    }

}