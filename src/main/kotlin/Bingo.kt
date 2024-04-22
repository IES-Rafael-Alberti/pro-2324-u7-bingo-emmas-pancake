import java.io.File

class Bingo(
    private val consola: IConsola,
    private val bombo: IBombo,
    private val gestorFichero: IFicheros,
    private val fichero: File
) {
    private var finJuego = false
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


    /** Cambia la cantidad de bolas que se sacan de la pila a 3 bolas
     */
    private fun cambiarGeneracionBolasA3() {
        if (bombo is IBomboPideBolas) {
            bombo.setNumbolas((NumBolas.LINEA))
        }
    }


    /** Cambia la cantidad de bolas que se sacan de la pila a 1 bola
     */
    private fun cambiarGeneracionBolasA1() {
        if (bombo is IBomboPideBolas) {
            bombo.setNumbolas((NumBolas.A1NUMERO))
        }
    }


    /** Ejecuta una partida de bingo hasta que un jugador complete uno de sus cartones
     */
    fun jugar() {
        val generador = GeneradorVisualCartonInterno()
        var ganador: String? = null
        var primeraLinea = false
        var primeraFinal = false
        var rondas = 0

        while (!finJuego){

            val listaNumeros = bombo.sacarBolas()
            for (num in listaNumeros) {
                rondas++
                consola.imprimir(" -*- Ronda $rondas -*- ")
                for (jugador in jugadores) {
                    jugador.marcarNumero(num)
                    consola.imprimir(jugador.nombre)
                    for (carton in jugador.listaCartones) {
                        consola.imprimir(generador.retornarCartonVisual(carton.casillas))
                    }

                    if (!primeraLinea && jugador.linea) {
                        cambiarGeneracionBolasA3()
                        gestorFichero.escribir(fichero, "${jugador.nombre} ha hecho línea\n")
                        primeraLinea = true
                    }

                    if (!primeraFinal && jugador.a1Numero) {
                        cambiarGeneracionBolasA1()
                        gestorFichero.escribir(fichero, "${jugador.nombre} a 1 bola\n")
                        primeraFinal = true
                    }

                    if (jugador.bingo) {
                        finJuego = true
                        ganador = jugador.nombre
                    }


                }
                Utilidades.pausar(consola)
            }
        }
        gestorFichero.escribir(fichero, "$ganador ha ganado!\n")

    }

}