fun main(args: Array<String>) {
    //val carton = Carton()
    val gestorConsola = GestorConsola()
    val bomboLocal = BomboLocal(gestorConsola)

    bomboLocal.sacarBolas()
}