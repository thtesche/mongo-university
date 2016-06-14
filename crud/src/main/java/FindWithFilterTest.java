/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Projections.exclude;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author thtesche
 */
public class FindWithFilterTest {

   private static final Logger LOGGER = Logger.getLogger(FindWithFilterTest.class.getName());

   public static void main(String[] args) {

      MongoClient client = new MongoClient();
      MongoDatabase database = client.getDatabase("course");
      MongoCollection<Document> collection = database.getCollection("findWithFilterTest");

      collection.drop();

      for (int i = 0; i < 10; i++) {
         collection.insertOne(new Document()
                 .append("x", new Random().nextInt(2))
                 .append("y", new Random().nextInt(100))
                 .append("i", i)
         );
      }

//      Bson filter = new Document("x", 0)
//              .append("y", new Document("$gt", 10).append("$lt", 80));
//      Bson projection = new Document("i", 1)
//              .append("y", 1)
//              .append("_id", 0);
      Bson filter = and(eq("x", 0), gt("y", 10), lt("y", 80));
      Bson projection = fields(include("i", "y"), exclude("_id"));

      List<Document> docs = collection
              .find(filter)
              .projection(projection)
              .into(new ArrayList<Document>());
      docs.stream().forEach((Document doc) -> {
         LOGGER.log(Level.INFO, "Find list {0}", doc.toString());
      });

      long count = collection.count(filter);
      LOGGER.log(Level.INFO, "Count {0}", count);
   }
}
