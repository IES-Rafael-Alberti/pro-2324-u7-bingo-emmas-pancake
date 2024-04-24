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

    companion object {
        private const val NOMBRE_JUGADOR_RED = "EMMANUEL_ZZZ"
        private const val ACIERTOS_PARA_BINGO = 18
    }


    /** Crea una lista con todos los jugadores
     *
     * @param cantidadJugadores Cantidad de jugadores sin contar con la IA
     *
     * @return La lista con todos los jugadores
     */
    private fun crearJugadores(cantidadJugadores: Int): List<Jugador> {
        val listaJugadores = mutableListOf<Jugador>()

        if (Utilidades.isOffline(bombo)) {
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
        return "\n         CARTÓN ${jugador.nombre} - ${String.format("%02d", numCarton)}" +
                " (${carton.aciertos} de ${ACIERTOS_PARA_BINGO}\n)" +
                genVisualCarton.retornarCartonVisual(carton.casillas) + "\n\n"

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


    private fun mostrarResultadosRondas(mensajesAciertos: String, cartones: String) {
        if (!Utilidades.isOffline(bombo)) {
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
        var ganador: String? = null
        var primeraLinea = false
        var primeraFinal = false
        var ronda: Int

        confirmarConexion()

        while (!finJuego){
            val listaNumeros = bombo.sacarBolas()
            var numCarton = 0
            var mensajesAciertos = ""
            var cartones = ""

            ronda = bombo.numRondas - 1
            val mensajeRonda = "Ronda $ronda - ${listaNumeros.joinToString(" ")}\n\n"

            consola.imprimir(mensajeRonda)
            if (Utilidades.isOffline(bombo)) {
                gestorFichero.escribir(fichero, mensajeRonda)
            }

            for (jugador in jugadores) {

                for (carton in jugador.listaCartones) {
                    numCarton++

                    for (num in listaNumeros) {

                        if (carton.contiene(num)) {
                            carton.comprobarNumero(num)
                            mensajesAciertos += imprimirMensaje(jugador, carton, numCarton, num)
                        }

                    }

                    cartones += imprimirCarton(jugador, carton, numCarton)

                }

            }

            if (mensajesAciertos == "") {
                mensajesAciertos = "Sin aciertos...\n"
            }

            mostrarResultadosRondas(mensajesAciertos, cartones)



            for (num in listaNumeros) {

                for (jugador in jugadores) {
                    var numCarton = 1
                    jugador.marcarNumero(num)
                    for (carton in jugador.listaCartones) {
                        cartones +="         " +
                                "CARTÓN ${jugador.nombre} - 0$numCarton (${carton.aciertos} de 18)\n" +
                                genVisualCarton.retornarCartonVisual(carton.casillas)
                        
                        if (Utilidades.isOffline(bombo) && carton.contiene(num)) {
                            mensajesAciertos+="$num - ${jugador.id} (${jugador.nombre}): cartón0$numCarton" +
                                    "(${carton.coordenadasAciertos(num)?.joinToString(" ")})\n"
                        }

                        numCarton++
                    }
                    consola.imprimir(mensajesAciertos + cartones)

                    val lista = mutableListOf<Int>()
                    for (carton in jugador.listaCartones) {
                        if (Utilidades.isOffline(bombo)) {
                            gestorFichero.escribir(fichero, "         " +
                                    "CARTÓN ${jugador.nombre} - 0$numCarton (${carton.aciertos} de 18)\n" +
                                    genVisualCarton.retornarCartonVisual(carton.casillas))
                        }else{
                            lista.add(carton.aciertosPorRondas)
                        }
                    }
                    if (!Utilidades.isOffline(bombo)) {
                        gestorFichero.escribir(
                            fichero,
                            "$NOMBRE_JUGADOR_RED - ronda ${bombo.numRondas} - ${lista[0]} - ${lista[1]}\n"
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

            }

            if (Utilidades.isOffline(bombo)) {
                Utilidades.pausar(consola)
            }
        }
        gestorFichero.escribir(fichero, "$ganador ha ganado!\n")

    }

}