class Jugador(val nombre: String):IJugador {
    var listaCartones: MutableList<Carton> = mutableListOf()


    override fun marcarNumero(numero: Int) {
        listaCartones.forEach { carton ->
            carton.comprobarNumero(numero)
        }
    }
}

interface IJugador {
    fun marcarNumero(numero: Int)
}