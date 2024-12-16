package nagusia;

import configuration.ConfigXML;
import configuration.UtilDate;

import domainua.Ride;
import domainua.HibernateUtil;
import domainua.Driver;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.File;
import java.util.*;

public class GertaerakDataAccess {

    private EntityManager db;
    private EntityManagerFactory emf;
    private ConfigXML c = ConfigXML.getInstance();

    public GertaerakDataAccess() {
        
    }

	public List<Ride> getRides(String from, String to, Date date) {
	    System.out.println(">> DataAccess: getRides => from= " + from + " to= " + to + " date= " + date);

	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();
	    
	    Query query = session.createQuery(
	        "SELECT r FROM Ride r WHERE r.from = :from AND r.to = :to AND r.date = :date"
	        
	    );
	    query.setParameter("from", from);
	    query.setParameter("to", to);
	    query.setParameter("date", date);

	    List rides = query.list();
	    
	    session.getTransaction().commit();
	    return rides;
	}
	public Ride createRide(String from, String to, Date date, int nPlaces, float price, String driverEmail) 
	        throws RideAlreadyExistException, RideMustBeLaterThanTodayException {
	    System.out.println(">> DataAccess: createRide => from= " + from + " to= " + to + " driver= " + driverEmail + " date= " + date);

	    Transaction tx = null; // Manejo explícito de la transacción
	    try {
	    	Session session = HibernateUtil.getSessionFactory().openSession();// Bloque try-with-resources para garantizar cierre de la sesión
	        tx = session.beginTransaction();

	        // Validar que la fecha sea futura
	        if (new Date().compareTo(date) > 0) {
	            throw new RideMustBeLaterThanTodayException(
	                ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorRideMustBeLaterThanToday")
	            );
	        }

	        // Buscar al conductor (Driver)
	        Driver driver = (Driver)session.get(Driver.class, driverEmail);
	        if (driver == null) {
	            throw new NullPointerException("Driver not found with email: " + driverEmail);
	        }

	        // Verificar si ya existe un ride con los mismos detalles
	        if (driver.doesRideExists(from, to, date)) {
	            throw new RideAlreadyExistException(
	                ResourceBundle.getBundle("Etiquetas").getString("DataAccess.RideAlreadyExist")
	            );
	        }

	        // Crear el nuevo ride y asociarlo al conductor
	        Ride ride = driver.addRide(from, to, date, nPlaces, price);

	        // Persistir los cambios en la base de datos
	        session.saveOrUpdate(driver); // Actualiza el conductor y sus rides asociados automáticamente

	        tx.commit(); // Confirmar la transacción
	        return ride;
	    } catch (Exception e) {
	        if (tx != null && tx.isActive()) {
	            tx.rollback(); // Revertir la transacción si ocurre un error
	        }
	        System.err.println("Error during ride creation: " + e.getMessage());
	        throw e; // Re-lanzar la excepción para manejarla en niveles superiores
	    }
	}

	public List<String> getArrivalCities(String from) {
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();
	    
	    Query query = session.createQuery(
	        "SELECT DISTINCT r.to FROM Ride r WHERE r.from = :from ORDER BY r.to"
	    );
	    query.setParameter("from", from);

	    List arrivingCities = query.list();

	    session.getTransaction().commit();
	    return arrivingCities;
	}
	public List<Ride> getBidaiakData(Date date) {
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();
	    
	    Query query = session.createQuery(
	        "SELECT DISTINCT r FROM Ride r WHERE r.date = :date ORDER BY r.to"
	    );
	    query.setParameter("date", date);

	    List<Ride> arrivingCities = query.list();
	    for(Ride ride : arrivingCities) {
        	System.out.println( ride.toString());
        }

	    session.getTransaction().commit();
	    System.out.println("Bidaiak lortuta DataAccess");
	    return arrivingCities;
	}

		public List<String> getDepartCities(){
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		    session.beginTransaction();
		    
		    Query query = session.createQuery(
		        "SELECT DISTINCT r.from FROM Ride r ORDER BY r.from"
		    );

		    List<String> cities = query.list();

		    session.getTransaction().commit();
		    return cities;
		
		}
		
		
	

	public static void main(String[] args) {
		Transaction tx = null;
		Session session = session = HibernateUtil.getSessionFactory().openSession();
		try {
		    
		    tx = session.beginTransaction();
		    
		    Calendar today = Calendar.getInstance();
		    int month = today.get(Calendar.MONTH) + 1; // Hibernate utiliza meses del 1 al 12
		    int year = today.get(Calendar.YEAR);

		    if (month == 12) {
		        month = 1;
		        year += 1;
		    }

		    // Crear conductores
		    Driver driver1 = new Driver("driver1@gmail.com", "Aitor Fernandez");
		    Driver driver2 = new Driver("driver2@gmail.com", "Ane Gaztañaga");
		    Driver driver3 = new Driver("driver3@gmail.com", "Test driver");

		    // Crear viajes (rides)
		    driver1.addRide("Donostia", "Bilbo", UtilDate.newDate(year, month, 15), 4, 7);
		    driver1.addRide("Donostia", "Gazteiz", UtilDate.newDate(year, month, 6), 4, 8);
		    driver1.addRide("Bilbo", "Donostia", UtilDate.newDate(year, month, 25), 4, 4);
		    driver1.addRide("Donostia", "Iruña", UtilDate.newDate(year, month, 7), 4, 8);

		    driver2.addRide("Donostia", "Bilbo", UtilDate.newDate(year, month, 15), 3, 3);
		    driver2.addRide("Bilbo", "Donostia", UtilDate.newDate(year, month, 25), 2, 5);
		    driver2.addRide("Eibar", "Gasteiz", UtilDate.newDate(year, month, 6), 2, 5);

		    driver3.addRide("Bilbo", "Donostia", UtilDate.newDate(year, month, 14), 1, 3);

		    // Persistir los conductores (y sus rides asociadas)
		    session.persist(driver1);
		    session.persist(driver2);
		    session.persist(driver3);
		    System.out.println("Bidaiariak gehituta");
		    //session.getTransaction().commit();
		    System.out.println("Base de datos inicializada");
		    tx.commit();  // Confirmar la transacción
		} catch (Exception e) {
		    if (tx != null) {
		        tx.rollback();  // Revertir la transacción si ocurre un error
		    }
		    e.printStackTrace();
		} finally {
		    if (session != null) {
		        session.close();  // Asegúrate de cerrar la sesión al final
		    }
		}


	}
}
