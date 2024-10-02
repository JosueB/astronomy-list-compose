import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.adyen.android.assignment.R
import com.adyen.android.assignment.api.model.AstronomyPicture

@Composable
fun AstronomyDetailScreen(
    astronomyPicture: AstronomyPicture
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 0.dp)
    ) {
        // Background Image with half-screen height and gradient fade
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp) // Set height to half the screen or as needed
        ) {
            // Async Image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(astronomyPicture.hdUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Astronomy Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )

        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = 0f,
                        endY = 800f // Adjust this for smoother or faster fade
                    )
                )
        ) {
            // Content overlay
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Spacer(modifier = Modifier.height(150.dp)) // To push the content below the image

                // Main content (Title and description)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = astronomyPicture.title,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(35.dp))
                    Text(
                        text = astronomyPicture.date.toString(),
                        fontSize = 14.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = astronomyPicture.explanation,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Icon and text list (Warped, 200 Billion stars, etc.)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_warped), // Substitute this with the correct icon
                            contentDescription = "Warped",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "It's warped.",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_stars), // Use an appropriate star icon here
                            contentDescription = "200 billion stars",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Over 200 billion stars.",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_dust), // Use a cloud-like icon for dust
                            contentDescription = "Dust and Gas",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "It's really dusty and gassy.",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_blackhole), // Use an icon representing black hole
                            contentDescription = "Black hole at the center",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Black hole at the centre.",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
