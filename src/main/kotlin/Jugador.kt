class Jugador(val nombre: String){
    var listaCartones: MutableList<Carton> = mutableListOf()


    fun marcarNumero(numero: Int) {
        cartones.forEach { Carton ->
            if (numero in carton.numeros) {
                carton.numerosMarcados.add(numero)
            }
        }
    }
}