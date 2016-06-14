
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONObject;

/**
 * Created by thomas.tesche on 07.06.16.
 */
public class Application_1 {

   public static void main(String[] args) {
      MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(10).build();
      MongoClient client = new MongoClient(new ServerAddress(), options);

      for (Document database : client.listDatabases()) {
         System.out.println(database);
      }

      MongoDatabase database = client.getDatabase("video");

      MongoCollection<Document> collection = database.getCollection("insertTest");

      collection.drop();
      Document document = new Document("name", "Smith").append("salutation", "Sir");
      printJson(document);
      collection.insertOne(document);
      document.remove("_id");
      collection.insertOne(document);
      printJson(document);

   }

   private static void printJson(Document document) {
      JSONObject json = new JSONObject(document.toJson());
      System.out.println(json.toString(2));
   }
}
