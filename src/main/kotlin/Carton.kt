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

    /**
     * Formatea el numero con un 0 delante si es menor a 10. 4 -> 04
     */
    fun Int.formatear(): String{
        return if (this < 10) "0$this" else this.toString()

    }

}