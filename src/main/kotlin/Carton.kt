class Carton {
    // TODO: REVISAR
    /*
        val numeros = arrayOf(
                arrayOf(9, 9, 9, 9, 9, 9, 9, 9, 9),
                arrayOf(9, 9, 9, 9, 9, 9, 9, 9, 9),
                arrayOf(9, 9, 9, 9, 9, 9, 9, 9, 9)
        )
        Triple(1, 1, 3),
            Triple(1, 2, 2),
            Triple(1, 3, 1),
            Triple(2, 1, 2),
            Triple(2, 2, 1),
            Triple(3, 1, 1)
    */
    init {
        val posiciones = listaAleatoria()
        println(posiciones[0])
        println(posiciones[1])
        println(posiciones[2])
    }

    companion object {
        val combinaciones = listOf(
            Triple(1, 1, 3), Triple(1, 2, 2), Triple(1, 3, 1),
            Triple(2, 1, 2), Triple(2, 2, 1), Triple(3, 1, 1)
        )
        private lateinit var combinacion: Triple<Int, Int, Int>
        var primerNumero:Int = 0
        var segundoNumero:Int = 0
        var tercerNumero:Int = 0
        private fun obtenercombinacion() {
            combinacion = combinaciones.random()
            primerNumero = combinacion.first
            segundoNumero = combinacion.second
            tercerNumero = combinacion.third
        }

    }

    private fun listaAleatoria(): List<List<Int?>> {
        var numeros: List<List<Int?>> = mutableListOf(
            mutableListOf(9, 9, 9, 9, 9, 9, 9, 9, 9),
            mutableListOf(9, 9, 9, 9, 9, 9, 9, 9, 9),
            mutableListOf(9, 9, 9, 9, 9, 9, 9, 9, 9)
        )
        obtenercombinacion()
        val lista = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
        var contador = 0
        for (i in lista) {
            if (contador < 1) {
                numeros = colocar3numeros(numeros, lista[i])
            } else {
                if (contador < 6) {
                    numeros = colocar2numeros(numeros, lista[i])
                } else {
                    numeros = colocar1numeros(numeros, lista[i])
                }
            }
            contador++
        }
        return numeros
    }

    private fun colocar3numeros(numeros: List<List<Int?>>, posicion: Int): MutableList<List<Int?>> {
        val numero = numeros.toMutableList()
        for (i in 0..2) {
            val lista = numeros[i].toMutableList()
            lista[posicion] = 1
            numero[i] = lista
        }
        return numero
    }

    private fun colocar2numeros(numeros: List<List<Int?>>, posicion: Int): MutableList<List<Int?>> {

        val numero = numeros.toMutableList()
        if (primerNumero != 0) {
            primerNumero -= 1
            for (j in 0..1) {
                val lista = numero[j].toMutableList()
                lista[posicion] = 1
                numero[j] = lista
            }
        } else {
            if (segundoNumero != 0) {
                segundoNumero -= 1
                var lista = numero[0].toMutableList()
                lista[posicion] = 1
                numero[0] = lista
                lista = numero[2].toMutableList()
                lista[posicion] = 1
                numero[2] = lista
            } else {
                if (tercerNumero != 0) {
                    tercerNumero -= 1
                    for (j in 1..2) {
                        val lista = numero[j].toMutableList()
                        lista[posicion] = 1
                        numero[j] = lista
                    }
                }
            }
        }
        return numero
    }

    private fun colocar1numeros(numeros: List<List<Int?>>, posicion: Int): MutableList<List<Int?>> {

        val numero = numeros.toMutableList()
        val fila0 = numero[0].count()
        val fila1 = numero[1].count()
        val fila2 = numero[2].count()
        if (fila0 > 6){
            val lista = numeros[0].toMutableList()
            lista[posicion] = 1
            numero[0] = lista
        }else{
            if (fila1 > 6){
                val lista = numeros[1].toMutableList()
                lista[posicion] = 1
                numero[1] = lista
            }else{
                if (fila2 > 6){
                    val lista = numeros[2].toMutableList()
                    lista[posicion] = 1
                    numero[2] = lista
                }
            }
        }
        return numero
    }

    private fun crearCarton() {

    }

    private fun rellenarCarton() {

    }

    /**
     * Formatea el numero con un 0 delante si es menor a 10. 4 -> 04
     */
    fun Int.formatear(): String {
        return if (this < 10) "0$this" else this.toString()
    }

    fun comprobarNumero(num: Int) {

    }

}