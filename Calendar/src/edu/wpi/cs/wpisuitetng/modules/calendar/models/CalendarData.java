package edu.wpi.cs.wpisuitetng.modules.calendar.models;

import edu.wpi.cs.wpisuitetng.Permission;
import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.YearData;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

import java.util.HashMap;

import com.google.gson.Gson;

public class CalendarData extends AbstractModel {

	
	// class map of YearData objects
	// Storage Structure for calendars and their events/commitments
	private HashMap<Integer, YearData> dataMap = new HashMap<Integer, YearData>();

	public CalendarData (){}
	// Required Functions for Model
	/* database interaction */
	public void save(){}
	public void delete(){}
	
	/* serializing */
	/** toJSON : serializing this Model's contents into a JSON/GSON string
	 * @return	A string containing the serialized JSON representation of this Model.
	 */
	public String toJSON(){
		return "null";
	}
	
	/* deserializing */
	/* Built-in overrides/overloads */
	/** toString : enforce an override. May simply call serializeToJSON.
	 * @return	The string representation of this Model
	 */
	public String toString(){
		return "null";
	}
	
	@Override
	public Boolean identify(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
/*
	
	 * Returns an array of Requirements parsed from the given JSON-encoded
	 * string.
	 * 
	 * @param json
	 *            string containing a JSON-encoded array of Requirement
	 * 
	 * @return an array of Requirement deserialized from the given JSON string
	public static Requirement[] fromJsonArray(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Requirement[].class);
	}*/
}

