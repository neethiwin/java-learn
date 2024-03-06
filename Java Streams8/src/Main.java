import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;

record Person (String name, String country){}

public class Main {

    //1.Sum of numbers in a list.
    public static void findSum(){
        List<Integer> numbers = List.of(20, 30, 50, 100); //Introduced in java 9

        System.out.println(
                numbers.stream().mapToInt(Integer::intValue).sum());

    }

    //2. Find the maximum element in a list.
    public static void findMaxElement(){
        List<Integer> numbers = List.of(700, 20, 30, 50, 100);

        //way 1
        System.out.println(
                numbers.stream()
                        .mapToInt(Integer::intValue)
                        .max().getAsInt());

        //way 2
        System.out.println(
                numbers.stream().max(naturalOrder()).get()); //max delegates to comparator

        //way 3
        System.out.println(
                numbers.stream().max(Integer::compareTo).get()); //max delegates to comparator

        //way 4
        System.out.println(
                numbers.stream().max(comparingInt(Integer::intValue)).get());

        //way 5
        System.out.println(
                numbers.stream()
                        .reduce(Integer::max).get());

    }

    //3. Filter even numbers from a list.
    public static void findEvenNumber(){
        List<Integer> numbers = List.of(25, 30, 35, 50, 41);
        System.out.println(
        numbers.stream()
                .filter(e -> e % 2 == 0)
                .collect(toList()));
    }

    //4. Convert a list of strings to uppercase
    public static void convertListOfStringToUpperCase(){
        List<String> randomStrings = List.of("convert", "to", "upper", "string");
        //Best solution
        System.out.println(
        randomStrings.stream()
                .map(String::toUpperCase)
                .collect(toList()));

        //raw collect implementation
        System.out.println(
        randomStrings.stream()
                .reduce(new ArrayList<String>(),
                        (list, str) -> {
                    list.add(str.toUpperCase()); return list;
                    }, (list1, list2) -> {
                    list1.addAll(list2); return list1;
                })
        );
    }

    //5. Find the average of numbers in a list
    public static void findAverage(){
        List<Double> decimalNumbers = List.of(25.0, 30.1, 50.0, 41.1);
        System.out.println(
        decimalNumbers.stream()
                .mapToDouble(Double::doubleValue)
                .average());
    }

    //6. Count the occurrence of a specific element in a list.

    public static void findOccurrenceCountOfSpecificElement(){
        List<String> randomStrings = List.of("person1", "person2", "person1", "person4");
        System.out.println(
        randomStrings.stream()
                .filter(value -> value.equalsIgnoreCase("person1"))
                .count());
    }

    //6. Count the occurrence count of every element in a list.
    public static void findOccurrenceCount(){
        List<String> randomStrings = List.of("person1", "person2", "person1", "person4");
        System.out.println(
                randomStrings.stream()
                        .collect(groupingBy(String::valueOf, counting())));
    }

    //7. Count the occurrence count and sort them by name
    public static void findDelegatesCountByCountrySortThemByName(){
        List<Person> randomPeople = List.of(
                new Person("Nitesh", "India"),
                new Person("Sam", "US"),
                new Person("Bill", "US"),
                new Person("Abdul", "Singapore")
        );

        List<Map.Entry<String, List<String>>> sortedEntries = randomPeople.stream()
                        .collect(
                                collectingAndThen(
                                groupingBy(Person::country,
                                        mapping(Person::name, toList())
                                ),
                                   map -> map.entrySet().stream().sorted((e1, e2) -> e2.getKey().compareTo(e1.getKey()))
                                           .collect(Collectors.toList())
                                        ));
        sortedEntries.forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));

        //more concise solution
                List<Map.Entry<String, Long>> sortedEntries2 = randomPeople.stream()
                .collect(
                        collectingAndThen(
                                groupingBy(Person::country,
                                        counting()
                                ),
                                map -> map.entrySet().stream().sorted(Map.Entry.comparingByKey())
                                        .collect(Collectors.toList())
                        ));
        sortedEntries2.forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }

    public static void main1(String[] args) {
        List<String> list = Arrays.asList("apple", "banana", "cherry", "apple", "banana", "cherry", "apple", "banana", "date");

        // Grouping elements by their occurrence count and sorting the result
        List<Map.Entry<String, Long>> sortedEntries = list.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.groupingBy(e -> e, Collectors.counting()),
                        map -> map.entrySet().stream()
                                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                                .collect(Collectors.toList())
                ));

        // Printing the sorted entries
        sortedEntries.forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }
    public static void main(String[] args) {
        findDelegatesCountByCountrySortThemByName();
    }
}