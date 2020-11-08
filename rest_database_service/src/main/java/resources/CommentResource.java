package resources;

import entities.CommentEntity;

public class CommentResource {

    private Long id;
    private String text;


    public CommentResource(final String text) {
        this.setText(text);
    }

    public CommentResource(final CommentEntity commentEntity) {
        this.setId(commentEntity.getId());
        this.setText(commentEntity.getText());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return String.format("{ Id: %s, Text: %s }", this.getId(), this.getText());
    }

}
