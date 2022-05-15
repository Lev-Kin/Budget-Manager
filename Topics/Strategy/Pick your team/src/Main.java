import java.util.Arrays;
import java.util.Scanner;

class SelectionContext {

    private PersonSelectionAlgorithm algorithm;

    public void setAlgorithm(PersonSelectionAlgorithm algorithm) {
        this.algorithm = algorithm;
        // write your code here
    }

    public Person[] selectPersons(Person[] persons) {
        return algorithm.select(persons);
        // write your code here
    }
}

interface PersonSelectionAlgorithm {

    Person[] select(Person[] persons);
}

class TakePersonsWithStepAlgorithm implements PersonSelectionAlgorithm {

    private int step = 0;

    public TakePersonsWithStepAlgorithm(int step) {
       this.step = step;
    }

    @Override
    public Person[] select(Person[] persons) {

        Person[] result = new Person[persons.length == 1 ? 1 : (persons.length - 1) / step + 1];
        for (int i = 0; i < persons.length; i += step) {
            for (int j = 0; j < result.length; j++) {
                if (result[j] == null) {
                    result[j] = persons[i];
                    break;
                }
            }
        }
        return result;
        //return new Person[persons.length == 1 ? 1 : (persons.length - 1) / step + 1];

        // write your code here

    }
}


class TakeLastPersonsAlgorithm implements PersonSelectionAlgorithm {

    private int count = 0;

    public TakeLastPersonsAlgorithm(int count) {
        this.count = count;
        // write your code here
    }

    @Override
    public Person[] select(Person[] persons) {
        return Arrays.copyOfRange(persons, persons.length - count, persons.length);

    }
}

class Person {

    String name;

    public Person(String name) {
        this.name = name;
    }
}

/* Do not change code below */
public class Main {

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        final int count = Integer.parseInt(scanner.nextLine());
        final Person[] persons = new Person[count];

        for (int i = 0; i < count; i++) {
            persons[i] = new Person(scanner.nextLine());
        }

        final String[] configs = scanner.nextLine().split("\\s+");

        final PersonSelectionAlgorithm alg = create(configs[0], Integer.parseInt(configs[1]));
        SelectionContext ctx = new SelectionContext();
        ctx.setAlgorithm(alg);

        final Person[] selected = ctx.selectPersons(persons);
        for (Person p : selected) {
            System.out.println(p.name);
        }
    }

    public static PersonSelectionAlgorithm create(String algType, int param) {
        switch (algType) {
            case "STEP": {
                return new TakePersonsWithStepAlgorithm(param);
            }
            case "LAST": {
                return new TakeLastPersonsAlgorithm(param);
            }
            default: {
                throw new IllegalArgumentException("Unknown algorithm type " + algType);
            }
        }
    }
}