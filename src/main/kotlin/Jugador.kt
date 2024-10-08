class Jugador(val nombre: String, numCartones: Int):IJugador {

    var listaCartones: MutableList<Carton> = mutableListOf()
    val id: String = generarIdUnica()
    private var linea:Boolean = false
    private var a1Numero:Boolean = false
    private var bingo:Boolean = false

    init{
        agregarCartones(numCartones)
    }

    companion object {
        private var contadorIds = 0

        fun generarIdUnica(): String {
            contadorIds++
            return "J$contadorIds"
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
                    if (carton.comprobarA1Numeros()){
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

    private fun agregarCartones(num:Int) {
        for (i in 1..num){
            listaCartones.add(Carton())
        }
    }
}

interface IJugador {
    fun marcarNumero(numero: Int)
}
