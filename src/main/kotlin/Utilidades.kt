import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Clase de utilidades para proporcionar funciones comunes utilizadas en el juego de Bingo.
 * Incluye métodos para comprobar argumentos de entrada, generar nombres de ficheros de log, y obtener configuraciones de juego.
 */
class Utilidades {

    companion object {

        /**
         * Comprueba y extrae los argumentos necesarios para configurar el juego.
         *
         * @param args Array de strings conteniendo los argumentos de entrada.
         * @param consola Interfaz de entrada/salida para comunicarse con el usuario.
         * @return Un par conteniendo el formato y la ruta del fichero si está especificado, o null si no hay fichero.
         */
        fun comprobarArgumentos(args: Array<String>, consola: IConsola) : Pair<String, String?>{
            var formato = "tx" // Valor por defecto
            var rutaFichero : String? = null

            // Iterar sobre los argumentos para identificar el formato deseado
            for (i in args.indices) {
                if (args[i] == "-f" && i + 1 < args.size) {
                    formato = args[i + 1]
                }
                else if (args[i] == "-b" && i + 1 < args.size) {
                    rutaFichero = args[i + 1]
                }
            }

            // Comprobar existencia del directorio de Bingo Central
            if (rutaFichero != null) {
                if (!File(rutaFichero).isDirectory) {
                    rutaFichero = null
                    consola.imprimir("*Error* ruta de fichero no encontrado para Bingo Central, pasamos a Bingo local")
                }
            }

            return Pair(formato, rutaFichero)
        }

        /**
         * Genera la ruta completa del fichero de log para el Bingo basándose en la fecha y hora actual.
         *
         * @return La ruta completa del fichero de log.
         */
        fun generarFicheroLogBingo() = "${generarNombreRutaRelativaLog()}\\${generarNombreArchivoLog()}"

        /**
         * Genera una ruta relativa basada en la fecha actual para almacenar logs.
         *
         * @return Ruta relativa para el log.
         */
        private fun generarNombreRutaRelativaLog(): String {
            val fechaActual = LocalDate.now()
            val formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return "logoBingo\\${fechaActual.format(formateador)}\\"
        }

        /**
         * Genera el nombre del archivo de log basado en la fecha y hora actual.
         *
         * @return Nombre del archivo de log.
         */
        private fun generarNombreArchivoLog(): String {
            val fechaHoraActual = LocalDateTime.now()
            val formateador = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
            val fechaHoraFormateada = fechaHoraActual.format(formateador)
            return "logBingo_$fechaHoraFormateada.txt"
        }

        /**
         * Genera una cabecera para el fichero de log del Bingo, incluyendo la fecha y hora actual.
         *
         * @return Cabecera del log del Bingo.
         */
        fun getCabeceraLogoBingo() : String {
            val now = LocalDateTime.now()
            val dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
            val formattedDateTimeLine = now.format(dateTimeFormatter)
            return "Juego del Bingo $formattedDateTimeLine\n\n"
        }

        /**
         * Solicita al usuario el número de jugadores que participarán en el juego.
         *
         * @param consola Interfaz de entrada/salida para comunicarse con el usuario.
         * @return El número de jugadores.
         */
        fun preguntarJugadores(consola: IConsola) : Int {
            return consola.pedirNumero("Introduzca el número de jugadores (1-3): ",1,3)
        }

        /**
         * Solicita al usuario el número de cartones por jugador.
         *
         * @param consola Interfaz de entrada/salida para comunicarse con el usuario.
         * @return El número de cartones por jugador.
         */
        fun preguntarCartones(consola: IConsola) : Int {
            return consola.pedirNumero("Introduzca el número de cartones (1-2): ",1,2)
        }

        /**
         * Obtiene la fecha y hora actual formateada como "dd/MM/yyyy HH:mm:ss".
         *
         * @return La fecha y hora actual formateada.
         */
        fun obtenerFechaHoraActual(): String {
            val ahora = LocalDateTime.now()
            val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
            return ahora.format(formato)
        }


        /** Pausa el programa hasta que el usuario pulse enter
         */
        fun pausar() {
            println("pulsa ENTER para continuar...")
            readln()
        }

    }

}