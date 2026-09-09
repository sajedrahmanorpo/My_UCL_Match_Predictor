import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.JsonParser
import java.io.File
import java.util.Properties

private fun getApiKey(): String {
    val properties = Properties()
    val propertiesFile = File(System.getProperty("user.dir"), "local.properties")
    if (propertiesFile.exists()) {
        propertiesFile.inputStream().use { input ->
            properties.load(input)
        }
        return properties.getProperty("FOOTBALL_API_KEY") ?: ""
    }
    return System.getenv("FOOTBALL_API_KEY") ?: ""
}

object FootballApiClient {
    private val API_KEY = getApiKey()
    private val client = OkHttpClient()

    fun fetchRealTeamStats(teamInput: String): TeamStats {
        val url = "https://api.football-data.org/v4/competitions/CL/standings"

        val request = Request.Builder()
            .url(url)
            .addHeader("X-Auth-Token", API_KEY)
            .build()

        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    println("⚠️ API Response Error (${response.code}). Using fallback stats.")
                    return TeamStats(
                        teamName = teamInput.uppercase(),
                        matchesPlayed = 0,
                        wins = 0,
                        points = 0,
                        recentFormPoints = 0
                    )
                }

                val responseBody = response.body?.string() ?: ""
                val jsonObject = JsonParser.parseString(responseBody).asJsonObject

                val standingsArray = jsonObject.getAsJsonArray("standings")
                if (standingsArray != null && standingsArray.size() > 0) {
                    val tableArray = standingsArray.get(0).asJsonObject.getAsJsonArray("table")

                    for (element in tableArray) {
                        val entry = element.asJsonObject
                        val teamObject = entry.getAsJsonObject("team")
                        val teamName = teamObject.get("name").asString

                        if (teamName.contains(teamInput, ignoreCase = true)) {
                            val playedGames = entry.get("playedGames").asInt
                            val won = entry.get("won").asInt
                            val points = entry.get("points").asInt

                            return TeamStats(
                                teamName = teamName,
                                matchesPlayed = playedGames,
                                wins = won,
                                points = points,
                                recentFormPoints = won * 3 // বা আপনার MatchModels অনুযায়ী ভ্যালু
                            )
                        }
                    }
                }
            }
        } catch (e: Exception) {
            println("⚠️ Network error: ${e.message}")
        }

        return TeamStats(
            teamName = teamInput.uppercase(),
            matchesPlayed = 0,
            wins = 0,
            points = 0,
            recentFormPoints = 0
        )
    }
}