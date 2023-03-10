package view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import viewmodel.TableItem

@Composable
fun TableEntry(title: String, allergens: String, price: String, cardWidth: Dp, cardHeight: Dp) {
    Card(
        modifier = Modifier
            .width(cardWidth)
            .height(cardHeight)
            .padding(5.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(title)
            Text(allergens)
            Text(price)
        }
    }
}

@Composable
fun HeaderEntry(title: String, cardWidth: Dp) {
    Card(
        modifier = Modifier
            .width(cardWidth)
            .padding(5.dp)
    ) {
        Text(
            title,
            modifier = Modifier.padding(5.dp)
        )
    }
}

@Composable
fun Table(
    tableHeader: List<String?>,
    rowLegend: List<String>,
    tableRows: List<List<TableItem>>,
    cardWidth: Dp,
    cardHeight: Dp
) {

    // Header
    Row {
        HeaderEntry("Plan", cardWidth)
        for (item in tableHeader) {
            item?.let { HeaderEntry(it, cardWidth) }
        }
    }
    // Body
    for ((i, row) in tableRows.withIndex()) {
        Row {
            HeaderEntry(rowLegend[i], cardWidth)
            for (item in row) {
                TableEntry(item.title, item.allergens, item.price, cardWidth, cardHeight)
            }
        }
    }
}

