import com.sun.org.apache.xpath.internal.operations.Bool

class GestorConsola: IConsola {
    override fun imprimir(msg: String, newLine: Boolean) {
        if (newLine) println(msg) else print(msg)
    }

    override fun pedirNombre(msg: String): String {
        var nombre: String?
        do {
            imprimir(msg, false)
            nombre = readln()
            if (nombre.isBlank()) {
                imprimir("No puede estar vacio. ")
                nombre = null
            }
        }while (nombre == null)
        return nombre
    }

    override fun pedirNumero(msg: String,min:Int,max:Int): Int {
        var num: Int?
        do {
            imprimir( msg, false)
            num = readln().toIntOrNull()
            if (num != null) {
                if (num < min || num > max) {
                    imprimir("Error - Entrada invalida.  ")
                    num = null
                }
            }
        }while (num == null)
        return num
    }


    override fun limipiar(nLines: Int) {
        imprimir("".repeat(nLines))

    }
}