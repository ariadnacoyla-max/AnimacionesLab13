package com.tecsup.animacioneslab13

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
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
fun PantallaEjercicio2(modifier: Modifier = Modifier) {

    // Estado que controla el color del cuadro.
    var esAzul by remember { mutableStateOf(true) }

    // Estado que permite elegir entre Tween y Spring.
    var usarSpring by remember { mutableStateOf(false) }

    // Colores utilizados en el ejercicio.
    val azul = Color(0xFF2196F3)
    val verde = Color(0xFF4CAF50)
    val lila = Color(0xFF9575CD)
    val fondoLila = Color(0xFFF3E5F5)

    // Animación suave del color utilizando Tween o Spring.
    val colorAnimado by animateColorAsState(
        targetValue = if (esAzul) azul else verde,
        animationSpec = if (usarSpring) {
            spring()
        } else {
            tween(durationMillis = 1200)
        },
        label = "AnimacionColor"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(fondoLila)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Título principal.
        Text(
            text = "Laboratorio 13",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF673AB7)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Ejercicio 2: animateColorAsState",
            fontSize = 19.sp,
            color = Color.DarkGray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Cuadro que cambia suavemente entre azul y verde.
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(
                    color = colorAnimado,
                    shape = RoundedCornerShape(20.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (esAzul) "AZUL" else "VERDE",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Botón que cambia el color del cuadro.
        Button(
            onClick = {
                esAzul = !esAzul
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = lila
            )
        ) {
            Text(
                text = "Cambiar color",
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Tipo de animación",
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Botones para seleccionar Tween o Spring.
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Botón Tween.
            Button(
                onClick = {
                    usarSpring = false
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!usarSpring)
                        lila
                    else
                        Color(0xFFE1BEE7)
                )
            ) {
                Text(
                    text = "Tween",
                    color = if (!usarSpring)
                        Color.White
                    else
                        Color.DarkGray
                )
            }

            // Botón Spring.
            Button(
                onClick = {
                    usarSpring = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (usarSpring)
                        lila
                    else
                        Color(0xFFE1BEE7)
                )
            ) {
                Text(
                    text = "Spring",
                    color = if (usarSpring)
                        Color.White
                    else
                        Color.DarkGray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Muestra el tipo de animación seleccionado.
        Text(
            text = if (usarSpring)
                "Animación seleccionada: Spring"
            else
                "Animación seleccionada: Tween",
            fontSize = 14.sp,
            color = Color(0xFF673AB7)
        )

    } // Fin del Column

} // Fin de PantallaEjercicio2

@Preview(showBackground = true)
@Composable
fun Ejercicio2Preview() {
    PantallaEjercicio2()
}
