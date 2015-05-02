package edu.mbhs.speedtrig.util;

/**
 * Created by eyob-- on 3/31/15.
 */
public class QuestionSolver {

    private static double incompetenceDiminisher = 0.0001;

    public static String solve(String question){

        if (question.substring(0,3).equals("arc")){
            // I'm an inverse trig problem!!!

            String operation = question.substring(0, 6);
            String operand = question.substring(7, question.length() - 1);

            String correctAnswer = "";

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

            boolean flip = false;
            double operand;
            double badAnswer = 0;
            String goodAnswer = "";

            String operation = question.substring(0,3);
            String strOperand = question.substring(4,question.length()-1);

            // some mathing skills <-- yes that's a word
            //takes the character in second place
            switch (operation.charAt(1)){
                case 'e'://secant
                    operation = "cos";
                    flip = true;
                    break;
                case 's'://cosecant
                    operation = "sin";
                    flip = true;
                    break;
                case 'o'://cotangent
                    if (operation.charAt(2) == 's') break; // because cot and cos have the same second letter
                    operation = "tan";
                    flip = true;
                    break;
            }

            if (!strOperand.contains("π"))
                operand = 0;
            else if (!strOperand.contains("/")){
                int number;
                if (strOperand.equals("π")) number = 1;
                else number = Integer.parseInt(strOperand.substring(0,strOperand.length()-1));
                operand = Math.PI * number;
            }
            else {	// Note: operator contains both a pi and a fraction ('/')
                int numerator;
                if (strOperand.substring(0,strOperand.indexOf('π')).isEmpty()) numerator = 1;
                else numerator = Integer.parseInt(strOperand.substring(0,strOperand.indexOf('π')));
                operand = Math.PI * numerator /
                        Integer.parseInt(strOperand.substring(strOperand.indexOf('/')+1));
            }
            //GET THA FIRST CHAR
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
            // I'm sorry, lots of bad code... It's a chiseled unit circle
            // +- 1
            if (Math.abs(badAnswer-1) < incompetenceDiminisher ||
                    Math.abs(badAnswer+1) < incompetenceDiminisher)
                goodAnswer = (Math.signum(badAnswer)==1 ? "" : "-") + "1";
                // +- root 3 over 2
            else if (Math.abs(badAnswer-Math.sqrt(3)/2) < incompetenceDiminisher ||
                    Math.abs(badAnswer+Math.sqrt(3)/2) < incompetenceDiminisher)
                goodAnswer = (Math.signum(badAnswer)==1 ? "" : "-") + "√3/2";
                // +- root 2 over 2
            else if (Math.abs(badAnswer-Math.sqrt(2)/2) < incompetenceDiminisher ||
                    Math.abs(badAnswer+Math.sqrt(2)/2) < incompetenceDiminisher)
                goodAnswer = (Math.signum(badAnswer)==1 ? "" : "-") + "√2/2";
                // +- 1/2
            else if (Math.abs(badAnswer-0.5) < incompetenceDiminisher ||
                    Math.abs(badAnswer+0.5) < incompetenceDiminisher)
                goodAnswer = (Math.signum(badAnswer)==1 ? "" : "-") + "1/2";
                // 0
            else if (Math.abs(badAnswer) < incompetenceDiminisher)
                goodAnswer = "0";
                // +- root 3 over 3
            else if (Math.abs(badAnswer-Math.sqrt(3)/3) < incompetenceDiminisher ||
                    Math.abs(badAnswer+Math.sqrt(3)/3) < incompetenceDiminisher)
                goodAnswer = (Math.signum(badAnswer)==1 ? "" : "-") + "√3/3";
                // +- root 3
            else if (Math.abs(badAnswer-Math.sqrt(3)) < incompetenceDiminisher ||
                    Math.abs(badAnswer+Math.sqrt(3)) < incompetenceDiminisher)
                goodAnswer = (Math.signum(badAnswer)==1 ? "" : "-") + "√3";
                // DNE = tan(pi/2)
            else if (badAnswer-10 > 0)
                goodAnswer = "DNE";
            else
                goodAnswer = "Java is incompetent";

            if (flip)
                goodAnswer = flipFraction(goodAnswer);

            return goodAnswer;
        }

    }

    private static String flipFraction(String frac){
        String flipped = "";

        if (frac.equals("0"))
            return "DNE";
        else if (frac.equals("DNE"))
            return "0";
        else if (frac.equals("-1") || frac.equals("1"))
            return frac;

        if (frac.contains("/"))
            flipped += frac.substring(frac.indexOf('/')+1);
        if (frac.contains("√")){
            char coolChar = frac.charAt(frac.indexOf("√")+1);
            flipped += "√" + coolChar + "/" + coolChar;
        }

        if (flipped.charAt(0) == flipped.charAt(flipped.length()-1) && flipped.contains("/")){
            // it's either going to be "2 root 3 over 3" or "2 root 2 over 2"
            if (flipped.charAt(2) == 3) flipped = "√3";
            else flipped = "√2";
        }

        if (frac.charAt(0) == '-') flipped = "-" + flipped;

        return flipped;
    }

}
