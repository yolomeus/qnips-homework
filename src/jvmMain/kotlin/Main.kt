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
import model.QnipsApi
import model.RemoteDataSource
import view.Table
import viewmodel.RowViewModel

@Preview
@Composable
fun App(rowVm: RowViewModel) {
    // collect states from data flows in order to display in compose
    val tableRows by rowVm.getTableRows().collectAsState(listOf())
    val tableHeader by rowVm.getTableHeader().collectAsState(listOf())
    val rowLegend by rowVm.getRowLegend().collectAsState(listOf())

    // displayed if no data loaded yet or loading has been triggered
    var loadingText by remember { mutableStateOf("No data yet: press refresh!") }

    MaterialTheme {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    loadingText = "loading..."
                    rowVm.updateData()
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
                // only try displaying table if all flows have emitted data
                if (listOf(tableRows, tableHeader, rowLegend).all { it.isNotEmpty() }) {
                    // would be better if layout were responsive, but I'm not familiar enough with compose yet and
                    // there's no more time, hence fixed sizes
                    Table(tableHeader, rowLegend, tableRows, 180.dp, 150.dp)
                } else {
                    Text(loadingText, color = Color.White)
                }
            }
        }
    }
}


fun main() {
    // model
    val dataSource = RemoteDataSource(QnipsApi.service)
    val rowVm = RowViewModel(dataSource)

    // UI
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Menu",
            state = WindowState(width = 1200.dp, height = 600.dp)
        ) {
            App(rowVm)
        }
    }
}