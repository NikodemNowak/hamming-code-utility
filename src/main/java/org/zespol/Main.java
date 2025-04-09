package org.zespol;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String path;
        FileOperations fileOperations = new FileOperations();
        Scanner sc = new Scanner(System.in);
        byte[] dataByteFromFile;
        boolean[][] encodedData;
        boolean[][] encodedDataReadFromFile;

        while (true) {
            System.out.println("1. Kodowanie");
            System.out.println("2. Odkodowanie");
            System.out.println("3. Wyjście");

            switch (sc.nextInt()) {
                case 1:
                    System.out.println("Kodowanie");
                    while (true) {
                        System.out.println("Podaj ścieżkę do pliku, który chcesz opakować w Kod Hamminga: ");
                        if (sc.hasNext()) {
                            path = sc.next();
                            break;
                        }
                    }
                    dataByteFromFile = fileOperations.readTextFromFile(path);
                    encodedData = fileOperations.encodeData(dataByteFromFile);
                    String fileName = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
                    fileOperations.writeEncodedToFile(encodedData, "./encodedData/" + fileName + "_encoded.txt");
                    break;
                case 2:
                    System.out.println("Odkodowanie");
                        while (true) {
                            System.out.println("Podaj ścieżkę do pliku, który chcesz odpakować z Kod Hamminga: ");
                            if (sc.hasNext()) {
                                path = sc.next();
                                break;
                            }
                        }
                    encodedDataReadFromFile = fileOperations.readEncodedFromFile(path);
                    String someText = fileOperations.decodeData(encodedDataReadFromFile);
                    System.out.println("Odkodowane dane: " + someText);
                    String fileName2 = path.substring(path.lastIndexOf("/") + 1);
                    int underscoreIndex = fileName2.indexOf("_");
                    String savePath = "./data/" + (underscoreIndex > 0 ? fileName2.substring(0, underscoreIndex) : fileName2) + "_decoded.txt";
                    fileOperations.writeTextTofile(someText, savePath);

                    break;
                case 3:
                    System.out.println("Koniec programu.");
                    return;
                default:
                    System.out.println("Niepoprawny wybór. Wybierz ponownie.");
            }
        }
    }
}
