/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author thtesche
 */
public class SortTest {

   public static void main(String[] args) {
      MongoClient client = new MongoClient();
      MongoDatabase database = client.getDatabase("course");
      MongoCollection<Document> collection = database.getCollection("sortTest");

      List<Document> docs = collection.find().sort(new Document("value", -1)).skip(2).limit(1).into(new ArrayList<Document>());
      docs.stream().forEach((doc) -> {
         System.out.println("Find list" + doc.toString());
      });

      long count = collection.count();
      System.out.println("Count " + count);

      collection.updateOne(new Document("_id", 1), new Document("$set", new Document("examiner", "Jones")));
      List<Document> docsFinal = collection.find().into(new ArrayList<Document>());
      docsFinal.stream().forEach((doc) -> {
         System.out.println("Find list" + doc.toString());
      });

   }

}
