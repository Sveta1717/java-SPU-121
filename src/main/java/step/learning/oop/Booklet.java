package step.learning.oop;

public class Booklet extends Literature implements Copyable{
    private String printery;

    public String getPrintery() {
        return printery;
    }

    public void setPrintery(String printery) {
        this.printery = printery;
    }

    @Override
    public String getCard() {
        return String.format(
                "Booklet: '%s' Printery: %s ",
                super.getTitle(),
                this.getPrintery()

        );
    }

    @Override
    public String getCopy() {
        return "Copyable";
    }
}
