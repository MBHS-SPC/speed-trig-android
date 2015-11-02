package edu.mbhs.speedtrig.util;

/**
 * Created by eyob-- on 3/31/15.
 * Class used to solve questions given by QuestionGenerator
 */
public class QuestionSolver {

    /**
     * Solves a simple trig question and returns a String answer
     * @param question question such as "cos(π/3)" using any of the 6 regular trig functions
     *                 or their inverse functions (such as "arccos")
     * @return a String of the condensed form of the answer such as "√3/2"
     */
    public static String solve(String question){
        if (question.substring(0,3).equals("arc")){
            // Inverse trig problems

            // Extract operation and operand
            String operation = question.substring(0, 6);
            String operand = question.substring(7, question.length() - 1);

            String correctAnswer = "";

            // hard-code check for answer
            if((operation.equals("arcsin"))) {

                if(operand.equals("0"))
                    correctAnswer = "0";

                if(operand.equals("1"))
                    correctAnswer = "π/2";

                if(operand.equals("-1"))
                    correctAnswer = "-π/2";

                if(operand.equals("1/2"))
                    correctAnswer = "π/6";

                if(operand.equals("-1/2"))
                    correctAnswer = "-π/6";

                if(operand.equals("√2/2"))
                    correctAnswer = "π/4";

                if(operand.equals("-√2/2"))
                    correctAnswer = "-π/4";

                if(operand.equals("√3/2"))
                    correctAnswer = "π/3";

                if(operand.equals("-√3/2"))
                    correctAnswer = "-π/3";
            }

            if((operation.equals("arccos"))) {

                if(operand.equals("0"))
                    correctAnswer = "π/2";

                if(operand.equals("1"))
                    correctAnswer = "0";

                if(operand.equals("-1"))
                    correctAnswer = "π";

                if(operand.equals("1/2"))
                    correctAnswer = "π/3";

                if(operand.equals("-1/2"))
                    correctAnswer = "2π/3";

                if(operand.equals("√2/2"))
                    correctAnswer = "π/4";

                if(operand.equals("-√2/2"))
                    correctAnswer = "3π/4";

                if(operand.equals("√3/2"))
                    correctAnswer = "π/6";

                if(operand.equals("-√3/2"))
                    correctAnswer = "5π/6";
            }

            if((operation.equals("arccsc"))) {

                if(operand.equals("1"))
                    correctAnswer = "π/2";

                if(operand.equals("-1"))
                    correctAnswer = "-π/2";

                if(operand.equals("2"))
                    correctAnswer = "π/6";

                if(operand.equals("-2"))
                    correctAnswer = "-π/6";

                if(operand.equals("√2"))
                    correctAnswer = "π/4";

                if(operand.equals("-√2"))
                    correctAnswer = "-π/4";

                if(operand.equals("(2√3)/3"))
                    correctAnswer = "π/3";

                if(operand.equals("-(2√3)/3"))
                    correctAnswer = "-π/3";
            }

            if((operation.equals("arcsec"))) {

                if(operand.equals("1"))
                    correctAnswer = "0";

                if(operand.equals("-1"))
                    correctAnswer = "π";

                if(operand.equals("2"))
                    correctAnswer = "π/3";

                if(operand.equals("-2"))
                    correctAnswer = "2π/3";

                if(operand.equals("√2"))
                    correctAnswer = "π/4";

                if(operand.equals("-√2"))
                    correctAnswer = "3π/4";

                if(operand.equals("(2√3)/3"))
                    correctAnswer = "π/6";

                if(operand.equals("-(2√3)/3"))
                    correctAnswer = "5π/6";
            }

            if((operation.equals("arctan"))) {

                if(operand.equals("0"))
                    correctAnswer = "0";

                if(operand.equals("1"))
                    correctAnswer = "π/4";

                if(operand.equals("-1"))
                    correctAnswer = "-π/4";

                if(operand.equals("√3"))
                    correctAnswer = "π/3";

                if(operand.equals("-√3"))
                    correctAnswer = "-π/3";

                if(operand.equals("√3/3"))
                    correctAnswer = "π/6";

                if(operand.equals("-√3/3"))
                    correctAnswer = "-π/6";
            }

            if((operation.equals("arccot"))) {

                if(operand.equals("0"))
                    correctAnswer = "π/2";

                if(operand.equals("1"))
                    correctAnswer = "π/4";

                if(operand.equals("-1"))
                    correctAnswer = "3π/4";

                if(operand.equals("√3"))
                    correctAnswer = "π/6";

                if(operand.equals("-√3"))
                    correctAnswer = "5π/6";

                if(operand.equals("√3/3"))
                    correctAnswer = "π/3";

                if(operand.equals("-√3/3"))
                    correctAnswer = "2π/3";
            }

            return  correctAnswer;
        }

        else {
            // Regular trig problem

            boolean flip = false;   // whether the operation is a reciprocal trig operation (sec, csc, cot)
            double operand;         // numerical value passed into the trig function
            double badAnswer = 0;   // raw value of answer (as opposed to String form)

            // example: operation="csc", strOperand="3π/4"
            String operation = question.substring(0,3);
            String strOperand = question.substring(4,question.length()-1);

            // identify reciprocal operations
            switch (operation.charAt(1)){
                case 'e':   //secant
                    operation = "cos";
                    flip = true;
                    break;
                case 's':   //cosecant
                    operation = "sin";
                    flip = true;
                    break;
                case 'o':   //cotangent
                    if (operation.charAt(2) == 's') break; // because cot and cos have the same second letter
                    operation = "tan";
                    flip = true;
                    break;
            }

            if (!strOperand.contains("π"))  // if it doesn't have a 'π', then it's 0
                operand = 0;
            else if (!strOperand.contains("/")){    // if it doesn't have a '/', then it's just k*Math.PI
                int number;     // k to be multiplied by Math.PI
                // if there's no k, 1 is implied
                if (strOperand.equals("π")) number = 1;
                else number = Integer.parseInt(strOperand.substring(0,strOperand.length()-1));
                operand = Math.PI * number;
            }
            else {  // Note: operator contains both a pi and a fraction ('/')
                int numerator;
                if (strOperand.substring(0,strOperand.indexOf('π')).isEmpty()) numerator = 1;
                else numerator = Integer.parseInt(strOperand.substring(0,strOperand.indexOf('π')));
                // simply operand = pi * num / denom
                operand = Math.PI * numerator /
                        Integer.parseInt(strOperand.substring(strOperand.indexOf('/')+1));
            }

            // retrieve the bad answer by identifying the operation and applying it
            // (reciprocal functions evaluated as the original function)
            switch (operation.charAt(0)){
                case 's'://sin
                    badAnswer = Math.sin(operand);
                    break;
                case 'c'://cos
                    badAnswer = Math.cos(operand);
                    break;
                case 't'://tan
                    badAnswer = Math.tan(operand);
                    break;
            }

            return getClosestAnswer(badAnswer, flip);
        }

    }

    public static String getClosestAnswer(double rawAnswer, boolean reciprocalFunction) {

        /*
         * Precision used to verify that a raw value is equivalent to a mathematical expression
         * such as 3.14159 == "π"
         */
        final double precision = 0.0001;

        String goodAnswer;

        // brute-force compare to known possible answers and build good answers
        // +- 1
        if (Math.abs(rawAnswer-1) < precision ||
                Math.abs(rawAnswer+1) < precision)
            goodAnswer = (Math.signum(rawAnswer)==1 ? "" : "-") + "1";
            // +- root 3 over 2
        else if (Math.abs(rawAnswer-Math.sqrt(3)/2) < precision ||
                Math.abs(rawAnswer+Math.sqrt(3)/2) < precision)
            goodAnswer = (Math.signum(rawAnswer)==1 ? "" : "-") + "√3/2";
            // +- root 2 over 2
        else if (Math.abs(rawAnswer-Math.sqrt(2)/2) < precision ||
                Math.abs(rawAnswer+Math.sqrt(2)/2) < precision)
            goodAnswer = (Math.signum(rawAnswer)==1 ? "" : "-") + "√2/2";
            // +- 1/2
        else if (Math.abs(rawAnswer-0.5) < precision ||
                Math.abs(rawAnswer+0.5) < precision)
            goodAnswer = (Math.signum(rawAnswer)==1 ? "" : "-") + "1/2";
            // 0
        else if (Math.abs(rawAnswer) < precision)
            goodAnswer = "0";
            // +- root 3 over 3
        else if (Math.abs(rawAnswer-Math.sqrt(3)/3) < precision ||
                Math.abs(rawAnswer+Math.sqrt(3)/3) < precision)
            goodAnswer = (Math.signum(rawAnswer)==1 ? "" : "-") + "√3/3";
            // +- root 3
        else if (Math.abs(rawAnswer-Math.sqrt(3)) < precision ||
                Math.abs(rawAnswer+Math.sqrt(3)) < precision)
            goodAnswer = (Math.signum(rawAnswer)==1 ? "" : "-") + "√3";
            // DNE = tan(pi/2)
        else if (rawAnswer-10 > 0 || rawAnswer + 10 < 0)
            goodAnswer = "DNE";
        else
            goodAnswer = "ERROR";

        // if it was a reciprocal function, reciprocate the answer
        if (reciprocalFunction)
            goodAnswer = flipFraction(goodAnswer);

        return goodAnswer;
    }

    /**
     * Returns the reciprocal of a fraction (only works for fractions given by solve())
     * @param frac any integer, rational fraction, or irrational fraction of the forms
     *             √a or √a/b in String form
     * @return the reciprocal of frac in String form
     */
    private static String flipFraction(String frac){
        String flipped = "";

        switch (frac) {
            case "0":
                return "DNE";
            case "DNE":
                return "0";
            case "-1":
            case "1":
                return frac;
        }

        if (frac.contains("/"))
            // what was in the denominator comes to the numerator
            flipped += frac.substring(frac.indexOf('/')+1);
        if (frac.contains("√")){
            char numUnderRoot = frac.charAt(frac.indexOf("√")+1);
            // reciprocal of √a is √a/a, so reciprocal of √a/b is b√a/a
            flipped += "√" + numUnderRoot + "/" + numUnderRoot;
        }

        // if the resulting fraction can be simplified
        if (flipped.charAt(0) == flipped.charAt(flipped.length()-1) && flipped.contains("/")) {
            // it's either going to be "3 root 3 over 3" or "2 root 2 over 2"
            if (flipped.charAt(2) == '3') flipped = "√3";
            else flipped = "√2";
        }

        // if the fraction was negative, make the reciprocal negative
        if (frac.charAt(0) == '-') flipped = "-" + flipped;

        return flipped;
    }

}
