public class Square {
    private boolean hasBomb;
    private boolean hasFlag;
    private boolean visible;
    private int squareValue = 0;

    public Square(boolean hasBomb, boolean hasFlag, boolean visible) {
        this.hasBomb = hasBomb;
        this.hasFlag = hasFlag;
        this.visible = visible;
    }

    public void setValue(int value) {
        this.squareValue = value;
    }

    public int getValue() {
        return this.squareValue;
    }

    public boolean isCover() {
        return !visible;
    }

    public boolean isBomb() {
        return hasBomb;
    }

    public boolean checkFlag() {
        return hasFlag;
    }

    public void setCover(boolean visibility) {
        visible = visibility;
    }

    public void setBomb() {
        hasBomb = true;
    }

    public void setFlag() {
        hasFlag = !hasFlag;
    }
}
