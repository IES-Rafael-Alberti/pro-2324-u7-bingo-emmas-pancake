class BomboLocal(private val gestorConsola: GestorConsola) : Bombo {
    private val bomboLocal = Pila<Int>()

    init {
        meterBolas()
    }


    override fun meterBolas() {
        val bolas = (1..90).toList().shuffled()

        for (bola in bolas) {
            bomboLocal.push(bola)
        }
    }


    override fun sacarBolas(total: Int): List<Int> {
        val bolasSacadas = mutableListOf<Int>()

        for (i in 1..total) {
            val bolaSacada = bomboLocal.pop()
            if (bolaSacada != null){
                bolasSacadas.add(bolaSacada)
            }else{
                gestorConsola.imprimir("La pila esta vacia.")
            }
        }

        return bolasSacadas
    }

}