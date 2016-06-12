package de.student.homework;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.json.JSONObject;

/**
 *
 * @author thtesche
 */
public class Application {

   public static void main(String[] args) {
      MongoClient client = new MongoClient();
      MongoDatabase database = client.getDatabase("school");
      MongoCollection<Document> collection = database.getCollection("students");

      List<Document> docs = collection.find().into(new ArrayList<Document>());
      docs.stream().forEach((doc) -> {
         printJson(doc);
      });
   }

   private static void printJson(Document document) {
      JSONObject json = new JSONObject(document.toJson());
      System.out.println(json.toString(2));
   }
}
