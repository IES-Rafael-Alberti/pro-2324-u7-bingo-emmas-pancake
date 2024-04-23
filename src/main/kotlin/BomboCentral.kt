import java.io.File

/**
 * Saca las bolas de un fichero. Sacar bolas igual que en local, misma funcion
 * Pasarle ruta del fichero xxxx a Bombocentral(xxxx). Enconctrar el ultimo modificado txt  -> bomboCentral_yyy_mm_dd.txt
 *
 * Bingocentral genera el fichero bomboCentral_yyy_mm_dd  y bombocentralUsuario yyy mm dd --> estos estaran en \\PcProfe\Bingo
 * Bingo central empieza. Kotlin mainkt -b \\PcProfe\Bingo. Comprueba que es en red y no comprueba nada, solo busca los dos ficheros, usando bomboRed/bombocentral.
 * EN bombo ccentral, crear Ibombored con IGetficherobombo -> leer  y  IgetFicheroUSUARIO -> escribir
 *
 *
 * Bombocentral yyyy mm dd.txt EN red -->:
 * usar list.size para leer los numeros
 * quedarse en bucle leyendo
 * coger con readline todas las lineas del fichero, y usar lista.last para coger la ultima liniea de todo el fichero
 * una vez leido, nuestro jugar() hace lo mismo que en local, comprueba linea etc
 *
 * Comprobar si la variable coincide con el num de lineas del txt
 * En red: 1j y 2 cartone
 * Leer en un fichero los numeros, y escribir en el otro de users cada ronda
 *
  */


class BomboCentral(private val rutaBingoCentral: String, private val gestorFicheros: IFicheros) : IBombo {

    override var numRondas = 0
    override fun sacarBolas(): List<Int> {
        var contador = 0
        val numeros = mutableListOf<Int>()
        val fichero = File(rutaBingoCentral).listFiles { _, nombre -> nombre.startsWith("bomboBingoCentral_") }?.maxByOrNull { it.lastModified() }
        if (fichero != null)
        do{
            val lineas = gestorFicheros.leer(fichero)
            if (lineas!= null && numRondas == lineas.size){
                lineas.last().split(" ").map { it.toIntOrNull() }.forEach {numeroAAnadir-> if (numeroAAnadir !=null) numeros.add(numeroAAnadir) }
                numRondas++
            }
            println("xd")
            Thread.sleep(500)
            contador++
        }while (numeros.size <= 0 && contador < 120)
        return numeros
    }
}