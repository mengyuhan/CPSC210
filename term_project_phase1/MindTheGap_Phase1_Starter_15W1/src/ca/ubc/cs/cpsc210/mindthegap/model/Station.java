package ca.ubc.cs.cpsc210.mindthegap.model;

import ca.ubc.cs.cpsc210.mindthegap.util.LatLon;

import java.util.*;

/**
 * Represents a station on the underground with an id, name, location (lat/lon)
 * set of lines with stops at this station and a list of arrival boards.
 */
public class Station implements Iterable<ArrivalBoard> {
    private List<ArrivalBoard> arrivalBoards;
    private String id;
    private String name;
    private LatLon locn;
    private Set<Line> lines;
    /**
     * Constructs a station with given id, name and location.
     * Set of lines and list of arrival boards are empty.
     *
     * @param id    the id of this station (cannot by null)
     * @param name  name of this station
     * @param locn  location of this station
     */


    public Station(String id, String name, LatLon locn) {

        this.id=id;
        this.name=name;
        this.locn=locn;
        lines=new HashSet<>();
        arrivalBoards=new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public LatLon getLocn() {
        return locn;
    }

    public String getID() {
        return id;
    }

    public Set<Line> getLines() {
        return lines;
    }

    public int getNumArrivalBoards() {
        return arrivalBoards.size();
    }

    /**
     * Add line to set of lines with stops at this station.
     *
     * @param line  the line to add
     */
    public void addLine(Line line) {
        if(!(hasLine(line))) {
            lines.add(line);
            line.addStation(this);
            }
    }

    /**
     * Remove line from set of lines with stops at this station
     *
     * @param line the line to remove
     */
    public void removeLine(Line line) {
        if (hasLine(line)){
        lines.remove(line);
        line.removeStation(this);
        }
    }
    /**
     * Determine if this station is on a given line
     * @param line  the line
     * @return  true if this station is on given line
     */
    public boolean hasLine(Line line) {
        boolean hasLine=false;
        for (Line l:lines){
            if (l.equals(line)){
                hasLine=true;
            }
        }
        return hasLine;
    }



    /**
     * Add train arrival travelling on a particular line in a particular direction to this station.
     * Arrival is added to corresponding arrival board based on the line on which it is
     * operating and the direction of travel (as indicated by platform prefix).  If the arrival
     * board for given line and travel direction does not exist, it is created and added to
     * arrival boards for this station.
     *
     * @param line    line on which train is travelling
     * @param arrival the train arrival to add to station
     */
    public void addArrival(Line line, Arrival arrival) {
        String direction=arrival.getTravelDirn();
        for (int i=0;i<arrivalBoards.size();i++) {
            ArrivalBoard next=arrivalBoards.get(i);
            String d = next.getTravelDirn();
            Line l = next.getLine();
            if (l.equals(line)  && d.equals(direction) ) {
                next.addArrival(arrival);
                return;
            }
        }
        ArrivalBoard ab=new ArrivalBoard(line,direction);
        ab.addArrival(arrival);
        arrivalBoards.add(ab);


    }

    /**
     * Remove all arrival boards from this station
     */
    public void clearArrivalBoards() {
        arrivalBoards.clear();

    }

    /**
     * Two stations are equal if their ids are equal
     */
//    @Override
//    public boolean equals(Object o) {
//        return false;
//    }
//
//    /**
//     * Two stations are equal if their ids are equal
//     */
//    @Override
//    public int hashCode() {
//        return 0;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Station that = (Station) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
    @Override
    public Iterator<ArrivalBoard> iterator() {
        // Do not modify the implementation of this method!
        return arrivalBoards.iterator();
    }

    public List<ArrivalBoard> getArrivalBoards(){
        return arrivalBoards;
    }
}
