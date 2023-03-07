import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import model.Api
import model.RemoteDataSource


fun main() = application {

    val source = RemoteDataSource(Api.service)

    Window(
        onCloseRequest = ::exitApplication,
        title = "Menu"
    ) {

        val data = remember { source.apiData }
        var loadingText by remember { mutableStateOf("no data") }

        MaterialTheme {
            Button(onClick = {
                loadingText = "loading..."
                source.updateData()
            }) {
                Text("load")
            }
            Text(if (data.value.isEmpty()) loadingText else data.value.toString())
        }
    }
}