package course;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import org.bson.Document;

import java.util.List;

public class BlogPostDAO {

   MongoCollection<Document> postsCollection;

   public BlogPostDAO(final MongoDatabase blogDatabase) {
      postsCollection = blogDatabase.getCollection("posts");
   }

   // Return a single post corresponding to a permalink
   public Document findByPermalink(String permalink) {

      Document post = getDocForPermalink(permalink);

      return post;
   }

   private Document getDocForPermalink(String permalink) {
      // XXX HW 3.2,  Work Here
      Document post = postsCollection.find(new Document("permalink", permalink)).first();
      return post;
   }

   // Return a list of posts in descending order. Limit determines
   // how many posts are returned.
   public List<Document> findByDateDescending(int limit) {

      // XXX HW 3.2,  Work Here
      // Return a list of DBObjects, each one a post from the posts collection
      List<Document> posts = postsCollection.find().sort(Sorts.descending("date")).limit(limit).into(new ArrayList<Document>());

      return posts;
   }

   public String addPost(String title, String body, List tags, String username) {

      System.out.println("inserting blog entry " + title + " " + body);

      String permalink = title.replaceAll("\\s", "_"); // whitespace becomes _
      permalink = permalink.replaceAll("\\W", ""); // get rid of non alphanumeric
      permalink = permalink.toLowerCase();

      // XXX HW 3.2, Work Here
      // Remember that a valid post has the following keys:
      // author, body, permalink, tags, comments, date
      //
      // A few hints:
      // - Don't forget to create an empty list of comments
      // - for the value of the date key, today's datetime is fine.
      // - tags are already in list form that implements suitable interface.
      // - we created the permalink for you above.
      // Build the post object and insert it
      Document post = new Document();
      post.append("title", title);
      post.append("body", body);
      post.append("author", username);
      post.append("permalink", permalink);
      post.append("date", new Date());
      post.append("comments", Collections.emptyList());

      postsCollection.insertOne(post);

      return permalink;
   }

   // White space to protect the innocent
   // Append a comment to a blog post
   public void addPostComment(final String name, final String email, final String body,
      final String permalink) {

      Document comment = createComment(name, email, body);
      // XXX HW 3.3, Work Here
      // Hints:
      // - email is optional and may come in NULL. Check for that.
      // - best solution uses an update command to the database and a suitable
      //   operator to append the comment on to any existing list of comments
      Document post = postsCollection.findOneAndUpdate(Filters.eq("permalink", permalink),
         Updates.push("comments", comment));

      System.out.println(post);

   }

   private Document createComment(final String name, final String email, final String body) {

      Document comment = new Document("author", name);
      if (email != null && !email.isEmpty()) {
         comment.append("email", email);
      }
      comment.append("body", body);
      return comment;
   }

}
