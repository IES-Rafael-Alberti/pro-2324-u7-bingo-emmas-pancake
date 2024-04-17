class Jugador(val nombre: String, numCartones: Int):IJugador {
    init{
        agregarCartones(numCartones)
    }
    var listaCartones: MutableList<Carton> = mutableListOf()
    val id: Int = generarIdUnica()
    var linea:Boolean = false
    var a3Numeros:Boolean = false
    var bingo:Boolean = false

    companion object {
        private var contadorIds = 0

        fun generarIdUnica(): Int {
            contadorIds++
            return contadorIds
        }
    }
    fun comprobarLineaBingo(){
        listaCartones.forEach{carton->
            val resultado= carton.comprobarLineaBingo()
        }
    }

    override fun marcarNumero(numero: Int){
            listaCartones.forEach { carton ->
                carton.comprobarNumero(numero)
                val resultado=carton.comprobarLineaBingo()
            }
    }

    fun agregarCartones(num:Int) {
        for (i in 0..num){
            listaCartones.add(Carton())
        }
    }

    override fun toString(): String {
        return "Jugador $id - $nombre\n $listaCartones"
    }
}

interface IJugador {
    fun marcarNumero(numero: List<Int>)
}
