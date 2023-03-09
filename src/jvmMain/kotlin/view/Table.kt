package view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TableEntry(title: String, allergens: String, price: String) {
    Card(modifier = Modifier.padding(10.dp).width(150.dp).height(120.dp)) {
        Column(modifier = Modifier) {
            Text(title)
            Text(allergens)
            Text(price)
        }
    }
}

@Composable
fun HeaderEntry(title: String) {
    Card {
        Text(title, modifier = Modifier.padding(5.dp))
    }
}