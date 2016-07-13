package database;

import java.io.File;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.terracotta.modules.ehcache.wan.WANUtil;

public class HibernateUtil {
    private static ServiceRegistry service;
    private static Configuration cfg;
    private static StandardServiceRegistryBuilder ssrb;
    private static SessionFactory factory;
 
    static {
    	try {
            cfg=new Configuration().configure();
            ssrb=new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
      
            service=ssrb.build();
            factory=cfg.buildSessionFactory(service);
            
            Session session = factory.openSession();
            String columns[] = new String[] {"content","title","author","type","other","baseUrl"};   
            Query query;
            for(String column : columns){
            	//mysql
            	//String s = "ALTER TABLE `record` CHANGE `"+column+"` `"+column+"` TEXT CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL;";
            	//derby
            	String s = "alter table  record alter column  "+column+" set data type  VARCHAR(30000)";

            	//System.out.println(s);
            	query = session.createSQLQuery(s);
            	query.executeUpdate();
            }
            session.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	//File file = new File("hibernate.cfg.xml");

    }

    public static Session getSession(){
        return  factory.openSession();
    }
}
