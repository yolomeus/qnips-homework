import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import kotlinx.coroutines.Dispatchers
import model.Api
import model.RemoteDataSource
import viewmodel.RowViewModel


@Composable
fun App(dataSource: RemoteDataSource) {
    val rowVm = RowViewModel(dataSource)

    val rows by remember { rowVm.rows }.collectAsState(Dispatchers.IO)

    MaterialTheme {
        Button(onClick = {
            dataSource.updateData()
        }) {
            Text("load")
        }
        Text(rows.toString())
    }
}

fun main() {
    val source = RemoteDataSource(Api.service)

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Menu",
            state = WindowState(width = 1200.dp, height = 800.dp)
        ) {
            App(source)
        }
    }
}