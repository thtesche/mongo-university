import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Created by thomas.tesche on 07.06.16.
 */
public class Application_1 {

   public static void main(String[] args) {
      MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(10).build();
      MongoClient client = new MongoClient(new ServerAddress(), options);


      for(Document database: client.listDatabases()){
         System.out.println(database);
      }

      MongoDatabase database= client.getDatabase("video");

      MongoCollection<Document> collection = database.getCollection("insertTest");

      collection.drop();
      Document document= new Document("name", "Smith").append("salutation", "Sir");
      System.out.println(document.toString());
      collection.insertOne(document);
      document.remove("_id");
      collection.insertOne(document);
      System.out.println(document.toString());

   }
}
