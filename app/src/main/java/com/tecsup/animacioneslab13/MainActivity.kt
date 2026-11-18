
package com.tecsup.animacioneslab13

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import com.tecsup.animacioneslab13.ui.theme.AnimacionesLab13Theme

private val Lila = Color(0xFF9575CD)
private val FondoLila = Color(0xFFF3E5F5)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AnimacionesLab13Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = FondoLila
                ) { innerPadding ->
                    PantallaEjercicio4(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PantallaAnimacion(modifier: Modifier = Modifier) {

    // Controla si el cuadro debe mostrarse u ocultarse.
    var visible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Laboratorio 13",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF673AB7)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Ejercicio 1: AnimatedVisibility",
            fontSize = 19.sp,
            color = Color.DarkGray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                // Alterna la visibilidad al presionar el botón.
                visible = !visible
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Lila
            )
        ) {
            Text(
                text = if (visible) "Ocultar cuadro" else "Mostrar cuadro",
                fontSize = 17.sp
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Aplica una transición suave de entrada y salida.
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(800)),
            exit = fadeOut(animationSpec = tween(800))
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(
                        color = Lila,
                        shape = RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "¡Hola Ariadna!",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaAnimacionPreview() {
    AnimacionesLab13Theme {
        PantallaAnimacion()
    }
}
