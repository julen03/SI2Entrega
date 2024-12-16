package beans;

import java.util.Date;
import java.util.List;

import javax.faces.bean.RequestScoped;

import domainua.Ride;
import dataAccess.DataAccess;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import nagusia.GertaerakDataAccess;

@RequestScoped
public class FacadeBean {
    private String departCity;
    private String arrivalCity;
    private Date rideDate;
    private int seats;
    private float price;
    private String driverEmail;
    private List<String> departCities;
    private List<String> arrivalCities;
    private List<Ride> rides;

    private static FacadeBean singleton = new FacadeBean( );
    private static GertaerakDataAccess facadeInterface;
    private FacadeBean() {
        try {
            facadeInterface = new GertaerakDataAccess();
        } catch (Exception e) {
            System.out.println("FacadeBean: Errorea negozioaren logika sortzean: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static GertaerakDataAccess getBusinessLogic( ) {
     return facadeInterface;
    }
    // Getters and Setters
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

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDriverEmail() {
        return driverEmail;
    }

    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    public List<String> getDepartCities() {
        return departCities;
    }

    public void setDepartCities(List<String> departCities) {
        this.departCities = departCities;
    }

    public List<String> getArrivalCities() {
        return arrivalCities;
    }

    public void setArrivalCities(List<String> arrivalCities) {
        this.arrivalCities = arrivalCities;
    }

    public List<Ride> getRides() {
        return rides;
    }

    public void setRides(List<Ride> rides) {
        this.rides = rides;
    }

    // Business Logic Methods
    public void loadDepartCities() {
        this.departCities = facadeInterface.getDepartCities();
    }

    public void loadArrivalCities() {
        if (departCity != null && !departCity.isEmpty()) {
            this.arrivalCities = facadeInterface.getArrivalCities(departCity);
        }
    }

    public void getRides(String c, String a, Date d) {
        if (c != null && a != null && d != null) {
            this.rides = facadeInterface.getRides(c, a, d);
            System.out.println("Bidaiak lortu dira");
        }
    }

    public void createRide() {
        try {
        	facadeInterface.createRide(departCity, arrivalCity, rideDate, seats, price, driverEmail);
        } catch (RideMustBeLaterThanTodayException e) {
            System.err.println("Ride date must be later than today: " + e.getMessage());
        } catch (RideAlreadyExistException e) {
            System.err.println("Ride already exists: " + e.getMessage());
        }
    }
/*
    public void closeFacade() {
    	facadeInterface.close();
    }*/
}
