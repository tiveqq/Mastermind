package sk.tuke.kpi.kp.mastermind.core;

public enum Color {
    NONE, RED, GREEN, BLUE, YELLOW, ORANGE, VIOLET, INDIGO, MINT, PINK;

    public Color getColor(char color) {
        switch(color) {
            case 'R':
                return RED;
            case 'G':
                return GREEN;
            case 'B':
                return BLUE;
            case 'Y':
                return YELLOW;
            case 'O':
                return ORANGE;
            case 'V':
                return VIOLET;
            case 'I':
                return INDIGO;
            case 'M':
                return MINT;
            case 'P':
                return PINK;
            default:
                return null;
        }
    }

    public Object getCharColor(Color color) {
        switch (color) {
            case RED:
                return 'R';
            case GREEN:
                return 'G';
            case BLUE:
                return 'B';
            case YELLOW:
                return 'Y';
            case ORANGE:
                return 'O';
            case VIOLET:
                return 'V';
            case INDIGO:
                return 'I';
            case NONE:
                return ' ';
            case MINT:
                return 'M';
            case PINK:
                return 'P';
            default:
                return null;
        }
    }
}

