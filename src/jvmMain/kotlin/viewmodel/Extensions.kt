package viewmodel

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken

fun <T> JsonElement.toStringMap(): Map<String, T> {
    val token = object : TypeToken<Map<String, T>>() {}.type
    return Gson().fromJson(this, token)
}