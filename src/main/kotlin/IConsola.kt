interface IConsola{
    fun imprimir(msg: String, newLine: Boolean = true)
    fun pedirNombre(msg: String): String
    fun pedirNumero(msg: String): Int
    fun limipiar(nLines: Int)

}