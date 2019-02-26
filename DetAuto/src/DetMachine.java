
import java.io.IOException;
import java.io.InputStream;
import java.util.*;



public class DetMachine {
    private InputStream is;
    private Map<Integer, Map<Character, Integer>> transitions = new HashMap<>();
    private Set<Integer> finishStates = new HashSet<>();

    private DetMachine(String filename) throws DetMachineInputException {
        this.is = System.in;
        try  {
            var is = getClass().getClassLoader().getResourceAsStream(filename);
            Scanner scanner = new Scanner(is);
            String statesStrings = scanner.nextLine();
            for (String substr : statesStrings.split(" ")) {
                int finishState = Integer.valueOf(substr);
                finishStates.add(finishState);
            }
            while (scanner.hasNextInt()) {
                int beginState = scanner.nextInt();
                String transition = scanner.next();
                if (transition.length() > 1)
                    throw new DetMachineInputException("Found unknown transition type.");
                int endState = scanner.nextInt();
                addState(beginState, transition.charAt(0), endState);
            }
        } catch (Exception e) {
            throw new DetMachineInputException("Can't parse states file.");
        }
    }


    private DetMachine(String statesFilename, String dataFilename) throws DetMachineInputException {
        this(statesFilename);
        this.is = getClass().getClassLoader().getResourceAsStream(dataFilename);
        if (is == null)
            throw new DetMachineInputException("Can't find input file!");
    }

    private void addState(int beginState, char symbol, int endState) {
        if (!transitions.containsKey(beginState)) {
            transitions.put(beginState, new HashMap<>());
        }
        transitions.get(beginState).put(symbol, endState);
    }

    private void recognize() throws DetMachineInputException {
        int symbol;
        int currentState = 1;
        try {
            while (((symbol = is.read()) != -1) && (symbol != '\n')) {
                currentState = transitions.get(currentState).get((char) symbol);
            }
        }
        catch (IOException exc) {
            throw new DetMachineInputException("Error during input!");
        }
        catch (NullPointerException exc) {
            throw new DetMachineInputException("Unknown transition or state found!");
        }
        if (finishStates.contains(currentState)) {
            System.out.println("String was recognized.");
        }
        else {
            System.out.println("String wasn't recognized.");
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("No states file given! Terminating");
        }
        try {
            DetMachine detMachine;
            if(args.length == 2) {
                detMachine = new DetMachine(args[0], args[1]);
            }
            else{
                detMachine = new DetMachine(args[0]);
            }
            detMachine.recognize();
        }
        catch (DetMachineInputException e) {
            System.out.println(e.getMessage());
        }
    }
}

class DetMachineInputException extends IOException {
    DetMachineInputException(String exception) {
        super(exception);
    }
}