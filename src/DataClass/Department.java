package DataClass;

import java.util.ArrayList;
import java.util.Arrays;
import API.API;

public class Department {

	public String id;
	public String name;
	public ArrayList<String> stagesIds = new ArrayList<String>();
	
	public Department(String id, String name) {
		this.id = id;
		API.registerId(id);
		
		this.name = name;
	}
	
	public Department(String name) {
		this(API.getUniqueId(), name);
	}
	
	public Department(String id, String name, String ...stagesIds) {
		this(id, name);
		this.stagesIds.addAll(Arrays.asList(stagesIds));
	}
	
	public Department(String name, String ...stagesIds) {
		this(name);
		this.stagesIds.addAll(Arrays.asList(stagesIds));
	}
	
	public void addStage(Stage stage)
	{
		API.addStage(stage);
		stagesIds.add(stage.id);
		API.updateDepartmentById(id, this);
	}
	
	public Stage[] getStages()
	{
		return API.getAllStagesById(stagesIds);
	}
	
	public void delete()
	{
		API.deleteDepartmentById(id);
	}

    @Override
    public String toString() {
        return name;
    }
}