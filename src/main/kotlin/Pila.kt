open class Pila<Int> {
    private val elementos: MutableList<Int> = mutableListOf()

    /** Agrega una bola a la pila
     *
     * @param bola número de bola
     */
    fun push(bola: Int) {
        elementos.add(bola)
    }


    /** Elimina la última bola agregada
     *
     * @return número de la bola
     */
    fun pop(): Int? {
        return if (isPilaEmpty()) {
            null
        } else {
            elementos.removeAt(elementos.size - 1)
        }
    }


    /** Verifica si la pila está vacía
     *
     * @return depende de si está vacía o no
     */
    private fun isPilaEmpty(): Boolean {
        return elementos.isEmpty()
    }

}