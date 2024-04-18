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
                if (!linea || !carton.linea){
                    if (carton.comprobarLinea(nombre)){
                        linea = true
                    }
                }
                if (!a3Numeros || !carton.a3Numeros){
                    if (carton.comprobarA3Numeros(nombre)){
                        a3Numeros = true
                    }
                }
                if (!bingo || !carton.bingo){
                    if (carton.comprobarBingo(nombre)){
                        bingo = true
                    }
                }
            }
    }

    fun agregarCartones(num:Int) {
        for (i in 1..num){
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
