package Models;

public class Orders {
    String d_order;
    String d_sourceName;
    String d_targetName;
    Integer d_noOfArmiesToMove;

    public Orders(String d_order, String d_targetName, Integer d_noOfArmiesToMove) {
        this.d_order = d_order;
        this.d_targetName = d_targetName;
        this.d_noOfArmiesToMove = d_noOfArmiesToMove;
    }

    public String getD_order() {
        return d_order;
    }

    public void setD_order(String d_order) {
        this.d_order = d_order;
    }

    public String getD_sourceName() {
        return d_sourceName;
    }

    public void setD_sourceName(String d_sourceName) {
        this.d_sourceName = d_sourceName;
    }

    public String getD_targetName() {
        return d_targetName;
    }

    public void setD_targetName(String d_targetName) {
        this.d_targetName = d_targetName;
    }

    public Integer getD_noOfArmiesToMove() {
        return d_noOfArmiesToMove;
    }

    public void setD_noOfArmiesToMove(Integer d_noOfArmiesToMove) {
        this.d_noOfArmiesToMove = d_noOfArmiesToMove;
    }
}
