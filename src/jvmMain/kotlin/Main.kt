import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.Dispatchers
import model.Api
import model.RemoteDataSource
import viewmodel.AllergensViewModel


fun main() = application {

    val source = RemoteDataSource(Api.service)
    val allergensVm = AllergensViewModel(source)

    Window(
        onCloseRequest = ::exitApplication,
        title = "Menu"
    ) {

        val allergens by remember { allergensVm.allergens }.collectAsState(Dispatchers.IO)

        MaterialTheme {
            Button(onClick = {
                allergensVm.updateState()
            }) {
                Text("load")
            }
            Text(allergens.toString())
        }
    }
}