package beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import domainua.Ride;
import nagusia.GertaerakDataAccess;

@SuppressWarnings("serial")
@ManagedBean(name = "DatarekinBeans")
@RequestScoped
public class DatarekinBeans implements Serializable{
	
	public Date date;
	private List<Ride> rides;
	

	public String getBidaiakData() {
		 GertaerakDataAccess facade = FacadeBean.getBusinessLogic();
         this.rides = facade.getBidaiakData(date);
         FacesContext.getCurrentInstance().addMessage(null,
                 new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Ride created successfully!"));
         System.out.println( "Bidaiak datarekin lortuak Bean");
         return "Bidaiak datarekin lortuak Bean";
	}
	 public Date getDate() {
	        return date;
	    }

	 public void setDate(Date rideDate) {
	        this.date = rideDate;
	    }

	    public List<Ride> getRides() {
	         System.out.println( "Rides lortuta");
	        return rides;
	    }

	    public void setRides(List<Ride> rides) {
	        this.rides = rides;
	    }
}
