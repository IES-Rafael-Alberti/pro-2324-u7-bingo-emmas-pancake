import java.io.File

class Bingo(
    private val consola: IConsola,
    private val bombo: IBombo,
    private val gestorFichero: IFicheros,
    private val fichero: File,
    private val genVisualCarton: IGeneradorVisualCarton
) {
    private var finJuego = false
    private val jugadores: List<Jugador> = crearJugadores()

    companion object {
        private const val NOMBRE_JUGADOR_RED = "EMMANUEL_ZZZ"
        private const val ACIERTOS_PARA_BINGO = 18
    }


    /** Crea una lista con todos los jugadores
     *
     * @return La lista con todos los jugadores
     */
    private fun crearJugadores(): List<Jugador> {
        val listaJugadores = mutableListOf<Jugador>()

        if (Utilidades.isOffline(bombo)) {
            val cantidadJugadores = Utilidades.preguntarJugadores(consola)

            val numCartones = Utilidades.preguntarCartones(consola)

            listaJugadores.add(Jugador("CPU", numCartones))

            for (i in 1..cantidadJugadores) {
                println("Introduce el nombre del jugador ${i + 1}: ")
                listaJugadores.add(Jugador(readln(), numCartones))
            }
        } else {
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


    /** Confirma la conexion al fichero imprimiendo un ok
     */
    private fun confirmarConexion() {
        for (jugador in jugadores) {
            gestorFichero.escribir(fichero, "${jugador.nombre} - ok\n")
        }
    }


    /** Devuelve un cartón en una cadena
     *
     * @param jugador un jugador
     * @param carton el cartón del jugador
     * @param numCarton el número del cartón en la partida
     *
     * @return cartón visual
     */
    private fun imprimirCarton(jugador: Jugador, carton: Carton, numCarton: Int): String {
        return "         CARTÓN ${jugador.nombre} - ${String.format("%02d", numCarton)}" +
                " (${carton.aciertos} de ${ACIERTOS_PARA_BINGO})\n" +
                genVisualCarton.retornarCartonVisual(carton.casillas)

    }


    /** Devuelve el mensaje de un jugador que ha acertado un número
     *
     * @param jugador el jugador que ha acertado un número
     * @param carton el cartón en el que se ha encontrado el número
     * @param numCarton el número del cartón en la partida
     * @param num el número que ha coincidido en el cartón
     *
     * @return mensaje de jugador
     */
    private fun imprimirMensaje(jugador: Jugador, carton: Carton, numCarton: Int, num: Int): String {
        return "$num - ${jugador.id} (${jugador.nombre}):  " +
                "cartón${String.format("%02d", numCarton)} " +
                "(${carton.coordenadasAciertos(num)?.joinToString(" ")})\n"
    }


    /** Imprime en el fichero y en la consola la información necesaria según el tipo de partida
     *
     * @param mensajesAciertos mensaje de cada acierto por cada cartón
     * @param cartones cadena de todos los cartones
     * @param mensajeOnline mensaje de la información necesaria para cada ronda de las partidas online
     */
    private fun mostrarResultadosRondas(mensajesAciertos: String, cartones: String, mensajeOnline: String) {
        if (!Utilidades.isOffline(bombo)) {
            gestorFichero.escribir(fichero, mensajeOnline + "\n")
            consola.imprimir(mensajesAciertos)
            consola.imprimir(cartones)
        } else {
            consola.imprimir(mensajesAciertos)
            gestorFichero.escribir(fichero, mensajesAciertos)
            consola.imprimir(cartones)
            gestorFichero.escribir(fichero, cartones)
        }

    }


    /** Ejecuta una partida de bingo hasta que un jugador complete uno de sus cartones
     */
    fun jugar() {
        var ganador: String?
        var primeraLinea = false
        var a1numero = false
        var ronda: Int

        confirmarConexion()

        while (!finJuego) {
            val listaNumeros = bombo.sacarBolas()
            var numCarton = 0
            var mensajesAciertos = ""
            var cartones = ""
            var mensajeOnline = ""
            var mensajeLogroOnline = ""
            var mensajeLogro = ""

            ronda = bombo.numRondas - 1
            val mensajeRonda = "Ronda $ronda - ${listaNumeros.joinToString(" ")}\n\n"

            consola.imprimir(mensajeRonda)
            if (Utilidades.isOffline(bombo)) {
                gestorFichero.escribir(fichero, mensajeRonda)
            }

            for (jugador in jugadores) {
                mensajeOnline += "$NOMBRE_JUGADOR_RED - ronda $ronda"

                for (carton in jugador.listaCartones) {
                    var aciertosPorRonda = 0
                    numCarton++

                    for (num in listaNumeros) {

                        if (carton.contiene(num)) {
                            carton.comprobarNumero(num)
                            mensajesAciertos += imprimirMensaje(jugador, carton, numCarton, num)
                            aciertosPorRonda++
                        }

                    }

                    mensajeOnline += " - $aciertosPorRonda"
                    cartones += imprimirCarton(jugador, carton, numCarton)


                    if (!finJuego) {
                        if (carton.comprobarBingo()) {
                            finJuego = true
                            ganador = jugador.nombre
                            mensajeLogro = "¡¡¡BINGO de ${jugador.id} ($ganador)!!!"
                            mensajeLogroOnline += " - Bingo"
                        }
                    } else if (!a1numero) {
                        if (carton.comprobarA1Numeros()) {
                            cambiarGeneracionBolasA1()
                            a1numero = true
                            mensajeLogro = "¡${jugador.id} (${jugador.nombre}) a 1 bola!\n"
                            mensajeLogroOnline += " - Solo1"
                        }
                    } else if (!primeraLinea) {
                        if (carton.comprobarLinea()) {
                            cambiarGeneracionBolasA3()
                            primeraLinea = true
                            mensajeLogro = "¡Línea de ${jugador.id} (${jugador.nombre})!\n"
                            mensajeLogroOnline += " - Linea"
                        }
                    }

                }

            }

            if (mensajesAciertos == "") {
                mensajesAciertos = "Sin aciertos...\n"
            } else if (mensajeLogro != "") {
                mensajesAciertos += mensajeLogro
            }

            mensajeOnline += mensajeLogroOnline

            mostrarResultadosRondas(mensajesAciertos, cartones, mensajeOnline)

            if (Utilidades.isOffline(bombo)) {
                Utilidades.pausar(consola)
            }

        }
    }

}