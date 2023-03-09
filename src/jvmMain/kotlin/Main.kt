import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import model.Api
import model.RemoteDataSource
import view.HeaderEntry
import view.TableEntry
import viewmodel.RowViewModel

@Preview
@Composable
fun App(dataSource: RemoteDataSource) {
    val rowVm = RowViewModel(dataSource)

    MaterialTheme {
        Scaffold(floatingActionButton = {
            FloatingActionButton(onClick = { dataSource.updateData() }) {
                Icon(
                    Icons.Default.Refresh,
                    contentDescription = null
                )
            }
        }) {

            val tableRows = rowVm.getTableRows().collectAsState(listOf()).value

            val tableHeader = rowVm.getTableHeader().collectAsState(listOf()).value
            val rowLegend = rowVm.getRowLegend().collectAsState(listOf()).value
            // TODO: modularize table content and frame
            if (listOf(tableRows, tableHeader, rowLegend).all { it.isNotEmpty() }) {

                Box {
                    Column {
                        // Header
                        Row {
                            HeaderEntry("Plan")
                            for (item in tableHeader) {
                                item?.let { HeaderEntry(it) }
                            }
                        }
                        // Body
                        for ((i, row) in tableRows.withIndex()) {
                            Row {

                                HeaderEntry(rowLegend[i])

                                for (item in row) {
                                    TableEntry(item.title, item.allergens, item.price)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


fun main() {
    val source = RemoteDataSource(Api.service)

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Menu",
            state = WindowState(width = 1000.dp, height = 600.dp)
        ) {
            App(source)
        }
    }
}