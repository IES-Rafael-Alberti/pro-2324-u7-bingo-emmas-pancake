fun main(args: Array<String>) {
    val gestorConsola = GestorConsola()

    val gestorFicheros = FicherosLog(gestorConsola)


    val (formato, rutaBingoCentral) = Utilidades.comprobarArgumentos(args, gestorConsola)

    val bombo =
        if (rutaBingoCentral != null) {
            BomboCentral()
        } else {
            BomboLocal(gestorConsola)
        }

    val genVisualCarton =
        if (formato == "kf") {
            GeneradorVisualCartonKFormat()
        } else {
            GeneradorVisualCartonInterno()
        }

    val logBingo = gestorFicheros.crearFic(Utilidades.generarFicheroLogBingo(),Utilidades.getCabeceraLogoBingo())

    if (logBingo != null){
        val bingo = Bingo(gestorConsola, bombo, gestorFicheros)
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