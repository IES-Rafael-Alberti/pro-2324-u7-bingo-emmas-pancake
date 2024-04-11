class Jugador(val nombre: String){
    var listaCartones: MutableList<Carton> = mutableListOf()


    fun marcarNumero(numero: Int) {
        listaCartones.forEach {Carton->
            Carton.comprobarNumero(numero)
        }
    }
}
interface