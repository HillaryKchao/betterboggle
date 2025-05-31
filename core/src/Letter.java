public class Letter {
    private final String letter;

    public Letter(String letter) {
        this.letter = letter;
    }

    public String getLetter() {
        return this.letter;
    }

    @Override
    public String toString() {
        return this.letter;
    }
}
