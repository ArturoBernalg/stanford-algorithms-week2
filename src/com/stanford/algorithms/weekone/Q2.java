package com.stanford.algorithms.weekone;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by LordNikon on 18/07/15.
 */

public class Q2
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        ArrayList<JobQ2> jobsArray = readJobsFromFile();

        //SORT JOBS
        Collections.sort(jobsArray, new JobsComparatorQ2());

        for(JobQ2 job : jobsArray){
            System.out.println("Job " + (jobsArray.indexOf(job) + 1) + " => Range : " + job.jobRangeValue());
        }

        //CALCULATE THE SUM OF WEIGHTED COMPLETION TIMES
        long sumLength = 0;
        long sumWeighted = 0;

        for(JobQ2 job : jobsArray)
        {
            sumLength += job.getLength();

            sumWeighted += (sumLength * job.getWeight());
        }

        System.out.println("Total Sum Weighted => " + sumWeighted);
    }

    /**
     * Reads the Job list
     * @return A list of jobs
     */
    private static ArrayList<JobQ2> readJobsFromFile()
    {
        ArrayList<JobQ2> jobsArray = new ArrayList<JobQ2>();

        FileInputStream fstream = null;
        try {
            fstream = new FileInputStream("jobs.txt");

            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            //READ THE JOBS ARRAY
            int index = 0;
            String line;
            while ((line = br.readLine()) != null)
            {
                if(index != 0)
                {
                    // process the line
                    StringTokenizer tokens = new StringTokenizer(line);

                    Integer weight = new Integer(tokens.nextToken());
                    Integer length = new Integer(tokens.nextToken());

                    jobsArray.add(new JobQ2(weight.intValue(), length.intValue()));
                }

                index++;
            }

            br.close();
        }catch (FileNotFoundException ex) {
            Logger.getLogger(Q2.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IOException ex) {
            Logger.getLogger(Q2.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                fstream.close();
            } catch (IOException ex) {
                Logger.getLogger(Q2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return jobsArray;
    }
}

/**
 * This class represents a single job
 */
class JobQ2
{
    private int weight;
    private int length;

    public JobQ2(int weight, int length){
        super();
        this.weight = weight;
        this.length = length;
    }

    public int getWeight(){
        return weight;
    }

    public int getLength(){
        return length;
    }

    public float jobRangeValue(){
        return (float)weight / length;
    }
}

/**
 * Compare two jobs ordering them by the difference weight - lenght (descending)
 * If there is a tie then schedule the job with higher weight first
 */
class JobsComparatorQ2 implements Comparator<JobQ2> {
    @Override
    public int compare(JobQ2 job1, JobQ2 job2)
    {
        int result = 0;

        if(job1.jobRangeValue() < job2.jobRangeValue()){
            result = 1;
        }
        else if(job1.jobRangeValue() > job2.jobRangeValue()){
            result = -1;
        }

        return result;
    }
}