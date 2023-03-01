package sk.tuke.kpi.kp.mastermind.core;

public enum KeyColor {
    NONE, WHITE, BLACK;

    public Object getCharKeyColor(KeyColor color) {
        switch (color) {
            case NONE:
                return ' ';
            case WHITE:
                return 'W';
            case BLACK:
                return 'B';
            default:
                return null;
        }
    }
}
