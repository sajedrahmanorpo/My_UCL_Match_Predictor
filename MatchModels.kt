data class TeamStats(
    val teamName: String,
    val matchesPlayed: Int = 0,
    val wins: Int = 0,
    val points: Int = 0,
    val recentFormPoints: Int = 0,
    val avgGoalsScored: Double = 1.5,
    val uclExperienceScore: Double = 5.0
)

data class MatchPredictionResult(
    val teamA: String,
    val teamB: String,
    val teamAWinProb: Double,
    val teamBWinProb: Double,
    val drawProb: Double,
    val predictedWinner: String
)