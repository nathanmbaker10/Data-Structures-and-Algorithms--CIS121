import java.util.*;


class Solution {
    public static int reverse(int x) {
        boolean negative = x < 0;
        if (negative) {
            x *= -1;
        }
        LinkedList<Integer> digitList = new LinkedList<Integer>();
        
        while (x > 0) {
            digitList.add(x % 10);
            x /= 10;
        }
        
        int currentDigitPlace = digitList.size() - 1;
        if (currentDigitPlace > 9) {
            return 0;
        }
        int output = 0;
        int currentDigit;
        int numberToAdd;
        int tenMultiplier;
        for (int i = 0; i < digitList.size(); i++) {
            currentDigit = digitList.get(i);
            tenMultiplier = (int) Math.pow(10, currentDigitPlace);
            
            if(!Solution.valid(currentDigit, tenMultiplier, output, negative)) {
                return 0;
            } else {
                numberToAdd = currentDigit * tenMultiplier;
                output += numberToAdd;
                currentDigitPlace--;    
            }
            
        }
        
        if(negative) {
            output *= -1;
        }
        
        return output;
        
    }
    
    public static boolean valid(int currentDigit, int tenMultiplier,
            int currentOutput, boolean negative) {
        
        int max = (int) Math.pow(2, 31);
        if (!negative) {
            max -= 1;
        }
        int threshHold = max - currentOutput;
        
        int numToAdd;
        if(threshHold / currentDigit < tenMultiplier) {
            return false;
        } else {
            numToAdd = currentDigit * tenMultiplier;
        }
        
        
        return numToAdd <= threshHold;
    }
   
    public static void main(String[] args) {
        System.out.println(reverse(1534236469));
    }
}
