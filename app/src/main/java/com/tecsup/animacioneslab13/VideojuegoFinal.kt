
package com.tecsup.animacioneslab13

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// Estados del videojuego.
enum class EstadoJuegoFinal {
    INICIO,
    JUGANDO,
    VICTORIA,
    DERROTA
}

private val Lila = Color(0xFF9575CD)
private val LilaOscuro = Color(0xFF673AB7)
private val FondoLila = Color(0xFFF3E5F5)
private val Dorado = Color(0xFFFFC107)

// --------------------------------------------------
// PANTALLA PRINCIPAL DEL VIDEOJUEGO
// --------------------------------------------------

@Composable
fun PantallaVideojuegoFinal(
    modifier: Modifier = Modifier
) {
    var estado by remember {
        mutableStateOf(EstadoJuegoFinal.INICIO)
    }

    var jugador by remember { mutableIntStateOf(1) }
    var estrella by remember { mutableIntStateOf(0) }
    var puntos by remember { mutableIntStateOf(0) }
    var vidas by remember { mutableIntStateOf(3) }

    var estrellaVisible by remember {
        mutableStateOf(true)
    }

    var mensaje by remember {
        mutableStateOf("¡Atrapa las estrellas!")
    }

    var controlesActivos by remember {
        mutableStateOf(true)
    }

    var corazonVisible by remember {
        mutableStateOf(false)
    }

    var corazonRoto by remember {
        mutableStateOf(false)
    }

    var mostrarPunto by remember {
        mutableStateOf(false)
    }

    var eventoCorazon by remember {
        mutableIntStateOf(0)
    }

    var eventoEstrella by remember {
        mutableIntStateOf(0)
    }

    var eventoPunto by remember {
        mutableIntStateOf(0)
    }

    // ANIMACIÓN 1: movimiento con rebote.
    val movimientoX by animateDpAsState(
        targetValue = when (jugador) {
            0 -> (-85).dp
            1 -> 0.dp
            else -> 85.dp
        },
        animationSpec = spring(
            dampingRatio = 0.55f,
            stiffness = 350f
        ),
        label = "MovimientoJugador"
    )

    // ANIMACIÓN 2: cambio de tamaño.
    val tamanoJugador by animateDpAsState(
        targetValue = if (puntos % 2 == 0) {
            65.dp
        } else {
            80.dp
        },
        animationSpec = spring(
            dampingRatio = 0.5f,
            stiffness = 300f
        ),
        label = "TamanoJugador"
    )

    // ANIMACIÓN 3: cambio de color.
    val colorJugador by animateColorAsState(
        targetValue = if (puntos % 2 == 0) {
            Lila
        } else {
            Color(0xFFEC407A)
        },
        animationSpec = tween(700),
        label = "ColorJugador"
    )

    // ANIMACIÓN 4: estrella flotante.
    val infinito = rememberInfiniteTransition(
        label = "EstrellaFlotante"
    )

    val flotacion by infinito.animateFloat(
        initialValue = -6f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(900),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Flotacion"
    )

    val brillo by infinito.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(650),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Brillo"
    )

    // Animaciones manuales del corazón y temblor.
    val escalaCorazon = remember {
        Animatable(1f)
    }

    val temblor = remember {
        Animatable(0f)
    }

    fun reiniciarJuego() {
        jugador = 1
        estrella = 0
        puntos = 0
        vidas = 3

        estrellaVisible = true
        corazonVisible = false
        corazonRoto = false
        mostrarPunto = false
        controlesActivos = true

        mensaje = "¡Atrapa las estrellas!"
        estado = EstadoJuegoFinal.JUGANDO
    }

    // ANIMACIÓN 5: corazón latiendo y rompiéndose.
    LaunchedEffect(eventoCorazon) {
        if (eventoCorazon > 0) {
            corazonRoto = false
            escalaCorazon.snapTo(1f)
            corazonVisible = true

            repeat(3) {
                escalaCorazon.animateTo(
                    targetValue = 1.4f,
                    animationSpec = tween(150)
                )

                escalaCorazon.animateTo(
                    targetValue = 0.85f,
                    animationSpec = tween(150)
                )
            }

            corazonRoto = true

            escalaCorazon.animateTo(
                targetValue = 1.6f,
                animationSpec = tween(250)
            )

            delay(500)
            corazonVisible = false
            delay(300)

            if (vidas <= 0) {
                estado = EstadoJuegoFinal.DERROTA
            } else {
                controlesActivos = true
            }
        }
    }

    // ANIMACIÓN 6: temblor cuando pierde una vida.
    LaunchedEffect(eventoCorazon) {
        if (eventoCorazon > 0) {
            temblor.snapTo(0f)

            repeat(3) {
                temblor.animateTo(10f, tween(65))
                temblor.animateTo(-10f, tween(65))
            }

            temblor.animateTo(0f, tween(80))
        }
    }

    // ANIMACIÓN 7: mensaje +1.
    LaunchedEffect(eventoPunto) {
        if (eventoPunto > 0) {
            mostrarPunto = true
            delay(900)
            mostrarPunto = false
        }
    }

    // ANIMACIÓN 8: desaparición de estrella.
    LaunchedEffect(eventoEstrella) {
        if (eventoEstrella > 0) {
            delay(350)

            if (puntos >= 3) {
                estado = EstadoJuegoFinal.VICTORIA
            } else {
                estrella = when (puntos) {
                    1 -> 2
                    else -> 1
                }

                estrellaVisible = true
                controlesActivos = true
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FondoLila)
            .padding(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "ARIADNA STAR",
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
            color = LilaOscuro
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = "Atrapa las Estrellas ✨",
            fontSize = 16.sp,
            color = LilaOscuro
        )

        Spacer(Modifier.height(18.dp))

        // ANIMACIÓN 9: cambio entre pantallas.
        AnimatedContent(
            targetState = estado,
            transitionSpec = {
                fadeIn(
                    animationSpec = tween(750)
                ) togetherWith fadeOut(
                    animationSpec = tween(400)
                )
            },
            label = "CambioPantallas"
        ) { pantalla ->

            when (pantalla) {

                EstadoJuegoFinal.INICIO -> {
                    PantallaInicioStar(
                        onComenzar = { reiniciarJuego() }
                    )
                }

                EstadoJuegoFinal.JUGANDO -> {

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        // Marcador de puntos y vidas.
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement =
                                Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "⭐ Puntos: $puntos/3",
                                fontWeight = FontWeight.Bold,
                                color = LilaOscuro
                            )

                            Text(
                                text = "❤️ Vidas: $vidas",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE53935)
                            )
                        }

                        Spacer(Modifier.height(12.dp))

                        // Escenario principal.
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(285.dp)
                                .background(
                                    Color.White,
                                    RoundedCornerShape(24.dp)
                                )
                                .border(
                                    2.dp,
                                    Color(0xFFD1C4E9),
                                    RoundedCornerShape(24.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = "✦      ✨      ✦",
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .padding(top = 12.dp),
                                fontSize = 18.sp,
                                color = Color(0xFFCE93D8)
                            )

                            // Estrella: componente independiente.
                            EstrellaAnimada(
                                visible = estrellaVisible,
                                posicion = estrella,
                                flotacion = flotacion,
                                brillo = brillo,
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .offset(y = 52.dp)
                            )

                            // Personaje animado.
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(bottom = 38.dp)
                                    .offset(
                                        x = movimientoX +
                                                temblor.value.dp
                                    )
                                    .size(tamanoJugador)
                                    .background(
                                        colorJugador,
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "★",
                                    fontSize = 36.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            // Corazón: componente independiente.
                            CorazonRotoAnimado(
                                visible = corazonVisible,
                                roto = corazonRoto,
                                escala = escalaCorazon.value,
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )

                            // Puntos: componente independiente.
                            PuntoAnimado(
                                visible = mostrarPunto,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .offset(y = (-65).dp)
                            )

                            Text(
                                text = "IZQ.       CENTRO       DER.",
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(bottom = 10.dp),
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                        }

                        Spacer(Modifier.height(14.dp))

                        Text(
                            text = mensaje,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = LilaOscuro,
                            textAlign = TextAlign.Center
                        )

                        Spacer(Modifier.height(16.dp))

                        // Controles de movimiento.
                        Row(
                            horizontalArrangement =
                                Arrangement.spacedBy(10.dp)
                        ) {

                            Button(
                                enabled = controlesActivos,
                                onClick = {
                                    if (jugador > 0) {
                                        jugador--
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Lila
                                )
                            ) {
                                Text("◀ Izquierda")
                            }

                            Button(
                                enabled = controlesActivos,
                                onClick = {
                                    if (jugador < 2) {
                                        jugador++
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Lila
                                )
                            ) {
                                Text("Derecha ▶")
                            }
                        }

                        Spacer(Modifier.height(12.dp))

                        // Acción principal del videojuego.
                        Button(
                            enabled = controlesActivos,
                            onClick = {
                                controlesActivos = false

                                if (jugador == estrella) {

                                    puntos++
                                    estrellaVisible = false
                                    mensaje =
                                        "¡Excelente! ⭐ +1 punto"

                                    eventoPunto++
                                    eventoEstrella++

                                } else {

                                    vidas--
                                    mensaje =
                                        "¡Oh no! Perdiste una vida 💔"

                                    eventoCorazon++
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Dorado,
                                contentColor = Color.Black
                            )
                        ) {
                            Text(
                                text = "⭐ ATRAPAR ESTRELLA",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                EstadoJuegoFinal.VICTORIA -> {
                    PantallaResultadoFinal(
                        titulo = "¡GANASTE!",
                        descripcion =
                            "¡Felicitaciones! Atrapaste " +
                                    "las 3 estrellas mágicas.",
                        simbolo = "🏆✨",
                        color = Color(0xFF4CAF50),
                        onReiniciar = { reiniciarJuego() }
                    )
                }

                EstadoJuegoFinal.DERROTA -> {
                    PantallaResultadoFinal(
                        titulo = "GAME OVER",
                        descripcion =
                            "Se rompieron tus corazones. " +
                                    "¡Vuelve a intentarlo!",
                        simbolo = "💔",
                        color = Color(0xFFE53935),
                        onReiniciar = { reiniciarJuego() }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Laboratorio 13 - Animaciones Combinadas",
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

// --------------------------------------------------
// PANTALLA DE INICIO
// --------------------------------------------------

@Composable
private fun PantallaInicioStar(
    onComenzar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White,
                RoundedCornerShape(24.dp)
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "🌟",
            fontSize = 70.sp
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "¡Bienvenida a Ariadna Star!",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = LilaOscuro,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(14.dp))

        Text(
            text = "Mueve tu personaje y atrapa " +
                    "3 estrellas para ganar.",
            color = Color.DarkGray,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = "❤️ Tienes 3 vidas",
            color = Color(0xFFE53935),
            fontSize = 17.sp
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onComenzar,
            colors = ButtonDefaults.buttonColors(
                containerColor = Lila
            )
        ) {
            Text("🎮 ¡COMENZAR!")
        }
    }
}

// --------------------------------------------------
// ESTRELLA ANIMADA
// --------------------------------------------------

@Composable
private fun EstrellaAnimada(
    visible: Boolean,
    posicion: Int,
    flotacion: Float,
    brillo: Float,
    modifier: Modifier = Modifier
) {

    val movimientoEstrella by animateDpAsState(
        targetValue = when (posicion) {
            0 -> (-85).dp
            1 -> 0.dp
            else -> 85.dp
        },
        animationSpec = tween(350),
        label = "PosicionEstrella"
    )

    Box(
        modifier = modifier.offset(x = movimientoEstrella),
        contentAlignment = Alignment.Center
    ) {

        // AnimatedVisibility sin conflicto de ColumnScope.
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(350)) +
                    scaleIn(tween(350)),
            exit = fadeOut(tween(300)) +
                    scaleOut(tween(300))
        ) {
            Text(
                text = "⭐",
                fontSize = 43.sp,
                modifier = Modifier
                    .offset(y = flotacion.dp)
                    .scale(brillo)
            )
        }
    }
}

// --------------------------------------------------
// CORAZÓN QUE LATE Y SE ROMPE
// --------------------------------------------------

@Composable
private fun CorazonRotoAnimado(
    visible: Boolean,
    roto: Boolean,
    escala: Float,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(150)) +
                    scaleIn(tween(150)),
            exit = fadeOut(tween(300)) +
                    scaleOut(tween(300))
        ) {
            Text(
                text = if (roto) "💔" else "❤️",
                fontSize = 65.sp,
                modifier = Modifier.scale(escala)
            )
        }
    }
}

// --------------------------------------------------
// EFECTO +1 PUNTO
// --------------------------------------------------

@Composable
private fun PuntoAnimado(
    visible: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(200)) +
                    scaleIn(tween(250)),
            exit = fadeOut(tween(350)) +
                    scaleOut(tween(350))
        ) {
            Text(
                text = "+1 ⭐",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFFFFA000)
            )
        }
    }
}

// --------------------------------------------------
// PANTALLA DE VICTORIA O DERROTA
// --------------------------------------------------

@Composable
fun PantallaResultadoFinal(
    titulo: String,
    descripcion: String,
    simbolo: String,
    color: Color,
    onReiniciar: () -> Unit
) {

    val infinito = rememberInfiniteTransition(
        label = "AnimacionResultado"
    )

    val escala by infinito.animateFloat(
        initialValue = 0.92f,
        targetValue = 1.12f,
        animationSpec = infiniteRepeatable(
            animation = tween(700),
            repeatMode = RepeatMode.Reverse
        ),
        label = "EscalaResultado"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White,
                RoundedCornerShape(24.dp)
            )
            .padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = simbolo,
            fontSize = 72.sp,
            modifier = Modifier.scale(escala)
        )

        Spacer(Modifier.height(18.dp))

        Text(
            text = titulo,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = color
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = descripcion,
            textAlign = TextAlign.Center,
            color = Color.DarkGray
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onReiniciar,
            colors = ButtonDefaults.buttonColors(
                containerColor = Lila
            )
        ) {
            Text("🎮 Jugar nuevamente")
        }
    }
}

// --------------------------------------------------
// VISTA PREVIA
// --------------------------------------------------

@Preview(showBackground = true)
@Composable
fun VideojuegoFinalPreview() {
    PantallaVideojuegoFinal()
}
