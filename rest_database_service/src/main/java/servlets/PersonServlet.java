package servlets;

import connectors.PersonConnector;
import entities.PersonEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
            JSONObject requestBodyJson = new JSONObject(requestBodyRaw);
            // Extract data from JSON object and create corresponding entity.
            String firstName = String.valueOf(requestBodyJson.get("firstName"));
            String lastName = String.valueOf(requestBodyJson.get("lastName"));
            PersonEntity personEntity = new PersonEntity(firstName, lastName);
            // Extract 'id' value from JSON object and insert it to entity (if possible).
            if (requestBodyJson.has("id")) {
                String stringId = String.valueOf(requestBodyJson.get("id"));
                long longId = Long.parseLong(stringId);
                personEntity.setId(longId);
            }
            // Run database query. In case the provided entity instance is already
            // present in database, it will be updated. Otherwise, it will be created.
            PersonEntity createdPersonEntity = PersonConnector.createOrUpdate(personEntity);
            // Update HTTP response body.
            if (createdPersonEntity != null) {
                resultJson.put("success", true);
                resultJson.put("payload", new JSONObject(createdPersonEntity.toString()));
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
            long longId = Long.parseLong(stringId);
            PersonEntity personEntity = PersonConnector.get(longId);
            // Update HTTP response body.
            if (personEntity != null) {
                resultJson.put("success", true);
                resultJson.put("payload", new JSONObject(personEntity.toString()));
            } else {
                resultJson.put("success", false);
                resultJson.put("payload", String.format("Could not find PersonEntity with ID %d.", longId));
            }
        } else {
            // Get all database entries from table.
            ArrayList<PersonEntity> personEntities = PersonConnector.getAll();
            resultJson.put("success", true);
            resultJson.put("payload", new JSONArray(personEntities));
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
            long longId = Long.parseLong(stringId);
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
