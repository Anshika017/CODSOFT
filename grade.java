import java.util.Scanner;

public class grade
{
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of subjects: ");
        int sub = sc.nextInt();
        int[] marks = new int[sub];
        int total = 0;
        
        for (int i = 0; i < sub; i++) {
            System.out.print("Enter marks for subject " + (i + 1) + ": ");
            marks[i] = sc.nextInt();
            total += marks[i];
        }
        int totalmarks = 100 * sub;
        double per = (total / (double)totalmarks) * 100;

        String grade;
        if (per >= 90) {
            grade = "A";
        } else if (per >= 80) {
            grade = "B";
        } else if (per >= 70) {
            grade = "C";
        } else if (per >= 60) {
            grade = "D";
        } else {
            grade = "F";
        }

        System.out.println("Total Marks: " + total);
        System.out.println("Average Percentage: " + String.format("%.2f", per) + "%");
        System.out.println("Grade: " + grade);

        sc.close();
    }
}
