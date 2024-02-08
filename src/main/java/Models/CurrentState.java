package Models;

import java.util.List;

public class CurrentState {
    List<Player> d_players;
    List<Orders> d_pendingOrders;
    Map d_map;

    public List<Player> getD_players() {
        return d_players;
    }

    public void setD_players(List<Player> p_players) {
        this.d_players = p_players;
    }

    public List<Orders> getD_pendingOrders() {
        return d_pendingOrders;
    }

    public void setD_pendingOrders(List<Orders> p_pendingOrders) {
        this.d_pendingOrders = p_pendingOrders;
    }

    public Map getD_map() {
        return d_map;
    }

    public void setD_map(Map d_map) {
        this.d_map = d_map;
    }
}
