package beans;

import java.io.Serializable;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import domainua.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import nagusia.GertaerakDataAccess;

@ManagedBean(name = "createRideBean")
@RequestScoped 
public class CreateRideBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String departCity;
    private String arrivalCity;
    private int numSeats;
    private float price;
    private Date rideDate;

    public String createRide() {
        try {
            GertaerakDataAccess facade = FacadeBean.getBusinessLogic();
            Ride ride = facade.createRide(departCity, arrivalCity, rideDate, numSeats, price, "driver1@gmail.com");
            System.out.println("Ride sortu da " + ride.toString());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Ride created successfully!"));
            return "Prueba.xhtml?faces-redirect=true";

        } catch (RideMustBeLaterThanTodayException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ride date must be later than today."));
        } catch (RideAlreadyExistException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ride already exists."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "An unexpected error occurred."));
        }

        return null;
    }

    // Getters y setters
    public String getDepartCity() {
        return departCity;
    }

    public void setDepartCity(String departCity) {
        this.departCity = departCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getRideDate() {
        return rideDate;
    }

    public void setRideDate(Date rideDate) {
        this.rideDate = rideDate;
    }
}
