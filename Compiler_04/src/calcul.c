/* Main routine of Calculator */

#include<stdio.h>

#include"calcul.h"
YYSTYPE yylval;

double store[26];
int loc;
int tok;

double term(void);
double uterm(void);
double expression(void);
double Func(void); //곱하기나누기함수
double Paren(void);
void statements(void);
void statement(void);

void yyerror(char* s) {
	printf("*** Error: %s\n", s);
}

void program(void) {
	statements();
	printf("END\n");
}

void statements(void) {
	statement();
	while (tok == SEMI || tok == NL) {
		tok = yylex();
		statement();
	}
	printf("tok = %d\n", tok);
}

void statement(void) { //문자에수를저장후출력
	if (tok == IDENT) {
		loc = yylval.id - 'a';
		tok = yylex();
		if (tok == ASSIGN) {
			tok = yylex();
			store[loc] = expression();
			printf("Var %c  = %f\n", loc + 'a', store[loc]);
		} else
			yyerror("ASSIGN expected");
	} else if (tok == PRINT) {
		tok = yylex();
		printf("%f\n", expression());
	}
	/*****
	 else
	 yyerror ("IDENT or PRINT expected");
	 *****/
}

double expression(void) { //연산을해주는함수
	double temp;
	temp = uterm();
	while (tok == PLUS || tok == MINUS || tok == TIMES || tok == DIVIDE) {
		if (tok == PLUS) {
			tok = yylex();
			temp = temp + Func();
		} else if (tok == MINUS) {
			tok = yylex();
			temp = temp - Func();
		} else if (tok == TIMES) {
			tok = yylex();
			temp = temp * Func();
		} else if (tok == DIVIDE) {
			tok = yylex();
			temp = temp / Func();
		}
	}
	return temp;
}

double Func(void) {
// T();
	double temp;
	temp = uterm();
	while (tok == TIMES || tok == DIVIDE) {
		if (tok == TIMES) {
			tok = yylex();
			temp *= term();
		} else if (tok == DIVIDE) {
			tok = yylex();
			temp /= term();
		}
	}
	return temp;
}

double Paren(void) {
// T();
	double temp;
	if (tok == LPAREN) {
		tok = yylex();
		temp = expression();
		if (tok == RPAREN){
			tok = yylex();
		}
		else
			printf("error: ( Expected. \n");
	}
	else
		printf("error: Invalid tokken. \n");

	return temp;
}

double uterm(void) {
	double temp;
	if (tok == MINUS) {
		tok = yylex();
		temp = -term();
	} else {
		temp = term();
	}
	return temp;
}

double term(void) {

	double temp;
	if (tok == NUM) {
		temp = yylval.num;
		tok = yylex();
		return temp;
	} else if (tok == IDENT) {
		temp = store[yylval.id - 'a'];
		tok = yylex();
		return temp;
	} else if (tok == LPAREN) {
		tok = yylex();
		Paren();
	} else {
		yyerror("NUM or IDENT expected");
		return 0;
	}
}

int main(void) {
	tok = yylex();
	program();
	return 0;
}
