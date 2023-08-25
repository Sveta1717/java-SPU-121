package step.learning.oop;

public class Poster extends Literature implements Copyable{

    private boolean color;

    public boolean isColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    @Override
    public String getCard() {
        return String.format(
                "Poster: '%s' Color: %s ",
                super.getTitle(),
                //this.color
                color ? "Yes" : "No"
        );
    }

    @Override
    public String getCopy() {
        return "Copyable";
    }
}
