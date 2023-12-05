package org.labsec;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;


import java.util.HashMap;
import java.math.BigInteger;


@Path("/labseq/")
public class LabseqResource {

    static int maxN = Integer.MAX_VALUE; // Can be used to limit the input range, needs to trowh an error if;
    static int preventOverflowStepSize = 1000;
    int maxCalculatedN = 3;

    static HashMap<Integer, BigInteger> labseqCacheMap; //https://quarkus.io/guides/cache


    public LabseqResource() {
        resetCache();
    }

    @GET
    public String labseqGet() {
        return "Append /{n} to the URL to get the n-th number in the Labseq sequence.";
    }

    @Path("{n}")
    @GET
    public String labseqGet(@PathParam("n") Integer n) {
        if (n < 0 || n > maxN){
            resetCache(); // experimental purpose
            return "Positive integers only, please.";
        }

        // Prevent stack overflow by doing atmost preventOverflowStepSize recursive steps at a time, creating the cache.
        if (n > maxCalculatedN){
            while(n - preventOverflowStepSize > maxCalculatedN){
                maxCalculatedN += preventOverflowStepSize;
                labseq(maxCalculatedN);
            }
            maxCalculatedN = n;
        }

        return labseq(n).toString();
    }


    public BigInteger labseq(Integer n) {
        if (labseqCacheMap.containsKey(n)){
            return labseqCacheMap.get(n);
        }

        BigInteger result = new BigInteger(labseq(n-3).toString());
        result = result.add(labseq(n-4));

        labseqCacheMap.put(n, result);

        return result;
    }


    private void resetCache(){
        labseqCacheMap = new HashMap<>();
        labseqCacheMap.put(0, new BigInteger("0"));
        labseqCacheMap.put(1, new BigInteger("1"));
        labseqCacheMap.put(2, new BigInteger("0"));
        labseqCacheMap.put(3, new BigInteger("1"));
    }

}

