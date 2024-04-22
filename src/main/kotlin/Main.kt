import java.io.File
import kotlin.math.log

fun main(args: Array<String>) {
    val gestorConsola = GestorConsola()

    val gestorFicheros = FicherosLog(gestorConsola)


    val (formato, rutaBingoCentral) = Utilidades.comprobarArgumentos(args, gestorConsola)

    val bombo =
        if (rutaBingoCentral != null) {
            BomboCentral(rutaBingoCentral,gestorFicheros)
        } else {
            BomboLocal(gestorConsola)
        }

    val genVisualCarton =
        if (formato == "kf") {
            GeneradorVisualCartonKFormat()
        } else {
            GeneradorVisualCartonInterno()
        }

    val logBingo =
        if (rutaBingoCentral != null) {
            File(rutaBingoCentral)
        } else {
            gestorFicheros.crearFic(Utilidades.generarFicheroLogBingo(),Utilidades.getCabeceraLogoBingo())
        }

    if (logBingo != null){
        val txt = logBingo.listFiles { _, nombre -> nombre.endsWith(".txt") }?.maxByOrNull { it.lastModified() }

        if (txt != null){
            val bingo = Bingo(gestorConsola, bombo, gestorFicheros, txt, genVisualCarton)
            try {
                bingo.jugar()
            }
            catch (e: Exception) {
                gestorConsola.imprimir("Error ${e.message}")
            }
        }else{
            gestorConsola.imprimir("Error - No se encontro ningun archivo de texto ")
        }



    }else{
        gestorConsola.imprimir("Error - No se pudo generar log Bingo")
    }

}