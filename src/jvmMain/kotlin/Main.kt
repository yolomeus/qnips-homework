import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import model.Api
import model.RemoteDataSource
import view.Table
import viewmodel.RowViewModel

@Preview
@Composable
fun App(dataSource: RemoteDataSource) {

    val rowVm = RowViewModel(dataSource)

    MaterialTheme {

        var loadingText by remember { mutableStateOf("no data yet: press refresh") }

        val tableRows = rowVm.getTableRows().collectAsState(listOf()).value
        val tableHeader = rowVm.getTableHeader().collectAsState(listOf()).value
        val rowLegend = rowVm.getRowLegend().collectAsState(listOf()).value

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    loadingText = "loading..."
                    dataSource.updateData()
                }) {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = null
                    )
                }
            }) {

            Column(
                modifier = Modifier.fillMaxSize().background(Color.Gray),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (listOf(tableRows, tableHeader, rowLegend).all { it.isNotEmpty() }) {
                    Table(tableHeader, rowLegend, tableRows, 150, 120)
                } else {
                    Text(loadingText, color = Color.White)
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