/*
 * lexer.c
 *
 *  Created on: 2012. 9. 20.
 *      Author: cskim
 */
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdarg.h>
#include "util.h"
#include "errormsg.h"
#include "tokens.h"

FILE *yyin;
// binarytree.java를 입출력할 파일 포인터

char ch = -1; // 현재 읽어낼 character의 default값
int state = 0; // 현재 status 0으로 default
string token; // 단어 단위로 읽어낼 string token
char symbol;  // char를 읽어 어떤 기호인지 판별할 char
enum {
	DIGIT, LETTER, SPECIAL, WHITESPACE
}; // 순서대로 token이나 symbol의 status를 enumeration
int charClass(char ch) {
	// charClass 메소드. 읽는 char가 숫자이면 DIGIT, 알파벳이면 LETTER,
	//공백의 경우 WHITESPACE, 나머지는 SPECIAL
	if (ch >= '0' && ch <= '9')
		return DIGIT;
	else if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == '$'
			|| ch == '_')
		return LETTER;
	else if ((ch == ' ' || ch == '\t' || ch == '\n'))
		return WHITESPACE;
	else
		return SPECIAL;
}

int yylex1() {
	// 본격적 분석 메소드
	clearbuf(); // 버퍼의 내용을 clear.
	if (ch == -1)
		ch = getc(yyin);
	// char가 -1 (default)의 경우면 파일로부터 문자를 읽어온다.
	while (1) {
		switch (state) {
		case 0:
			// 문자의 분류상 어느곳에 해당되는곳인지 판별
			if (charClass(ch) == DIGIT) {
				putbuf(ch);
				ch = getc(yyin);
				if (ch == EOF)
					// END OF FILE의 경우 메소드 종료 (return 0;)
					return 0;
				state = 1;
			} else if (charClass(ch) == LETTER) {
				putbuf(ch);
				ch = getc(yyin);
				if (ch == EOF)
						// END OF FILE의 경우 메소드 종료 (return 0;))
					return 0;
				state = 2;
			}
			// 문자가 SPECIAL인지 판별하기 위한 state=3 설정
			else if (charClass(ch) == SPECIAL) {
				putbuf(ch);
				ch = getc(yyin);
				if (ch == EOF)
					// END OF FILE의 경우 메소드 종료 (return 0;)
					return 0;
				state = 3;
			}
			// 문자가 어떠한 기준에도 속하지 않은 경우는 state가 99로 설정된다.
			else {
				state = 99;
			}
			break;
		case 1:
			// 문자가 DIGIT에 해당되는 경우
			if (charClass(ch) == DIGIT) {
				putbuf(ch);
				ch = getc(yyin);
				if (ch == EOF)
					// END OF FILE의 경우 메소드 종료 (return 0;)
					return 0;
				state = 1;
			} else {
				// 이전의 숫자들을 int로 변환하고,ival에 입력 하고  INTLIT.
				yylval.ival = atoi(String(strbuf));
				state = 0;
				return INTLIT;
			}
			break;
		case 2:
			// 문자가 숫자이거나 문자일경우 예약어나 ID인지 판별하는 케이스
			token = (String(strbuf));
			//printf("%s   !!", token);
			// 문자들로 구성된 토큰이 공백을 만난 경우 예약어인지 판단
			// 예약어인 경우, state는 0이되고, 다시 문자를 읽기 시작.
			if (strcmp(token, "boolean") == 0) {
				putbuf(ch);
				state = 0;
				return BOOLEAN;
			} else if (strcmp(token, "class") == 0) {
				putbuf(ch);
				state = 0;
				return CLASS;
			} else if (strcmp(token, "interface") == 0) {
				putbuf(ch);
				state = 0;
				return INTERFACE;
			} else if (strcmp(token, "else") == 0) {
				putbuf(ch);
				state = 0;
				return ELSE;
			} else if (strcmp(token, "extends") == 0) {
				putbuf(ch);
				state = 0;
				return EXTENDS;
			} else if (strcmp(token, "false") == 0) {
				putbuf(ch);
				state = 0;
				return FALSEX;
			} else if (strcmp(token, "if") == 0) {
				putbuf(ch);
				state = 0;
				return IF;
			} else if (strcmp(token, "while") == 0) {
				putbuf(ch);
				state = 0;
				return WHILE;
			} else if (strcmp(token, "int") == 0) {
				putbuf(ch);
				state = 0;
				return INTEGER;
			} else if (strcmp(token, "length") == 0) {
				putbuf(ch);
				state = 0;
				return LENGTH;
			} else if (strcmp(token, "main") == 0) {
				putbuf(ch);
				state = 0;
				return MAIN;
			} else if (strcmp(token, "new") == 0) {
				putbuf(ch);
				state = 0;
				return NEW;
			} else if (strcmp(token, "public") == 0) {
				putbuf(ch);
				state = 0;
				return PUBLIC;
			} else if (strcmp(token, "return") == 0) {
				putbuf(ch);
				state = 0;
				return RETURN;
			} else if (strcmp(token, "static") == 0) {
				putbuf(ch);
				state = 0;
				return STATIC;
			} else if (strcmp(token, "String") == 0) {
				putbuf(ch);
				state = 0;
				return STRING;
			} else if (strcmp(token, "this") == 0) {
				putbuf(ch);
				state = 0;
				return THIS;
			} else if (strcmp(token, "true") == 0) {
				putbuf(ch);
				state = 0;
				return TRUEX;
			} else if (strcmp(token, "System.out.println") == 0) {
				// 주석이 끝날때까지 판별해야 하므로 state = 5 설정
				//아직 개발되지 못한 영역입니다..
				putbuf(ch);
				state = 0;
				return PRINT;
			} else if (strcmp(token, "void") == 0) {
				putbuf(ch);
				state = 0;
				return VOID;
			}

			// 다음부터는 위에 정의된 예약어가 아닌 경우입니다.
			// 뒤의 문자나 숫자를 포함하여 다시 판별하기 위해 state=2 설정

			if (charClass(ch) == DIGIT || charClass(ch) == LETTER) {
				putbuf(ch);
				ch = getc(yyin);
				if (ch == EOF)
					// END OF FILE의 경우 메소드 종료 (return 0;)
					return 0;
				state = 2;
			} else {
				// 그 어느 예약어에도 속하지 않은 token이므로 고유의 ID로 판별됩니다.
				yylval.sval = String(strbuf);
				// 현재 buf에 저장된 String (token의 이름이 저장되어있음)을 yylval.sval에 저장
				state = 0; // state가 0이 됨
				return ID;  // ID 반환되어 driver.c의 메인에서 ID와 sval이 출력됩니다.
			}
			break;

		case 3:
			symbol = String(strbuf)[0];
			// strbuf에 저장된 맨 처음 index의 문자를 통해 판별합니다.
			if (symbol == ',') {
				state = 0;
				return COMMA;
			} else if (symbol == ';') {
				state = 0;
				return SEMICOLON;
			} else if (symbol == '(') {
				state = 0;
				return LPAREN;
			} else if (symbol == ')') {
				state = 0;
				return RPAREN;
			} else if (symbol == '[') {
				state = 0;
				return LBRACK;
			} else if (symbol == ']') {
				state = 0;
				return RBRACK;
			} else if (symbol == '{') {
				state = 0;
				return LBRACE;
			} else if (symbol == '}') {
				state = 0;
				return RBRACE;
			} else if (symbol == '.') {
				state = 0;
				return DOT;
			} else if (symbol == '+') {
				state = 0;
				return PLUS;
			} else if (symbol == '-') {
				state = 0;
				return MINUS;
			} else if (symbol == '*') {
				state = 0;
				return TIMES;

			// 이후에 나오는 symbol들은 한개의 char를 더 읽고나서 다른 기호로 판별될수 있는 것들입니다.
			} else if (symbol == '/') {
				// DIVIDE이거나 주석일 수도 있는 경우 state=4 설정
				state = 4;
			} else if (symbol == '=') {
				state = 4;
				// ASSIGN 혹은 EQ가 될수 있는 경우이므로 state =4
			} else if (symbol == '!') {
				state = 4;
				// NOT 혹은 NEQ가 될수 있는 경우이므로 state =4
			} else if (symbol == '<') {
				// LT이거나 LE일 수도 있는 경우 state=4 설정
				state = 4;
			} else if (symbol == '>') {
				// GT이거나 GE일 수도 있는 경우 state=4 설정
				state = 4;
			} else if (symbol == '&') {
				state = 4;
				// & (정의되지 않음) 혹은 AND가 될수 있으므로 state =4 로 설정
			} else if (symbol == '|') {
				state = 4;
				// | (정의되지 않음) 혹은 OR가 될수 있으므로 state =4 로 설정
			}

			else if (symbol == '"') {
				// 출력을 위한 String의 시작이므로 끝을 알기위해 일단 state=6로 보낸다.
				clearbuf();
				putbuf(ch);
				ch = getc(yyin);
				state = 6;
			}
			break;


		case 4:
			// case3의 첫번째 심볼이 다른 심볼과 합쳐져 새로운 의미를 갖을 수도 있는 경우의 SPECIAL에 대한 처리 케이스
			putbuf(ch);
			token = (String(strbuf));

			if (strcmp(token, "<=") == 0) {
				putbuf(ch);
				ch = getc(yyin);
				state = 0;
				return LE;
			} else if (strcmp(token, "<>") == 0) {
				putbuf(ch);
				ch = getc(yyin);
				state = 0;
				return NEQ;
			} else if (strcmp(token, ">=") == 0) {
				putbuf(ch);
				ch = getc(yyin);
				state = 0;
				return GE;
			} else if (strcmp(token, "&&") == 0) {
				putbuf(ch);
				ch = getc(yyin);
				state = 0;
				return AND;
			} else if (strcmp(token, "||") == 0) {
				putbuf(ch);
				ch = getc(yyin);
				state = 0;
				return OR;
			} else if (strcmp(token, "==") == 0) {
				putbuf(ch);
				ch = getc(yyin);
				state = 0;
				return EQ;
			} else if (strcmp(token, "!=") == 0) {
				putbuf(ch);
				ch = getc(yyin);
				state = 0;
				return NEQ;
			} else if (strcmp(token, "/*") == 0) {
				// 주석이 끝날때까지 판별해야 하므로  끝을 알기위해 일단 state = 5 설정
				ch = getc(yyin);
				clearbuf();
				ch = getc(yyin);
				putbuf(ch);
				state = 5;
			} else if (strcmp(token, "//") == 0) {
				// 주석이 끝날때까지 판별해야 하므로  끝을 알기위해 일단 state = 7 설정
				ch = getc(yyin);
				clearbuf();
				ch = getc(yyin);
				putbuf(ch);
				state = 7;
			} else {
				// 위의 조건에 만족되지 않는다면 하나의 심볼만이 의미를  갖는 SPECIAL이므로
				// ch에 두번째 심볼을 다시 입력하여 state를 초기화해  새롭게 분류가 될 수 있도록 설정

				if (token[0] == '<') {
					putbuf(ch);
					ch = token[1];
					state = 0;
					return LT;
				} else if (token[0] == '>') {
					putbuf(ch);
					ch = token[1];
					state = 0;
					return GT;

				} else if (token[0] == '/') {
					putbuf(ch);
					ch = token[1];
					state = 0;
					return DIVIDE;
				} else if (token[0] == '=') {
					putbuf(ch);
					ch = token[1];
					state = 0;
					return ASSIGN;
				} else if (token[0] == '!') {
					putbuf(ch);
					ch = token[1];
					state = 0;
					return NOT;
				}

				// 이 경우, 뒤에 공백이나 의미없는 심볼이 나오기 마련이므로 state = 99 설정
				state = 99;

			}
			break;

			// 문자가 주석인 경우를 처리하는 케이스
		case 5:
			token = (String(strbuf));
			if (strcmp(token, "*/") == 0) {
				// 주석이 끝난 경우 주석인 문자를 모두 제거
				clearbuf();
				ch = getc(yyin);
				state = 0;
			} else {
				// 끝난게 아니라면 문자를 계속 읽어 버퍼에 저장하며  주석처리 진행
				clearbuf();
				putbuf(ch);
				ch = getc(yyin);
				putbuf(ch);
			}
			break;

			// 문자가 문자열인 경우를 처리하는 케이스
		case 6:
			token = (String(strbuf));

			if (ch == '"') {
				// "를 만나면 STRING이 완성됨
				yylval.sval = String(strbuf);
				clearbuf();
				ch = getc(yyin);
				state = 0;
				return STRLIT;
			} else if (ch == '\\') {
				// \뒤에는 무조건 토큰임
				putbuf(ch);
				ch = getc(yyin);
			}
			putbuf(ch);
			ch = getc(yyin);
			state = 6;
			break;

		case 7:
			if (ch == '\n') {
				// 주석이 끝난 경우 주석인 문자를 모두 제거
				clearbuf();
				ch = getc(yyin);
				state = 0;
			} else {
				// 끝난게 아니라면 문자를 계속 읽어 버퍼에 저장하며  주석처리 진행
				clearbuf();
				putbuf(ch);
				ch = getc(yyin);
				putbuf(ch);
			}
			break;
		case 99:
			// 이외의 모든 경우를 처리하기 위한 case
			if (charClass(ch) == WHITESPACE) {
				// 공백을 나타나는 char인 경우.
				ch = getc(yyin);
				if (ch == EOF)
					// END OF FILE의 경우 메소드 종료 (return 0;)
					return 0;
				state = 0;
			} else {
				int tok = ch;
				ch = getc(yyin);
				if (ch == EOF)
					// END OF FILE의 경우 메소드 종료 (return 0;)
					return 0;
				state = 0;
				return tok;
			}
			break;
		}
	}
}
