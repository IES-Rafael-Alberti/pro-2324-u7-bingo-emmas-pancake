import java.io.File

class Bingo(
    private val consola: IConsola,
    private val bombo: IBombo,
    private val gestorFichero: IFicheros,
    private val fichero: File,
    private val genVisualCarton: IGeneradorVisualCarton
) {
    private var finJuego = false
    private val jugadores: List<Jugador> = crearJugadores(Utilidades.preguntarJugadores(consola))
    private val offline = (bombo is IBomboPideBolas)

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

        if (offline) {
            val numCartones = Utilidades.preguntarCartones(consola)

            listaJugadores.add(Jugador("CPU", numCartones))

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


    private fun confirmarConexion() {
        for (jugador in jugadores) {
            gestorFichero.escribir(fichero, "${jugador.nombre} - ok\n")
        }
    }


    private fun imprimirInicioPartida() {

    }


    /** Ejecuta una partida de bingo hasta que un jugador complete uno de sus cartones
     */
    fun jugar() {
        val generador = GeneradorVisualCartonInterno()
        var ganador: String? = null
        var primeraLinea = false
        var primeraFinal = false
        var ronda: Int

        confirmarConexion()

        while (!finJuego){
            val listaNumeros = bombo.sacarBolas()
            var mensaje = ""
            var cartones = ""

            ronda = bombo.numRondas

            consola.imprimir("Ronda $ronda - ${listaNumeros.joinToString(" ")}\n")
            if (bombo is IBomboPideBolas) {
                gestorFichero.escribir(fichero, "Ronda $ronda - ${listaNumeros.joinToString(" ")}\n")
            }

            for (num in listaNumeros) {

                for (jugador in jugadores) {
                    var numCarton = 1
                    jugador.marcarNumero(num)
                    for (carton in jugador.listaCartones) {
                        cartones +="         " +
                                "CARTÓN ${jugador.nombre} - 0$numCarton (${carton.aciertos} de 18)\n" +
                                generador.retornarCartonVisual(carton.casillas)

                        if (bombo is IBomboPideBolas && carton.contiene(num)) {
                            mensaje+="$num - ${jugador.id} (${jugador.nombre}): cartón0$numCarton" +
                                    "(${carton.coordenadasAciertos(num)?.joinToString(" ")})\n"
                        }

                        numCarton++
                    }
                    consola.imprimir(mensaje + cartones)

                    val lista = mutableListOf<Int>()
                    for (carton in jugador.listaCartones) {
                        if (bombo is IBomboPideBolas) {
                            gestorFichero.escribir(fichero, "         " +
                                    "CARTÓN ${jugador.nombre} - 0$numCarton (${carton.aciertos} de 18)\n" +
                                    generador.retornarCartonVisual(carton.casillas))
                        }else{
                            lista.add(carton.aciertosPorRondas)
                        }
                    }
                    if (bombo !is IBomboPideBolas) {
                        gestorFichero.escribir(
                            fichero,
                            "$NOMBRE_JUGADOR_RED - ronda ${bombo.numRondas} - ${lista[0]} - ${lista[1]}"
                        )
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