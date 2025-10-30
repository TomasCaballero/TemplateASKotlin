package com.example.parcialtp3.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.parcialtp3.ui.theme.FinGreen

/**
 * Logo de FinWise con gráfico de barras y línea de tendencia
 */
@Composable
fun FinWiseLogo(
    modifier: Modifier = Modifier,
    size: Dp = 80.dp,
    color: Color = FinGreen
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasWidth = this.size.width
        val canvasHeight = this.size.height
        val strokeWidth = 6f

        // Barras verticales
        // Barra 1 (izquierda) - de abajo hasta 34.6666
        drawLine(
            color = color,
            start = Offset(canvasWidth * 0.166f, canvasHeight * 0.833f),
            end = Offset(canvasWidth * 0.166f, canvasHeight * 0.541f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        // Barra 2 - de abajo hasta 26.6666
        drawLine(
            color = color,
            start = Offset(canvasWidth * 0.375f, canvasHeight * 0.833f),
            end = Offset(canvasWidth * 0.375f, canvasHeight * 0.416f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        // Barra 3 - de abajo hasta 42.6666
        drawLine(
            color = color,
            start = Offset(canvasWidth * 0.583f, canvasHeight * 0.833f),
            end = Offset(canvasWidth * 0.583f, canvasHeight * 0.666f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        // Barra 4 (derecha) - de abajo hasta 18.6666
        drawLine(
            color = color,
            start = Offset(canvasWidth * 0.791f, canvasHeight * 0.833f),
            end = Offset(canvasWidth * 0.791f, canvasHeight * 0.291f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        // Línea de tendencia (zigzag)
        val path = Path().apply {
            // Empezar en 10.6667, 24
            moveTo(canvasWidth * 0.166f, canvasHeight * 0.375f)
            // Línea a 24, 16
            lineTo(canvasWidth * 0.375f, canvasHeight * 0.25f)
            // Línea a 37.3333, 29.3333
            lineTo(canvasWidth * 0.583f, canvasHeight * 0.458f)
            // Línea a 53.3333, 10.6666
            lineTo(canvasWidth * 0.833f, canvasHeight * 0.166f)
        }

        drawPath(
            path = path,
            color = color,
            style = Stroke(
                width = strokeWidth,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )

        // Flecha en la esquina superior derecha
        // Línea horizontal de la flecha: de 42.6667 a 53.3333 en x=10.6666
        drawLine(
            color = color,
            start = Offset(canvasWidth * 0.666f, canvasHeight * 0.166f),
            end = Offset(canvasWidth * 0.833f, canvasHeight * 0.166f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        // Línea vertical de la flecha: de 10.6666 a 21.3333 en y
        drawLine(
            color = color,
            start = Offset(canvasWidth * 0.833f, canvasHeight * 0.166f),
            end = Offset(canvasWidth * 0.833f, canvasHeight * 0.333f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}
