package ca.ubc.cs.cpsc210.integerset.util;

// Represents a set of integers
public interface IntegerSet {

    // Inserts element into the IntegerSet
    // MODIFIES: this
    // EFFECTS: Integer i is added to the IntegerSet if it's notj
    // already in the IntegerSet
    void insert(Integer i);

    // Removes element from the IntegerSet
    // REQUIRES: Integer i is an element of the IntegerSet
    // MODIFIES: this
    // EFFECTS: Integer i is removed from the IntegerSet
    void remove(Integer i);

    // Determines if element is in the IntegerSet
    // EFFECTS: Returns true if Integer i is in the IntegerSet
    // and false otherwise
    boolean contains(Integer i);

    // Gets the size of the IntegerSet
    // EFFECTS: Returns the number of items in the set
    int size();

    IntegerSet intersection(IntegerSet a, IntegerSet b);    
}

