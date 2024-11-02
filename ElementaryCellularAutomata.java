
import java.util.Random;

public class ElementaryCellularAutomata {
    private boolean[] cells;
    private double[] rules;
    private Random random;
    private int size;
    private int iter;
    private String offColor;
    private String onColor;

    public ElementaryCellularAutomata(int size, String offColor, String onColor, long seed, double init, int iterations, double[] rules) {
        this.size = size;
        this.offColor = offColor;
        this.onColor = onColor;
        this.iter = iterations;
        this.rules = rules;
        this.random = new Random(seed);

        this.cells = new boolean[size];
        for (int i = 0; i < size; i++) {
            // Randomly initialize cells based on init probability
            cells[i] = random.nextDouble() < init;
        }
    }

    // Method to run automaton for a given number of iterations
    public void run() {
        for (int i = 0; i < iter; i++) {
            printCells();
            next();
        }
    }

    // Method to print the cells
    private void printCells() {
        for (boolean cell : cells) {
            AnsiColor color = cell ? new AnsiColor(onColor) : new AnsiColor(offColor);
            color.printBlock();
        }
        System.out.println();
    }

    // Method to find the next line
    private void next() {
        boolean[] newCells = new boolean[size];
        double[] realRules = new double[8];
        if(rules.length==1){ // Turns the integer rule into binary
            int remainder = (int)rules[0];
            for(int i = 0; i<8; i++){
                int counter = remainder%2;
                realRules[i] = counter;
                remainder /= 2;
            }
        }
        else{
            realRules = rules;
        }
        for (int i = 0; i < size; i++) {
            // Circular array
            boolean left = cells[(i - 1 + size) % size];
            boolean center = cells[i];
            boolean right = cells[(i + 1) % size];

            // Calculate the configuration of the neighbors (convert to integer)
            int config = (left ? 4 : 0) + (center ? 2 : 0) + (right ? 1 : 0);

            // Apply the probabilistic rule for the given configuration
            double ruleProbability = realRules[config];
            if (ruleProbability <= 0) {
                newCells[i] = false;
            } else if (ruleProbability >= 1) {
                newCells[i] = true;
            } else {
                newCells[i] = random.nextDouble() < ruleProbability;
            }
        }
        cells = newCells;
    }

    public static void main(String[] args) {
        double[] rules;
        String offColor;
        String onColor;
        long randomSeed;
        int size;
        float init;
        int iter;
        if(args[2].equals("-off-color")){ // Checks if it is an integer rule or probabilities
            rules = new double[1];
            rules[0] = Double.parseDouble(args[1]);
            offColor = args[3];
            onColor = args[5];
            randomSeed = Long.parseLong(args[7]);
            size = Integer.parseInt(args[9]);
            init = Float.parseFloat(args[11]);
            iter = Integer.parseInt(args[13]);
            ElementaryCellularAutomata automaton = new ElementaryCellularAutomata(size, offColor, onColor, randomSeed, init, iter, rules);
            automaton.run();
        }
        else{
            rules = new double[8];
            for(int i = 0; i < 8; i++){
                rules[i] = Double.parseDouble(args[i+1]);
            }
            offColor = args[10];
            onColor = args[12];
            randomSeed = Long.parseLong(args[14]);
            size = Integer.parseInt(args[16]);
            init = Float.parseFloat(args[18]);
            iter = Integer.parseInt(args[20]);
            ElementaryCellularAutomata automaton = new ElementaryCellularAutomata(size, offColor, onColor, randomSeed, init, iter, rules);
            automaton.run();
        }
    }
}

class AnsiColor {
    // Store the 3 bit color (values 0 through 7)
    final private int color;

    static public String[] names = {"black", "red", "green", "yellow", "blue", "magenta", "cyan", "white"};

    public AnsiColor(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Color input cannot be null or empty");
        }

        int color = findIndex(names, input.toLowerCase());

        if (color == -1) {
            throw new IllegalArgumentException("Color must be one of: " + String.join(", ", names));
        }

        this.color = color;
    }

    // Return -1 if the target is not found
    public static int findIndex(String[] array, String target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(target)) return i;
        }
        return -1;
    }

    public int getColorValue() { return color; }

    @Override
    public String toString() { return names[color]; }

    private String getBackgroundColor() {
        return "\u001B[4" + color + "m";
    }

    private static final String ANSI_RESET = "\u001B[0m";

    public void printBlock() {
        System.out.print(getBackgroundColor() + "  " + ANSI_RESET);
    }
}