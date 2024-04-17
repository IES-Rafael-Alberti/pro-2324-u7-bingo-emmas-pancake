fun main(args: Array<String>) {
    val consola = Consola()
    val gestorConsola = GestorConsola()
    val bomboLocal = IBomboLocal(gestorConsola)
    val bingo = Bingo()

    val (formato, rutaFichero, ficheroBingoCentral) = Utilidades.comprobarArgumentos(args, consola)

    val bombo =
        if (rutaFichero != null && ficheroBingoCentral != null) {
            BomboCentral(ficheroBingoCentral, gestorFich)
        } else {
            BomboLocal()

        }

    val genVisualCarton =
        if (formato == "kf") {
            GeneradorVisualCartonKFormat()
        } else {
            GeneradorVisualCartonInterno()
        }

    val logBingo = Utilidades.generarFicheroLogBingo()
}