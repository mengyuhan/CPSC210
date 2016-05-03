package ca.ubc.cs.cpsc210.mindthegap.parsers;

import ca.ubc.cs.cpsc210.mindthegap.model.*;
import ca.ubc.cs.cpsc210.mindthegap.parsers.exception.TfLArrivalsDataMissingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

/**
 * A parser for the data returned by the TfL station arrivals query
 */
public class TfLArrivalsParser extends TfLAbstractParser {

    /**
     * Parse arrivals from JSON response produced by TfL query.  All parsed arrivals are
     * added to given station assuming that corresponding JSON object as all of:
     * timeToStation, platformName, lineID and one of destinationName or towards.  If
     * any of the aforementioned elements is missing, the arrival is not added to the station.
     *
     * @param stn          station to which parsed arrivals are to be added
     * @param jsonResponse the JSON response produced by TfL
     * @throws JSONException                   when JSON response does not have expected format
     * @throws TfLArrivalsDataMissingException when all arrivals are missing at least one of the following:
     *                                         <ul>
     *                                         <li>timeToStation</li>
     *                                         <li>platformName</li>
     *                                         <li>lineId</li>
     *                                         <li>destinationName and towards</li>
     *                                         </ul>
     */
    public static void parseArrivals(Station stn, String jsonResponse)
            throws JSONException, TfLArrivalsDataMissingException {
        JSONArray jsonArray = new JSONArray(jsonResponse);
        int numberOfIncompleteArrivals=0;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject arrivalJSONObject = jsonArray.getJSONObject(i);
            if (arrivalJSONObject.has("timeToStation") && arrivalJSONObject.has("platformName") && arrivalJSONObject.has("lineId") && (arrivalJSONObject.has("destinationName") || arrivalJSONObject.has("towards"))) {
                int timeToStation = arrivalJSONObject.getInt("timeToStation");
                String destinationName;
                if (arrivalJSONObject.has("destinationName")) {
                    String name = arrivalJSONObject.getString("destinationName");
                    destinationName = parseName(name);
                } else destinationName = arrivalJSONObject.getString("towards");
                String platformName = arrivalJSONObject.getString("platformName");
                String lineId = arrivalJSONObject.getString("lineId");
                String lineName = arrivalJSONObject.getString("lineName");
                Arrival arrival = new Arrival(timeToStation, destinationName, platformName);
                boolean haveLine=false;
                for (Line l:stn.getLines()){
                    if (l.getId().equals(lineId)){
                        haveLine=true;
                        stn.addArrival(l,arrival);
                    }
                }
                if (!haveLine) {
                    Line line = new Line(LineResourceData.valueOf(lineId.toUpperCase()), lineId, lineName);
                    line.addStation(stn);
                    stn.addArrival(line, arrival);
                }
            }
            else numberOfIncompleteArrivals+=1;
        }
        if (numberOfIncompleteArrivals==jsonArray.length())
            throw new TfLArrivalsDataMissingException();
    }
}
