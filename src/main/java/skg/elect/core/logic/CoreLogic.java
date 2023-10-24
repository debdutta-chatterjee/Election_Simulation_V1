package skg.elect.core.logic;

import java.util.Arrays;
import java.util.Random;

public class CoreLogic
{
    private static int[] splitIntoParts(int whole, int parts) {
        int[] arr = new int[parts];
        int remain = whole;
        int partsLeft = parts;
        for (int i = 0; partsLeft > 0; i++) {
            int size = (remain + partsLeft - 1) / partsLeft; // rounded up, aka ceiling
            arr[i] = size;
            remain -= size;
            partsLeft--;
        }
        return arr;
    }

    private static int[] splitIntoRandomParts(int whole, int parts) {
        int[] nums = new int[parts];
        int total = whole;
        Random rand = new Random();
        for (int i = 0; i < nums.length - 1; i++) {
            nums[i] = rand.nextInt(total);
            total -= nums[i];
        }
        nums[nums.length - 1] = total;
        //Arrays.sort(nums);
        return nums;
    }

    private static double[] splitIntoRandomParts(double whole, int parts) {
        double[] nums = new double[parts];
        double total = whole;
        Random rand = new Random();
        for (int i = 0; i < nums.length - 1; i++) {
            nums[i] = Math.round(rand.nextDouble(total) * 100) / 100;
            total -= nums[i];
        }
        nums[nums.length - 1] = total;
        //Arrays.sort(nums);
        return nums;
    }

    public static void main(String[] args)
    {
        double[] arr = splitIntoRandomParts(100.0,18);
        Arrays.stream(arr).forEach(System.out::println);
    }

}
