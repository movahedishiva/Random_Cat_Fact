package com.example.randomcatfact.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.Modifier.Companion.then
import androidx.compose.ui.unit.dp
import com.example.randomcatfact.data.local.FavoriteFact


@Composable
fun CatFactCard(fact:String, isFavorite: Boolean, onSave:(String)->Unit, onRemove:(String)->Unit, filledStyle: Boolean=true){


    val baseModifier=Modifier
        .fillMaxWidth().padding(vertical = 4.dp)

    val cardModifier = if (filledStyle) {
        baseModifier
    } else {
        baseModifier.border(
            width = 2.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
            shape = RoundedCornerShape(12.dp)
        )
    }
    val cardColors = if (filledStyle) {
        CardDefaults.cardColors()
    } else {
        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    }
    Card(
        modifier = cardModifier

                   ,
                    colors = if(filledStyle) CardDefaults.cardColors() else  CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                    shape = RoundedCornerShape(12.dp),
                    elevation =if(filledStyle)CardDefaults.cardElevation() else  CardDefaults.cardElevation(defaultElevation = 0.dp)

    ){
        Column(modifier = Modifier.fillMaxWidth()) {


            IconButton(
                onClick = { if (isFavorite) onRemove(fact) else onSave(fact) },
                modifier = Modifier.align(alignment = Alignment.End)
            )
            {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite", tint = if (isFavorite)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurface
                )
            }
            Text(
                text = fact,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top=0.dp, bottom = 16.dp, start = 16.dp, end=16.dp)

            )
        }
    }
}