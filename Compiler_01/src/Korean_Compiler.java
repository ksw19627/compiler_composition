import java.util.LinkedList;
import java.util.Queue;

public class Korean_Compiler {
	// 한국어 컴파일러 클래스 선언.

	enum Status { S, J, JM, JMJ, JMM, JMXJ };
	// 진행중인 상태를 나타내기 위한 enumeration. 
	final char J[] = {'r','s','e','f','a','q','t','d','w','c','z','x','v','g','R','E','Q','T','W'};
	// J = 자음의 집합. (순서대로 keyboard 상의 ㄱ,ㄴ,ㄷ,ㄹ,ㅁ,ㅂ,ㅅ,ㅇ,ㅈ,ㅊ,ㅋ,ㅌ,ㅍ,ㅎ,ㅃ,ㄸ,ㅉ,ㄸ,ㅆ)
	final char M[] = {'k','i','j','u','h','y','n','b','m','l','o','p','O','P'};
	// M = 모음의 집합.  (순서대로 keyboard 상의ㅏ,ㅑ,ㅓ,ㅕ,ㅗ,ㅛ,ㅜ,ㅠ,ㅡ,ㅣ,ㅐ,ㅔ,ㅒ,ㅖ)
	final char O[] = {' ', '1','2','3','4','5','6','7','8','9','0','+','-','/','[',']','<','>','!','?','.','*'};
	// O = Other의 집합.  (한글과 관계없는 기타에 해당.)
	static int index;
	// Input String을 차례대로 읽어주기 위한 index. 
	Queue<Character> Buffer;
	// JM, JMJ, JMXJ 에서의 완성된 형태의 한글을 얻어내기위해 미리 저장하는 Buffer. 
	String Input;
	// Input을 받기위한 String 배열
	Status State;
	// enumeration으로 선언한 Status를 기반으로 현재 Compile중인 character의 상태를 나타내는 변수 State.
	
	Korean_Compiler()
	{
		Buffer = new LinkedList<Character>();	 // Buffer를 초기화. 
		State = Status.S; // 현재 상태를 기본 상태인 S로 초기화.
		index=0; 			 // index를 0으로 초기화.
	}
	
	public void Input(String Input)
	{
		this.Input= Input;		
		// 외부로부터 String을 입력받는다.
		System.out.println(Input);
		// 올바르게 입력받았는지 확인하기위한 출력문.
	}
	
	public void push(char ch)
	// Buffer에 현재 조사중인 character를 저장시키기 위한 메소드
	{
		Buffer.add(ch); 
	}
	
	public void out(int n)
	// Buffer에 저장된 온전한 한글을 출력시키기 위한 메소드 
	{
		for(int i=0; i<n; i++)
		System.out.print(Buffer.poll());		
		// 출력하며 Buffer에서 뺀다. 
		System.out.print('|');
		// 한 글자의 경계를 표시하기 위한 '|' 를 출력
	}
	
	public void out(char o)
	// 조사중인 character가 O 집합에 해당된 경우, 이를 그대로 출력시키기 위한 메소드
	{
		System.out.print(o);
		// 그대로 출력.
	}
	
	public void Compile_end()
	//입력받은 String의 한글 Compile이 온전히, 정상적으로 종료된 경우 호출되는 메소드.
	{
		for(int i=0; i<=Buffer.size(); i++)
			System.out.print(Buffer.poll());
		// 여전히 남겨진 Buffer의 모든 값을 출력, Buffer 비워냄
		System.out.println('|'); 	// 한 글자의 경계를 표시하기 위한 '|' 를 출력
		System.out.println("Compile Finished."); 
		// 컴파일 종료를 알림 
	}
	
	public char getchar()
	// Input String으로부터 조사할 character를 1글자씩 불려들이기 위한 메소드.
	{	
		char ch;		// 지역변수 ch.
		ch=Input.charAt(index++); // index를 후위증가하며 한 문자씩 얻어냄.
		return ch; // ch 반환. 
	}

	public boolean IsIncludedJ(char ch)
	// 파라미터로 받은 char가 J(자음) 집합에 포함되어있는것인지 확인하기 위한 메소드 
	{
		boolean result=false; 
		// false로 초기화. 
		for(char cr : J) // J집합에 포함된 모든 char에 대해 
		{
			if(cr== ch)
			result = true; // 자음 발견시 true로 변경.
		}		
		return result;
		// 값 반환.		
	}
	
	public boolean IsIncludedM(char ch)
	// 파라미터로 받은 char가 M(모음) 집합에 포함되어있는것인지 확인하기 위한 메소드 
	{
		boolean result=false;
		// false로 초기화. 
		for(char cr : M) // M집합에 포함된 모든 char에 대해 
		{
			if(cr== ch)
			result = true; // 모음 발견시 true로 변경.
		}		
		return result;
		// 값 반환.
	}
	
	public boolean IsIncludedO(char ch)
	// 파라미터로 받은 char가 O(Other) 집합에 포함되어있는것인지 확인하기 위한 메소드 
	{
		boolean result=false;
		// false로 초기화. 
		for(char cr : O) // O 집합에 포함된 모든 char에 대해 
		{
			if(cr== ch)
			result = true; // Other 발견시 true로 변경.
		}		
		return result;
		// 값 반환.
	}
	
	public void error()
	// 정상적인 한글의 조합으로 이루어 지지 않는 경우. 
	// J, JM, JMJ, JMM, JMXJ의 형태 이외의 다른 형태가 이루어지게 된 경우 사용되는 메소드
	{
		System.out.printf("Error occured, maybe This sentence will be wrong.");
		System.exit(0);
		// 즉시 불필요한 루프 없이 프로그램 종료.
	}
	
	public void execute() {
		// 본격적인 Compile 메소드. 
		char ch;
		// 임의의 char ch 선언. 
		while(index != Input.length())
		// index가 입력받은 String의 length와 같을시 (즉 String의 모든 character를 탐색 완료시) 까지 loop.
		{
			switch(State) 
			//State에 따른 조건 분기.
			{
			case S:	
			// S의 경우.
				ch= getchar();	
				// 문자 추출	
				if(IsIncludedJ(ch))
				// 추출된 문자가 자음인 경우
				{
					State= Status.J;
					// J상태로 변환.
					push(ch);			
					// 추출된 문자는 Buffer로 삽입.
				}
				else if(IsIncludedO(ch))
				// 추출된 문자가 Other 집합에 포함된 경우
				{					
					State = Status.S;
					// 한글이 아니므로, 상태 변환없음.
					out(ch);	
					// 문자 그대로 출력. 					
				}			
				else
					// 이외의 경우 (바로 모음이 나오는 경우)
					error();				
					// 한글로 이루어질 수 없는 조합으로 error 호출
				
				break;
				// case S 탈출
			case J:				
			//J의 경우
				ch = getchar();
				// 문자 추출.
				if(IsIncludedM(ch)){
				// 추출된 문자가 모음에 해당되는 경우. 
					State = Status.JM;
					// JM (자음+모음) 상태로 변환.
					push(ch);		
					// Buffer에 추출된 모음을 삽입.
				}
				else
				// 기타 경우
					error();
				// 한글로 이루어질 수 없는 조합으로 error 호출
				break;				
				// case J 탈출
			case JM:	
				//JM의 경우
				ch= getchar();
				// 문자 추출.
				if(IsIncludedJ(ch)){		
				// 추출된 문자가 자음에 해당되는 경우. 
					State= Status.JMJ;			
					// JMJ (자음+모음+자음) 상태로 변환.
					push(ch); 		
					// Buffer에 추출된 자음을 삽입.
				}
				else if(IsIncludedM(ch)){
					// 추출된 문자가 모음에 해당되는 경우. 
					State = Status.JMM;
					// JMM (자음+모음+모음) 상태로 변환.
					push(ch); 					
					// Buffer에 추출된 모음을 삽입.
				}
				else if(IsIncludedO(ch)){
					// 추출된 문자가 Others에 해당되는 경우. 
					State= Status.S;
					// S 상태로 변환. (Buffer에는 JM이 저장되어있음)
					out(2); // JM으로 구성된 문자 출력
					out(ch);// 추출된 Other문자 출력					
				}			
				else
				// 기타의 경우
				error();
				// 한글로 이루어질 수 없는 조합으로 error 호출
				break;
				// case JM 탈출
			case JMM:		
				//JMM의 경우
				ch=getchar();
				// 문자 추출.
				if(IsIncludedJ(ch)){
				// 추출된 문자가 자음에 해당되는 경우. 
					State=Status.JMXJ;
					//  JMXJ (자음+모음+모음+자음) 상태로 변환.
					push(ch); 
					// 추출된 자음을 Buffer에 삽입.
				}
				else if(IsIncludedO(ch)){
					// 추출된 문자가 Others에 해당되는 경우. 
					State= Status.S;
					//  S 상태로 변환,
					out(3); // Buffer내에 완성되어있는 JMM으로 구성된 문자를 출력
					out(ch);// 추출된 Other 문자출력				
				}
				else
					// 기타 경우
					error(); // 한글로 이루어 질 수 없는 형태이므로 오류 호출
				
				break;
				// case JMM 탈출
			case JMJ:	
			// JMJ의 경우
				ch=getchar();				
				// 문자 추출.
				if(IsIncludedJ(ch)){	
				// 추출된 문자가 자음에 해당되는 경우. 
					State=Status.JMXJ;
					// JMXJ (자음+모음+자음+자음) 상태로 변환.
					push(ch);
					// 추출된 자음을 Buffer에 삽입.
				}
				else if(IsIncludedM(ch)){
				// 추출된 문자가 모음에 해당되는 경우. 
					State= Status.JM; 
					// JMJ 자음+모음+자음에서 M을 추가한 JMJM은 하나의 문자가 되지 못한다. 
					// 즉, M을 추가하면 JM은 하나의 한글로 떨어지고 JM의 상태로 남는다.
					out(2); 	// Buffer에 저장되어있는 JM 출력.
					push(ch);	// 추출된 모음을 Buffer에 삽입.				
				}
				else if(IsIncludedO(ch)){	
				// 추출된 문자가 Others에 해당되는 경우. 
					State= Status.S;		
					// JMJ를 하나의 한글로 만들고, O를 출력, S상태로 남는다.
					out(3);  // Buffer에 저장되어있는 JMJ 출력.
					out(ch); // 추출된 O를 출력.					
				}
				else
				// 기타 경우
					error();
					// 한글로 이루어 질수 없으므로 오류문 출력. 
			
				break;		
				// case JMJ 탈출
			case JMXJ:		
			// JMXJ의 경우
				ch=getchar();
				// 문자 추출.
				if(IsIncludedM(ch)){
				// 추출된 문자가 모음에 해당되는 경우. 
					State= Status.JM;
					// JMXJ, 즉 JMMJ나 JMJJ의 경우에서 M이 추가되면, 
					// JMM + JM 혹은 JMJ+ JM이 된다. 
					out(3); 
					// 따라서 무엇이 되든 Buffer내부의 문자열 3개 출력, J을 남긴다. 
					push(ch);					
					// M을 추가로 저장, 상태는 JM이므로 다음 loop에서 case JM으로 이동하여 계속한다. 
				}
				else if(IsIncludedJ(ch)){
				// 추출된 문자가 자음에 해당되는 경우. 
					State = Status.J;
					// JMXJ, 즉 JMMJ나 JMJJ의 경우에서 J가 추가되면, 
					// JMMJ 혹은 JMJJ 의 경우 둘다 하나의 한글로 조합이 가능하다. 
					out(4);
					// 따라서 무엇이 되든 Buffer내부의 문자열 4개를 모두 출력.
					push(ch);
					// J를 추가로 저장, 상태는 J가 되고 다음 loop에서 case J로 이동하여 계속된다.  
				}
				else if(IsIncludedO(ch)){
					// 추출된 문자가 기타에 해당되는 경우. 
					State = Status.S;
					// JMXJ 상태 그대로 출력 후 기타 문자도 모두 출력, 상태는 S로 이동.
					out(4);
					out(ch);
				}
				else
					// 이외의 경우 
					error();
					// error() 반환.
				break;
				// case JMXJ 탈출
							
			}
		}
		
		Compile_end();
		// 모든 loop 가 끝나면, 마지막으로 Compile_end() 메소드를 호출한다.
		// 이 메소드에서는 모든 loop을 마친 후에 남은 Buffer속의  jm혹은 jmj, jmm등의 값을 모두 출력시키는 기능을 맡는다. 		
	}
}


