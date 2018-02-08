/**
 * 
 */
package control;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

import boundary.FinestraUtente;

import java.util.*;
import entity.*;
/**
 * @author cresc
 *
 */
class ClienteDAOTest {
    static AmazonDynamoDB dynamoDB;
    private static DynamoDB connessione;
    private ClienteDAO clienteDAO;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
        try {
            credentialsProvider.getCredentials();
        } catch (Exception e) {
        }
        dynamoDB = AmazonDynamoDBClientBuilder.standard()
            .withCredentials(credentialsProvider)
            .withRegion("eu-central-1")
            .build();
		connessione = new DynamoDB(dynamoDB);
	    clienteDAO=new ClienteDAO(connessione);
		clienteDAO.inserisciModifica("Michele", "Caparezza", "michelecaparezza@gmail.com", "MCHSVM73R09F284X", "9 ottobre 1973");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		clienteDAO.elimina("MCHSVM73R09F284X");
	}

	@Test
	void cercaTest() {
		List<Cliente> risultati = clienteDAO.cerca("", "", "", "MCHSVM73R09F284X", ""); 
		for(Cliente curr:risultati) assertEquals("Errore, i parametri non coincidono","Michele", curr.getNome());
		
	}

}
