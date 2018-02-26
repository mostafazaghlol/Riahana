package com.mostafa.android.riahana;

/**
 * Created by mostafa on 2/26/18.
 */

public class services {
    String servicesname,ids,descriptions,imagesurl,colors;
    int countrate;

    public services(String Mservicesname, String Mids, String Mdescriptions, String Mimagesurl, String Mcolors, int countrate) {
        this.servicesname = Mservicesname;
        this.ids = Mids;
        this.descriptions = Mdescriptions;
        this.imagesurl = Mimagesurl;
        this.colors = Mcolors;
        this.countrate = countrate;
    }
}
