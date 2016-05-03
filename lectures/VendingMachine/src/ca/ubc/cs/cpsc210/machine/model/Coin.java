package ca.ubc.cs.cpsc210.machine.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Represents a coin (one of loonie, quarter, dime & nickel).
 */
public enum Coin {
    LOONIE(100), QUARTER(25), DIME(10), NICKEL(5), NONE(0);

    private int value;  // value of coin in cents

    Coin(int value) {
        this.value = value;
    }



    // Get the value of this coin
    // EFFECTS: returns the value of this coin in cents
    public int getValue() {
        return value;
    }

}
