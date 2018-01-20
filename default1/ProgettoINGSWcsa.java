package default1;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JTextField;
import javax.swing.UIManager;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
 
public class ProgettoINGSWcsa {
    static AmazonDynamoDB dynamoDB;
    static DynamoDB connessione;
    
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		
		
		 try 
		    { 
			  UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		    } 
		    catch(Exception e){ 
		    }
		
		init();
		connessione = new DynamoDB(dynamoDB);
		FinestraUtente window = new FinestraUtente();
		ClienteDAO ClienteDAOcurr = new ClienteDAO();
		ClienteController ClienteControllercurr = new ClienteController(window, ClienteDAOcurr);
		window.frmProgettoingswcsa.setVisible(true);
		
	}

    public static void init() throws Exception {
        
        ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
        try {
            credentialsProvider.getCredentials();
        } catch (Exception e) {
        }
        dynamoDB = AmazonDynamoDBClientBuilder.standard()
            .withCredentials(credentialsProvider)
            .withRegion("eu-central-1")
            .build();
       
    }
}
