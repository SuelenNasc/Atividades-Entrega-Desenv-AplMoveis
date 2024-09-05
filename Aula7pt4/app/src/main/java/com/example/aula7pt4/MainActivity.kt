package com.example.aula7pt4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aula7pt4.ui.theme.Aula7pt4Theme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Aula7pt4Theme {
                JourneyGame()
            }
        }
    }
}
// Função que cria a lógica do jogo
@Composable
fun JourneyGame() {
    var totalClicks by remember { mutableStateOf(Random.nextInt(1, 51)) } // Gera um número aleatório entre 1 e 50
    var currentClicks by remember { mutableStateOf(0) } //Variável que guarda o número atual de cliques feitos pelo jogador
    var currentImage by remember { mutableStateOf(R.drawable.imagem_inicial) } //Variável que guarda a imagem atual a ser exibida
    var gameEnded by remember { mutableStateOf(false) } // Variável que indica se o jogo terminou ou não

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!gameEnded) {
            Image(
                painter = painterResource(id = currentImage),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                currentClicks++
                updateImage(totalClicks, currentClicks) { newImage ->
                    currentImage = newImage
                }
                if (currentClicks >= totalClicks) {
                    gameEnded = true
                }
            }) {
                Text(text = "Clique para avançar")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                currentImage = R.drawable.imagem_desistencia
                gameEnded = true
            }) {
                Text(text = "Desistir")
            }
        } else {
            Text(
                text = if (currentImage == R.drawable.imagem_conquista) "Parabéns! Você conquistou!" else "Você desistiu. Novo jogo?",
                color = Color.Black,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (currentImage == R.drawable.imagem_conquista) {
                    totalClicks = Random.nextInt(1, 51)
                }
                currentClicks = 0
                currentImage = R.drawable.imagem_inicial
                gameEnded = false
            }) {
                Text(text = "Sim")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                // Encerrar ou voltar à tela inicial (logica adicional pode ser implementada aqui)
            }) {
                Text(text = "Não")
            }
        }
    }
}

fun updateImage(totalClicks: Int, currentClicks: Int, update: (Int) -> Unit) {
    val progress = currentClicks.toFloat() / totalClicks
    when {
        progress >= 1.0f -> update(R.drawable.imagem_conquista)
        progress >= 0.66f -> update(R.drawable.imagem_final)
        progress >= 0.33f -> update(R.drawable.imagem_mediana)
        else -> update(R.drawable.imagem_inicial)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Aula7pt4Theme {
        JourneyGame()
    }
}
