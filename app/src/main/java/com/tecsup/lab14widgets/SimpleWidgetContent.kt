package com.tecsup.lab14widgets

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.Action
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
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
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider

class SimpleWidgetContent : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            MyContent()
        }
    }

    @Composable
    private fun MyContent() {
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(Color(0xFF0F766E))
                .cornerRadius(24.dp)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "🧭 ¿A dónde quieres dirigirte?",
                style = TextStyle(
                    color = ColorProvider(Color.White),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = GlanceModifier.height(10.dp))
            Row(modifier = GlanceModifier.fillMaxWidth()) {
                BotonWidget(
                    modifier = GlanceModifier.defaultWeight(),
                    texto = "Principal",
                    fondo = Color.White,
                    colorTexto = Color(0xFF0F766E),
                    accion = actionStartActivity<MainActivity>()
                )
                Spacer(modifier = GlanceModifier.width(8.dp))
                BotonWidget(
                    modifier = GlanceModifier.defaultWeight(),
                    texto = "Segunda vista",
                    fondo = Color(0xFFF59E0B),
                    colorTexto = Color(0xFF1E1B4B),
                    accion = actionStartActivity<SegundaActivity>()
                )
            }
        }
    }
}

@Composable
private fun BotonWidget(
    modifier: GlanceModifier,
    texto: String,
    fondo: Color,
    colorTexto: Color,
    accion: Action
) {
    Box(
        modifier = modifier
            .height(40.dp)
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
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}