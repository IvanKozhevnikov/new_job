package ru.kozhevnikov;

import ru.kozhevnikov.element.Element;
import ru.kozhevnikov.element.PlentyElement;
import ru.kozhevnikov.group.Set;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Application {

    private final static String SEPARATE = File.separator;
    private final static String FILE = System.getProperty("user.dir") + SEPARATE + "src" +
            SEPARATE + "main" + SEPARATE + "resources" + SEPARATE + "lng.csv";

    private static Map<Integer, ArrayList<Set>> sortedGroup = new TreeMap<>(Collections.reverseOrder());
    private static Map<Element, ArrayList<PlentyElement>> storage = new HashMap<>();
    private static java.util.Set uniqueStrings = new HashSet<>();

    private static int numberGroup = 0;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dataFiltering(line.replace("\"", "").split(";", -1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        clearSingleGroup();
        ultimateGrouping();
        print();

        System.out.println(((System.currentTimeMillis() - start) / 1000f) + " seconds");
    }

    private static void dataFiltering(String[] values) {
        if(values.length == 3) {
            PlentyElement plentyElement = new PlentyElement(values);
            if(!uniqueStrings.contains(plentyElement) && plentyElement.isValid()) {
                uniqueStrings.add(plentyElement);
                initialGrouping(plentyElement);
            }
        }
    }

    private static void initialGrouping(PlentyElement plentyElement) {
        for (Element element : plentyElement.getValidElements()) {
            ArrayList<PlentyElement> value = storage.getOrDefault(element, new ArrayList<>());
            value.add(plentyElement);
            storage.put(element, value);
        }
    }

    private static void clearSingleGroup() {
        Map<Element, ArrayList<PlentyElement>> newContains = new HashMap<>();
        for (Map.Entry<Element, ArrayList<PlentyElement>> entry : storage.entrySet()) {
            if(entry.getValue().size() > 1) {
                newContains.put(entry.getKey(), entry.getValue());
            }
        }
        storage = newContains;
    }

    private static void ultimateGrouping() {
        additionalGrouping();

        for (Map.Entry<Element, ArrayList<PlentyElement>> entry : storage.entrySet()) {
            ArrayList<Set> value = sortedGroup.getOrDefault(entry.getValue().size(), new ArrayList<>());
            Set group = new Set(entry.getValue());
            value.add(group);
            sortedGroup.put(group.size(), value);
            numberGroup++;
        }
    }

    private static void additionalGrouping() {
        Map<PlentyElement, Integer> plentyElementCount = new HashMap<>();

        for (Map.Entry<Element,  ArrayList<PlentyElement>> entry : storage.entrySet()) {
            ArrayList<PlentyElement> elements = entry.getValue();
            for(PlentyElement plentyElement : elements) {
                int value = plentyElementCount.getOrDefault(plentyElement, 0);
                plentyElementCount.put(plentyElement, value + 1);
            }
        }

        List<java.util.Set> plentyElementSubGroup = new ArrayList<>();
        for (Map.Entry<PlentyElement, Integer> entry : plentyElementCount.entrySet()) {
            if(entry.getValue() > 1) {
                java.util.Set set = new HashSet<>();
                for (Element element : entry.getKey().getValidElements()) {
                    if(storage.containsKey(element)) {
                        set.addAll(storage.remove(element));
                    }
                }
                if(set.size() > 1)
                    plentyElementSubGroup.add(set);
            }
        }

        for (java.util.Set subGroup : plentyElementSubGroup) {
            ArrayList<Set> value = sortedGroup.getOrDefault(subGroup.size(), new ArrayList<>());
            Set group = new Set(new ArrayList<>(subGroup));
            value.add(group);
            sortedGroup.put(group.size(), value);
            numberGroup++;
        }
    }

    public static void print() {
        System.out.println("Кол-во групп " + numberGroup);
        int count = 1;
        for (Map.Entry<Integer, ArrayList<Set>> entry : sortedGroup.entrySet()) {
            for(Set group : entry.getValue()) {
                System.out.println("Группа " + count + "\n");
                System.out.println(group);
                count++;
            }
        }
    }
}
