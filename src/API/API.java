package API;

import java.util.ArrayList;
import java.util.Random;

import DataClass.Course;
import DataClass.Department;
import DataClass.Stage;
import DataClass.Student;
import Database.Database;
import Database.IDatabaseChangeListener;

public final class API {

	private API() {}

	// Custom Event handling for database changes

	private static ArrayList<IDatabaseChangeListener> listeners = new ArrayList<IDatabaseChangeListener>();

	public static void addListener(IDatabaseChangeListener toAdd) {
		listeners.add(toAdd);
	}

	public static void databaseUpdated() {
		System.out.println("Database Updated");

		// Notify everybody in listeners ArrayList
		for (IDatabaseChangeListener l : listeners)
			l.onDatabaseUpdated();

		Database.save();
	}

	public static void readDatabase() {
		Database.read();
	}

	// **************************************
	// Student
	// **************************************

	public static void deleteStudentById(String id) {
		Database.students.remove(id);
		databaseUpdated();
	}

	public static Student[] getStudentsByName(String fullName) {

		ArrayList<Student> tempList = new ArrayList<Student>();

		for (Student s : Database.students.values()) {
			if (s.fullName.toLowerCase().contains(fullName.toLowerCase())) {
				tempList.add(s);
			}
		}

		return tempList.toArray(new Student[0]);
	}

	public static Student[] getStudentsByMobileNumber(String mobileNumber) {

		ArrayList<Student> tempList = new ArrayList<Student>();

		for (Student s : Database.students.values()) {
			if (s.mobileNumber.toLowerCase().contains(mobileNumber.toLowerCase())) {
				tempList.add(s);
			}
		}

		return tempList.toArray(new Student[0]);
	}

	public static Student[] getStudentsByCourseName(String courseName) {

		ArrayList<Student> tempList = new ArrayList<Student>();

		for (Student s : Database.students.values()) {
			for (Course c : s.getCourses()) {
				if (c.name.toLowerCase().contains(courseName.toLowerCase())) {
					tempList.add(s);
				}
			}
		}

		return tempList.toArray(new Student[0]);
	}

	public static Student[] getAllStudents() {
		return Database.students.values().toArray(new Student[0]);
	}

	public static Student getStudentById(String studentID) {
		return Database.students.get(studentID);
	}

	public static void updateStudentById(String studentID, Student student) {
		Database.students.put(studentID, student);
		databaseUpdated();
	}

	public static void addStudent(Student student) {
		Database.students.put(student.id, student);
		databaseUpdated();
	}

	// **************************************
	// Department
	// **************************************

	public static void deleteDepartmentById(String id) {
		Database.departments.remove(id);
		databaseUpdated();
	}

	public static Department[] getAllDepartment() {
		return Database.departments.values().toArray(new Department[0]);
	}

	public static Department getDepartmentById(String id) {
		return Database.departments.get(id);
	}

//	public static Department[] getAllDepartmentsById(ArrayList<String> departmentIds)
//	{
//		ArrayList<Department> tempList = new ArrayList<Department>();
//		
//		for(Department department : Database.departments.values())
//		{
//			for(String id : departmentIds)
//			{
//				if(department.id.equals(id))
//				{
//					tempList.add(department);
//					
//				}
//			}
//		}
//		
//		return tempList.toArray(new Department[0]);
//	}

	public static void addDepartment(Department department) {
		Database.departments.put(department.id, department);
		databaseUpdated();
	}

	public static void updateDepartmentById(String id, Department department) {
		Database.departments.put(id, department);
		databaseUpdated();
	}

	// **************************************
	// Stage
	// **************************************

	public static void addStage(Stage stage) {
		Database.stages.put(stage.id, stage);
		databaseUpdated();
	}

	public static void deleteStageById(String id) {
		Database.stages.remove(id);
		databaseUpdated();
	}

	public static Stage getStageById(String id) {
		return Database.stages.get(id);
	}

	public static Stage[] getAllStage() {
		return Database.stages.values().toArray(new Stage[0]);
	}
	
	public static Stage[] getAllStagesById(ArrayList<String> stageIds) {
		ArrayList<Stage> tempList = new ArrayList<Stage>();

		for (Stage stage : Database.stages.values()) {
			for (String id : stageIds) {
				if (stage.id.equals(id)) {
					tempList.add(stage);

				}
			}
		}

		return tempList.toArray(new Stage[0]);
	}

	public static Stage getStageByName(Department department, String stageName) {
		Stage[] stages = department.getStages();

		for (Stage stage : stages) {
			if (stage.name.equals(stageName)) {
				return stage;
			}
		}

		return null;
	}

	public static void updateStageById(String id, Stage stage) {
		Database.stages.put(id, stage);
		databaseUpdated();
	}

	// **************************************
	// Course
	// **************************************

	public static void deleteCourseById(String id) {
		Database.courses.remove(id);
		databaseUpdated();
	}

	public static void addCourse(Course course) {
		Database.courses.put(course.id, course);
		databaseUpdated();
	}

	public static Course[] getAllCoursesById(ArrayList<String> courseIds) {
		ArrayList<Course> tempList = new ArrayList<Course>();

		for (Course course : Database.courses.values()) {
			for (String id : courseIds) {
				if (course.id.equals(id)) {
					tempList.add(course);

				}
			}
		}

		return tempList.toArray(new Course[0]);
	}

	public static Course[] getAllCourses() {
		return Database.courses.values().toArray(new Course[0]);
	}
	
	public static void updateCourseById(String id, Course course) {
		Database.courses.put(id, course);
		databaseUpdated();
	}
	
	public static Course getCourseById(String id) {
		return Database.courses.get(id);
	}
	
	// **************************************
	// ID GENERATOR
	// **************************************

	private static ArrayList<String> registeredIds = new ArrayList<String>();
	 
	public static void registerId(String id)
	{
		registeredIds.add(id);		
	}
	
	public static String getUniqueId() {
		
		String id = RandomIdGenerator.GetBase62(10);
		
		if(registeredIds.contains(id))
		{
			return getUniqueId();
		}
		else
		{
			registerId(id);
			return id;
		}
		
	}

	static class RandomIdGenerator {
		private static char[] _base62chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
				.toCharArray();

		private static Random _random = new Random();

		public static String GetBase62(int length) {
			var sb = new StringBuilder(length);

			for (int i = 0; i < length; i++)
				sb.append(_base62chars[_random.nextInt(62)]);

			return sb.toString();
		}

	}

}