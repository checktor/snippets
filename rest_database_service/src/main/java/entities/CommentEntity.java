package entities;

import resources.CommentResource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "Comment")
public class CommentEntity implements Serializable {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "Text", length = 5000)
    private String text;

    public CommentEntity() {
    }

    public CommentEntity(final CommentResource commentResource) {
        this.setId(commentResource.getId());
        this.setText(commentResource.getText());
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

}
