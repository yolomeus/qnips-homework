package view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import viewmodel.TableItem

/**
 * UI element representing entry in the food menu table.
 */
@Composable
fun TableEntry(
    tableItem: TableItem,
    cardWidth: Dp,
    cardHeight: Dp
) {
    Card(
        modifier = Modifier
            .width(cardWidth)
            .height(cardHeight)
            .padding(5.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            tableItem.items.forEachIndexed { i, p ->
                p?.let {
                    Text(p.title)
                    Text(p.allergens)
                    Text(p.price)
                }
                if (i != tableItem.items.size - 1)
                    Spacer(modifier = Modifier.size(15.dp))
            }
        }
    }
}

/**
 * UI element representing header entry of table.
 */
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

/**
 * The full menu table as UI element.
 */
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
                TableEntry(item, cardWidth, cardHeight)
            }
        }
    }
}

