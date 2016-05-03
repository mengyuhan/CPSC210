package ca.ubc.cs.cpsc210.mindthegap.tests.model;
import ca.ubc.cs.cpsc210.mindthegap.model.Branch;
import ca.ubc.cs.cpsc210.mindthegap.model.Line;
import ca.ubc.cs.cpsc210.mindthegap.model.LineResourceData;
import ca.ubc.cs.cpsc210.mindthegap.model.Station;
import ca.ubc.cs.cpsc210.mindthegap.parsers.BranchStringParser;
import ca.ubc.cs.cpsc210.mindthegap.util.LatLon;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import javax.xml.ws.BindingType;
import java.util.*;

import static org.junit.Assert.*;
/**
 * Created by Mengyu on 2015/10/26.
 */
public class LineTest {
    private List<Station> stns;
    private Set<Branch> branches;
    private LineResourceData lmd;
    private String id;
    private String name;
    private Line line;
    private Station st;
    private Branch branch;
    private LatLon latlon;

    @Before
    public void setUp(){
        stns=new ArrayList<Station>();
        branches=new HashSet<Branch>();

    }

    @Test
    public void testGetName(){
        line=new Line(lmd,id,"random");
        assertEquals("random",line.getName());
    }

    @Test
    public void testGetId(){
        line=new Line(lmd,"randomid","random");
        assertEquals("randomid",line.getId());
    }

    @Test
    public void testGetColour(){
       lmd= LineResourceData.CENTRAL;
        line=new Line(lmd,id,"random");
        assertEquals(0xFFDC241F,line.getColour());
    }

    @Test
    public void testAddStation(){
        line=new Line(lmd,id,"random");
        st=new Station(id,name,latlon);
        line.addStation(st);
        assertEquals(st,line.getStations().get(0));
    }

    @Test
    public void testRemoveStation(){
        line=new Line(lmd,id,"random");
        st=new Station(id,name,latlon);
        line.addStation(st);
        assertEquals(1, line.getStations().size());
        line.removeStation(st);
        assertEquals(0, line.getStations().size());
    }

    @Test
    public void testClearStation(){
        line=new Line(lmd,id,"random");
        st=new Station("1",name,latlon);
        Station station=new Station("2",name,latlon);
        line.addStation(st);
        station.addLine(line);
        assertTrue(st.getLines().contains(line));
        assertTrue(line.hasStation(station));
        assertEquals(2, line.getStations().size());
        line.clearStations();
        assertEquals(0, line.getStations().size());
        assertFalse(st.getLines().contains(line));
        assertFalse(station.getLines().contains(line));
    }

    @Test
    public void testGetStations(){
        line=new Line(lmd,id,"random");
        st=new Station(id,name,latlon);
        line.addStation(st);
        List<Station> sts= line.getStations();
        assertEquals(sts, line.getStations());
    }

    @Test
    public void testHasStations(){
        line=new Line(lmd,id,"random");
        st=new Station(id,name,latlon);
        assertFalse(line.hasStation(st));
        line.addStation(st);
        assertTrue(line.hasStation(st));
    }

    @Test
    public void testAddBranch()  {
        line=new Line(lmd,id,"random");
        branch=new Branch("[[[0.093493,51.6037],[0.091015,51.5956],[0.088596,51.5857]]]");
        line.addBranch(branch);
        assertTrue(line.getBranches().contains(branch));
    }

    @Test
    public void testEquals(){
        line=new Line(lmd,"random","random");
        Line l=new Line(lmd,"random","random2");
        assertEquals(l, line);
        List<Line> lines=new ArrayList<>();
        lines.add(l);
        assertTrue(lines.contains(line));
    }


}
