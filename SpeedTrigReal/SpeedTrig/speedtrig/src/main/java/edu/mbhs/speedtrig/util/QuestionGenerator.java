package edu.mbhs.speedtrig.util;

import java.util.Random;

/**
 * Created by eyob-- on 3/31/15.
 * Class used to generate questions for Speed Trig quizzes
 */
public class QuestionGenerator {

    /**
     * Generate a random regular trig question
     * @return a random question String. example: "cos(3π/2)"
     */
    public static String genRegular(){
        int denominator, numerator, random;
        String operation = "", question;

        random = (int)(Math.random()*10);   // 1/10 chance of getting a fraction >= 2*pi
        if (random == 0) {  // generate a "harder" problem: fraction >= 2*pi
            // generate a denominator of 1,2,3,4,or 6
            random = (int)(Math.random() * 5) + 1;
            denominator = random == 5 ? 6 : random;
            // generate a numerator from 2*denom to 20
            numerator = (int)(Math.random() * (21 - 2 * denominator)) + 2 * denominator;

            // simplify the fraction "numerator/denominator"
            int[] fraction = simplify(numerator, denominator);
            numerator = fraction[0];
            denominator = fraction[1];
        }
        else {  // generate "nice" unit circle value: 0 <= fraction < 2*pi
            int rand = (int)(Math.random()*4);
            if (rand == 0) {    // generate a pi/4 type value
                numerator = (int) (Math.random() * 4) * 2 + 1;
                denominator = 4;
            }
            else {  // generate any other kind of value (pi/3, pi/6, pi/2, pi, 0, ...)
                // if you take a number from 0 to 11 and divide it by 12, you'll get one of the
                // other unit circle values
                numerator = (int) (Math.random() * 12);
                int[] fraction = simplify(numerator, 6);
                numerator = fraction[0];
                denominator = fraction[1];
            }
        }

        // get an operation
        random = (int)(Math.random()*6);
        switch(random) {
            case 0:
                operation = "sin";
                break;
            case 1:
                operation = "cos";
                break;
            case 2:
                operation = "tan";
                break;
            case 3:
                operation = "csc";
                break;
            case 4:
                operation = "sec";
                break;
            case 5:
                operation = "cot";
                break;
        }

        //format the String question. example:  numerator=3, denominator=2, operation="cos"
        //                                      question="cos(3π/2)"
        question = operation + "(";
        if (numerator != 1) question += numerator;
        if (numerator != 0){
            question += "\u03C0";
            if (denominator != 1) question += "/" + denominator;
        }
        question += ")";

        return question;
    }

    /**
     * Generate a random inverse trig question or a regular trig question
     * @return a random question String. example: "arccos(1/2)"
     */
    public static String genInverse(){

        Random generator = new Random();

        boolean getRegularTrigQuestion = generator.nextBoolean();

        if(getRegularTrigQuestion){
            return genRegular();
        }

        else{

            int random;
            String operation = "", question = "";

            random = generator.nextInt(6);
            switch(random) {
                case 0:
                    operation = "arcsin";
                    break;
                case 1:
                    operation = "arccos";
                    break;
                case 2:
                    operation = "arctan";
                    break;
                case 3:
                    operation = "arccsc";
                    break;
                case 4:
                    operation = "arcsec";
                    break;
                case 5:
                    operation = "arccot";
                    break;
            }

            if((operation.equals("arcsin")) || (operation.equals("arccos"))) {
                random = generator.nextInt(5);
                switch (random) {
                    case 0:
                        question = "0";
                        break;
                    case 1:
                        if (generator.nextBoolean())
                            question = "1";
                        else
                            question = "-1";
                        break;
                    case 2:
                        if (generator.nextBoolean())
                            question = "1/2";
                        else
                            question = "-1/2";
                        break;
                    case 3:
                        if (generator.nextBoolean())
                            question = "√2/2";
                        else
                            question = "-√2/2";
                        break;
                    case 4:
                        if (generator.nextBoolean())
                            question = "√3/2";
                        else
                            question = "-√3/2";
                        break;
                }
            }

            if((operation.equals("arccsc")) || (operation.equals("arcsec"))){
                random = generator.nextInt(4);
                switch (random) {
                    case 0:
                        if (generator.nextBoolean())
                            question = "1";
                        else
                            question = "-1";
                        break;
                    case 1:
                        if (generator.nextBoolean())
                            question = "2";
                        else
                            question = "-2";
                        break;
                    case 2:
                        if (generator.nextBoolean())
                            question = "√2";
                        else
                            question = "-√2";
                        break;
                    case 3:
                        if (generator.nextBoolean())
                            question = "(2√3)/3";
                        else
                            question = "-(2√3)/3";
                        break;
                }
            }

            if((operation.equals("arctan")) || (operation.equals("arccot"))){
                random = generator.nextInt(4);
                switch (random) {
                    case 0:
                        question = "0";
                        break;
                    case 1:
                        if (generator.nextBoolean())
                            question = "1";
                        else
                            question = "-1";
                        break;
                    case 2:
                        if (generator.nextBoolean())
                            question = "√3";
                        else
                            question = "-√3";
                        break;
                    case 3:
                        if (generator.nextBoolean())
                            question = "√3/3";
                        else
                            question = "-√3/3";
                        break;
                }
            }

            question = operation + "(" + question + ")";

            return question;
        }
    }

    /**
     * Generate a random custom quiz question
     * @return a random question String
     */
    public static String genCustom() {

        Random generator = new Random();

        boolean getRegularTrigQuestion = generator.nextBoolean();

        if (getRegularTrigQuestion) {
            return genRegular();
        } else {
            return genInverse();
        }
    }

    /**
     * Simplifies a fraction
     * @param numer numerator of the fraction
     * @param denom denominator of the fraction
     * @return array of length 2 such that arr[0] is the simplified numerator and arr[1] is the
     * simplified denominator
     */
    private static int[] simplify (int numer, int denom){
        for (int i = Math.min(numer, denom); i > 1; i--){
            if ((double)numer/i == (numer/i) && (double)denom/i == (denom/i)){
                numer /= i;
                denom /= i;
            }
        }
        return new int[]{numer, denom};
    }

}
