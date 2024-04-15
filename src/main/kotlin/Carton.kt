class Carton {
    // TODO: REVISAR
/*
    val numeros = arrayOf(
            arrayOf(null, null, null, null, null, null, null, null, null),
            arrayOf(null, null, null, null, null, null, null, null, null),
            arrayOf(null, null, null, null, null, null, null, null, null)
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
    }

    private fun listaAleatoria(): List<Int> {
        val numeros: List<List<Int?>> = mutableListOf(
            mutableListOf(null, null, null, null, null, null, null, null, null),
            mutableListOf(null, null, null, null, null, null, null, null, null),
            mutableListOf(null, null, null, null, null, null, null, null, null)
        )
        val lista = listOf(0, 1, 2 ,3 ,4 ,5 ,6, 7, 8).shuffled()
        var contador = 0
        for (i in lista){
            if (contador <2){
                colocar3numeros(numeros,lista[i])
            }else{
                if (contador < 7){

                }else{

                }
            }
           contador++
        }
    }

    private fun colocar3numeros(numeros: List<List<Int?>>, posicion: Int) {
        var numeros = numeros.toMutableList()
        for (i in 0..2){
            val lista = numeros[i].toMutableList()
            lista[posicion]= 1
            numeros[i] = lista
        }

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

    fun comprobarNumero(num: Int){
        
    }

}