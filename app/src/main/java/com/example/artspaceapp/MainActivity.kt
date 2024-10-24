package com.example.artspaceapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
        }
    }
}

@Composable
fun ArtSpaceApp() {
    var currentIndex by remember { mutableStateOf(0) }

    val artworks = listOf(
        Artwork(R.drawable.artwork1, "Sailing Under the Bridge", "Kat Kuan", 2017),
        Artwork(R.drawable.artwork2, "Misty Mountains", "John Doe", 2020),
        Artwork(R.drawable.artwork3, "Golden Sunrise", "Jane Smith", 2019)
    )

    // Определяем, в каком режиме отображается экран: портретном или альбомном
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    // В зависимости от ориентации экрана выбираем макет
    if (isLandscape) {
        LandscapeLayout(artworks = artworks, currentIndex = currentIndex) { newIndex ->
            currentIndex = newIndex
        }
    } else {
        PortraitLayout(artworks = artworks, currentIndex = currentIndex) { newIndex ->
            currentIndex = newIndex
        }
    }
}

@Composable
fun PortraitLayout(artworks: List<Artwork>, currentIndex: Int, onIndexChange: (Int) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        DisplayArtwork(
            artwork = artworks[currentIndex],
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)  // 70% высоты экрана
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопки управления в портретном режиме
        NavigationButtons(
            artworks = artworks,
            currentIndex = currentIndex,
            onIndexChange = onIndexChange,
            modifier = Modifier.weight(0.3f)  // 30% высоты экрана
        )
    }
}

@Composable
fun LandscapeLayout(artworks: List<Artwork>, currentIndex: Int, onIndexChange: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Изображение и кнопки рядом друг с другом в альбомном режиме
        DisplayArtwork(
            artwork = artworks[currentIndex],
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)  // 50% ширины экрана
        )

        NavigationButtons(
            artworks = artworks,
            currentIndex = currentIndex,
            onIndexChange = onIndexChange,
            modifier = Modifier.weight(1f)  // 50% ширины экрана
        )
    }
}

@Composable
fun DisplayArtwork(artwork: Artwork, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.padding(16.dp),
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
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
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

@Composable
fun NavigationButtons(
    artworks: List<Artwork>,
    currentIndex: Int,
    onIndexChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Button(onClick = {
            onIndexChange((currentIndex - 1 + artworks.size) % artworks.size)
        }) {
            Text("Previous")
        }
        Button(onClick = {
            onIndexChange((currentIndex + 1) % artworks.size)
        }) {
            Text("Next")
        }
    }
}

data class Artwork(val imageResId: Int, val title: String, val artist: String, val year: Int)