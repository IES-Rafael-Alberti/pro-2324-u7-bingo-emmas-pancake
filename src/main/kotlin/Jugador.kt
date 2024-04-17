class Jugador(val nombre: String):IJugador {
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


    override fun toString(): String {
        return "Jugador $id - $nombre\n $listaCartones"
    }
}

interface IJugador {
    fun marcarNumero(numero: Int)
}
