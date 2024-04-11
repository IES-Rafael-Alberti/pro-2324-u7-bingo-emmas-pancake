class BomboLocal : Bombo {
    override val bolas: MutableList<Int> = mutableListOf()

    init {
        meterBolas()
    }


    override fun meterBolas() {
        for (i in 1..90) {
            bolas.add(i)
        }
    }


    override fun sacarBolas(total: Int): List<Int> {
        for (i in 1..total) {
            val bolaAleatoria = bolas[1]

        }

        //val bolasElegidas = bolas.subList(0, total)
        //bolas.removeAll(bolasElegidas)
        //return bolasElegidas
    }

}
