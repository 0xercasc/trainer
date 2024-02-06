import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String[] task = {"D x", "Y z", "W on a", "I b", "Quit"};
        float[] taskRMod = {1f, 1f, 1f, 1f};
        float[] taskCMod = {1f, 1f, 1f, 1f};
        float taskMod = 1.1f;

        String[] reward;
        String[] rewardText = new String[]{"M for # m", "V for # m", "S for # m", "S $# ", "Conseq"};
        int[] rewardValue = new int[]{3, 15, 15, 2};
        int[] rewardMod = new int[]{1, 5, 5, 2};

        String[] conseq;
        String[] conseqText = new String[]{"G sed # ts", "W nc for # m", "W bp f # m", "Cs f # m"};
        int[] conseqValue = new int[]{10, 30, 30, 30};
        float conseqMod = 1.3f;

        int choice;
        boolean complete;






    //main loop
        while (true) {
        //task
            choice = choose(task);
            if (choice == task.length) return;
            choice--;
            System.out.println();
            System.out.println("Complete?");
            complete = choose();
            reward = fillValues(rewardText, rewardValue, taskRMod[choice]);
            if (complete) conseq = fillValues(conseqText, conseqValue, 1);
            else conseq = fillValues(conseqText, conseqValue, taskCMod[choice]);
            for (int a = 0; a < task.length - 1; a++) {
                if (a != choice) {
                    taskRMod[a] *= taskMod;
                    //taskCMod[a] /= taskMod;
                }
            }
            if (complete) {
                taskRMod[choice] /= taskMod;
                taskCMod[choice] /= taskMod;
            }
            else taskCMod[choice] *= conseqMod;

        //reward
            if (complete) {
                choice = choose(reward);
                for (int a = 0; a < reward.length - 1; a++) {
                    if (a == choice - 1) rewardValue[a] -= rewardMod[a];
                    else rewardValue[a] += rewardMod[a];
                }
                if (choice == reward.length) complete = false;
            }

        //conseq
            while (!complete) {
                choice = choose(conseq) - 1;
                for (int a = 0; a < conseq.length; a++) {
                    if (a==choice) conseqValue[a] *= conseqMod;
                    else conseqValue[a] /= conseqMod;
                    if (conseqValue[a]<5) conseqValue[a]=5;
                }
                System.out.println();
                System.out.println("Complete?");
                complete = choose();
                if (complete) conseqValue[choice] *= conseqMod;
            }

        }

    }





    static boolean choose() {
        System.out.println("  (1)  Yes");
        System.out.println("  (2)  No");

        Scanner in = new Scanner(System.in);
        int choice = 0;

        while (choice <= 0 || choice > 2) {
            System.out.print("  >");
            try {
                choice = in.nextInt();
            } catch (Exception e) {
                in.next();
            }
        }

        return choice == 1;
    }

    static int choose(String[] choices) {
        System.out.println();
        System.out.println("Choose:");
        for (int a = 0; a < choices.length; a++) {
            System.out.println("  (" + (a + 1) + ")  " + choices[a]);
        }

        Scanner in = new Scanner(System.in);
        int choice = 0;

        while (choice <= 0 || choice > choices.length) {
            System.out.print("  >");
            try {
                choice = in.nextInt();
            } catch (Exception e) {
                in.next();
            }
        }

        return choice;
    }

    static String[] fillValues(String[] baseText, int[] value, float mod) {
        String[] outString = new String[baseText.length];

        for (int a = 0; a < baseText.length; a++) {
            if (a < value.length)
                outString[a] = baseText[a].split("#")[0] + Math.round(value[a] * mod) + baseText[a].split("#")[1];
            else outString[a] = baseText[a];
        }

        return outString;
    }
}