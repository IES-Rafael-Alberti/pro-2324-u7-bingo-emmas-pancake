import java.io.File

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
        val bingo = Bingo(gestorConsola, bombo, gestorFicheros, logBingo, genVisualCarton)
        try {
            bingo.jugar()
        }
        catch (e: Exception) {
            gestorConsola.imprimir("Error ${e.message}")
        }

    }else{
        gestorConsola.imprimir("Error - No se pudo generar log Bingo")
    }

}