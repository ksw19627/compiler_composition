class Main {
	// Main class.
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Korean_Compiler Kor = new Korean_Compiler(); 
		// 한국어 컴파일러 선언. 
		Kor.Input("gksrnrdhlrnrdjeogkrry zjavbxjrhdgkrrhk <rlatldnjs> zjavkdlffjsms woaldlTsms rhkahrdlek! gnqodprp Rhr tnrkdgkfkrh godirpTek");
		// Input String = "한국외국어대학교 컴퓨터공학과 <김시원> 컴파일러는 재미있는 과목이다! 후배들에게 꼭 수강하라고 해야겠다."
		Kor.execute();
		// 컴파일러 실행
		
		
	}

}
