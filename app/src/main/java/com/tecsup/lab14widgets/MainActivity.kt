package com.tecsup.lab14widgets

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.lab14widgets.ui.theme.Lab14WidgetsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab14WidgetsTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    PantallaPrincipal(
                        onIrSegunda = {
                            startActivity(Intent(this@MainActivity, SegundaActivity::class.java))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PantallaPrincipal(onIrSegunda: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Página Principal", style = MaterialTheme.typography.headlineMedium)
        Text(
            "Esta es la vista principal de la app",
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = onIrSegunda) {
            Text("Ir a la segunda vista")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaPrincipalPreview() {
    Lab14WidgetsTheme {
        PantallaPrincipal(onIrSegunda = {})
    }
}