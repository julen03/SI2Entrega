package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import nagusia.GertaerakDataAccess;
import domainua.Ride;

@ManagedBean(name = "findRidesBean")
@ViewScoped
public class FindRidesBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String departCity;
    private String arrivalCity;
    private Date rideDate;
    private List<Ride> rides;
    private List<String> departCities;      // Lista de ciudades de salida
    private List<String> destinationCities; // Lista de ciudades de destino

    public FindRidesBean() {
        rides = new ArrayList<>();
        departCities = new ArrayList<>();
        destinationCities = new ArrayList<>();
        loadDepartCities(); // Inicializar las ciudades de salida
    }

    private void loadDepartCities() {
        try {
            GertaerakDataAccess facade = FacadeBean.getBusinessLogic();
            departCities = facade.getDepartCities(); 
            System.out.println("Lortu dira ateratzeko iriak");
        } catch (Exception e) {
            System.out.println("Error loading depart cities: " + e.getMessage());
        }
    }

    public void updateDestinationCities() {
        try {
            GertaerakDataAccess facade = FacadeBean.getBusinessLogic();
            destinationCities = facade.getArrivalCities(departCity); 
            System.out.println("Lortu dira bueltatzeko iriak");
        } catch (Exception e) {
            System.out.println("Error updating destination cities: " + e.getMessage());
        }
    }

    public List<Ride> queryRides() {
        try {
            GertaerakDataAccess facade = FacadeBean.getBusinessLogic();
            rides = facade.getRides(departCity, arrivalCity, rideDate);
            System.out.println("Bidaiak bilatuta");
            return rides;
        } catch (Exception e) {
            System.out.println("Error querying rides: " + e.getMessage());
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

    public Date getRideDate() {
        return rideDate;
    }

    public void setRideDate(Date rideDate) {
        this.rideDate = rideDate;
    }

    public List<Ride> getRides() {
        return rides;
    }

    public void setRides(List<Ride> rides) {
        this.rides = rides;
    }

    public List<String> getDepartCities() {
        return departCities;
    }

    public List<String> getDestinationCities() {
        return destinationCities;
    }
}
