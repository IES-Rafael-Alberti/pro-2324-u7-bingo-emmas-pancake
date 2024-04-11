interface Bombo {

    val bolas: MutableList<Int>

    fun meterBolas()

    fun sacarBolas(total: Int = 1): List<Int>

}