/**
fun main(args: Array<String>) {
val consola = Consola()
val gestorFich = FicherosTxt()

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
 * */

class Carton {
    init {
        val posiciones = listaAleatoria()
    }

    companion object {
        /**
         * Estas son la combinaciones posibles que puede haber en el carton
         * son permutaciones, en este caso tenemos 27 numero y tomamos 18 numeros
         * función matemática de la permutación: (falta comprobar si esto es verdad:D)
         * P(m,n) = m! /(m-n)!
         * @author Nicolás De Gomar
        */
        private val combinaciones = listOf(
            Triple(1, 1, 3), Triple(1, 2, 2), Triple(1, 3, 1),
            Triple(2, 1, 2), Triple(2, 2, 1), Triple(3, 1, 1)
        )
        /**
         * esta es la combinacion elegida en este caso
         */
        private lateinit var combinacion: Triple<Int, Int, Int>
        /**
         * esta variable me marca que aqui tengo que poner dos numero en la parter de arriba del carton
         * ejemplo
         * fila 1: 1
         * fila 2: 1
         * fila 3: null
         * */
        var primerNumero:Int = 0
        /**
         * esta variable me marca que aqui tengo que poner dos numero en los extremos del carton
         * ejemplo
         * fila 1: 1
         * fila 2: null
         * fila 3: 1
         * */
        var segundoNumero:Int = 0
        /**
         * esta variable me marca que aqui tengo que poner dos numero en la parte de abajo del carton
         * ejemplo
         * fila 1: null
         * fila 2: 1
         * fila 3: 1
         * */
        var tercerNumero:Int = 0

        /**
         * funcion privada
         * cogemos una combinacion aleatoria posible
         * le asginamos esa combiancion a las 3 filas
         * */
        private fun obtenercombinacion() {
            combinacion = combinaciones.random()
            primerNumero = combinacion.first
            segundoNumero = combinacion.second
            tercerNumero = combinacion.third
        }

    }

    /**
     * función pone los numeros sobre el carton
     * */
    private fun listaAleatoria(): List<List<Int?>> {
        var numeros: MutableList<MutableList<Int?>> = mutableListOf(
            mutableListOf(null, null, null, null, null, null, null, null, null),
            mutableListOf(null, null, null, null, null, null, null, null, null),
            mutableListOf(null, null, null, null, null, null, null, null, null)
        )
        obtenercombinacion()
        val lista = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8).shuffled()
        var contador = 0
        for (i in lista) {
            if (contador < 2) {
                numeros = colocar3numeros(numeros, lista[i])
            } else {
                if (contador < 7) {
                    numeros = colocar2numeros(numeros, lista[i])
                } else {
                    numeros = colocar1numeros(numeros, lista[i])
                }
            }
            contador++

        }
        for (numeross in numeros){
            for (numero in numeross ){
                if (numero ==null){
                    print("   ")
                }else {
                    print(" $numero ")
                }

            }
            println()


        }
        println("----------------------")
        return numeros
    }

    private fun obtenerNumeros(numero :Int,cantidad:Int): List<Int> {
        val numeros = mutableListOf<Int>()
        var contador = 0
        if ( numero == 0){
            val lista = mutableListOf<Int>()
            for (i in 1..9){
                lista.add(i)
            }
            while (contador != cantidad){
                val number = lista.random().toString().toInt()
                if (number !in numeros){
                    numeros.add(number)
                    contador++
                }
            }
        }
        if ( numero == 8){
            val lista = mutableListOf<Int>()
            for (i in 80..90){
                lista.add(i)
            }
            while (contador != cantidad){
                val number = lista.random().toString().toInt()
                if (number !in numeros){
                    numeros.add(number)
                    contador++
                }
            }
        }
        else{
            val lista = mutableListOf<Int>()
            for (i in numero*10..numero*10+9){
                lista.add(i)
            }
            while (contador != cantidad){
                val number = lista.random().toString().toInt()
                if (number !in numeros){
                    numeros.add(number)
                    contador++
                }
            }
        }
        return numeros
    }

    private fun colocar3numeros(numeros: MutableList<MutableList<Int?>>, posicion: Int): MutableList<MutableList<Int?>> {
        val numero= obtenerNumeros(posicion,3).sorted()
        for (i in 0..2) {
            numeros[i][posicion] = numero[i]
        }
        return numeros
    }

    private fun colocar2numeros(numeros: MutableList<MutableList<Int?>>, posicion: Int): MutableList<MutableList<Int?>> {
        val numero= obtenerNumeros(posicion,2).sorted()
        if (primerNumero != 0) {
            primerNumero -= 1
            for (i in 0..1) {
                numeros[i][posicion] = numero[i]
            }
        } else {
            if (segundoNumero != 0) {
                segundoNumero -= 1
                numeros[0][posicion] = numero[0]
                numeros[2][posicion] = numero[1]
            } else {
                if (tercerNumero != 0) {
                    tercerNumero -= 1
                    for (j in 1..2) {
                        numeros[j][posicion] = numero[j-1]
                    }
                }
            }
        }
        return numeros
    }

    private fun colocar1numeros(numeros: MutableList<MutableList<Int?>>, posicion: Int): MutableList<MutableList<Int?>> {

        val numero = obtenerNumeros(posicion,1)
        val fila0 = numeros[0].filterNotNull().count()
        val fila1 = numeros[1].filterNotNull().count()
        val fila2 = numeros[2].filterNotNull().count()
        if (fila0 != 6 && fila0 < 6){
            numeros[0][posicion] = numero[0]
        }else{
            if (fila1 != 6 && fila1 < 6){
                numeros[1][posicion] = numero[0]
            }else{
                if (fila2 != 6 && fila2 < 6){
                    numeros[2][posicion] = numero[0]
                }
            }
        }
        return numeros
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