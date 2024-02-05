package Models;

import java.util.List;

public class CurrentState {
    List<Player> d_players;
    List<Orders> d_pendingOrders;

    public List<Player> getD_players() {
        return d_players;
    }

    public void setD_players(List<Player> d_players) {
        this.d_players = d_players;
    }

    public List<Orders> getD_pendingOrders() {
        return d_pendingOrders;
    }

    public void setD_pendingOrders(List<Orders> d_pendingOrders) {
        this.d_pendingOrders = d_pendingOrders;
    }
}
