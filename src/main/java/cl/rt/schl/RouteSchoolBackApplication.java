package cl.rt.schl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import cl.rt.schl.repository.TransportistaRepository;

@SpringBootApplication
public class RouteSchoolBackApplication {

	   private static final Logger log = LoggerFactory.getLogger(RouteSchoolBackApplication.class);

	    @Autowired
	    private static TransportistaRepository repository;

	
	public static void main(String[] args) {
		FileInputStream serviceAccount = null;
		try {
			serviceAccount = new FileInputStream("routeschoollogin-firebase-adminsdk-rleil-50b7c9c6f5.json");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

				FirebaseOptions options = null;
				try {
					options = new FirebaseOptions.Builder()
					  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
					  .setDatabaseUrl("https://routeschoollogin.firebaseio.com")
					  .build();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				FirebaseApp.initializeApp(options);
		SpringApplication.run(RouteSchoolBackApplication.class, args);

	}

	 public void run(String[] args) {

	        log.info("StartApplication...");

	     
	        System.out.println("\nfindAll()");
	        repository.findAll().forEach(x -> System.out.println(x));

	 }
	
}
