
import java.io.File

/**
 * Implementación concreta de la interfaz IFicheros para manejar ficheros de texto.
 * Provee funcionalidades para operar con ficheros y directorios, específicamente para el formato de texto.
 *
 * @property consola Instancia de IEntradaSalida para imprimir mensajes de error o confirmación durante las operaciones de ficheros.
 */
class FicherosLog(private val consola: IConsola) : IFicheros {

    /**
     * Verifica si un directorio existe en la ruta especificada.
     *
     * @param ruta La ruta del directorio a verificar.
     * @return Verdadero si el directorio existe, falso de lo contrario.
     */
    override fun existeDir(ruta: String): Boolean {
        try {
            if (File(ruta).isDirectory) {
                return true
            }
        } catch (e: SecurityException) {
            consola.imprimir("Error al comprobar si existe el directorio $ruta: ${e.message}")
        }
        return false
    }

    /**
     * Verifica si un archivo existe en la ruta especificada.
     *
     * @param ruta La ruta del archivo a verificar.
     * @return Verdadero si el archivo existe, falso de lo contrario.
     */
    override fun existeFic(ruta: String): Boolean {
        try {
            if (File(ruta).isFile) {
                return true
            }
        } catch (e: SecurityException) {
            consola.imprimir("Error al comprobar si existe el fichero $ruta: ${e.message}")
        }
        return false
    }

    /**
     * Escribe información en un fichero especificado. Si el fichero no existe, no se crea automáticamente.
     *
     * @param fichero El fichero en el que se desea escribir.
     * @param info La información a escribir en el fichero.
     * @return Verdadero si la escritura fue exitosa, falso de lo contrario.
     */
    override fun escribir(fichero: File, info: String): Boolean {
        try {
            fichero.appendText(info)
        } catch (e: Exception) {
            consola.imprimir("Error al escribir en el archivo: ${e.message}")
            return false
        }
        return true
    }

    /**
     * Lee el contenido de un fichero y retorna una lista de strings, donde cada elemento representa una línea del fichero.
     *
     * @param fichero El fichero a leer.
     * @return Lista de strings con el contenido del fichero, o null si hubo un error al leer.
     */
    override fun leer(fichero: File): List<String>? {
        val lista : List<String>
        try {
            lista = fichero.readLines()
        } catch (e: Exception) {
            consola.imprimir("Error al leer las líneas del archivo: ${e.message}")
            return null
        }
        return lista
    }

    /**
     * Crea un directorio en la ruta especificada.
     *
     * @param ruta La ruta donde se desea crear el directorio.
     * @return Verdadero si la creación fue exitosa, falso si el directorio ya existe o si la creación falló.
     */
    override fun crearDir(ruta: String): Boolean {
        val dirRuta = File(ruta)
        try {
            if (!dirRuta.isDirectory) {
                if (!dirRuta.mkdirs()) {
                    return false
                }
            }
        } catch (e: Exception) {
            consola.imprimir("Error al crear el directorio $ruta: ${e.message}")
            return false
        }
        return true
    }

    /**
     * Crea un fichero en la ruta especificada, con la posibilidad de incluir información inicial y de sobreescribir un fichero existente.
     *
     * @param ruta La ruta donde se desea crear el fichero.
     * @param info Información inicial para escribir en el fichero al crearlo. Por defecto está vacío.
     * @param sobreescribir Si es verdadero, sobrescribe el fichero si ya existe; de lo contrario, no modifica el fichero existente.
     * @return El fichero creado, o null si la creación falló o si el fichero ya existe y no se eligió sobreescribir.
     */
    override fun crearFic(ruta: String, info: String, sobreescribir: Boolean): File? {
        val fichero = File(ruta)
        crearDir(fichero.parent)
        try {
            if (sobreescribir) {
                fichero.writeText(info)
            } else {
                fichero.createNewFile()
                if (info.isNotEmpty()) {
                    fichero.appendText(info)
                }
            }
        } catch (e: Exception) {
            consola.imprimir("Error al crear el fichero $ruta: ${e.message}")
            return null
        }
        return fichero
    }
}