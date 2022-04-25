package DataClass;

public class Address {
		
	public String country;
	public String city;
	public String street;
	public String buildingNumber;
	
	public Address(String country, String city, String street, String buildingNumber) {
		this.country = country;
		this.city = city;
		this.street = street;
		this.buildingNumber = buildingNumber;
	}
	
	public String toString() {
		return String.format("%s, %s, %s, %s", country, city, street, buildingNumber);
	}
	
}
