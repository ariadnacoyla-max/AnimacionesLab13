package com.tecsup.animacioneslab13

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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

// Estados que utilizaremos en AnimatedContent.
enum class EstadoPantalla {
    CARGANDO,
    CONTENIDO,
    ERROR
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PantallaEjercicio4(modifier: Modifier = Modifier) {

    var estadoActual by remember {
        mutableStateOf(EstadoPantalla.CARGANDO)
    }

    val lila = Color(0xFF9575CD)
    val fondoLila = Color(0xFFF3E5F5)
    val lilaOscuro = Color(0xFF673AB7)

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
            color = lilaOscuro
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Ejercicio 4: AnimatedContent",
            fontSize = 19.sp,
            color = Color.DarkGray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(30.dp))

        // AnimatedContent realiza la transición entre estados.
        AnimatedContent(
            targetState = estadoActual,
            transitionSpec = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = 1000
                    )
                ) togetherWith fadeOut(
                    animationSpec = tween(
                        durationMillis = 500
                    )
                )
            },
            label = "CambioContenido"
        ) { estado ->

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
                    .background(
                        Color.White,
                        RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(20.dp)
                ) {

                    when (estado) {

                        EstadoPantalla.CARGANDO -> {
                            CircularProgressIndicator(
                                color = lila
                            )

                            Spacer(
                                modifier = Modifier.height(18.dp)
                            )

                            Text(
                                text = "Cargando...",
                                fontSize = 23.sp,
                                fontWeight = FontWeight.Bold,
                                color = lilaOscuro
                            )

                            Text(
                                text = "Espere un momento",
                                fontSize = 15.sp,
                                color = Color.Gray
                            )
                        }

                        EstadoPantalla.CONTENIDO -> {
                            Text(
                                text = "✓",
                                fontSize = 48.sp,
                                color = Color(0xFF4CAF50)
                            )

                            Text(
                                text = "¡Contenido cargado!",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF388E3C),
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = "La información está disponible",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }

                        EstadoPantalla.ERROR -> {
                            Text(
                                text = "!",
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE53935)
                            )

                            Text(
                                text = "¡Ocurrió un error!",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE53935),
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = "No se pudo cargar la información",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Selecciona un estado",
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(15.dp))

        // Botones para cambiar el estado.
        Button(
            onClick = {
                estadoActual = EstadoPantalla.CARGANDO
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = lila
            )
        ) {
            Text("Cargando", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                estadoActual = EstadoPantalla.CONTENIDO
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50)
            )
        ) {
            Text("Contenido", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                estadoActual = EstadoPantalla.ERROR
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE53935)
            )
        ) {
            Text("Error", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Entrada: fadeIn (1000 ms)\nSalida: fadeOut (500 ms)",
            fontSize = 14.sp,
            color = lilaOscuro,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Ejercicio4Preview() {
    PantallaEjercicio4()
}
