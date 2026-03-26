package com.roastkoff.mypokedex.ui

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CatchingPokemon
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.roastkoff.mypokedex.model.Pokemon
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onClickItem: (String) -> Unit = {}
) {
    val pokemonList by viewModel.pokemonList.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    val listState = rememberLazyGridState()
    val shouldLoadMore = remember {
        derivedStateOf {
            val totalItems = listState.layoutInfo.totalItemsCount
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            totalItems > 0 && lastVisibleItem != null && lastVisibleItem.index >= totalItems - 4
        }
    }

    // Trigger initial load
    LaunchedEffect(Unit) {
        if (pokemonList.isEmpty()) {
            viewModel.loadNextPage()
        }
    }

    // Trigger pagination load
    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value && !isLoading && pokemonList.isNotEmpty()) {
            viewModel.loadNextPage()
        }
    }

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
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFFF9F9F9))
        ) {
            Column {
                LazyVerticalGrid(
                    state = listState,
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(pokemonList.size) { index ->
                        PokemonCard(
                            pokemon = pokemonList[index],
                            onClick = onClickItem,
                            modifier = Modifier.animateItem(
                                fadeInSpec = tween(300),
                                placementSpec = spring(stiffness = Spring.StiffnessLow)
                            )
                        )
                    }

                    if (isLoading && pokemonList.isNotEmpty()) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Box(
                                Modifier.fillMaxWidth().padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(modifier = Modifier.size(32.dp))
                            }
                        }
                    }
                }
            }
            if (isLoading && pokemonList.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun PokemonCard(
    pokemon: Pokemon,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onClick(pokemon.name) },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = pokemon.backgroundColor),
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
                color = Color.Black.copy(alpha = 0.6f),
                fontWeight = FontWeight.Bold
            )

            Text(
                text = pokemon.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}
