package skg.elect.core.runner;

import skg.elect.core.util.RandomGenerationUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RunnerV1
{
    public static void main(String[] args)
    {
        int total_seat = RandomGenerationUtil.generate_random_int(1, 100);
        System.out.println("Total Seats:"+ total_seat);
        int total_cand =0;
        int[] total_tally = new int[15];
        int[] total_runnerup_tally = new int[15];

        for (int i  = 0; i < total_seat; i++)
        {
            System.out.println("======================================================================");
            System.out.println("Constituency -"+i);
            total_cand = RandomGenerationUtil.generate_random_int(1, 15);
            System.out.println("Total Candidates:"+ total_cand);

            int total_vote = RandomGenerationUtil.generate_random_int(100000, 200000);
            System.out.println("Total Vote:"+ total_vote);

            double votePercent = RandomGenerationUtil.generate_random_double(0.30,1);
            int total_polled_vote =(int) (total_vote * votePercent);
            System.out.println("Total polled vote:"+ total_polled_vote+ " ["+votePercent*100+"%]");

            List<Integer> mandate = RandomGenerationUtil.distribute_randomly_integer(
                    total_polled_vote,total_cand,RandomGenerationUtil.populate_bias(total_cand,true),true);
            System.out.println(mandate);

            int party=0;
            int maxIndex = mandate.indexOf(Collections.max(mandate));
            int secondMaxIndex = RandomGenerationUtil.secondMaxIndex(mandate);
            for(int m: mandate)
            {
                System.out.print("party : "+RandomGenerationUtil.getSymbol(party) +"  ");
                System.out.print(m+ " [");
                double percent =(double)m/total_polled_vote*100;
                System.out.print(Math.floor(percent * 100) / 100.0+"%]");
                if(maxIndex == party)
                {
                    total_tally[party] = total_tally[party]+1;
                    System.out.println("Wins!");
                }
                else if(secondMaxIndex ==party)
                {
                    total_runnerup_tally[party] = total_runnerup_tally[party]+1;
                    System.out.println("Second!");
                }
                else System.out.println();

                party++;
            }
            System.out.println("Party:"+RandomGenerationUtil.getSymbol(maxIndex) +
                            " defeats:"+RandomGenerationUtil.getSymbol(secondMaxIndex) +" by:"+ (mandate.get(maxIndex)-mandate.get(secondMaxIndex))+" votes.");
            System.out.println("**********************************************************");
            for(int j = 0; j < total_tally.length; j++)
            {
                if(total_tally[j]>0 || total_runnerup_tally[j]>0)
                    System.out.print(RandomGenerationUtil.getSymbol(j) +" "+total_tally[j]+" ("+total_runnerup_tally[j]+ ")   ");
            }
            System.out.println();
            System.out.println("**********************************************************");
        }
    }
}
