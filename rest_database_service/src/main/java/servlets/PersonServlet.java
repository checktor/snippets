package servlets;

import connectors.PersonConnector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import resources.CommentResource;
import resources.PersonResource;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/person")
public class PersonServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // Prepare HTTP response and its JSON body.
        res.setContentType("application/json");
        JSONObject resultJson = new JSONObject();
        // Read HTTP request body.
        String requestBodyRaw = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        try {
            // Convert request body to JSON object.
            JSONObject requestBody = new JSONObject(requestBodyRaw);
            // Extract 'Person' data from JSON object and create corresponding entity.
            String firstName = String.valueOf(requestBody.get("FirstName"));
            String lastName = String.valueOf(requestBody.get("LastName"));
            PersonResource personResource = new PersonResource(firstName, lastName);
            // Extract 'id' value from JSON object and insert it to entity (if possible).
            String idKey = "Id";
            if (requestBody.has(idKey)) {
                String stringId = String.valueOf(requestBody.get(idKey));
                Long longId = Long.parseLong(stringId);
                personResource.setId(longId);
            }
            // Extract 'Comments' data from JSON object and create corresponding list of entities.
            String commentsKey = "Comments";
            if (requestBody.has(commentsKey)) {
                String commentsBodyRaw = String.valueOf(requestBody.get(commentsKey));
                JSONArray commentsBody = new JSONArray(commentsBodyRaw);
                for (int i = 0; i < commentsBody.length(); i++) {
                    JSONObject comment = commentsBody.getJSONObject(i);
                    String text = String.valueOf(comment.get("Text"));
                    CommentResource commentResource = new CommentResource(text);
                    String commentIdKey = "Id";
                    if (comment.has(commentIdKey)) {
                        String stringId = String.valueOf(comment.get(commentIdKey));
                        Long longId = Long.parseLong(stringId);
                        commentResource.setId(longId);
                    }
                    personResource.addComment(commentResource);
                }
            }
            // Run database query. In case the provided entity instance is already
            // present in database, it will be updated. Otherwise, it will be created.
            PersonResource createdPersonResource = PersonConnector.createOrUpdate(personResource);
            // Update HTTP response body.
            if (createdPersonResource != null) {
                resultJson.put("success", true);
                resultJson.put("payload", new JSONObject(createdPersonResource.toString()));
            } else {
                resultJson.put("success", false);
                resultJson.put("payload", "Could not create PersonEntity.");
            }
        } catch (JSONException exception) {
            resultJson.put("success", false);
            resultJson.put("payload", exception.getMessage());
        } finally {
            // Send HTTP response.
            res.getWriter().write(resultJson.toString());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // Prepare HTTP response and its JSON body.
        res.setContentType("application/json");
        JSONObject resultJson = new JSONObject();
        // Get URL parameter 'id'.
        String stringId = req.getParameter("id");
        if (stringId != null) {
            // Get database entry with corresponding 'id'.
            Long longId = Long.parseLong(stringId);
            PersonResource personResource = PersonConnector.get(longId);
            // Update HTTP response body.
            if (personResource != null) {
                resultJson.put("success", true);
                resultJson.put("payload", new JSONObject(personResource.toString()));
            } else {
                resultJson.put("success", false);
                resultJson.put("payload", String.format("Could not find PersonEntity with ID %d.", longId));
            }
        } else {
            // Get all database entries from table.
            ArrayList<PersonResource> personResources = PersonConnector.getAll();
            resultJson.put("success", true);
            resultJson.put("payload", new JSONArray(personResources));
        }
        // Send HTTP response.
        res.getWriter().write(resultJson.toString());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {
        doPost(req, res);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // Prepare HTTP response and its JSON body.
        res.setContentType("application/json");
        JSONObject resultJson = new JSONObject();
        // Get URL parameter 'id'.
        String stringId = req.getParameter("id");
        if (stringId != null) {
            // Delete database entry with corresponding ID.
            Long longId = Long.parseLong(stringId);
            boolean result = PersonConnector.delete(longId);
            // Update HTTP response body.
            resultJson.put("success", result);
        } else {
            resultJson.put("success", false);
        }
        // Send HTTP response.
        res.getWriter().write(resultJson.toString());
    }

}
