package org.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

import static javax.swing.text.html.parser.DTDConstants.ID;

public class AnimalRegistry {

    private int nextID = 1; //добавил
    private List<Animal> animalList;

    public AnimalRegistry() {
        animalList = new ArrayList<>();
    }


    public void addAnimal(Animal animal) {
        animal.setID(nextID);
        nextID++;
        animalList.add(animal);
        System.out.println("Животное добавлено в реестр: " + animal.getName());
    }
    //public void addAnimal(Animal animal) {
    //    animalList.add(animal);
    //   System.out.println("Животное добавлено в реестр: " + animal.getName());
    //}


    public void printRegistry() {
        System.out.println("Реестр домашних животных (отсортированный по дате рождения):");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        for (Animal animal : animalList) {
            String formattedDate = dateFormat.format(animal.BirthDate);
            LocalDate birthDate = animal.BirthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate currentDate = LocalDate.now();
            Period age = Period.between(birthDate, currentDate);
            int months = age.getYears() * 12 + age.getMonths();
            String commands = String.join(", ", animal.Commands);
            System.out.println("№" + " | " + "Кличка" + " | " + "Тип" + " | " + "Дата рождения" + " | " + "Знает команды" + " | " + "Возраст");
            System.out.println(animal.ID + " | " + animal.getName() + " | " + animal.getType() + " | " + formattedDate + " | " + commands + " | " + months + " мес");
            System.out.println("Всего животных: " + animal.ID);
        }
    }
    public void printCommands() {
        System.out.println("Реестр домашних животных (отсортированный по дате рождения):");
        for (Animal animal : animalList) {
            System.out.println(animal.getName() + " - " + animal.getType());
            animal.printAnimalCommands();
        }
    }

    public void trainAnimal(String name, String newCommand) {
        for (Animal animal : animalList) {
            if (animal.getName().equals(name)) {
                animal.addCommand(newCommand);
                System.out.println("Животное " + name + " обучено новой команде: " + newCommand);
                return;
            }
        }
        System.out.println("Животное с именем " + name + " не найдено в реестре.");
    }

    public static void main(String[] args) {
        AnimalRegistry registry = new AnimalRegistry();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Меню:");
            System.out.println("1. Добавить животное в реестр");
            System.out.println("2. Вывести список команд для животного");
            System.out.println("3. Добавить новую команду животному");
            System.out.println("4. Вывести список животных по дате рождения за период");
            System.out.println("5. Вывести реестр домашних животных");
            System.out.println("0. Выход");

            System.out.println("Введите номер пункта меню:");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:

                    //System.out.println("Введите ID животного (или 'выход' для завершения):");
                    //String input = scanner.nextLine();
                    //int ID = Integer.parseInt(input);
                    System.out.println("Введите имя животного:");
                    String name = scanner.nextLine();

                    System.out.println("Введите тип животного:");
                    String type = scanner.nextLine();

                    System.out.println("Введите дату рождения животного (ДД.ММ.ГГГГ):");
                    String birthDateStr = scanner.nextLine();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

                    Date birthDate = null;
                    try {
                        birthDate = dateFormat.parse(birthDateStr);
                        System.out.println("Вы ввели дату: " + dateFormat.format(birthDate));
                    } catch (ParseException e) {
                        System.out.println("Неверный формат даты. Повторите попытку.");
                    }


                    System.out.println("Введите команды животного (через запятую):");
                    String commands = scanner.nextLine();

                    Animal animal = createAnimal(ID, name, type, birthDate, commands);
                    if (animal != null) {
                        registry.addAnimal(animal);
                    } else {
                        System.out.println("Неизвестный тип животного. Пожалуйста, введите корректный тип.");
                    }
                    // Запрос и обработка данных для добавления животного в реестр
                    break;
                case 2:
                    System.out.println("Введите имя животного:");
                    String animalName = scanner.nextLine();

                    boolean found = false;
                    for (Animal requestedAnimal : registry.animalList) {
                        if (requestedAnimal.getName().equalsIgnoreCase(animalName)) {
                            requestedAnimal.printAnimalCommands();
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("Животное с именем " + animalName + " не найдено в реестре.");
                    }
                    break;

                // Запрос и обработка имени животного для вывода списка команд

                case 3:
                    System.out.println("Желаете обучить животное новой команде? (да/нет)");
                    String trainCommand = scanner.nextLine();
                    while (trainCommand.equalsIgnoreCase("да")) {
                        System.out.println("Введите имя животного:");
                        animalName = scanner.nextLine();

                        System.out.println("Введите новую команду:");
                        String newCommand = scanner.nextLine();

                        registry.trainAnimal(animalName, newCommand);

                        System.out.println("Желаете обучить еще одно животное новой команде? (да/нет)");
                        trainCommand = scanner.nextLine();
                    }
                    // Запрос и обработка имени животного и новой команды
                    break;
                case 4:
                    System.out.println("Введите дату начала интервала в формате дд.мм.гггг:");
                    String startDateString = scanner.nextLine();
                    System.out.println("Введите дату окончания интервала в формате дд.мм.гггг:");
                    String endDateString = scanner.nextLine();

                    dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                    Date startDate;
                    Date endDate;
                    try {
                        startDate = dateFormat.parse(startDateString);
                        endDate = dateFormat.parse(endDateString);
                    } catch (ParseException e) {
                        System.out.println("Неверный формат даты. Повторите попытку.");
                        break;
                    }

                    List<Animal> animalsInInterval = new ArrayList<>();
                    for (Animal animalInterval : registry.animalList) {
                        Date animalBirthDate = animalInterval.getBirthDate();
                        if (animalBirthDate.after(startDate) && animalBirthDate.before(endDate)) {
                            animalsInInterval.add(animalInterval);
                        }
                    }

                    if (animalsInInterval.isEmpty()) {
                        System.out.println("В указанном интервале нет животных с датой рождения.");
                    } else {
                        System.out.println("Животные с датой рождения в указанном интервале:");
                        for (Animal animalInterval : animalsInInterval) {
                            System.out.println(animalInterval.getName() + " - " + dateFormat.format(animalInterval.getBirthDate()));
                        }
                    }
                    break;
                // Запрос и обработка дат для вывода списка животных по дате рождения за период
                case 5:
                    registry.printRegistry();
                    // Вывод реестра домашних животных
                    break;
                case 0:
                    System.out.println("Программа завершена.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Некорректный номер пункта меню.");
            }
        }



    }

    private static Animal createAnimal(int ID, String name, String type, Date birthDate, String commands) {
        switch (type.toLowerCase()) {
            case "лошадь":
                return new PackAnimal(ID, name, type, birthDate, commands);
            case "осел":
                return new PackAnimal(ID, name, type, birthDate, commands);
            case "верблюд":
                return new PackAnimal(ID, name, type, birthDate, commands);
            case "кошка":
                return new Pets(ID, name, type, birthDate, commands);
            case "хомяк":
                return new Pets(ID, name, type, birthDate, commands);
            case "собака":
                return new Pets(ID, name, type, birthDate, commands);
            // Добавьте другие типы животных по мере необходимости
            default:
                return null;
        }
    }

    private static Date parseDate(String dateString) {
        // Ваш код разбора строки даты и создания объекта java.util.Date
        // В этом примере предполагается, что формат даты - "ГГГГ-ММ-ДД"
        return null;
    }
}

class Animal {
    protected int ID;
    protected String Name;
    protected String Type;
    protected Date BirthDate;
    protected List<String> Commands;

    public Animal(int ID, String name, String type, Date birthDate, String commands) {
        this.ID = 1;
        this.Name = name;
        this.Type = type;
        this.BirthDate = birthDate;
        this.Commands = new ArrayList<>();
        if (!commands.isEmpty()) {
            String[] commandArray = commands.split(",");
            for (String command : commandArray) {
                this.Commands.add(command.trim());
            }
        }
    }

    public void setID(int id) {
        this.ID = id;
    }
    public int getId() {
        return ID;
    }
    public String getName() {
        return Name;
    }

    public String getType() {
        return Type;
    }
    public Date getBirthDate() {
        return BirthDate;
    }



    public void printAnimalCommands() {
        System.out.println("Команды для животного " + Name + ": " + Commands);
    }

    public void addCommand(String command) {
        Commands.add(command);
    }
}

class PackAnimal extends Animal {
    public PackAnimal(int ID, String name, String type, Date birthDate, String commands) {
        super(ID, name, type, birthDate, commands);
    }
}

class Pets extends Animal {
    public Pets(int ID, String name, String type, Date birthDate, String commands) {
        super(ID, name, type, birthDate, commands);
    }
}