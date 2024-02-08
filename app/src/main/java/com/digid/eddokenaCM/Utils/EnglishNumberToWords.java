package com.digid.eddokenaCM.Utils;

import android.util.Log;

import java.text.DecimalFormat;

public class EnglishNumberToWords {

    private static final String[] tensNames = { "", " dix", " vingt", " trente", " quarante",
            " cinquante", " soixante", " soixantedix", " quatrevingt", " quatrevingtdix" };

    private static final String[] numNames = { "", " un", " deux", " trois", " quatre", " cinq",
            " six", " sept", " huit", " neuf", " dix", " onze", " douze", " treize",
            " quatorze", " quinze", " seize", " dixsept", " dixhuit", " dixneuf" };


    public static String convertLessThanOneThousand(int number) {
        String soFar;

        if (number % 100 < 20)
        {
            soFar = numNames[number % 100];
            number /= 100;
        } else
        {
            soFar = numNames[number % 10];
            number /= 10;

            soFar = tensNames[number % 10] + soFar;
            number /= 10;
        }
        if (number == 0)
            return soFar;
        if (number ==1)
            return " cent"+ soFar;
        return numNames[number] + " cent" + soFar;
    }

    public static String convert(double number)
    {
        // 0 to 999 999 999 999
        if (number == 0)
        {
            return "zero";
        }

        String snumber = Double.toString(number);

        if (snumber.contains(".")){

            Log.i("pdf", "------------------ ");
            int index=snumber.indexOf(".");
            snumber=snumber.substring(0,index);
            number=Double.parseDouble(snumber);
        }



        // pad with "0"
        String mask = "000000000000";
        DecimalFormat df = new DecimalFormat(mask);
        snumber = df.format(number);

        // XXXnnnnnnnnn
        int billions = Integer.parseInt(snumber.substring(0, 3));
        // nnnXXXnnnnnn
        int millions = Integer.parseInt(snumber.substring(3, 6));
        // nnnnnnXXXnnn
        int hundredThousands = Integer.parseInt(snumber.substring(6, 9));
        // nnnnnnnnnXXX
        int thousands = Integer.parseInt(snumber.substring(9, 12));

        String tradBillions;
        switch (billions)
        {
            case 0:
                tradBillions = "";
                break;
            case 1:
                tradBillions = convertLessThanOneThousand(billions) + " milliard ";
                break;
            default:
                tradBillions = convertLessThanOneThousand(billions) + " milliard ";
        }
        String result = tradBillions;

        String tradMillions;
        switch (millions)
        {
            case 0:
                tradMillions = "";
                break;
            case 1:
                tradMillions = convertLessThanOneThousand(millions) + " million ";
                break;
            default:
                tradMillions = convertLessThanOneThousand(millions) + " million ";
        }
        result = result + tradMillions;

        String tradHundredThousands;
        switch (hundredThousands)
        {
            case 0:
                tradHundredThousands = "";
                break;
            case 1:
                tradHundredThousands = "mille ";
                break;
            default:
                tradHundredThousands = convertLessThanOneThousand(hundredThousands) + " mille ";
        }
        result = result + tradHundredThousands;

        String tradThousand;
        tradThousand = convertLessThanOneThousand(thousands);
        result = result + tradThousand;

        if (result.contains("quatrevingtdix un")){
            result=result.replace( "quatrevingtdix un","quatrevingt onze");
        }
        if (result.contains("quatrevingtdix deux")){
            result=result.replace( "quatrevingtdix deux","quatrevingt douze");
        }
        if (result.contains("quatrevingtdix trois")){
            result=result.replace( "quatrevingtdix trois","quatrevingt treize");
        }
        if (result.contains("quatrevingtdix quatre")){
            result=result.replace( "quatrevingtdix quatre","quatrevingt quatorze");
        }

        if (result.contains("quatrevingtdix cinq")){
            result=result.replace( "quatrevingtdix cinq","quatrevingt quinze");
        }
        if (result.contains("quatrevingtdix six")){
            result=result.replace( "quatrevingtdix six","quatrevingt seize");
        }
        if (result.contains("quatrevingtdix sept")){
            result=result.replace( "quatrevingtdix sept","quatrevingt dixsept");
        }
        if (result.contains("quatrevingtdix huit")){
            result=result.replace( "quatrevingtdix huit","quatrevingt dixhuit");
        }
        if (result.contains("quatrevingtdix neuf")){
            result=result.replace( "quatrevingtdix neuf","quatrevingt dixneuf");
        }


        if (result.contains("soixantedix un")){
            result=result.replace( "soixantedix un","quatrevingt onze");
        }
        if (result.contains("soixantedix deux")){
            result=result.replace( "soixantedix deux","quatrevingt douze");
        }
        if (result.contains("soixantedix trois")){
            result=result.replace( "soixantedix trois","quatrevingt treize");
        }
        if (result.contains("soixantedix quatre")){
            result=result.replace( "soixantedix quatre","quatrevingt quatorze");
        }

        if (result.contains("soixantedix cinq")){
            result=result.replace( "soixantedix cinq","quatrevingt quinze");
        }
        if (result.contains("soixantedix six")){
            result=result.replace( "soixantedix six","quatrevingt seize");
        }
        if (result.contains("soixantedix sept")){
            result=result.replace( "soixantedix sept","quatrevingt dixsept");
        }
        if (result.contains("soixantedix huit")){
            result=result.replace( "soixantedix huit","quatrevingt dixhuit");
        }
        if (result.contains("soixantedix neuf")){
            result=result.replace( "soixantedix neuf","quatrevingt dixneuf");
        }

        // remove extra spaces!
        return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
    }

}
