/**
 * Clase que representa una pila genérica.
 * @param T el tipo de elementos que contendrá la pila.
 * @property pila Lista mutable que almacena los elementos de la pila.
 */
class Pila <T>{

    private val pila = mutableListOf<T>()



    /**
     * Devuelve el elemento en la cima de la pila.
     * @return el elemento en la cima de la pila o null si la pila está vacía.
     */
    private fun top(): T?{
        return pila.lastOrNull()
    }

    /**
     * * Inserta un elemento en la cima de la pila.
     * @param elemento el elemento a insertar.
     */
    fun push(elemento: T){
        pila.add(elemento)
    }


    /**
     * Elimina y devuelve el elemento en la cima de la pila, o nulo si no hay
     * @return el elemento eliminado o null si la pila está vacía.
     */
    fun pop(): T?{
        if (isEmpty()) {
            return null
        } else {
            return pila.removeAt(pila.size - 1)
        }
    }


    /**
     * Verifica si la pila está vacía
     * @return true si la pila está vacía, false en caso contrario.
     */
    private fun isEmpty(): Boolean{
        return pila.isEmpty()
    }


    /**
     *
     * @return El tamaño de la pila.
     */
    fun size(): Int{
        return pila.size
    }
}