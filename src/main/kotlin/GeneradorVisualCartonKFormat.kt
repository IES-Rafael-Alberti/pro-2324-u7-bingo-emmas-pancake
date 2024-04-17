

import de.m3y.kformat.Table
import de.m3y.kformat.table

class GeneradorVisualCartonKFormat() : IGeneradorVisualCarton {

    override fun retornarCartonVisual(casillas: Array<Array<Casilla?>>): String {

        val cabecera = (1..9).map { it.toString().padStart(2, '0') }

        return table {
            header(*cabecera.toTypedArray())

            casillas.forEach { fila ->
                row(
                    *(fila.map { "" }.toTypedArray())
                )
                row(
                    *(fila.map { casilla ->
                        casilla?.numero?.toString()?.padStart(2, '0')
                            ?: ""
                    }.toTypedArray())
                )
                row(
                    *(fila.map { casilla ->
                        casilla?.let { if (it.acertada) "XX" else "" }
                            ?: ""
                    }.toTypedArray())
                )
            }

            hints {
                cabecera.forEach {
                    alignment(it.toString(), Table.Hints.Alignment.CENTER)
                }
                borderStyle = Table.BorderStyle.SINGLE_LINE
            }
        }.render(StringBuilder()).toString()

    }

}