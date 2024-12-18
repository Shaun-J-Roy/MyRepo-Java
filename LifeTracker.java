import java.util.Scanner;

public class LifeTracker {
    // Activity inner class
    private static class Activity {
        private String description;
        private boolean isGoal;

        public Activity(String description, boolean isGoal) {
            this.description = description;
            this.isGoal = isGoal;
        }

        public String getDescription() {
            return description;
        }

        public boolean isGoal() {
            return isGoal;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    // LifeTracker class
    private String name;
    private int age;
    private double weight; // in kg
    private double height; // in feet
    private Activity[] activities;
    private int activityCount;

    // Constructor overloading
    public LifeTracker(String name, int age, double weight, double height) {
        this(name, age, weight, height, 10); // Default capacity of 10
    }

    public LifeTracker(String name, int age, double weight, double height, int initialCapacity) {
        this.name = name;
        setAge(age);
        setWeight(weight);
        setHeight(height);
        this.activities = new Activity[initialCapacity];
        this.activityCount = 0;
    }

    // Getter and setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age > 0) {
            this.age = age;
        } else {
            throw new IllegalArgumentException("Age must be positive");
        }
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        if (weight > 0) {
            this.weight = weight;
        } else {
            throw new IllegalArgumentException("Weight must be positive");
        }
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        if (height > 0) {
            this.height = height;
        } else {
            throw new IllegalArgumentException("Height must be positive");
        }
    }

    // Method overloading for adding activities
    public void addActivity(String description, boolean isGoal) {
        addActivity(new Activity(description, isGoal));
    }

    public void addActivity(Activity activity) {
        if (activityCount < activities.length) {
            activities[activityCount++] = activity;
        } else {
            Activity[] newActivities = new Activity[activities.length * 2];
            System.arraycopy(activities, 0, newActivities, 0, activities.length);
            activities = newActivities;
            activities[activityCount++] = activity;
        }
    }

    // Operator overloading for adding activities (using the + operator)
    public LifeTracker plus(Activity activity) {
        addActivity(activity);
        return this;
    }

    public void removeActivity(String description) {
        for (int i = 0; i < activityCount; i++) {
            if (activities[i].getDescription().equals(description)) {
                System.arraycopy(activities, i + 1, activities, i, activityCount - i - 1);
                activityCount--;
                break;
            }
        }
    }

    public Activity[] getActivities() {
        Activity[] result = new Activity[activityCount];
        System.arraycopy(activities, 0, result, 0, activityCount);
        return result;
    }

    public Activity[] getGoals() {
        return filterActivities(true);
    }

    public Activity[] getDailyTasks() {
        return filterActivities(false);
    }

    private Activity[] filterActivities(boolean isGoal) {
        int count = 0;
        for (int i = 0; i < activityCount; i++) {
            if (activities[i].isGoal() == isGoal) {
                count++;
            }
        }
        Activity[] result = new Activity[count];
        int index = 0;
        for (int i = 0; i < activityCount; i++) {
            if (activities[i].isGoal() == isGoal) {
                result[index++] = activities[i];
            }
        }
        return result;
    }

    public double calculateBMI() {
        double heightInMeters = height * 0.3048; // Convert feet to meters
        return weight / (heightInMeters * heightInMeters);
    }

    public void displayLifeInfo() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Weight: " + weight + " kg");
        System.out.println("Height: " + height + " ft");
        System.out.println("BMI: " + String.format("%.2f", calculateBMI()));

        System.out.println("Goals:");
        Activity[] goals = getGoals();
        if (goals.length > 0) {
            for (Activity goal : goals) {
                System.out.println("- " + goal);
            }
        } else {
            System.out.println("No goals set");
        }

        System.out.println("Daily Tasks:");
        Activity[] dailyTasks = getDailyTasks();
        if (dailyTasks.length > 0) {
            for (Activity task : dailyTasks) {
                System.out.println("- " + task);
            }
        } else {
            System.out.println("No daily tasks set");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your age: ");
        int age = scanner.nextInt();

        System.out.print("Enter your weight (in kg): ");
        double weight = scanner.nextDouble();

        System.out.print("Enter your height (in feet): ");
        double height = scanner.nextDouble();

        LifeTracker tracker = new LifeTracker(name, age, weight, height);

        System.out.println("\nInitial Life Info:");
        tracker.displayLifeInfo();

        scanner.nextLine(); // Consume the newline

        while (true) {
            System.out.println("\nEnter an activity (or 'quit' to finish):");
            String activity = scanner.nextLine();
            if (activity.equalsIgnoreCase("quit")) {
                break;
            }
            System.out.print("Is this a goal? (yes/no): ");
            boolean isGoal = scanner.nextLine().equalsIgnoreCase("yes");

            // Demonstrating operator overloading
            tracker = tracker.plus(new Activity(activity, isGoal));
        }

        System.out.println("\nUpdated Life Info:");
        tracker.displayLifeInfo();

        System.out.print("\nEnter an activity to remove: ");
        String activityToRemove = scanner.nextLine();
        tracker.removeActivity(activityToRemove);

        System.out.println("\nFinal Life Info:");
        tracker.displayLifeInfo();

        scanner.close();
    }
}

