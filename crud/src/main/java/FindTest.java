

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author thtesche
 */
public class FindTest {

   public static void main(String[] args) {
      MongoClient client = new MongoClient();
      MongoDatabase database = client.getDatabase("course");
      MongoCollection<Document> collection = database.getCollection("findTest");

      collection.drop();

      for (int i = 0; i < 10; i++) {
         collection.insertOne(new Document("x", i));
      }

      Document first = collection.find().first();
      System.out.println("Find first" + first.toString());

      List<Document> docs = collection.find().into(new ArrayList<Document>());
      docs.stream().forEach((doc) -> {
         System.out.println("Find list" + doc.toString());
      });

      try (MongoCursor<Document> cursor = collection.find().iterator()) {
         while (cursor.hasNext()) {
            Document document = cursor.next();
            System.out.println("Find with cursor " + document.toString());
         }
         cursor.close();
      }

      long count = collection.count();
      System.out.println("Count " + count);

   }

}
