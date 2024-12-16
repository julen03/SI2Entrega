package nagusia;

import domainua.LoginGertaera;
import org.hibernate.Session;

import com.mysql.cj.Query;

import domainua.Erabiltzailea;
import domainua.HibernateUtil;

import java.util.*;
public class GertaerakSortu {
	public GertaerakSortu(){
	}
	private void createAndStoreLoginGertaera(Long id, String deskribapena, Date data) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		domainua.LoginGertaera e = new LoginGertaera();
		e.setId(id);
		e.setDeskribapena(deskribapena);
		e.setData(data);
		session.persist(e);
		session.getTransaction().commit();
	}

	private List gertaerakZerrendatu() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List result = session.createQuery("from LoginGertaera").list();
		session.getTransaction().commit();
		return result;
	}
	public static void main(String[] args) {
	 GertaerakSortu e = new GertaerakSortu();
	 System.out.println("Gertaeren sorkuntza:");
	 e.createAndStoreErabiltzailea("Ane", "125", "ikaslea");
	 e.createAndStoreLoginGertaera("Ane",true, new Date());
	 e.createAndStoreLoginGertaera("Ane",false, new Date());
	System.out.println("Gertaeren zerrenda:");
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	session.beginTransaction();
	List result = session.createQuery("from LoginGertaera").list();
	for (int i = 0; i < result.size(); i++) {
	 LoginGertaera ev = (LoginGertaera) result.get(i);
	 System.out.println("Id: " + ev.getId() + " Deskribapena: " +
	 ev.getDeskribapena() + " Data: " + ev.getData()+ " Login: " + ev.isLogin());
	}
	session.getTransaction().commit();
	}
	private void createAndStoreLoginGertaera(String deskribapena, Date data) {
		 Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		 session.beginTransaction();
		 LoginGertaera e = new LoginGertaera();
		 e.setDeskribapena(deskribapena);
		 e.setData(data);
		 session.persist(e);
		 session.getTransaction().commit();
	}
	private void createAndStoreErabiltzailea(String izena,String pasahitza,String mota) {
		 Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		 session.beginTransaction();
		 Erabiltzailea u = new Erabiltzailea();
		 u.setIzena(izena);
		 u.setPasahitza(pasahitza);
		 u.setMota(mota);
		 session.persist(u);
		 session.getTransaction().commit();
	}
	private void createAndStoreLoginGertaera(String erabil,boolean login,Date data) {
		 Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		 session.beginTransaction();
		 org.hibernate.Query q = session.createQuery("from Erabiltzailea where izena= :erabiltzailea");
		 q.setParameter("erabiltzailea", erabil);
		 List result = q.list();


		 LoginGertaera e = new LoginGertaera();
		 try {
		 e.setErabiltzailea((Erabiltzailea)result.get(0));
		 }
		 catch (Exception ex)
		 {System.out.println("Errorea: erabiltzailea ez da existitzen"+ex.toString());}
		 e.setLogin(login);
		 e.setData(data);
		 session.persist(e);
		 session.getTransaction().commit();
		}
	public void printObjMemDB(String azalpena, Erabiltzailea e) {
		System.out.print("\tMem:<"+e+"> DB:<"+GertaerakBerreskuratuJDBC.getErabiltzaileaJDBC(e)+"> =>");
		System.out.println(azalpena); }
}