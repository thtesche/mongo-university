package de.student.homework;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.json.JSONObject;

/**
 * @author thtesche
 */
public class Application {

   public static void main(String[] args) {

      MongoClient client = new MongoClient();

      MongoDatabase database = client.getDatabase("school");
      MongoCollection<Document> students = database.getCollection("students");

      List<Document> docs = students.find().into(new ArrayList<Document>());
      docs.stream().forEach((doc) -> {

         ArrayList<Document> scores = (ArrayList) doc.get("scores");
         Document scoreToBeRemoved = scores.stream()
                 .filter(homeworkScore -> homeworkScore.get("type", String.class).equals("homework"))
                 .reduce((p1, p2) -> (Double) p1.get("score") >= (Double) p2.get("score") ? p2 : p1)
                 .get();
         System.out.println("Score to be removed");
         printJson(scoreToBeRemoved);

         Document update = new Document("$pull", new Document("scores", scoreToBeRemoved));

         students.updateOne(doc, update);

         printJson(doc);

      });

   }

   private static void printJson(Document document) {
      JSONObject json = new JSONObject(document.toJson());
      System.out.println(json.toString(2));
   }
}
