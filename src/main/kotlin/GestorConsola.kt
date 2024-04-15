import com.sun.org.apache.xpath.internal.operations.Bool

interface IConsola{
    fun imprimir(msg: String, newLine: Boolean = true)
    fun pedirNombre(msg: String)
    fun pedirNumero(msg: String)
    fun limipiar(nLines: Int)

}

class GestorConsola: IConsola {
    override fun imprimir(msg: String, newLine: Boolean) {
        TODO("Not yet implemented")
    }

    override fun pedirNombre(msg: String) {
        TODO("Not yet implemented")
    }

    override fun pedirNumero(msg: String) {
        TODO("Not yet implemented")
    }

    override fun limipiar(nLines: Int) {
        TODO("Not yet implemented")
    }
}