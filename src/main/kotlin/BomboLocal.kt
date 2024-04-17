class BomboLocal(private val gestorConsola: GestorConsola) : IBombo, IBomboPideBolas {
    private val bomboLocal = Pila<Int>()
    private var numBolas = NumBolas.ALETORIO

    init {
        meterBolas()
    }


    override fun meterBolas() {
        val bolas = (1..90).toList().shuffled()

        for (bola in bolas) {
            bomboLocal.push(bola)
        }

    }


    override fun setNumbolas(numBolas: NumBolas) {
        this.numBolas = numBolas
    }


    override fun sacarBolas(): List<Int> {
        val bolasSacadas = mutableListOf<Int>()

        val total = (numBolas.min..numBolas.max).random()

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