package skg.elect.core.runner;

import skg.elect.core.util.RandomGenerationUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RunnerV2_Allies
{
    public static void main(String[] args)
    {
        int total_seat = RandomGenerationUtil.generate_random_int(100, 450);
        System.out.println("Total Seats:"+ total_seat);
        int total_cand =0;
        int[] total_tally = new int[26];
        int[] total_runnerup_tally = new int[26];
        int[][] allianceMatrix ={
                {0,15,16,17,12,13,14,20,21,22,18,19},
                {2},
                {},
                {1,3,4,6,9,5,7,10,11,23}
        };
        long[] total_votes_received = new long[26];
        long totalVotePolled=0;
        for (int i  = 0; i < total_seat; i++)
        {
            System.out.println("======================================================================");
            System.out.println("Constituency -"+i);
            total_cand = RandomGenerationUtil.generate_random_int(16, 26);
            System.out.println("Total Candidates:"+ total_cand);

            int total_vote = RandomGenerationUtil.generate_random_int(100000, 200000);
            totalVotePolled= totalVotePolled+total_vote;
            System.out.println("Total Vote:"+ total_vote);

            double votePercent = RandomGenerationUtil.generate_random_double(0.30,1);
            int total_polled_vote =(int) (total_vote * votePercent);
            System.out.println("Total polled vote:"+ total_polled_vote+ " ["+votePercent*100+"%]");

            List<Integer> mandate = RandomGenerationUtil.distribute_randomly_integer(
                    total_polled_vote,total_cand,RandomGenerationUtil.populate_bias(total_cand,true),true);
            System.out.println(mandate);

            //Alliance data
            for(int k=0; k< allianceMatrix.length;k++) {
                mandate = formAlliance(mandate,allianceMatrix[k]);
            }

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
                total_votes_received[party] = total_votes_received[party]+mandate.get(party);
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

        System.out.println("================SUMMARY================================");
        System.out.println("Total:"+total_seat +" Majority:"+  (Math.round((double)total_seat/2)+1));
        for(int j = 0; j < total_tally.length; j++)
        {
            double tallyPercent = (double)total_votes_received[j]/ Arrays.stream(total_votes_received).sum()*100;

            //if(total_tally[j]>0 || total_runnerup_tally[j]>0)
                System.out.println(RandomGenerationUtil.getSymbol(j) +" "+
                        total_tally[j]+" ("+total_runnerup_tally[j]+ ")   "+
                         Math.floor(tallyPercent * 100) / 100.0+"%]"
                        );
        }
        System.out.println();

        System.out.println("======================================================================");
    }

    public static List<Integer> formAlliance(List<Integer> mandate, int[]allyIndex)
    {
        List<Integer> result = new ArrayList<Integer>(mandate);
        for(int i:allyIndex)
        {
            int collector =allyIndex[0];
            if(i!= collector && i<result.size())
            {
                result.set(collector,result.get(collector)+result.get(i));
                result.set(i,0);
            }

        }
        return result;
    }
}
