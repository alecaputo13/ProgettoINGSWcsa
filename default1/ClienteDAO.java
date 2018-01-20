package default1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.document.DeleteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

public class ClienteDAO {
	
	public static List<Cliente> cerca(String nome, String cognome, String email, String CodiceFiscale, String Data) {
	       
		 
		   String tableName = "Cliente";
		   Iterator<Item> iterator = null;
		   List<Cliente> risultati=new ArrayList<Cliente>();  
		   Table table = ((DynamoDB) ProgettoINGSWcsa.connessione).getTable(tableName);
		   Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
		   //se ogni campo � vuoto deve svolgere una scan di tutto
		   if(CodiceFiscale.equals("") && nome.equals("") && cognome.equals("") && email.equals("") && Data.equals("")) {
			   ItemCollection<ScanOutcome> items = table.scan (
				        null,                                  
				        null,
				        null,                                          
				        null);
				         
				       iterator = items.iterator();
				   
				       Item iteratorcurr;
				       String c1;
				       while (iterator.hasNext()) {
				        iteratorcurr = iterator.next();
				          System.out.println(iteratorcurr);
				      }
		   }else{ //un campo non � vuoto e deve costruire la query
			   String ricerca = "";
			   if(!(nome.equals(""))) {
			   ricerca=ricerca + "AND Nome = :nome ";
			   expressionAttributeValues.put(":nome", nome);
			   }
			   if(!(CodiceFiscale.equals(""))) {
				   expressionAttributeValues.put(":cf", CodiceFiscale);
				   ricerca=ricerca + "AND CodiceFiscale = :cf ";
				   }
			   if(!(Data.equals(""))) {
				   ricerca=ricerca + "AND Data = :data ";
				   expressionAttributeValues.put(":data", Data);
				   }
			   if(!(cognome.equals(""))) {
				   ricerca=ricerca + "AND Cognome = :cognome ";
				   expressionAttributeValues.put(":cognome", cognome);
				   }
			   if(!(email.equals(""))) {
				   ricerca=ricerca + "AND Email = :email ";
				   expressionAttributeValues.put(":email", email);
				   }
			   //rimuove i primi tre caratteri della stringa ricerca rendendola idonea alla scan con attributi
			   System.out.println("la query creata �:" + ricerca);
			   ricerca = ricerca.substring(3);
			   System.out.println("la query che voglio stampare �:" + ricerca);
			   ItemCollection<ScanOutcome> items = table.scan (
				        ricerca,                                  
				        "CodiceFiscale, Nome, Cognome, Email, DataNascita",
				        null,                                          
				        expressionAttributeValues);
				         
				       iterator = items.iterator();
				   
				      
		   		}
		   //costruisce la lista con i risultati da restituire
		   Item iteratorcurr;
		   Cliente curr;
	       while (iterator.hasNext()) {
	        iteratorcurr = iterator.next();
	        curr=new Cliente((String) iteratorcurr.get("Nome"),(String) iteratorcurr.get("Cognome"),(String)  iteratorcurr.get("Email"),(String) iteratorcurr.get("CodiceFiscale"),(String) iteratorcurr.get("DataNascita"));
	        risultati.add(curr);
	       }
	   return risultati;
	}
	
	 public static void inserisciModifica(String nome, String cognome, String email, String CodiceFiscale, String Data) {
		    String tableName = "Cliente";
		    Table table = ((DynamoDB) ProgettoINGSWcsa.connessione).getTable(tableName);
		    Item item = new Item()
		    	    .withPrimaryKey("CodiceFiscale", CodiceFiscale)
		    	    .withString("Nome", nome)
		    	    .withString("Cognome", cognome)
		    	    .withString("Email", email)
		    	    .withString("DataNascita", Data);
		    
		    PutItemOutcome outcome = table.putItem(item, null, null, null);
		    
		    
		    }
	 
	 public static void elimina(String CodiceFiscale) {
	        String tableName = "Cliente";
	        Table table = ((DynamoDB) ProgettoINGSWcsa.connessione).getTable(tableName);
	        DeleteItemOutcome outcome = table.deleteItem("CodiceFiscale", CodiceFiscale);
	        }
}
