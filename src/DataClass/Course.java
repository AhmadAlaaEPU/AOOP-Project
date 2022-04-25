package DataClass;

import API.API;

public class Course {

	public String id;
	public String name;

	public Course(String id, String name) {
		this.id = id;
		API.registerId(id);

		this.name = name;
	}

	public Course(String name) {
		this(API.getUniqueId(), name);
	}

	public Stage getStage()
	{
		for(Stage stage : API.getAllStage())
		{
			for(Course course : stage.getCourses())
			{
				if(course.id.equals(this.id))
				{
					return stage;
				}
			}
		}
		
		return null;
	}
	
	public Department getDepartment()
	{
		for(Department department : API.getAllDepartment())
		{
			for(Stage stage : department.getStages())
			{
				for(Course course : stage.getCourses())
				{
					if(course.id.equals(this.id))
					{
						return department;
					}
				}
			}
		}
		
		return null;
	}
	
	public void changeStage(Stage newStage)
	{
		Stage oldStage = this.getStage();

		if(!oldStage.id.equals(newStage.id))
		{
			for(int i = 0; i < oldStage.coursesIds.size(); i++)
			{
				if(oldStage.coursesIds.get(i).equals(this.id))
				{
					oldStage.coursesIds.remove(i);
					API.updateStageById(oldStage.id, oldStage);
					break;
				}
			}
			
			newStage.addCourse(this);
			
		}
	}

	public void delete()
	{
		API.deleteCourseById(id);
	}

	@Override
	public String toString() {
		return name;
	}
}