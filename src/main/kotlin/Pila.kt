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
        return if (isEmpty()) {
            null
        } else {
            elementos.removeAt(elementos.size - 1)
        }
    }


    /** Devuelve la última bola agregada
     *
     * @return número de la bola
     */
    fun peek(): Int? {
        return elementos.lastOrNull()
    }


    /** Devuelve la cantidad de bolas de la pila
     *
     * @return número de bolas
     */
    fun size(): kotlin.Int {
        return elementos.size
    }


    /** Verifica si la pila está vacía
     *
     * @return depende de si está vacía o no
     */
    fun isEmpty(): Boolean {
        return elementos.isEmpty()
    }


    /** Elimina todos los elementos de la pila
     */
    fun clear() {
        elementos.clear()
    }

}