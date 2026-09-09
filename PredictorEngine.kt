object PredictorEngine {
    fun predictMatch(teamA: TeamStats, teamB: TeamStats): MatchPredictionResult {
        val scoreA = (teamA.recentFormPoints * 2.5) + (teamA.avgGoalsScored * 4.0) + (teamA.uclExperienceScore * 3.0) + teamA.points
        val scoreB = (teamB.recentFormPoints * 2.5) + (teamB.avgGoalsScored * 4.0) + (teamB.uclExperienceScore * 3.0) + teamB.points

        val totalScore = if ((scoreA + scoreB) == 0.0) 1.0 else (scoreA + scoreB)

        val drawProb = 22.0
        val remainingProb = 100.0 - drawProb

        val teamAWinProb = (scoreA / totalScore) * remainingProb
        val teamBWinProb = (scoreB / totalScore) * remainingProb

        val winner = if (teamAWinProb > teamBWinProb) {
            teamA.teamName
        } else if (teamBWinProb > teamAWinProb) {
            teamB.teamName
        } else {
            "Draw / Even Match"
        }

        return MatchPredictionResult(
            teamA = teamA.teamName,
            teamB = teamB.teamName,
            teamAWinProb = String.format("%.1f", teamAWinProb).toDouble(),
            teamBWinProb = String.format("%.1f", teamBWinProb).toDouble(),
            drawProb = drawProb,
            predictedWinner = winner
        )
    }
}