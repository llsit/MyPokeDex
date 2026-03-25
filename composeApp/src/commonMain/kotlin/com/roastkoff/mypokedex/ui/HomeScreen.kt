package com.roastkoff.mypokedex.ui

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CatchingPokemon
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    val id: String,
    val name: String,
    val types: List<String>,
    val imageUrl: String,
    val backgroundColorHex: Long
) {
    val backgroundColor: Color
        get() = Color(backgroundColorHex)

    val routeId: String
        get() = name.lowercase()
}

val mockPokemonList = listOf(
    Pokemon(
        id = "#0001",
        name = "Bulbasaur",
        types = listOf("GRASS", "POISON"),
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
        backgroundColorHex = 0xFF4ade80 // green-400
    ),
    Pokemon(
        id = "#0004",
        name = "Charmander",
        types = listOf("FIRE"),
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/4.png",
        backgroundColorHex = 0xFFfb923c // orange-400
    ),
    Pokemon(
        id = "#0007",
        name = "Squirtle",
        types = listOf("WATER"),
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/7.png",
        backgroundColorHex = 0xFF60a5fa // blue-400
    ),
    Pokemon(
        id = "#0006",
        name = "Charizard",
        types = listOf("FIRE", "FLYING"),
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/6.png",
        backgroundColorHex = 0xFFea580c // orange-600
    ),
    Pokemon(
        id = "#0025",
        name = "Pikachu",
        types = listOf("ELECTRIC"),
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png",
        backgroundColorHex = 0xFFfacc15 // yellow-400
    ),
    Pokemon(
        id = "#0094",
        name = "Gengar",
        types = listOf("GHOST", "POISON"),
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/94.png",
        backgroundColorHex = 0xFFa855f7 // purple-500
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onClickItem: (Pokemon) -> Unit = {}) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CatchingPokemon,
                            contentDescription = null,
                            tint = Color.Red
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Pokedex", fontWeight = FontWeight.Black)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFFF9F9F9))
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Search Pokemon, Move, Type...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(mockPokemonList) { pokemon ->
                    PokemonCard(pokemon = pokemon, onClick = { onClickItem(pokemon) })
                }
            }
        }
    }
}

@Composable
fun PokemonCard(
    pokemon: Pokemon,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                Surface(
                    shape = CircleShape,
                    color = pokemon.backgroundColor.copy(alpha = 0.2f),
                    modifier = Modifier.fillMaxSize(0.9f)
                ) { }

                AsyncImage(
                    model = pokemon.imageUrl,
                    contentDescription = pokemon.name,
                    modifier = Modifier.fillMaxSize(0.8f)
                )
            }

            Text(
                text = "#${pokemon.id}",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray.copy(alpha = 0.6f),
                fontWeight = FontWeight.Bold
            )

            Text(
                text = pokemon.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                pokemon.types.forEach { type ->
                    TypeBadge(type)
                }
            }
        }
    }
}

@Composable
fun TypeBadge(type: String) {
    Surface(
        color = getPokemonTypeColor(type),
        shape = CircleShape
    ) {
        Text(
            text = type.uppercase(),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        )
    }
}

@Composable
fun getPokemonTypeColor(type: String): Color {
    return when (type.lowercase()) {
        "grass" -> Color(0xFF48D0B0)
        "poison" -> Color(0xFFA040A0)
        "fire" -> Color(0xFFFB6C6C)
        "water" -> Color(0xFF77BDFE)
        "electric" -> Color(0xFFFFD76F)
        "fairy" -> Color(0xFFF8A0E0)
        "ghost" -> Color(0xFF906790) // ปรับจาก CSS ให้ดู Modern ขึ้น
        else -> MaterialTheme.colorScheme.secondary
    }
}

// สำหรับพื้นหลังวงกลม (Soft Background)
fun getPokemonTypeSurface(type: String): Color {
    return when (type.lowercase()) {
        "grass" -> Color(0xFFE2F9E1)
        "fire" -> Color(0xFFFDE1E1)
        "water" -> Color(0xFFE1F1FD)
        "electric" -> Color(0xFFFEF6E1)
        "fairy" -> Color(0xFFFDE1F6)
        "ghost" -> Color(0xFFEDE1FD)
        else -> Color(0xFFEEEEEE)
    }
}