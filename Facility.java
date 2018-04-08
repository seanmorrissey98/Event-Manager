import java.util.*;
public class Facility
{
	public int facilityId;
	public String facilityName;
	public double pricePerHour;
	public Date decommissionedUntil;
	
	public Facility(int facilityId, String facilityName, double pricePerHour)
	{
		this.facilityId 		 = facilityId;
		this.facilityName		 = facilityName;
		this.pricePerHour 		 = pricePerHour;
		this.decommissionedUntil = decommissionedUntil;
	}
	
	
	public Facility(int facilityId, String facilityName, double pricePerHour, Date decommissionedUntil) //decommissionedUntil maybe as Date
	{
		this.facilityId 		 = facilityId;
		this.facilityName 		 = facilityName;
		this.pricePerHour 		 = pricePerHour;
		this.decommissionedUntil = decommissionedUntil;
	}
	
	public int getFacilityId()
	{
		return facilityId;
	}
	
	public String getFacilityName()
	{
		return facilityName;
	}
	
	public double getPricePerHour()
	{
		return pricePerHour;
	}
	
	public Date getDecommissionedUntil()
	{
		return decommissionedUntil;
	}	
	
	public String facilityToString()
	{
		String info	= facilityId + "," + facilityName + "," + pricePerHour + "," + decommissionedUntil;
		return info;
	}
}
