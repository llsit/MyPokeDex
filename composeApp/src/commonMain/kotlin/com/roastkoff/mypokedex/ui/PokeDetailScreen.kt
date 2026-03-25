package com.roastkoff.mypokedex.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

object PokedexTheme {
    val Slate950 = Color(0xFF020617)
    val Slate900 = Color(0xFF0F172A)
    val Slate400 = Color(0xFF94A3B8)
    val Orange500 = Color(0xFFF97316)
    val Orange600 = Color(0xFFEA580C)
}

@Composable
fun PokeDetailScreen(
    pokemonId: String = "#0006",
    name: String = "Charizard",
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        containerColor = PokedexTheme.Slate950,
        topBar = { PokedexTopBar(onBackClick) }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 40.dp)
                    .size(280.dp)
                    .background(PokedexTheme.Orange600.copy(alpha = 0.15f), CircleShape)
                    .blur(100.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    HeaderSection(pokemonId, name)
                }

                item {
                    AsyncImage(
                        model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/6.png",
                        contentDescription = name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .graphicsLayer {
                                shadowElevation = 40f
                                spotShadowColor = PokedexTheme.Orange500
                            }
                    )
                }

                item {
                    StatsBentoGrid()
                }
            }
        }
    }
}

@Composable
fun HeaderSection(id: String, name: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Column {
            Text(
                text = id,
                color = PokedexTheme.Orange500,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = name,
                color = Color.White,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.ExtraBold)
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TypeBadge("FIRE", PokedexTheme.Orange600)
            TypeBadge("FLYING", Color(0xFF2563EB))
        }
    }
}

@Composable
fun TypeBadge(label: String, color: Color) {
    Surface(
        color = color.copy(alpha = 0.2f),
        shape = CircleShape,
        border = BorderStroke(1.dp, color.copy(alpha = 0.3f)),
        modifier = Modifier.shadow(
            elevation = 8.dp,
            shape = CircleShape,
            ambientColor = color,
            spotColor = color
        )
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelSmall.copy(
                color = color,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun StatsBentoGrid() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Surface(
            color = PokedexTheme.Slate900.copy(alpha = 0.4f),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.05f))
        ) {
            Column(Modifier.padding(24.dp)) {
                LabelHeader(Icons.Default.Info, "About")

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    StatMetric("Weight", "90.5 kg")
                    StatMetric("Height", "1.7 m")
                }

                Spacer(Modifier.height(16.dp))
                Text(
                    "Abilities",
                    color = PokedexTheme.Slate400,
                    style = MaterialTheme.typography.labelSmall
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    AbilityChip("Blaze")
                    AbilityChip("Solar Power", isItalic = true)
                }
            }
        }

        Surface(
            color = PokedexTheme.Slate900.copy(alpha = 0.4f),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.05f))
        ) {
            Column(Modifier.padding(24.dp)) {
                LabelHeader(Icons.Default.Leaderboard, "Base Stats")
                StatBar("HP", 78, 0.78f)
                StatBar("ATK", 84, 0.84f)
                StatBar("DEF", 78, 0.78f)
                StatBar("SATK", 109, 1.0f)
            }
        }
    }
}

@Composable
fun StatBar(label: String, value: Int, progress: Float) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            label,
            color = PokedexTheme.Slate400,
            modifier = Modifier.width(40.dp),
            style = MaterialTheme.typography.labelSmall
        )
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.weight(1f).height(8.dp).clip(CircleShape),
            color = PokedexTheme.Orange500,
            trackColor = PokedexTheme.Slate900
        )
        Text(
            "$value",
            color = Color.White,
            modifier = Modifier.width(40.dp),
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun PokedexTopBar(
    onClickBack: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(PokedexTheme.Slate950.copy(alpha = 0.6f)) // Blur effect simulation
            .padding(horizontal = 24.dp)
    ) {
        IconButton(
            onClick = onClickBack,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "ArrowBack",
                tint = PokedexTheme.Orange500
            )
        }

        Text(
            text = "POKÉDEX",
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 4.sp,
                color = PokedexTheme.Orange500
            )
        )
    }
}

@Composable
fun LabelHeader(
    icon: ImageVector,
    label: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 20.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = PokedexTheme.Orange500,
            modifier = Modifier.size(16.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = label,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
fun StatMetric(
    label: String,
    value: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(
                letterSpacing = 1.sp,
                color = PokedexTheme.Slate400,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall.copy(
                color = Color.White,
                fontWeight = FontWeight.ExtraBold
            )
        )
    }
}

@Composable
fun AbilityChip(
    ability: String,
    isItalic: Boolean = false
) {
    Surface(
        color = PokedexTheme.Slate950.copy(alpha = 0.5f),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = ability,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.bodySmall.copy(
                color = if (isItalic) PokedexTheme.Slate400 else Color.White,
                fontStyle = if (isItalic) FontStyle.Italic else FontStyle.Normal,
                fontWeight = FontWeight.Medium
            )
        )
    }
}