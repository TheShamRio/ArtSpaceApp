package com.example.artspaceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspaceapp.ui.theme.ArtSpaceAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtSpaceApp()
                }
            }
 }}}


@Composable
fun ArtSpaceApp() {
    var currentIndex by remember { mutableStateOf(0) }

    val artworks = listOf(
        Artwork(R.drawable.artwork1, "Sailing Under the Bridge", "Kat Kuan", 2017),
        Artwork(R.drawable.artwork2, "Misty Mountains", "John Doe", 2020),
        Artwork(R.drawable.artwork3, "Golden Sunrise", "Jane Smith", 2019)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {

        DisplayArtwork(artwork = artworks[currentIndex])

        Spacer(modifier = Modifier.height(16.dp))


        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                currentIndex = (currentIndex - 1 + artworks.size) % artworks.size
            }) {
                Text("Previous")
            }
            Button(onClick = {
                currentIndex = (currentIndex + 1) % artworks.size
            }) {
                Text("Next")
            }
        }
    }
}

data class Artwork(val imageResId: Int, val title: String, val artist: String, val year: Int)


@Composable
fun DisplayArtwork(artwork: Artwork) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(artwork.imageResId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(300.dp).fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = artwork.title,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.Start)
            )
            Text(
                text = "${artwork.artist} (${artwork.year})",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.Start)
            )
        }
    }
}

