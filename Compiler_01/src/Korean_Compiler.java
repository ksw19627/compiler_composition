import java.util.LinkedList;
import java.util.Queue;

public class Korean_Compiler {
	// �ѱ��� �����Ϸ� Ŭ���� ����.

	enum Status { S, J, JM, JMJ, JMM, JMXJ };
	// �������� ���¸� ��Ÿ���� ���� enumeration. 
	final char J[] = {'r','s','e','f','a','q','t','d','w','c','z','x','v','g','R','E','Q','T','W'};
	// J = ������ ����. (������� keyboard ���� ��,��,��,��,��,��,��,��,��,��,��,��,��,��,��,��,��,��,��)
	final char M[] = {'k','i','j','u','h','y','n','b','m','l','o','p','O','P'};
	// M = ������ ����.  (������� keyboard ���Ǥ�,��,��,��,��,��,��,��,��,��,��,��,��,��)
	final char O[] = {' ', '1','2','3','4','5','6','7','8','9','0','+','-','/','[',']','<','>','!','?','.','*'};
	// O = Other�� ����.  (�ѱ۰� ������� ��Ÿ�� �ش�.)
	static int index;
	// Input String�� ���ʴ�� �о��ֱ� ���� index. 
	Queue<Character> Buffer;
	// JM, JMJ, JMXJ ������ �ϼ��� ������ �ѱ��� �������� �̸� �����ϴ� Buffer. 
	String Input;
	// Input�� �ޱ����� String �迭
	Status State;
	// enumeration���� ������ Status�� ������� ���� Compile���� character�� ���¸� ��Ÿ���� ���� State.
	
	Korean_Compiler()
	{
		Buffer = new LinkedList<Character>();	 // Buffer�� �ʱ�ȭ. 
		State = Status.S; // ���� ���¸� �⺻ ������ S�� �ʱ�ȭ.
		index=0; 			 // index�� 0���� �ʱ�ȭ.
	}
	
	public void Input(String Input)
	{
		this.Input= Input;		
		// �ܺηκ��� String�� �Է¹޴´�.
		System.out.println(Input);
		// �ùٸ��� �Է¹޾Ҵ��� Ȯ���ϱ����� ��¹�.
	}
	
	public void push(char ch)
	// Buffer�� ���� �������� character�� �����Ű�� ���� �޼ҵ�
	{
		Buffer.add(ch); 
	}
	
	public void out(int n)
	// Buffer�� ����� ������ �ѱ��� ��½�Ű�� ���� �޼ҵ� 
	{
		for(int i=0; i<n; i++)
		System.out.print(Buffer.poll());		
		// ����ϸ� Buffer���� ����. 
		System.out.print('|');
		// �� ������ ��踦 ǥ���ϱ� ���� '|' �� ���
	}
	
	public void out(char o)
	// �������� character�� O ���տ� �ش�� ���, �̸� �״�� ��½�Ű�� ���� �޼ҵ�
	{
		System.out.print(o);
		// �״�� ���.
	}
	
	public void Compile_end()
	//�Է¹��� String�� �ѱ� Compile�� ������, ���������� ����� ��� ȣ��Ǵ� �޼ҵ�.
	{
		for(int i=0; i<=Buffer.size(); i++)
			System.out.print(Buffer.poll());
		// ������ ������ Buffer�� ��� ���� ���, Buffer �����
		System.out.println('|'); 	// �� ������ ��踦 ǥ���ϱ� ���� '|' �� ���
		System.out.println("Compile Finished."); 
		// ������ ���Ḧ �˸� 
	}
	
	public char getchar()
	// Input String���κ��� ������ character�� 1���ھ� �ҷ����̱� ���� �޼ҵ�.
	{	
		char ch;		// �������� ch.
		ch=Input.charAt(index++); // index�� ���������ϸ� �� ���ھ� ��.
		return ch; // ch ��ȯ. 
	}

	public boolean IsIncludedJ(char ch)
	// �Ķ���ͷ� ���� char�� J(����) ���տ� ���ԵǾ��ִ°����� Ȯ���ϱ� ���� �޼ҵ� 
	{
		boolean result=false; 
		// false�� �ʱ�ȭ. 
		for(char cr : J) // J���տ� ���Ե� ��� char�� ���� 
		{
			if(cr== ch)
			result = true; // ���� �߽߰� true�� ����.
		}		
		return result;
		// �� ��ȯ.		
	}
	
	public boolean IsIncludedM(char ch)
	// �Ķ���ͷ� ���� char�� M(����) ���տ� ���ԵǾ��ִ°����� Ȯ���ϱ� ���� �޼ҵ� 
	{
		boolean result=false;
		// false�� �ʱ�ȭ. 
		for(char cr : M) // M���տ� ���Ե� ��� char�� ���� 
		{
			if(cr== ch)
			result = true; // ���� �߽߰� true�� ����.
		}		
		return result;
		// �� ��ȯ.
	}
	
	public boolean IsIncludedO(char ch)
	// �Ķ���ͷ� ���� char�� O(Other) ���տ� ���ԵǾ��ִ°����� Ȯ���ϱ� ���� �޼ҵ� 
	{
		boolean result=false;
		// false�� �ʱ�ȭ. 
		for(char cr : O) // O ���տ� ���Ե� ��� char�� ���� 
		{
			if(cr== ch)
			result = true; // Other �߽߰� true�� ����.
		}		
		return result;
		// �� ��ȯ.
	}
	
	public void error()
	// �������� �ѱ��� �������� �̷�� ���� �ʴ� ���. 
	// J, JM, JMJ, JMM, JMXJ�� ���� �̿��� �ٸ� ���°� �̷������ �� ��� ���Ǵ� �޼ҵ�
	{
		System.out.printf("Error occured, maybe This sentence will be wrong.");
		System.exit(0);
		// ��� ���ʿ��� ���� ���� ���α׷� ����.
	}
	
	public void execute() {
		// �������� Compile �޼ҵ�. 
		char ch;
		// ������ char ch ����. 
		while(index != Input.length())
		// index�� �Է¹��� String�� length�� ������ (�� String�� ��� character�� Ž�� �Ϸ��) ���� loop.
		{
			switch(State) 
			//State�� ���� ���� �б�.
			{
			case S:	
			// S�� ���.
				ch= getchar();	
				// ���� ����	
				if(IsIncludedJ(ch))
				// ����� ���ڰ� ������ ���
				{
					State= Status.J;
					// J���·� ��ȯ.
					push(ch);			
					// ����� ���ڴ� Buffer�� ����.
				}
				else if(IsIncludedO(ch))
				// ����� ���ڰ� Other ���տ� ���Ե� ���
				{					
					State = Status.S;
					// �ѱ��� �ƴϹǷ�, ���� ��ȯ����.
					out(ch);	
					// ���� �״�� ���. 					
				}			
				else
					// �̿��� ��� (�ٷ� ������ ������ ���)
					error();				
					// �ѱ۷� �̷���� �� ���� �������� error ȣ��
				
				break;
				// case S Ż��
			case J:				
			//J�� ���
				ch = getchar();
				// ���� ����.
				if(IsIncludedM(ch)){
				// ����� ���ڰ� ������ �ش�Ǵ� ���. 
					State = Status.JM;
					// JM (����+����) ���·� ��ȯ.
					push(ch);		
					// Buffer�� ����� ������ ����.
				}
				else
				// ��Ÿ ���
					error();
				// �ѱ۷� �̷���� �� ���� �������� error ȣ��
				break;				
				// case J Ż��
			case JM:	
				//JM�� ���
				ch= getchar();
				// ���� ����.
				if(IsIncludedJ(ch)){		
				// ����� ���ڰ� ������ �ش�Ǵ� ���. 
					State= Status.JMJ;			
					// JMJ (����+����+����) ���·� ��ȯ.
					push(ch); 		
					// Buffer�� ����� ������ ����.
				}
				else if(IsIncludedM(ch)){
					// ����� ���ڰ� ������ �ش�Ǵ� ���. 
					State = Status.JMM;
					// JMM (����+����+����) ���·� ��ȯ.
					push(ch); 					
					// Buffer�� ����� ������ ����.
				}
				else if(IsIncludedO(ch)){
					// ����� ���ڰ� Others�� �ش�Ǵ� ���. 
					State= Status.S;
					// S ���·� ��ȯ. (Buffer���� JM�� ����Ǿ�����)
					out(2); // JM���� ������ ���� ���
					out(ch);// ����� Other���� ���					
				}			
				else
				// ��Ÿ�� ���
				error();
				// �ѱ۷� �̷���� �� ���� �������� error ȣ��
				break;
				// case JM Ż��
			case JMM:		
				//JMM�� ���
				ch=getchar();
				// ���� ����.
				if(IsIncludedJ(ch)){
				// ����� ���ڰ� ������ �ش�Ǵ� ���. 
					State=Status.JMXJ;
					//  JMXJ (����+����+����+����) ���·� ��ȯ.
					push(ch); 
					// ����� ������ Buffer�� ����.
				}
				else if(IsIncludedO(ch)){
					// ����� ���ڰ� Others�� �ش�Ǵ� ���. 
					State= Status.S;
					//  S ���·� ��ȯ,
					out(3); // Buffer���� �ϼ��Ǿ��ִ� JMM���� ������ ���ڸ� ���
					out(ch);// ����� Other �������				
				}
				else
					// ��Ÿ ���
					error(); // �ѱ۷� �̷�� �� �� ���� �����̹Ƿ� ���� ȣ��
				
				break;
				// case JMM Ż��
			case JMJ:	
			// JMJ�� ���
				ch=getchar();				
				// ���� ����.
				if(IsIncludedJ(ch)){	
				// ����� ���ڰ� ������ �ش�Ǵ� ���. 
					State=Status.JMXJ;
					// JMXJ (����+����+����+����) ���·� ��ȯ.
					push(ch);
					// ����� ������ Buffer�� ����.
				}
				else if(IsIncludedM(ch)){
				// ����� ���ڰ� ������ �ش�Ǵ� ���. 
					State= Status.JM; 
					// JMJ ����+����+�������� M�� �߰��� JMJM�� �ϳ��� ���ڰ� ���� ���Ѵ�. 
					// ��, M�� �߰��ϸ� JM�� �ϳ��� �ѱ۷� �������� JM�� ���·� ���´�.
					out(2); 	// Buffer�� ����Ǿ��ִ� JM ���.
					push(ch);	// ����� ������ Buffer�� ����.				
				}
				else if(IsIncludedO(ch)){	
				// ����� ���ڰ� Others�� �ش�Ǵ� ���. 
					State= Status.S;		
					// JMJ�� �ϳ��� �ѱ۷� �����, O�� ���, S���·� ���´�.
					out(3);  // Buffer�� ����Ǿ��ִ� JMJ ���.
					out(ch); // ����� O�� ���.					
				}
				else
				// ��Ÿ ���
					error();
					// �ѱ۷� �̷�� ���� �����Ƿ� ������ ���. 
			
				break;		
				// case JMJ Ż��
			case JMXJ:		
			// JMXJ�� ���
				ch=getchar();
				// ���� ����.
				if(IsIncludedM(ch)){
				// ����� ���ڰ� ������ �ش�Ǵ� ���. 
					State= Status.JM;
					// JMXJ, �� JMMJ�� JMJJ�� ��쿡�� M�� �߰��Ǹ�, 
					// JMM + JM Ȥ�� JMJ+ JM�� �ȴ�. 
					out(3); 
					// ���� ������ �ǵ� Buffer������ ���ڿ� 3�� ���, J�� �����. 
					push(ch);					
					// M�� �߰��� ����, ���´� JM�̹Ƿ� ���� loop���� case JM���� �̵��Ͽ� ����Ѵ�. 
				}
				else if(IsIncludedJ(ch)){
				// ����� ���ڰ� ������ �ش�Ǵ� ���. 
					State = Status.J;
					// JMXJ, �� JMMJ�� JMJJ�� ��쿡�� J�� �߰��Ǹ�, 
					// JMMJ Ȥ�� JMJJ �� ��� �Ѵ� �ϳ��� �ѱ۷� ������ �����ϴ�. 
					out(4);
					// ���� ������ �ǵ� Buffer������ ���ڿ� 4���� ��� ���.
					push(ch);
					// J�� �߰��� ����, ���´� J�� �ǰ� ���� loop���� case J�� �̵��Ͽ� ��ӵȴ�.  
				}
				else if(IsIncludedO(ch)){
					// ����� ���ڰ� ��Ÿ�� �ش�Ǵ� ���. 
					State = Status.S;
					// JMXJ ���� �״�� ��� �� ��Ÿ ���ڵ� ��� ���, ���´� S�� �̵�.
					out(4);
					out(ch);
				}
				else
					// �̿��� ��� 
					error();
					// error() ��ȯ.
				break;
				// case JMXJ Ż��
							
			}
		}
		
		Compile_end();
		// ��� loop �� ������, ���������� Compile_end() �޼ҵ带 ȣ���Ѵ�.
		// �� �޼ҵ忡���� ��� loop�� ��ģ �Ŀ� ���� Buffer����  jmȤ�� jmj, jmm���� ���� ��� ��½�Ű�� ����� �ô´�. 		
	}
}


