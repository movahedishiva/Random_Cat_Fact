package com.example.randomcatfact.presentation.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.randomcatfact.presentation.viewModel.CatFactState
import com.example.randomcatfact.presentation.viewModel.CatViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import androidx.compose.runtime.getValue


import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.randomcatfact.R
import com.example.randomcatfact.data.local.FavoriteFact
import com.example.randomcatfact.presentation.ui.components.CatFactCard
import com.example.randomcatfact.presentation.ui.theme.RandomCatFactTheme

@Composable
fun CatFactScreen(
    viewModel: CatViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()
    CatFactScreenContent(
        state = state,
        favorites = favorites,
        onRefresh = viewModel::getFact,
        onSave =viewModel::saveFavorite,
        onRemove= viewModel::removeFavorite,
        modifier = modifier
    )
}

@Composable
fun CatFactScreenContent(state: CatFactState, favorites: List<FavoriteFact>, onRefresh:()->Unit={}, onSave:(String)->Unit={},onRemove:(String)->Unit={}, modifier: Modifier= Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = stringResource(R.string.random_cat_fact),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

        when (state) {

            is CatFactState.Loading -> {
                Box(contentAlignment = Alignment.Center, modifier= Modifier
                    .fillMaxWidth()
                    .height(200.dp)){
                    CircularProgressIndicator(color=MaterialTheme.colorScheme.primary,  modifier = Modifier.size(48.dp), strokeWidth = 4.dp)
                }

            }

            is CatFactState.Success -> {

                val fact =
                    state.fact

                val isFavorite= favorites.any{it.fact==fact}

             CatFactCard(fact,isFavorite,onSave,onRemove, filledStyle = false)



                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier= Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {

                    Button(
                        onClick =  onRefresh
                    ) {
                        Text(stringResource(R.string.new_fact))
                    }


                }
            }

            is CatFactState.Error -> {

                Text(state.message)

                Button(
                    onClick = onRefresh
                ) {
                    Text(stringResource(R.string.retry))
                }
            }
        }

        if(favorites.isNotEmpty()) {
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = stringResource(R.string.favorite_facts),
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn {

                items(favorites) { item ->

               CatFactCard(item.fact,true,{},onRemove)


                }
            }
        }
    }
}

@Preview(showBackground = true, name="Success State")
@Composable
fun PreviewContentSuccess(){

    RandomCatFactTheme {

        val state= CatFactState.Success(fact="this is a fact " )
        val favorites=listOf<FavoriteFact>(FavoriteFact(fact = "this is favorite1"))
        CatFactScreenContent(state,favorites)

    }
}

@Preview(showBackground = true,name="Loading State")
@Composable
fun PreviewContentLoading(){
    RandomCatFactTheme {
        CatFactScreenContent(CatFactState.Loading,emptyList())
    }
}


@Preview(showBackground = true, name="Error State")
@Composable
fun PreviewError(){
    RandomCatFactTheme {
        CatFactScreenContent(CatFactState.Error("this is an Error"), emptyList(),{},{})
    }
}


