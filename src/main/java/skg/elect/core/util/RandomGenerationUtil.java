package skg.elect.core.util;

import java.util.*;
import java.util.stream.Collectors;

public class RandomGenerationUtil
{
    private static Random r = new Random();

    public static double  generate_random_double(double lower, double upper) {

        double value=(double) (r.nextDouble() * (upper - lower)) + lower;
        return Math.floor(value*100)/100;

    }

    public static int generate_random_int(int lower, int upper) {
        //System.out.println(lower+"--"+upper+"--");

        return (int) (r.nextInt(upper - lower)) + lower;

    }

    public static List<Integer> distribute_randomly(int total, int numberOfCandidates, List<Double> bias) {
        int[] nums = new int[numberOfCandidates];
        int num_to_dist=0;
        for (int i = 0; i < nums.length - 1; i++) {

            double bias_original=Math.round(bias.get(i).doubleValue() * total);
            if(total>0) {num_to_dist=(int) (generate_random_int(0, total) +  (Math.floor(bias_original*100)/100));}
            else {num_to_dist=0;}

            nums[i] =num_to_dist;
            total -= nums[i];

        }
        nums[nums.length - 1] = total;
        List<Integer> lst = Arrays.stream(nums).boxed().collect(Collectors.toList());
        return lst;

    }

//public List<Integer> distribute_randomly_integer(int total, int numberOfCandidates, List<Double> bias) {
//	int[] nums = new int[numberOfCandidates];
//	double bias_original;
//	int num_to_dist=0;
//	double temp=0;
//	System.out.println(bias);
//	//System.out.println("total:"+total);
//	//System.out.println("numberOfCandidates:"+numberOfCandidates);
//
//	for (int i = 0; i < nums.length - 1; i++) {
//
//		bias_original=bias.get(i).doubleValue();
////		bias_original=bias_original*total/100;
////		System.out.println("bias"+bias_original);
////
//		if(total>0) {
//			temp=0;
//			try {
//				temp=this.generate_random_int(0, (int)(total/(numberOfCandidates-i)));
//				temp=this.generate_random_int(0, (int)(total/(numberOfCandidates)));
//			} catch (Exception e) {
//				temp=0;
//				//System.out.println("exception-"+total+"<---->"+(numberOfCandidates-i));
//			}
//			//System.out.println("temp1:"+temp);
//			temp=temp*bias_original;
//			//System.out.println("temp2:"+temp);
//			num_to_dist=(int) temp;
//			//System.out.println("num_to_dist:"+num_to_dist);
//
//		}
//		else {num_to_dist=0;}
//		if(num_to_dist<0) {num_to_dist=0;}
//		if(num_to_dist>=total) {num_to_dist=(int)(total*0.9);}
//
//		///System.out.println("num_to_dist:"+num_to_dist);
//		nums[i] = num_to_dist;
//		total -= nums[i];
//		///System.out.println("total:"+total);
//	}
//	nums[nums.length - 1] = total;
//	List<Integer> lst = Arrays.stream(nums).boxed().collect(Collectors.toList());
//	//Collections.shuffle(lst, new Random(System.nanoTime()));
//	System.out.println(lst);
//	System.exit(0);
//	return lst;
//}

    public static List<Integer> distribute_randomly_integer(
            int total, int numberOfCandidates, List<Double> bias,boolean shuffle) {
        int[] nums = new int[numberOfCandidates];
        double bias_original;
        int num_to_dist=0;
        double temp_2=0;
        double temp=0;
        double min_factor=0.9;
        double maxfacor=5;
        //System.out.println(bias);
        //System.out.println("total:"+total);

        for (int i = 0; i < nums.length - 1; i++) {

            bias_original=bias.get(i).doubleValue();
            //effective_min_factor=min_factor*bias_original

            //System.out.print("bias_original:"+bias_original);
            if(total>0) {
                temp=0;
                try {
                    temp=(int)total*bias_original/100;
                    //System.out.print("temp1:"+temp);
                    temp=generate_random_int((int)(temp*min_factor), (int)(temp*maxfacor));

                    //System.out.println(" temp:"+temp);
                } catch (Exception e) {
                    //System.out.println(e.getMessage()+"   "+(int)(temp*min_factor)+"    "+(int)(temp*maxfacor));
                    temp=0;

                }
                num_to_dist=(int) temp;
                //System.out.println("num_to_dist:"+num_to_dist);
            }
            else {num_to_dist=0;}
            if(num_to_dist<0) {num_to_dist=0;}
            if(num_to_dist>=total) {num_to_dist=(int)(total*0.9);}

            //System.out.println("num_to_dist:"+num_to_dist);
            nums[i] = num_to_dist;
            total -= nums[i];

        }
        nums[nums.length - 1] = total;
        List<Integer> lst = Arrays.stream(nums).boxed().collect(Collectors.toList());
        //System.out.println();
        //System.out.println(lst);
        //System.exit(0);
        if(shuffle)
        {
            int shuffleCount = r.nextInt(6);
            for(int l=0;l<shuffleCount;l++)
            {
                Collections.shuffle(lst, new Random(System.nanoTime()));
            }

        }
        return lst;
    }


    public List<Double> distribute_randomly_decimal(double total, int numberOfCandidates, List<Double> bias) {
        double[] nums = new double[numberOfCandidates];
        for (int i = 0; i < nums.length - 1; i++) {

            double bias_original=Math.round(bias.get(i).doubleValue() * total);
            nums[i] = this.generate_random_double(0, total) +  (Math.floor(bias_original*100)/100);
            total -= nums[i];
        }
        nums[nums.length - 1] = total;
        List<Double> lst = Arrays.stream(nums).boxed().collect(Collectors.toList());
        return lst;

    }


    public List<Double> distribute_randomly_decimal_OLD(double total, int numberOfCandidates) {
        double[] nums = new double[numberOfCandidates];
        for (int i = 0; i < nums.length - 1; i++) {

            nums[i] = this.generate_random_double(0, total);
            total -= nums[i];
        }
        nums[nums.length - 1] = Math.floor(total*100)/100;
        List<Double> lst = Arrays.stream(nums).boxed().collect(Collectors.toList());
        return lst;

    }


    public static List<Double> distribute_randomly_decimal(double total, int numberOfCandidates) {
        double[] nums = new double[numberOfCandidates];
        for (int i = 0; i < nums.length - 1; i++) {

            //nums[i] = this.generate_random_double(0, total/(numberOfCandidates-i));
            nums[i] = generate_random_double(0, total/(numberOfCandidates-i));
            total -= nums[i];
        }
        nums[nums.length - 1] = Math.floor(total*100)/100;
        List<Double> lst = Arrays.stream(nums).boxed().collect(Collectors.toList());
        Collections.shuffle(lst, new Random(System.nanoTime()));
        return lst;

    }


    public List<Double> distribute_uniformly_decimal(double total, int numberOfCandidates, List<Double> bias) {
        double uniformRandoms[] = new double[numberOfCandidates];
        double mean = total / numberOfCandidates;
        double sum = 0.0;

        for (int i=0; i<numberOfCandidates / 2; i++) {
            uniformRandoms[i] = r.nextDouble() * mean;

            uniformRandoms[numberOfCandidates - i - 1] = mean + r.nextDouble() * mean;

            sum += uniformRandoms[i] + uniformRandoms[numberOfCandidates - i -1];
        }
        uniformRandoms[(int)Math.ceil(numberOfCandidates/2)] = uniformRandoms[(int)Math.ceil(numberOfCandidates/2)] + total - sum;

        List<Double> lst = Arrays.stream(uniformRandoms).boxed().collect(Collectors.toList());
        return lst;

    }

    public String generate_random_String_with_prefix(String prefix, int length) {

        // chose a Character random from this String
        String alphaNumeric_string = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index = (int) (alphaNumeric_string.length() * Math.random());

            // add Character one by one in end of sb
            sb.append(alphaNumeric_string.charAt(index));
        }

        return prefix + sb.toString() + Long.toString(this.get_current_time_in_miliseconds());
    }

    public String generate_random_non_numeric_String_with_prefix(String prefix, int length) {

        // chose a Character random from this String
        String alphaNumeric_string = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index = (int) (alphaNumeric_string.length() * Math.random());

            // add Character one by one in end of sb
            sb.append(alphaNumeric_string.charAt(index));
            sb.append(".");
        }

        return prefix + sb.toString();
    }

    public long get_current_time_in_miliseconds() {
        return System.currentTimeMillis();
    }


    public static String getSymbol(int i) {
        if(i<=26) {
            return "ABCDEFGHIJKLMNOPQRSTUVWXYZ".substring(i, i+1);
        }
        else {

            return "ABCDEFGHIJKLMNOPQRSTUVWXYZ".substring(i%26, i%26+1)+(i-26);
        }
    }


    public static List<Double> populate_bias(int size,boolean shuffle){

        List<Double> lst = distribute_randomly_decimal(100,size);
        if(shuffle)
        {
            Collections.shuffle(lst, new Random(System.nanoTime()));
            Collections.shuffle(lst, new Random(System.nanoTime()));
            Collections.shuffle(lst, new Random(System.nanoTime()));
            Collections.shuffle(lst, new Random(System.nanoTime()));
        }
        return lst;
    }




    public static int secondMaxIndex(List<Integer> intArray)
    {
        int highest = 0;
        for(int i : intArray){
            if(i > highest) highest = i;
        }
        int secondHighest = 0;
        for(int i : intArray){
            if(i > secondHighest && i < highest) secondHighest = i;
        }
        return intArray.indexOf(secondHighest);
    }
}
