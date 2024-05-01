interface IConsola{
    fun imprimir(msg: String, newLine: Boolean = true)
    fun pedirNombre(msg: String): String
    fun pedirNumero(msg: String,min:Int,max:Int): Int
    fun limpiar(nLines: Int)

}