package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "Person")
@NamedQuery(name = "Person.getAll", query = "SELECT p FROM PersonEntity p")
public class PersonEntity implements Serializable {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "FirstName", length = 200)
    private String firstName;

    @NotNull
    @Column(name = "LastName", length = 200)
    private String lastName;

    public PersonEntity() {
    }

    public PersonEntity(@NotNull final String firstName, @NotNull final String lastName) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
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

    @Override
    public String toString() {
        return String.format("{ id: %s, firstName: %s, lastName: %s }", this.getId(), this.getFirstName(), this.getLastName());
    }
}
