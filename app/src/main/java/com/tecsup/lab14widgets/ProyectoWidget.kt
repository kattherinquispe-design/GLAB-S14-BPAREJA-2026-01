package com.tecsup.lab14widgets

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.Action
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// ✏️ Cambia estos textos según la función de tu proyecto final
private const val TITULO = "⚡ Accesos rápidos"
private const val ETIQUETA_INFO = "Última actualización"
private const val ETIQUETA_CONTADOR = "Veces"
private const val BOTON_1 = "Inicio"
private const val BOTON_2 = "Vista 2"
private const val BOTON_3 = "Actualizar"

class ProyectoWidget : GlanceAppWidget() {

    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Contenido()
        }
    }

    @Composable
    private fun Contenido() {
        val prefs = currentState<Preferences>()
        val ultima = prefs[ULTIMA] ?: "--:--:--"
        val veces = prefs[CONTADOR] ?: 0

        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(Color(0xFF1E1B4B))
                .cornerRadius(24.dp)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Encabezado
            Text(
                text = TITULO,
                modifier = GlanceModifier.fillMaxWidth(),
                style = TextStyle(
                    color = ColorProvider(Color.White),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = GlanceModifier.height(6.dp))

            // Tarjeta de información
            Row(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .background(Color(0xFF312E81))
                    .cornerRadius(16.dp)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = GlanceModifier.defaultWeight()) {
                    Text(
                        text = ETIQUETA_INFO,
                        style = TextStyle(
                            color = ColorProvider(Color(0xFFC7D2FE)),
                            fontSize = 11.sp
                        )
                    )
                    Text(
                        text = ultima,
                        style = TextStyle(
                            color = ColorProvider(Color.White),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = ETIQUETA_CONTADOR,
                        style = TextStyle(
                            color = ColorProvider(Color(0xFFC7D2FE)),
                            fontSize = 11.sp
                        )
                    )
                    Text(
                        text = veces.toString(),
                        style = TextStyle(
                            color = ColorProvider(Color(0xFFFBBF24)),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
            Spacer(modifier = GlanceModifier.height(8.dp))

            // Botones de acceso rápido
            Row(modifier = GlanceModifier.fillMaxWidth()) {
                Chip(
                    modifier = GlanceModifier.defaultWeight(),
                    texto = BOTON_1,
                    fondo = Color(0xFF6366F1),
                    colorTexto = Color.White,
                    accion = actionStartActivity<MainActivity>()
                )
                Spacer(modifier = GlanceModifier.width(8.dp))
                Chip(
                    modifier = GlanceModifier.defaultWeight(),
                    texto = BOTON_2,
                    fondo = Color(0xFF14B8A6),
                    colorTexto = Color.White,
                    accion = actionStartActivity<SegundaActivity>()
                )
                Spacer(modifier = GlanceModifier.width(8.dp))
                Chip(
                    modifier = GlanceModifier.defaultWeight(),
                    texto = BOTON_3,
                    fondo = Color(0xFFFBBF24),
                    colorTexto = Color(0xFF1E1B4B),
                    accion = actionRunCallback<ActualizarCallback>()
                )
            }
        }
    }

    companion object {
        val ULTIMA = stringPreferencesKey("ultima_actualizacion")
        val CONTADOR = intPreferencesKey("contador_actualizaciones")
    }
}

@Composable
private fun Chip(
    modifier: GlanceModifier,
    texto: String,
    fondo: Color,
    colorTexto: Color,
    accion: Action
) {
    Box(
        modifier = modifier
            .height(48.dp)
            .background(fondo)
            .cornerRadius(14.dp)
            .clickable(accion),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = texto,
            maxLines = 1,
            style = TextStyle(
                color = ColorProvider(colorTexto),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

class ActualizarCallback : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        val hora = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        updateAppWidgetState(context, PreferencesGlanceStateDefinition, glanceId) { prefs ->
            val nuevo = prefs.toMutablePreferences()
            nuevo[ProyectoWidget.ULTIMA] = hora
            nuevo[ProyectoWidget.CONTADOR] = (prefs[ProyectoWidget.CONTADOR] ?: 0) + 1
            nuevo
        }
        ProyectoWidget().update(context, glanceId)
    }
}

class ProyectoWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = ProyectoWidget()
}