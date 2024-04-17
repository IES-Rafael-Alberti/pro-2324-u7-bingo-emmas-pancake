

class GeneradorVisualCartonInterno() : IGeneradorVisualCarton {

    override fun retornarCartonVisual(casillas: Array<Array<Casilla?>>): String {
        var resultado = ""

        val linea = "-".repeat(Carton.ANCHURA) + "\n"
        resultado += linea

        for (fila in casillas) {
            resultado += "|    |    |    |    |    |    |    |    |    |\n"

            for (posicion in fila) {
                resultado += "| "
                if (posicion != null) {
                    resultado += String.format("%02d ", posicion.numero)
                } else {
                    resultado += "   "
                }
            }

            resultado += "|\n"
            resultado += "| "

            for (posicion in fila) {
                if (posicion != null && posicion.numeroSalido) {
                    resultado += "xx | "
                } else {
                    resultado += "   | "
                }
            }

            resultado += "\n"
            resultado += linea
        }

        return resultado
    }

}