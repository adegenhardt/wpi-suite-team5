package edu.wpi.cs.wpisuitetng.modules.calendar;

import edu.wpi.cs.wpisuitetng.Permission;
import java.util.List;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class EventCldr implements Model{
	String name;
	List<Event> e;
	Commitment c;
	
	public EventCldr(String name, List<Event> e, Commitment c){
		this.name = name;
		this.e = e;
		this.c = c;
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
	
	
}
