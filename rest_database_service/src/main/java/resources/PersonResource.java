package resources;

import entities.CommentEntity;
import entities.PersonEntity;

import java.util.ArrayList;
import java.util.List;

public class PersonResource {

    private Long id;
    private String firstName;
    private String lastName;
    private List<CommentResource> comments;

    public PersonResource(final String firstName, final String lastName) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setComments(new ArrayList<>());
    }

    public PersonResource(final PersonEntity personEntity) {
        this.setId(personEntity.getId());
        this.setFirstName(personEntity.getFirstName());
        this.setLastName(personEntity.getLastName());
        this.setComments(new ArrayList<>());
        for (CommentEntity commentEntity : personEntity.getComments()) {
            this.getComments().add(new CommentResource(commentEntity));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public List<CommentResource> getComments() {
        return comments;
    }

    public void setComments(final List<CommentResource> comments) {
        this.comments = comments;
    }

    public void addComment(final CommentResource commentResource) {
        this.getComments().add(commentResource);
    }

    @Override
    public String toString() {
        return String.format("{ Id: %s, FirstName: %s, LastName: %s, Comments: %s }", this.getId(), this.getFirstName(), this.getLastName(), this.getComments().toString());
    }

}
