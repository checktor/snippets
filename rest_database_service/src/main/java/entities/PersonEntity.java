package entities;

import resources.CommentResource;
import resources.PersonResource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Person")
@NamedQuery(name = "Person.getAll", query = "SELECT p FROM PersonEntity p")
public class PersonEntity implements Serializable {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "FirstName", length = 200)
    private String firstName;

    @NotNull
    @Column(name = "LastName", length = 200)
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "PersonId")
    private List<CommentEntity> comments;

    public PersonEntity() {
    }

    public PersonEntity(final PersonResource personResource) {
        this.setId(personResource.getId());
        this.setFirstName(personResource.getFirstName());
        this.setLastName(personResource.getLastName());
        this.setComments(new ArrayList<>());
        for (CommentResource commentResource : personResource.getComments()) {
            this.getComments().add(new CommentEntity(commentResource));
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

    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(final List<CommentEntity> comments) {
        this.comments = comments;
    }

}
