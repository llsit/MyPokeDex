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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.roastkoff.mypokedex.model.PokemonDetail
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

object PokedexTheme {
    val Slate950 = Color(0xFF020617)
    val Slate900 = Color(0xFF0F172A)
    val Slate400 = Color(0xFF94A3B8)
    val Orange500 = Color(0xFFF97316)
    val Orange600 = Color(0xFFEA580C)

    // Type accent colors (vivid, for badges/glows)
    val TypeGrass = Color(0xFF48D0B0)
    val TypeFire = Color(0xFFFB6C6C)
    val TypeWater = Color(0xFF77BDFE)
    val TypeElectric = Color(0xFFFFD76F)
    val TypeFairy = Color(0xFFF8A0E0)
    val TypeGhost = Color(0xFF906790)
    val TypePoison = Color(0xFFA040A0)
    val TypeDefault = Color(0xFF94A3B8)

    // Type surface colors (soft background circles/cards)
    val TypeGrassSurface = Color(0xFFE2F9E1)
    val TypeFireSurface = Color(0xFFFDE1E1)
    val TypeWaterSurface = Color(0xFFE1F1FD)
    val TypeElectricSurface = Color(0xFFFEF6E1)
    val TypeFairySurface = Color(0xFFFDE1F6)
    val TypeGhostSurface = Color(0xFFEDE1FD)
    val TypeDefaultSurface = Color(0xFFEEEEEE)

    fun typeColor(type: String?): Color = when (type?.lowercase()) {
        "grass" -> TypeGrass
        "fire" -> TypeFire
        "water" -> TypeWater
        "electric" -> TypeElectric
        "fairy" -> TypeFairy
        "ghost" -> TypeGhost
        "poison" -> TypePoison
        else -> TypeDefault
    }

    fun typeSurfaceColor(type: String?): Color = when (type?.lowercase()) {
        "grass" -> TypeGrassSurface
        "fire" -> TypeFireSurface
        "water" -> TypeWaterSurface
        "electric" -> TypeElectricSurface
        "fairy" -> TypeFairySurface
        "ghost" -> TypeGhostSurface
        else -> TypeDefaultSurface
    }
}

@Composable
fun PokeDetailScreen(
    name: String,
    viewModel: PokeDetailViewModel = koinViewModel(
        key = name,
        parameters = { parametersOf(name) }
    ),
    onBackClick: () -> Unit = {}
) {
    val pokemon by viewModel.pokemon.collectAsStateWithLifecycle()
    val typeColor = remember {
        derivedStateOf {
            PokedexTheme.typeColor(pokemon?.primaryType)
        }
    }

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        containerColor = PokedexTheme.Slate950,
        topBar = { PokedexTopBar(onBackClick, typeColor.value) }
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
                    HeaderSection(pokemon?.id.orEmpty(), name, pokemon?.types.orEmpty())
                }

                item {
                    AsyncImage(
                        model = pokemon?.imageUrl,
                        contentDescription = name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .graphicsLayer {
                                shadowElevation = 40f
                                spotShadowColor = typeColor.value
                            }
                    )
                }

                item {
                    StatsBentoGrid(pokemon)
                }
            }
        }
    }
}

@Composable
fun HeaderSection(id: String, name: String, types: List<String>) {
    val primaryColor = PokedexTheme.typeColor(types.firstOrNull())

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Column {
            Text(
                text = id,
                color = primaryColor,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = name,
                color = Color.White,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.ExtraBold)
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            types.forEach { type ->
                TypeBadge(type, PokedexTheme.typeColor(type))
            }
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
fun StatsBentoGrid(pokemon: PokemonDetail?) {
    val primaryColor = PokedexTheme.typeColor(pokemon?.primaryType)

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Surface(
            color = PokedexTheme.Slate900.copy(alpha = 0.4f),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.05f))
        ) {
            Column(Modifier.padding(24.dp)) {
                LabelHeader(Icons.Default.Info, "About", primaryColor)

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    StatMetric("Weight", "${pokemon?.weightInKg} kg")
                    StatMetric("Height", "${pokemon?.heightInMeters} m")
                }

                Spacer(Modifier.height(16.dp))
                Text(
                    "Abilities",
                    color = PokedexTheme.Slate400,
                    style = MaterialTheme.typography.labelSmall
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    pokemon?.abilities?.forEach {
                        AbilityChip(it, primaryColor = primaryColor)
                    }
                }
            }
        }

        Surface(
            color = PokedexTheme.Slate900.copy(alpha = 0.4f),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.05f))
        ) {
            Column(Modifier.padding(24.dp)) {
                LabelHeader(Icons.Default.Leaderboard, "Base Stats", primaryColor)
                StatBar(
                    "HP",
                    primaryColor,
                    pokemon?.hp ?: 0,
                    pokemon?.hp?.toFloat()?.div(255f) ?: 0f
                )
                StatBar(
                    "ATK",
                    primaryColor,
                    pokemon?.attack ?: 0,
                    pokemon?.attack?.toFloat()?.div(255f) ?: 0f
                )
                StatBar(
                    "DEF",
                    primaryColor,
                    pokemon?.defense ?: 0,
                    pokemon?.defense?.toFloat()?.div(255f) ?: 0f
                )
                StatBar(
                    "SATK",
                    primaryColor,
                    pokemon?.specialAttack ?: 0,
                    pokemon?.specialAttack?.toFloat()?.div(255f) ?: 0f
                )
                StatBar(
                    "SDEF",
                    primaryColor,
                    pokemon?.specialDefense ?: 0,
                    pokemon?.specialDefense?.toFloat()?.div(255f) ?: 0f
                )
            }
        }
    }
}

@Composable
fun StatBar(label: String, progressColor: Color, value: Int, progress: Float) {
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
            color = progressColor,
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
    onClickBack: () -> Unit = {},
    accentColor: Color = PokedexTheme.Orange500
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(PokedexTheme.Slate950.copy(alpha = 0.6f))
            .padding(horizontal = 24.dp)
    ) {
        IconButton(
            onClick = onClickBack,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "ArrowBack",
                tint = accentColor
            )
        }

        Text(
            text = "POKÉDEX",
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 4.sp,
                color = accentColor
            )
        )
    }
}

@Composable
fun LabelHeader(
    icon: ImageVector,
    label: String,
    color: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 20.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
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
    isItalic: Boolean = false,
    primaryColor: Color
) {
    Surface(
        color = primaryColor.copy(alpha = 0.5f),
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