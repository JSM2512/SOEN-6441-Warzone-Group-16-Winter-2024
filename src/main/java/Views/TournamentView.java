package Views;

import Constants.ProjectConstants;
import Models.CurrentState;
import Models.Tournament;

import java.util.List;

/**
 * The type Tournament view.
 */
public class TournamentView {
    /**
     * The D tournament.
     */
    Tournament d_tournament;
    /**
     * The D current states object.
     */
    List<CurrentState> d_currentStatesObject;

    /**
     * Instantiates a new Tournament view.
     *
     * @param p_tournament the p tournament
     */
    public TournamentView(Tournament p_tournament) {
        d_tournament = p_tournament;
        d_currentStatesObject = d_tournament.getD_currentStateList();
    }

    /**
     * Render center string.
     *
     * @param p_width  the p width
     * @param p_string the p string
     */
    public void renderCenterString(int p_width, String p_string) {
        String l_centeredString = String.format("%" + p_width + "s", String.format("%" + (p_string.length() + (p_width - p_string.length()) / 2) + "s", p_string));
        System.out.format(l_centeredString+"\n");
    }

    /**
     * Render separator.
     */
    public void renderSeparator() {
        StringBuilder l_separator = new StringBuilder();
        for (int i = 0; i < ProjectConstants.WIDTH - 2; i++) {
            l_separator.append("-");
        }
        System.out.format("+%s+%n", l_separator.toString());
    }

    /**
     * Render map name.
     *
     * @param p_index   the p index
     * @param p_mapName the p map name
     */
    public void renderMapName(Integer p_index, String p_mapName) {
        String l_formattedString = String.format("%s %s %d %s", p_mapName, "(Game Number:", p_index, ")");
        renderSeparator();
        renderCenterString(ProjectConstants.WIDTH, l_formattedString);
        renderSeparator();
    }

    /**
     * Render games.
     *
     * @param p_currentState the p current state
     */
    public void renderGames(CurrentState p_currentState) {
        String l_winner;
        String l_conclusion;
        if (p_currentState.getD_winner() == null) {
            l_winner = " ";
            l_conclusion = "Draw";
        } else {
            l_winner = p_currentState.getD_winner().getD_name();
            l_conclusion = "Winner Player Strategy: " + p_currentState.getD_winner().getD_playerBehaviourStrategy().getPlayerBehaviour();
        }
        String l_winnerString = String.format("%s %s", "Winner:", l_winner);
        StringBuilder l_commaSeparatedPlayers = new StringBuilder();
        for (int i=0; i< p_currentState.getD_playersFailed().size(); i++) {
            l_commaSeparatedPlayers.append(p_currentState.getD_playersFailed().get(i).getD_name());
            if (i < p_currentState.getD_playersFailed().size() - 1) {
                l_commaSeparatedPlayers.append(", ");
            }
        }
        String l_losingPlayer = "Losing Players: " + l_commaSeparatedPlayers.toString();
        String l_conclusionString = String.format("%s %s", "Conclusion of game : ", l_conclusion);
        System.out.println(l_winnerString);
        System.out.println(l_losingPlayer);
        System.out.println(l_conclusionString);
    }

    /**
     * View tournament.
     */
    public void viewTournament(){
        int l_counter = 0;
        System.out.println();
        if (d_tournament != null && d_currentStatesObject != null) {
            for (CurrentState l_currentState : d_tournament.getD_currentStateList()) {
                l_counter++;
                renderMapName(l_counter, l_currentState.getD_map().getD_mapName());
                renderGames(l_currentState);
            }
        }
    }
}
