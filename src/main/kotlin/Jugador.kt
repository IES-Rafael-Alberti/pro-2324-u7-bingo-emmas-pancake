class Jugador(val nombre: String, numCartones: Int):IJugador {

    var listaCartones: MutableList<Carton> = mutableListOf()
    val id: Int = generarIdUnica()
    var linea:Boolean = false
    var a3Numeros:Boolean = false
    var bingo:Boolean = false

    init{
        agregarCartones(numCartones)
    }

    companion object {
        private var contadorIds = 0

        fun generarIdUnica(): Int {
            contadorIds++
            return contadorIds
        }
    }

    override fun marcarNumero(numero: Int){
            listaCartones.forEach { carton ->
                carton.comprobarNumero(numero)
                if (!linea){
                    if (carton.comprobarLinea()){
                        linea = true
                    }
                }
                if (!a3Numeros){
                    if (carton.comprobarA3Numeros()){
                        a3Numeros = true
                    }
                }
                if (!bingo){
                    if (carton.comprobarBingo()){
                        bingo = true
                    }
                }
            }
    }

    fun agregarCartones(num:Int) {
        for (i in 0..num){
            val carton = Carton()
            listaCartones.add(carton)
        }
    }

    override fun toString(): String {
        return "Jugador $id - $nombre\n $listaCartones"
    }
}

interface IJugador {
    fun marcarNumero(numero: Int)
}
