fun main(args: Array<String>) {
    val gestorConsola = GestorConsola()
    val bomboLocal = BomboLocal(gestorConsola)
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

    val logBingo = Utilidades.generarFicheroLogBingo()


}