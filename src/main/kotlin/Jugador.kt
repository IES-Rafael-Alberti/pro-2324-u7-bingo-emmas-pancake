class Jugador(val nombre: String, numCartones: Int):IJugador {

    var listaCartones: MutableList<Carton> = mutableListOf()
    val id: Int = generarIdUnica()
    var linea:Boolean = false
    var a1Numero:Boolean = false
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
                    if (carton.comprobarLinea()){
                        linea = true
                    }
                }
                if (!a1Numero || !carton.a1Numero){
                    if (carton.comprobarA3Numeros()){
                        a1Numero = true
                    }
                }
                if (!bingo || !carton.bingo){
                    if (carton.comprobarBingo()){
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
}

interface IJugador {
    fun marcarNumero(numero: Int)
}
