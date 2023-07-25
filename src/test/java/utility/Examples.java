package utility;

public class Examples {

	public static void main(String args[]){
		Utility ep = new Utility();
		//char a = a;
		int charCount = getNumberOfAlphabetInString("Anushadiga@Gmail.com", 'a');
		System.out.println(charCount);
		String revString = reverseString("AnushAdiga@gmail.com");
		System.out.println(revString);
		System.out.println(ep.convertIntToString(556));
		System.out.println(ep.convertStringToInt("983845"));
		
	}
	
	public static int getNumberOfAlphabetInString(String expString, char test){
		char a[] = expString.toCharArray();
		int count =0;
		for(int i=0;i<a.length;i++){
			if(a[i]==test){
				count++;
			}
		}
		return count;
	}
	
	public static String reverseString(String inputString){
		char a[] = inputString.toCharArray();
		StringBuffer strBuf = new StringBuffer();
		for(int i=a.length-1;i>0;i--){
			strBuf.append(a[i]);
		}
		String outputString = strBuf.toString();
		return outputString;
	}
	
	
	
	
}
