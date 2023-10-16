// src/main/kotlin/Main.kt

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.HttpTimeout
import io.ktor.client.request.get
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup
import java.io.File
import java.io.FileOutputStream

@KtorExperimentalAPI
fun downloadInstagramMedia(url: String) {
    // Create an HttpClient with OkHttp engine
    val client = HttpClient(OkHttp) {
        install(HttpTimeout)
    }

    // Use runBlocking to execute the suspending function in a blocking manner
    runBlocking {
        // Send GET request to the Instagram URL
        val response = client.get<String>(url)

        // Parse the HTML response
        val document = Jsoup.parse(response)

        // Extract the video or image URL
        val mediaUrl = document.select("meta[property=og:video]")
            .attr("content")
            .takeIf { it.isNotEmpty() }
            ?: document.select("meta[property=og:image]")
                .attr("content")

        // Download the media file
        val fileName = mediaUrl.substringAfterLast("/")
        val file = File(fileName)
        val outputStream = FileOutputStream(file)

        // Send GET request to the media URL and save the content to a file
        val mediaResponse = client.get<ByteArray>(mediaUrl)
        outputStream.write(mediaResponse)
        outputStream.close()

        println("Media downloaded successfully: $file")
    }

    // Close the HttpClient
    client.close()
}

fun main() {
    val url = "https://www.instagram.com/p/ABC123/" // Replace with the desired Instagram URL
    downloadInstagramMedia(url)
}