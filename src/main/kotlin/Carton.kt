class Carton {

    init {
        println()
    }

    companion object {
        val posiciones = listOf(0, 1, 2 ,3 ,4 ,5 ,6, 7, 8).shuffled()
    }

    private fun crearCarton() {

    }

    private fun rellenarCarton() {

    }

    private fun Int.formatear(): Int{
        if (this < 10) return "0$this".toInt() else return this

    }

}