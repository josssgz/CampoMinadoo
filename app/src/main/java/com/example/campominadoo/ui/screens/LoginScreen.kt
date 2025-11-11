package com.example.campominadoo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    onPlayerClick: () -> Unit,
    onAdminClick: () -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Bem-vindo ao",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "Campo Minado",
            style = MaterialTheme.typography.displayMedium
        )

        Spacer(modifier = Modifier.height((64.dp)))

        Button(
            onClick = onPlayerClick,
            modifier = Modifier.width(200.dp)
        ) {
            Text("Modo Jogador")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onAdminClick,
            modifier = Modifier.width(200.dp)
        ) {
            Text("Modo Administrador")
        }

    }
}