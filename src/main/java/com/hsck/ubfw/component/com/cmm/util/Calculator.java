package com.hsck.ubfw.component.com.cmm.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;



/**
*
* $unibill.com.util.StringUtil.java
* 클래스 설명 :
*
* @author 홍길동
* @since 2014.11.03
* @version 1.0.0
* @see
*
* <pre>
* << 개정이력(Modification Information) >>
*
*  수정일		 		수정자			수정내용
*  -------    		--------    ---------------------------
*  2014.11.03		박현우		최초 생성
* </pre>
*/
public class Calculator {
	//연산자가 아닌 기호
	public static final String[] OPERATION0 = { "(", ")", "," };
    //수 한 개가 필요한 연산기호(수는 왼쪽에 배치)
	public static final String[] OPERATION1 = { "!" };
    //수 두 개가 필요한 연산기호(수는 양옆에 배치) - 왼쪽에서 오른쪽으로 계산한다.
    //예) 1 + 2 = 3, 6 / 3 = 2, 2 ^ 3 = 8..
	public static final String[] OPERATION2 = { "+", "-", "*", "/", "^", "%" };
    //수가 필요없는 문자 연산기호
	public static final String[] WORD_OPERATION1 = { "pi", "e" };
    //수 한 개가 필요한 문자 연산기호(괄호로 구분한다.)
	public static final String[] WORD_OPERATION2 = { "sin", "sinh", "asin", "cos", "cosh", "acos", "tan", "tanh", "atan",
            "sqrt", "exp", "abs", "log", "ceil", "floor", "round" };
    //수 두 개가 필요한 문자 연산기호(괄호, 콤마로 구분한다.)
	public static final String[] WORD_OPERATION3 = { "pow" };
    //나누기할 때 반올림 자릿수
	public static final int HARF_ROUND_UP = 6;

    /**
     * 내부에서 불리는 계산 함수
     * @param data 계산식
     * @return 결과 값
     */
    public static String calc(String data) {
        //계산 식 안의 빈칸을 없앤다.
        data = data.replace(" ", "");
        //토큰으로 구분,즉 구분되는 수, 구분을 모두 분할
        // 예) (10+2)*(3+4)의 경우는 (, 10, +, 2, ), *, (, 3, +, 4, )로 분할 된다.
        List<Object> tokenStack = makeTokens(data);
        List<String>  postFix = new ArrayList<>();
        // 후위 표기식으로 변환한다.
        // 예) (, 10, +, 2, ), *, (, 3, +, 4, )의 경우는 10 2 + 3 4 + * 로 변경
        postFix = convertPostOrder(tokenStack);
        // 후위 표기식 계산
        // List형식으로 tocken이 수가 나오면 스택, 연산자가 나오면 계산을 합니다.
        // 10 2 + 계산 12
        // 12 3 4 + 계산 12 7
        // 12 * 7 계산 84
        //
        return calcPostOrder(postFix);
    }
    
    /**
     * 후위표기식으로 변환 함수
     * @param tokenList 토큰 리스트
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static List<String> convertPostOrder( List<Object> infix){
    	String postfix = "";
    	List<String> postOrderList = new ArrayList<>();
		        Stack<String> operator = new Stack<String>();
		        String popped;

		        for (int i = 0; i < infix.size(); i++) {
		            String get = infix.get(i).toString();
		            if (!isOperator(get))
//		                postfix += get;
		            	postOrderList.add(get);

		            else if (get.equals(")"))
		                while (!(popped = operator.pop()).equals("("))
//		                    postfix += popped;
		            		postOrderList.add(popped);

		            else {
		                while (!operator.isEmpty() && !get.equals("(") && precedence(operator.peek()) >= precedence(get))
//		                    postfix += operator.pop();
		                	postOrderList.add(operator.pop());

		                operator.push(get);
		            }
		        }
		        // pop any remaining operator
		        while (!operator.isEmpty())
//		            postfix += operator.pop();
		        	postOrderList.add(operator.pop());
		        
		return postOrderList;
    }

    /**
     * 후위 표기식 계산
     * @param calcStack 스택에 담겨져 있는 값
     * @return
     */
    public static String calcPostOrder(List<String> calcStack) {
    	Stack<Float> numberStack = new Stack<Float>(); 
    	for (String exp : calcStack) { 
    		try { 
    			Float number = Float.parseFloat(exp); 
    			numberStack.push(number); 
    		} catch (NumberFormatException e) { 
    			Float num1 = numberStack.pop(); 
    			Float num2 = numberStack.pop(); 
    			//System.out.print("\n" + num2 + exp + num1); 
    			
    			switch (exp) { 
    				case "+": 
    					numberStack.push(num2 + num1); break; 
    				case "-": 
    					numberStack.push(num2 - num1); break; 
    				case "*": 
    					numberStack.push(num2 * num1); break; 
    				case "/": 
    					numberStack.push(num2 / num1); break; 
    			} 
    		} 
    	} 

    	
    	
    	return numberStack.pop().toString();
    }
    

   

    /**
     * 연산자가 필요한 수의 개수
     * @param opcode 연산자
     * @return 연산자가 수를 1개 필요하면 true, 연산자가 수를 2개 필요하면 false
     */
    public static boolean opCodeCheck(String opcode) {
        return containWord(opcode, WORD_OPERATION2) || containWord(opcode, OPERATION1);
    }

    
    public static boolean isOperator(String i) {
        return precedence(i) > 0;
    }
    
    public static int precedence(String i) {
    	if (i.equals("(") || i.equals(")")) return 1;
        else if (i.equals("-") || i.equals("+")) return 2;
        else if (i.equals("*") || i.equals("/")) return 3;
        else return 0;
    }
    
    
    
   


    /**
     * 토큰 만드는 함수
     * @param inputData
     * @return
     */
    public static List< Object > makeTokens(String inputData) {
        List< Object > tokenStack = new ArrayList<>();
        StringBuffer numberTokenBuffer = new StringBuffer();
        StringBuffer wordTokenBuffer = new StringBuffer();
        int argSize = inputData.length();
        char token;
        for (int i = 0; i < argSize; i++) {
            //char형식으로 분할
            token = inputData.charAt(i);
            //수 토큰
            if (!isOperation(token)) {
                //문자열이 있으면 넣는다.
                setWordOperation(tokenStack, wordTokenBuffer);
                numberTokenBuffer.append(token);
                if (i == argSize - 1) {
                    setNumber(tokenStack, numberTokenBuffer);
                }
            } else {
                //연산자면 기존의 수를 입력
                setNumber(tokenStack, numberTokenBuffer);
                if (setOperation(tokenStack, token)) {
                    continue;
                }
                //기호 연산자가 아니면 문자 연산자
                wordTokenBuffer.append(token);
                setWordOperation(tokenStack, wordTokenBuffer);
            }
        }
        return tokenStack;
    }

    /**
     * 기호 연산자 입력 
     * @param tokenStack
     * @param token
     * @return
     */
    public static boolean setOperation(List< Object > tokenStack, char token) {
        String tokenBuffer = Character.toString(token);
        if (containWord(tokenBuffer, OPERATION2) || containWord(tokenBuffer, OPERATION1)
                || containWord(tokenBuffer, OPERATION0)) {
            tokenStack.add(tokenBuffer);
            return true;
        }
        return false;
    }

    /**
     * 문자 연산자 입력
     * @param tokenStack
     * @param tokenBuffer
     */
    public static void setWordOperation(List< Object > tokenStack, StringBuffer tokenBuffer) {
        if (isWordOperation(tokenBuffer)) {
            tokenStack.add(tokenBuffer.toString());
            tokenBuffer.setLength(0);
        }
    }

    /**
     * 숫자 입력
     * @param tokenStack
     * @param tokenBuffer
     */
    public static void setNumber(List< Object > tokenStack, StringBuffer tokenBuffer) {
        if (tokenBuffer.length() > 0) {
            BigDecimal number = new BigDecimal(tokenBuffer.toString());
            tokenStack.add(number);
            tokenBuffer.setLength(0);
        }
    }

    /**
     * 연산자 체크 함수
     * @param token
     * @param check
     * @return
     */

    public static boolean containWord(String token, String[] check) {
        if (token == null) {
            return false;
        }
        for (String word : check) {
            if (word.equals(token)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 글자 연산자 여부 체크
     * @param wordTokenBuffer
     * @return
     */
    public static boolean isWordOperation(StringBuffer wordTokenBuffer) {
        String wordToken = wordTokenBuffer.toString();
        return isWordOperation(wordToken);
    }

    /**
     * 글자 연산자 여부 체크
     * @param wordTokenBuffer
     * @return
     */
    public static boolean isWordOperation(String wordToken) {
        return containWord(wordToken, WORD_OPERATION3) || containWord(wordToken, WORD_OPERATION2)
                || containWord(wordToken, WORD_OPERATION1);
    }

     /**
     * 기호 연산자인지 체크
     * @param token
     * @return
     */
    public static boolean isOperation(String token) {
        return containWord(token, OPERATION2) || containWord(token, OPERATION1);
    }

    /**
     * 기호 연산자인지 체크
     * @param token
     * @return
     */
    public static boolean isOperation(char token) {
        if ((token >= 48 && token <= 57) || token == 46) {
            return false;
        } else {
            return true;
        }
    }

    
    public static String fullnumber(String keyno){
    	String result = "";
    	String checkkeyno = keyno.replace(" ", "").replace("-", "");
    	boolean checkkeynoFlag = true;
    	try {
    		Long.valueOf(checkkeyno);
    	} catch (Exception e) {
			checkkeynoFlag = false;
		}
    	if(keyno != null && checkkeynoFlag){
    		int count = StringUtils.countMatches(keyno,"-");
    		
    		if(count == 2){
    			List<String> keyList = new ArrayList<String>(Arrays.asList(keyno.split("-"))) ;
    			for(int i=0; i<keyList.size(); i++){
    				result = result + StringUtils.leftPad(keyList.get(i), 4, "0");
    			}
    			
    		}else{
    			// 공백, - 제거
        		keyno = keyno.replace(" ", "");
        		List<String> keyList = new ArrayList<>();
        		if(keyno.length() > 8){
        			if(keyno.substring(0,2).equals("02")){
        				keyList.add(StringUtils.leftPad(keyno.substring(0,2), 4, "0") );
        				keyList.add(StringUtils.leftPad(keyno.substring(2, 2 + keyno.length()-6), 4, "0") );
        				keyList.add(keyno.substring(keyno.length()-4, keyno.length()) );
        			}else{
        				keyList.add(StringUtils.leftPad(keyno.substring(0,3), 4, "0") );
        				keyList.add(StringUtils.leftPad(keyno.substring(3, 3 + keyno.length()-7), 4, "0") );
        				keyList.add(keyno.substring(keyno.length()-4, keyno.length()) );
        			}
        			
        			for(int i=0; i<keyList.size(); i++){
        				result = result + keyList.get(i);
        			}

        		}else{
        			result = StringUtils.leftPad(keyno, 12, "0");
        		}
    		}
    		
    	}else{
    		result = keyno;
    	}
    	
    	
    	return result;
    }

    
    public static String costCnv(String fcost){
    	String result = "";
    	
    	
    	return result;
    }

}
