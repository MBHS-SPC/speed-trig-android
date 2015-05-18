package edu.mbhs.speedtrig.util;

import java.util.Random;

/**
 * Created by eyob-- on 3/31/15.
 */
public class QuestionGenerator {

    public static String genRegular(){
        int denominator, numerator, random;
        String operation = "", question;

        random = (int)(Math.random()*10);
        if (random == 0) {
            // generate random number from 2*rand_denom to 20
            random = (int)(Math.random() * 5) + 1;
            denominator = random == 5 ? 6 : random;
            numerator = (int)(Math.random()*(21-2*denominator))+2*denominator;
            int num = numerator;
            numerator = simplify(num, denominator)[0];
            denominator = simplify(num, denominator)[1];
        }
        else {
            // generate nice unit circle value
            int rand = (int)(Math.random()*4);
            if (rand == 0) {
                // generate a pi/4 type value
                int hand = (int) (Math.random() * 4);
                hand = hand * 2 + 1;
                numerator = hand;
                denominator = 4;
            }
            else {
                // generate any other kind of value
                int hand = (int) (Math.random() * 12);
                numerator = simplify(hand, 6)[0];
                denominator = simplify(hand, 6)[1];
            }
        }

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

        question = operation + "(";
        if (numerator != 1) question += numerator;
        if (numerator != 0){
            question += "\u03C0";
            if (denominator != 1) question += "/" + denominator;
        }
        question += ")";

        return question;
    }

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

    public static String genCustom() {

        Random generator = new Random();

        boolean getRegularTrigQuestion = generator.nextBoolean();

        if (getRegularTrigQuestion) {
            return genRegular();
        } else {
            return genInverse();
        }
    }
    public static String genAngle() {



        int denominator, numerator, random;
        String question ="";

        random = (int)(Math.random()*10);
        if (random < 3) {
            // generate random number from 2*rand_denom to 20
            random = (int)(Math.random() * 5) + 1;
            denominator = random == 5 ? 6 : random;
            numerator = (int)(Math.random()*(21-2*denominator))+2*denominator;
            int num = numerator;
            numerator = simplify(num, denominator)[0];
            denominator = simplify(num, denominator)[1];
        }
        else {
            // generate nice unit circle value
            int rand = (int)(Math.random()*4);
            if (rand == 0) {
                // generate a pi/4 type value
                int hand = (int) (Math.random() * 4);
                hand = hand * 2 + 1;
                numerator = hand;
                denominator = 4;
            }
            else {
                // generate any other kind of value
                int hand = (int) (Math.random() * 12);
                numerator = simplify(hand, 6)[0];
                denominator = simplify(hand, 6)[1];
            }
        }
        if (numerator != 1) question += numerator;
        if (numerator != 0){
            question += "\u03C0";
            if (denominator != 1) question += "/" + denominator;}
        return question;
    }
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
