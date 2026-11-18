package com.tecsup.animacioneslab13

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaEjercicio3(modifier: Modifier = Modifier) {

    // Controla si el cuadro está expandido.
    var expandido by remember { mutableStateOf(false) }

    // Permite experimentar con el orden de los modificadores.
    var offsetPrimero by remember { mutableStateOf(true) }

    val lila = Color(0xFF9575CD)
    val fondoLila = Color(0xFFF3E5F5)

    // Anima el tamaño del cuadro.
    val tamanoAnimado by animateDpAsState(
        targetValue = if (expandido) 150.dp else 90.dp,
        animationSpec = tween(durationMillis = 1000),
        label = "AnimacionTamano"
    )

    // Anima la posición horizontal del cuadro.
    val posicionX by animateDpAsState(
        targetValue = if (expandido) 65.dp else (-65).dp,
        animationSpec = tween(durationMillis = 1000),
        label = "AnimacionPosicionX"
    )

    // Anima la posición vertical del cuadro.
    val posicionY by animateDpAsState(
        targetValue = if (expandido) 35.dp else (-35).dp,
        animationSpec = tween(durationMillis = 1000),
        label = "AnimacionPosicionY"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(fondoLila)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Laboratorio 13",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF673AB7)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Ejercicio 3: Tamaño y Posición",
            fontSize = 19.sp,
            color = Color.DarkGray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Área que contiene el cuadro animado.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp),
            contentAlignment = Alignment.Center
        ) {

            // Permite comparar el orden de offset y size.
            val modificadorCuadro =
                if (offsetPrimero) {
                    Modifier
                        .offset(x = posicionX, y = posicionY)
                        .size(tamanoAnimado)
                } else {
                    Modifier
                        .size(tamanoAnimado)
                        .offset(x = posicionX, y = posicionY)
                }

            Box(
                modifier = modificadorCuadro
                    .background(
                        color = lila,
                        shape = RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (expandido) "GRANDE" else "PEQUEÑO",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Botón que activa el movimiento y el cambio de tamaño.
        Button(
            onClick = {
                expandido = !expandido
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = lila
            )
        ) {
            Text(
                text = if (expandido)
                    "Volver al inicio"
                else
                    "Mover y agrandar",
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Orden de modificadores",
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = { offsetPrimero = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (offsetPrimero)
                        lila else Color(0xFFE1BEE7)
                )
            ) {
                Text(
                    "Offset → Size",
                    color = if (offsetPrimero)
                        Color.White else Color.DarkGray
                )
            }

            Button(
                onClick = { offsetPrimero = false },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!offsetPrimero)
                        lila else Color(0xFFE1BEE7)
                )
            ) {
                Text(
                    "Size → Offset",
                    color = if (!offsetPrimero)
                        Color.White else Color.DarkGray
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Tamaño: ${tamanoAnimado.value.toInt()} dp",
            fontSize = 14.sp,
            color = Color(0xFF673AB7)
        )

        Text(
            text = "Posición X: ${posicionX.value.toInt()} dp",
            fontSize = 14.sp,
            color = Color(0xFF673AB7)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Ejercicio3Preview() {
    PantallaEjercicio3()
}
