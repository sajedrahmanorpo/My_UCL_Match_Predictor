import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)

    println("==================================================================")
    println("       REAL-TIME UEFA CHAMPIONS LEAGUE PREDICTOR             ")
    println("==================================================================")

    print("Enter Home Team Name: ")
    val teamAInput = scanner.nextLine()

    print("Enter Away Team Name: ")
    val teamBInput = scanner.nextLine()

    println("\nFetching live standings & stats from Football-Data API...")

    val teamAStats = FootballApiClient.fetchRealTeamStats(teamAInput)
    val teamBStats = FootballApiClient.fetchRealTeamStats(teamBInput)

    val result = PredictorEngine.predictMatch(teamAStats, teamBStats)

    println("\n==================================================================")
    println("LIVE MATCH ANALYSIS: ${result.teamA.uppercase()} vs ${result.teamB.uppercase()}")
    println("==================================================================")
    println("${result.teamA} Winning Chance : %.1f%%".format(result.teamAWinProb))
    println("${result.teamB} Winning Chance : %.1f%%".format(result.teamBWinProb))
    println("Match Draw Chance        : %.1f%%".format(result.drawProb))
    println("------------------------------------------------------------------")
    println("PREDICTED FAVORITE TO WIN : ${result.predictedWinner.uppercase()}")
    println("==================================================================\n")
}