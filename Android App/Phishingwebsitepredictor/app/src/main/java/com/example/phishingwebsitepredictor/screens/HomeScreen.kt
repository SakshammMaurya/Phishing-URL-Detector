package com.example.phishingwebsitepredictor.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.phishingwebsitepredictor.R
import com.example.phishingwebsitepredictor.ViewModel.PredictionViewModel
import com.example.phishingwebsitepredictor.ui.fonts.blinkerB
import com.example.phishingwebsitepredictor.ui.fonts.blinkerEB

@Composable
fun HomeScreen2(viewModel: PredictionViewModel) {
    val prediction by viewModel.prediction.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    var url by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Phishing Detector", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("Enter URL") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.getPrediction(url) },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Check")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            prediction.takeIf { it.isNotEmpty() }?.let {
                Text("Prediction: $it", style = MaterialTheme.typography.bodyLarge)
            }

            error?.let {
                Text("Error: $it", color = Color.Red)
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: PredictionViewModel){
    val prediction by viewModel.prediction.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    var url by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF4866b6), Color(0xFF001449))
                )
            )
        ){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Spacer(modifier = Modifier.height(120.dp))
            Text(
                "Phishing",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontFamily = blinkerB
                )
            Text(
                "URL Detector",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontFamily = blinkerB
            )

            Spacer(modifier = Modifier.height(70.dp))

            TextField(
                value = url,
                onValueChange = { url = it },
                placeholder = { Text("paste URL") },
                maxLines = 1,
                singleLine = true,
                modifier = Modifier.width(300.dp),
                colors = TextFieldDefaults.colors(
                    disabledContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledTextColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                )
            )

            Spacer(modifier = Modifier.height(22.dp))

            Button(
                onClick = { viewModel.getPrediction(url) },
                enabled = !isLoading,
                modifier = Modifier.width(150.dp),
                colors = ButtonColors(
                    containerColor = Color(0xFF6286e7),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFF001449),
                    disabledContentColor = Color.White
                )
            ) {
                Text(
                    "Check",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp

                    )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                CircularProgressIndicator()
            } else {
                prediction.takeIf { it.isNotEmpty() }?.let {
                    Image(
                        painter = if(it.equals("Phishing")) painterResource(id = R.drawable.unsafe) else painterResource(id = R.drawable.safe),
                        contentDescription =null,
                        modifier = Modifier.size(150.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if(it.equals("Phishing")) "Unsafe to use" else "Safe to use",
                        style = MaterialTheme.typography.bodyLarge,
                        color = if(it.equals("Phishing")) Color.Red else Color.Green
                    )
                }

                error?.let {
                    Text("Error: $it",
                        color = Color(0xFFD51E1E),
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(300.dp)
                    )
                }
            }
        }

    }
}






















