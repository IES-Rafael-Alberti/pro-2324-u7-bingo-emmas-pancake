class Carton {

    val casillas: Array<Array<Casilla?>> = Array(FILAS) { Array(COLUMNAS) { null } }
    var linea = false
    var a1Numero = false
    var bingo = false
    var aciertos = 0
    var aciertosPorRondas=0
    init {
        listaAleatoria()
    }

    companion object {
        const val FILAS = 3
        const val COLUMNAS = 9
        const val NUMEROS_COLUMNAS = 6
        const val ANCHURA = 46
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
        var primerNumero: Int = 0

        /**
         * esta variable me marca que aqui tengo que poner dos numero en los extremos del carton
         * ejemplo
         * fila 1: 1
         * fila 2: null
         * fila 3: 1
         * */
        var segundoNumero: Int = 0

        /**
         * esta variable me marca que aqui tengo que poner dos numero en la parte de abajo del carton
         * ejemplo
         * fila 1: null
         * fila 2: 1
         * fila 3: 1
         * */
        var tercerNumero: Int = 0

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
     * función pone los numeros sobre la lista posiciones
     * */
    private fun listaAleatoria() {
        obtenercombinacion()
        val lista = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8).shuffled()
        //este contador me dice cuantas filas de 3 de 2 de 1 tiene el carton
        for ((contador, i) in lista.withIndex()) {
            //el contador de 0 a 1 va a poner 3 numeros por columnas
            if (contador < 2) {
                colocar3numeros(lista[i])
            } else {
                //el contador de 2 a 6 va a poner 2 numeros por columnas
                if (contador < 7) {
                    colocar2numeros(lista[i])
                } else {
                    //el contador de 7 a 8 va a poner 1 numeros por columnas
                    colocar1numeros(lista[i])
                }
            }

        }
    }

    /**
     * esta funcion te devuelve
     * numeros aleatorios
     * */
    private fun obtenerNumeros(numero: Int, cantidad: Int): List<Int> {
        val numeros = mutableListOf<Int>()
        var contador = 0
        if (numero == 0) {
            val lista = mutableListOf<Int>()
            for (i in 1..9) {
                lista.add(i)
            }
            while (contador != cantidad) {
                val number = lista.random().toString().toInt()
                if (number !in numeros) {
                    numeros.add(number)
                    contador++
                }
            }
        }
        if (numero == 8) {
            val lista = mutableListOf<Int>()
            for (i in 80..90) {
                lista.add(i)
            }
            while (contador != cantidad) {
                val number = lista.random().toString().toInt()
                if (number !in numeros) {
                    numeros.add(number)
                    contador++
                }
            }
        } else {
            val lista = mutableListOf<Int>()
            for (i in numero * 10..numero * 10 + 9) {
                lista.add(i)
            }
            while (contador != cantidad) {
                val number = lista.random().toString().toInt()
                if (number !in numeros) {
                    numeros.add(number)
                    contador++
                }
            }
        }
        return numeros
    }

    /**
     *
     * */
    private fun colocar3numeros(posicion: Int) {
        val numero = obtenerNumeros(posicion, 3).sorted()
        for (i in 0..2) {
            casillas[i][posicion] = Casilla(numero[i])
        }
    }

    /**
     *
     * */
    private fun colocar2numeros(posicion: Int) {
        val numero = obtenerNumeros(posicion, 2).sorted()
        if (primerNumero != 0) {
            primerNumero -= 1
            for (i in 0..1) {
                casillas[i][posicion] = Casilla(numero[i])
            }
        } else {
            if (segundoNumero != 0) {
                segundoNumero -= 1
                casillas[0][posicion] = Casilla(numero[0])
                casillas[2][posicion] = Casilla(numero[1])
            } else {
                if (tercerNumero != 0) {
                    tercerNumero -= 1
                    for (j in 1..2) {
                        casillas[j][posicion] = Casilla(numero[j - 1])
                    }
                }
            }
        }
    }

    /**
     *
     * */
    private fun colocar1numeros(posicion: Int) {

        val numero = obtenerNumeros(posicion, 1)
        val fila0 = casillas[0].filterNotNull().count()
        val fila1 = casillas[1].filterNotNull().count()
        val fila2 = casillas[2].filterNotNull().count()
        if (fila0 != NUMEROS_COLUMNAS && fila0 < NUMEROS_COLUMNAS) {
            casillas[0][posicion] = Casilla(numero[0])
        } else {
            if (fila1 != NUMEROS_COLUMNAS && fila1 < NUMEROS_COLUMNAS) {
                casillas[1][posicion] = Casilla(numero[0])
            } else {
                if (fila2 != NUMEROS_COLUMNAS && fila2 < NUMEROS_COLUMNAS) {
                    casillas[2][posicion] = Casilla(numero[0])
                }
            }
        }
    }

    /**
     *
     * */
    fun comprobarNumero(num: Int) {
        aciertosPorRondas = 0
        for (casilla in casillas) {
            for (numero in casilla){
                if (numero != null) {
                    if (numero.numero == num){
                        numero.numeroSalido=true
                        aciertosPorRondas++
                        aciertos++
                    }
                }
            }
        }
    }

    /**
     *
     * */
    fun comprobarLinea(): Boolean {
        for (lineas in casillas){
            var contadorNumerosSalidos = 0
            for (numero in lineas){
                if (numero != null){
                    if(numero.numeroSalido){
                        contadorNumerosSalidos++
                    }
                }
            }
            if (contadorNumerosSalidos == 6){
                linea= true
                return true
            }
        }
        return false

    }

    /**
     *
     * */
    fun comprobarA3Numeros(): Boolean {
        var contadorNumerosSalidos = 0
        for (lineas in casillas){
            for (numero in lineas){
                if (numero != null){
                    if(numero.numeroSalido){
                        contadorNumerosSalidos++
                    }
                }
            }
            if (contadorNumerosSalidos >= 17){
                a1Numero = true
                return true
            }
        }
        return false

    }

    /**
     *
     * */
    fun comprobarBingo(): Boolean {
        var contadorNumerosSalidos = 0
        for (lineas in casillas){
            for (numero in lineas){
                if (numero != null){
                    if(numero.numeroSalido){
                        contadorNumerosSalidos++
                    }
                }
            }
            if (contadorNumerosSalidos == 18){
                bingo=true
                return true
            }
        }
        return false

    }

    /**
     *
     * */
    fun coordenadasAciertos(num:Int): List<Int>? {
        var fila = 1
        for (filas in casillas){
            var posicion =1
            for (numeros in filas){
                if (numeros != null) {
                    if (numeros.numero == num){
                        return listOf(fila,posicion)
                    }
                }
                posicion++
            }
            fila++
        }
        return null
    }


    fun contiene(num: Int): Boolean {
        for (filas in casillas){
            for (numero in filas){
                if (numero != null) {
                    if (numero.numero == num) {
                        return true
                    }
                }
                }
            }
        return false

        }

    }