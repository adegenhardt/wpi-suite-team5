package edu.wpi.cs.wpisuitetng.modules.calendar;

import edu.wpi.cs.wpisuitetng.Permission;
import java.util.List;
import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class EventCldr extends AbstractModel{
	private String name;
	private List<Event> events;
	//Make this a List<Commitment> later on
	private Commitment commits;
	
	public EventCldr(String name, List<Event> events, Commitment commits){
		this.name = name;
		this.events = events;
		this.commits = commits;
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Permission getPermission(User u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPermission(Permission p, User u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Project getProject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProject(Project p) {
		// TODO Auto-generated method stub
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public Commitment getCommitments() {
		return commits;
	}

	public void setCommitments(Commitment commitments) {
		this.commits = commitments;
	}
	
}
