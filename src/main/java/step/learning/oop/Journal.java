package step.learning.oop;

public class Journal extends Literature implements Periodic, Copyable {

    private  int number;

    public void setNumber(int number) {
        this.number = number;
    }

    public String getNumber() {
        return Integer.toString(number);
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String getCard() {
        return String.format(
                "Journal : '%s' №%s",
                super.getTitle(),
                getNumber()
        );
    }

    @Override
    public String getPeriod() {
        return "Monthly";
    }

    @Override
    public String getCopy() {
        return "Copyable";
    }
}
