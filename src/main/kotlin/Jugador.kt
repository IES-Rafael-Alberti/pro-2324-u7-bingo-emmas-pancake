class Jugador(val nombre: String, numCartones: Int):IJugador {
    init{
        agregarCartones(numCartones)
    }
    var listaCartones: MutableList<Carton> = mutableListOf()
    val id: Int = generarIdUnica()

    companion object {
        private var contadorIds = 0

        fun generarIdUnica(): Int {
            contadorIds++
            return contadorIds
        }
    }


    override fun marcarNumero(numero: Int) {
        listaCartones.forEach { carton ->
            carton.comprobarNumero(numero)
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
    fun marcarNumero(numero: Int)
}
