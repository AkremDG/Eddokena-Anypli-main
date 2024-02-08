package com.digid.eddokenaCM.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

import com.digid.eddokenaCM.Models.Article;

import java.io.File;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.PriorityQueue;

public class Utilities {
    private static final Utilities ourInstance = new Utilities();

    public static Utilities getInstance() {
        return ourInstance;
    }

    private Utilities() {
    }

    public static <T> int getArticlePositionById(List<T> list, long id) {
        for (int i = 0; i < list.size(); i++) {
            T item = list.get(i);
            // Assuming your object has a getId() method that returns its ID
            if (item instanceof Article && ((Article) item).getId() == id) {
                return i;
            }
        }
        return -1; // Return -1 if the object with the given ID is not found in the list
    }

    public void replaceCharEntetePdf(String strr, String str, int textY, int textX, Canvas canvas, Paint paint) {
        String x = str.substring(0,41);
        int index = x.lastIndexOf(' ');
        String leftX = x.substring(0,index);
        String rightX = x.substring(index+1);
        String newX = leftX + "\n" + rightX;

        canvas.drawText(strr+" "+leftX,textX,textY,paint);
        canvas.drawText(rightX+str.substring(42),textX+15,textY+15,paint);

    }

    public void replaceCharLignePdf(String strr, String str, int textY, int textX, Canvas canvas, Paint paint) {
        String x = str.substring(0,41);
        int index = x.lastIndexOf(' ');
        String leftX = x.substring(0,index);
        String rightX = x.substring(index+1);
        String newX = leftX + "\n" + rightX;

        canvas.drawText(strr+" "+leftX,textX,textY,paint);
        canvas.drawText(rightX+str.substring(42),textX+15,textY+15,paint);

    }

    public String getDateFormat(String date) {
        String convertedDate = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.FRANCE);
        try {
            cal.setTime(sdf.parse(date));// all done
            SimpleDateFormat sdf2 = new SimpleDateFormat("EEE dd/MMM/yyyy");
            convertedDate = sdf2.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return convertedDate;
    }

    public String getBOStringFromCalendar(Calendar date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.FRANCE);
        return sdf.format(date.getTime());
    }

    public String getStringFromCalendar(Calendar date) {
        date.set(Calendar.HOUR_OF_DAY, 00);
        date.set(Calendar.MINUTE, 00);
        date.set(Calendar.SECOND, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.FRANCE);
        return sdf.format(date.getTime());
    }

    public String getStringFromCalendarII(Calendar date) {
        date.set(Calendar.HOUR_OF_DAY, 00);
        date.set(Calendar.MINUTE, 00);
        date.set(Calendar.SECOND, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
        return sdf.format(date.getTime());
    }

    public String getExpireStringFromCalendar(Calendar date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.FRANCE);
        return sdf.format(date.getTime());
    }

    public Date lastWeek(Calendar cal) {
        cal.add(Calendar.DATE, -7);
        return cal.getTime();
    }

    public Date lastTwoWeek(Calendar cal) {
        cal.add(Calendar.DATE, -14);
        return cal.getTime();
    }

    public String getLastWeekDateString(Calendar date) {
        date.set(Calendar.HOUR_OF_DAY, 00);
        date.set(Calendar.MINUTE, 00);
        date.set(Calendar.SECOND, 0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.FRANCE);
        return dateFormat.format(lastWeek(date));
    }

    public String getLastTwoWeekDateString(Calendar date) {
        date.set(Calendar.HOUR_OF_DAY, 00);
        date.set(Calendar.MINUTE, 00);
        date.set(Calendar.SECOND, 0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.FRANCE);
        return dateFormat.format(lastTwoWeek(date));
    }


    public boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public boolean isArticleImgsDownloaded(Context context) {
        boolean result = false;

        File file ;
        File fileHidden ;

        if (android.os.Build.VERSION.SDK_INT >= 30){
            //file = new File(context.getExternalFilesDir("").getAbsolutePath(), "/SccArticleImgs/Ar_Photos.zip");
            file = new File(context.getExternalFilesDir("").getAbsolutePath(), "SccArticleImgs");
            fileHidden = new File(context.getExternalFilesDir("").getAbsolutePath(), ".SccArticleImgs");
        } else{
            file = new File(Environment.getExternalStorageDirectory(), "SccArticleImgs");
            fileHidden = new File(Environment.getExternalStorageDirectory(), ".SccArticleImgs");
        }

        if (!file.exists()) {
            if (!fileHidden.exists()) {
                if (!file.mkdirs()) {
                    Log.e("downloadRarX :: ", "Problem creating Image folder");
                    result = false;
                }

            }

        }
        Log.e("downloadRarX :: ", "creating Image folder:" + file.exists());
        if (!file.exists()) {
            if (fileHidden.exists()) {
                //if (fileHidden.listFiles().length > 0) {
                result = true;
                //}
            }
        }


        return result;
    }

    public boolean deleteFile() {
        File f = new File(Environment.getExternalStorageDirectory() + "/ArticleImgs/Multimidea.zip");
        return f.delete();
    }

    public void fileListNames(File[] fileList) {

    }

    public void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        fileOrDirectory.delete();
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static <K, V extends Comparable<? super V>> List<Map.Entry<K, V>> findGreatest(Map<K, V> map, int n) {
        Comparator<? super Map.Entry<K, V>> comparator =
                new Comparator<Map.Entry<K, V>>() {
                    @Override
                    public int compare(Map.Entry<K, V> e0, Map.Entry<K, V> e1) {
                        V v0 = e0.getValue();
                        V v1 = e1.getValue();
                        return v0.compareTo(v1);
                    }
                };
        PriorityQueue<Map.Entry<K, V>> highest =
                new PriorityQueue<Map.Entry<K, V>>(n, comparator);
        for (Map.Entry<K, V> entry : map.entrySet()) {
            highest.offer(entry);
            while (highest.size() > n) {
                highest.poll();
            }
        }

        List<Map.Entry<K, V>> result = new ArrayList<Map.Entry<K, V>>();
        while (highest.size() > 0) {
            result.add(highest.poll());
        }
        return result;
    }

    public int randomColor(int alpha) {

        int r = (int) (0xf * Math.random());
        int g = (int) (0xff * Math.random());
        int b = (int) (0xfff * Math.random());

        return Color.argb(alpha, r, g, b);
    }

    public int[] convertIntegers(List<Integer> integers) {
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();
        for (int i = 0; i < ret.length; i++) {
            ret[i] = iterator.next().intValue();
        }
        return ret;
    }


    public String getMonth(long month) {
        int mois = Integer.parseInt(String.valueOf(month));
        return new DateFormatSymbols().getMonths()[mois - 1];
    }

    public enum Nombrearabic {
        //nombre simple
        ZERO(0, "zero"),UN(1, "un"),DEUX(2, "deux"),TROIX(3, "trois"),
        QUATRE(4,"quatre"),CINQ(5, "cinq"),SIX(6, "six"),SEPT(7, "sept"),
        HUIT(8,"huit"),NEUF(9, "neuf"),DIX(10, "dix"),ONZE(11, "onze"),
        DOUZE(12, "douze"),TREIZE(13, "treize"),QUATORZE(14, "quatorze"),
        QUINZE(15, "quinze"),SEIZE(16, "seize"),DIXSEPT(17, "dixsept"),
        DIXHUIT(18, "dixhuit"),DIXNEUF(19, "dixneuf"),

        //de 20 a 99
        VINGT(20, 29, "vingt"),
        TRENTE(30, 39, "trente"),
        QUARANTE(40, 49, "quarante"),
        CINQUANTE(50, 59, "cinquante"),
        SOIXANTE(60, 69, "soixante"),
        SOIXANTEDIX (70, 79, "soixantedix"),
        QUATREVINGT(80, 89,"quatrevingt"),
        QUATREVINGTDIX(90, 99, "quatrevingtdix"),

        //de 10 a X milliard
        DIXAINE(10, 99),
        CENT(100, 999, "cent",DIXAINE),

        MILLE(1000, 999999,  "mille", CENT),
        MILLION(1000000,99999999, "million", MILLE),
        MILLIARD(1000000000, Long.MAX_VALUE,"milliard", MILLION),

        //enum de calcul
        CALCULATE(){
            protected String getValue(long value)throws Exception {
                if (value == 0) return ZERO.label;
                else return ((value < 0) ? "moins " : "")+ MILLIARD.getStringValue((Math.abs(value)));
            }

            public String getValue(double value, String separator, String Cts)throws Exception {
                if (value == 0) return ZERO.label+" "+separator;

                else{
                    StringBuilder sb = new StringBuilder();
                    sb.append((value < 0) ? "moins " : "");

                    String vstr = Double.toString(value);

                    // add by hatem m'hamed amine moslem of algeria
                    if (vstr.contains("E")){
                        // en cas de 10 milion et plus la valeur de vstr est 1.0E7
                        //cette valeur pose un problème est donne une erreur a l'execution
                        // car le caracteur E ne pas un nombre de plus
                        //le pont des milliard est interpret comme une virgule
                        //ca fause les calcule
                        // il faut rendre la valeur comme 10000000000.00
                        StringBuilder E = new StringBuilder();
                        E.append(vstr);
                        // en premier en copie la valeur après le caractère «  E »  jusque a la fin
                        // cette valeur determine la positon de la virgule
                        String ES = E.substring(E.indexOf("E")+1);
                        // en supprimé les  caractères depuis  «  E »  jusque a la fin
                        E.delete(E.indexOf("E"),E.length());
                        // en supprime la virgule des milliard que fause les calcule
                        E.deleteCharAt(E.indexOf(".")); // we have E equale to "10"
                        Integer EEE = 0;
                        EEE=EEE.valueOf(ES);
                        // dans le cas au les zéro en été enlevée en les insert si la longer  de  E est inférieure  au nombre des zéro
                        // cas de  E = "10"   avac  longer  = 2  est nombre avant zero EEE = 7  en ajout 6 zero en obtian 10000000
                        // une boucle de six fois 2,3,4,5,6,7
                        if (E.length()<= EEE){for (int i =E.length();i< EEE+1;i++){E.append("0");}}
                        E.insert(EEE+1, ".") ;
                        vstr = E.toString(); }
                    // fin de add by hatem

                    int indexOf = vstr.indexOf('.');

                    if(indexOf == -1){
                        sb.append(MILLIARD.getStringValue((long)(Math.abs(value))));
                        sb.append(" ");
                        sb.append(separator);
                    }else{
                        sb.append(MILLIARD.getStringValue(Long.parseLong(vstr.substring(0, indexOf))));
                        sb.append(" ");
                        sb.append(separator);
                        String floatting =vstr.substring(indexOf+1,(indexOf+3>=vstr.length())?vstr.length():indexOf+3) +(indexOf+3>vstr.length()?"0":"");



                        long v = Long.parseLong(floatting);
                        if(v!=0){
                            sb.append(" , ");
                            sb.append(CENT.getStringValue(v));
                            sb.append(" ");
                            sb.append(Cts);

                        }
                    }
                    return sb.toString();
                }
            }

        };

        protected long min, max;
        protected String label;
        protected Nombrearabic before;
        // valeur � ajout � la fin d'un nombre entier
        private String addMin;
        /* constructeurs*/
        Nombrearabic() {
        }

        Nombrearabic(long v, String s) {
            this(v, v, s);
        }

        Nombrearabic(long min, long max) {
            this.min = min;
            this.max = max;
        }

        Nombrearabic(long min, long max, String label, Nombrearabic before) {
            this(min, max, label);
            this.before = before;
        }

        Nombrearabic(long min, long max, String label,String addMin) {
            this(min, max, label);
            this.addMin = addMin;
        }

        Nombrearabic(long min, long max, String label) {
            this(min, max);
            this.label = label;
        }

        protected String getValue(long value)throws Exception{
            throw new Exception("Vous devez appeller la methode par l'enumeration Chiffre.CALCULATE");
        }

        public String getValue(double value, String separator, String Cts)throws Exception{
            throw new Exception("Vous devez appeller la methode par l'enumeration Chiffre.CALCULATE");
        }

        // fonction de transformation
        private String getStringValue(long value) {
            long v1 = value / this.min;
            if (v1 == 0 && before != null)return before.getStringValue(value);
            StringBuilder add = new StringBuilder();
            Nombrearabic[]values = Nombrearabic.values();
            if(value<20) return values[(int)value].label;
            for (int i = 0; i < values.length; i++) {
                Nombrearabic nombre = values[i];

                //si la valeur est inferieur a 100
                if (value < 100 && nombre.min <= value && nombre.max >= value) {
                    //cas des valeurs 20, 30, 40, etc...
                    if (value == nombre.min) return nombre.label+((nombre.addMin!=null)?nombre.addMin:"");
                    else{
                        StringBuilder sb = new StringBuilder();
                        //first chiffre
                        sb.append(((value - nombre.min > 0) ? DIXAINE.getStringValue(value - nombre.min) : ""));
                        sb.append("  ");
                        //second chiffre
                        sb.append(nombre.label);
                        return sb.toString();}
                    // fin si  la valeur est inferieur a 100 this work very good

                    // cas de valeur plus de 100
                } else if (nombre.min <= v1 && nombre.max >= v1 && value >= 100) {
                    //premiere partie du nombre
                    //100 et 1000 et 1000000 et 1000000000
                    if ((this.equals(MILLIARD) ||this.equals(MILLION) ||this.equals(MILLE) || this.equals(CENT))&& (Nombrearabic.UN.equals(nombre)|| Nombrearabic.DEUX.equals(nombre))){
                        add.append(label);
                        //200 et 2000 et 2000000 et 2000000000
                        /*if (this.equals(CENT)&&Nombrearabic.DEUX.equals(nombre)){ add.deleteCharAt(add.indexOf("ة"));}

                        add.append(((this.equals(CENT)&&Nombrearabic.DEUX.equals(nombre)) ? "ت" : ""));

                        add.append(((Nombrearabic.DEUX.equals(nombre)) ? "ين ": ""));*/

                    }

                    else{

                        add.append(nombre.getStringValue(v1));

                        //ajout du label si pr�sent
                        //if (this.equals(CENT)) add.deleteCharAt(add.indexOf("ة"));
                        //if (this.equals(CENT)&&Nombrearabic.HUIT.equals(nombre)) add.deleteCharAt(add.indexOf("ي"));
                        add.append(((this.equals(CENT)&&label != null) ? "" : " "));

                        add.append(((label != null) ? label : ""));

                        if (this.equals(MILLE)&&(Nombrearabic.TROIX.equals(nombre)||Nombrearabic.QUATRE.equals(nombre)||Nombrearabic.CINQ.equals(nombre)||Nombrearabic.SIX.equals(nombre)||Nombrearabic.SEPT.equals(nombre)||Nombrearabic.HUIT.equals(nombre)||Nombrearabic.NEUF.equals(nombre)||Nombrearabic.DIX.equals(nombre)))add.insert(add.length()-1, " ");

                    }


                    //deuxi�me partie du nombre
                    add.append(((value - (v1 * this.min) > 0) ? ("  " + before.getStringValue(value - (v1 * this.min))): ""));
                    return add.toString();
                }
            }
            return add.toString();
        }
    }

}
