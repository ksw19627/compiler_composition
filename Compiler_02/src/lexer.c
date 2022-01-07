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
// binarytree.java�� ������� ���� ������

char ch = -1; // ���� �о character�� default��
int state = 0; // ���� status 0���� default
string token; // �ܾ� ������ �о string token
char symbol;  // char�� �о� � ��ȣ���� �Ǻ��� char
enum {
	DIGIT, LETTER, SPECIAL, WHITESPACE
}; // ������� token�̳� symbol�� status�� enumeration
int charClass(char ch) {
	// charClass �޼ҵ�. �д� char�� �����̸� DIGIT, ���ĺ��̸� LETTER,
	//������ ��� WHITESPACE, �������� SPECIAL
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
	// ������ �м� �޼ҵ�
	clearbuf(); // ������ ������ clear.
	if (ch == -1)
		ch = getc(yyin);
	// char�� -1 (default)�� ���� ���Ϸκ��� ���ڸ� �о�´�.
	while (1) {
		switch (state) {
		case 0:
			// ������ �з��� ������� �ش�Ǵ°����� �Ǻ�
			if (charClass(ch) == DIGIT) {
				putbuf(ch);
				ch = getc(yyin);
				if (ch == EOF)
					// END OF FILE�� ��� �޼ҵ� ���� (return 0;)
					return 0;
				state = 1;
			} else if (charClass(ch) == LETTER) {
				putbuf(ch);
				ch = getc(yyin);
				if (ch == EOF)
						// END OF FILE�� ��� �޼ҵ� ���� (return 0;))
					return 0;
				state = 2;
			}
			// ���ڰ� SPECIAL���� �Ǻ��ϱ� ���� state=3 ����
			else if (charClass(ch) == SPECIAL) {
				putbuf(ch);
				ch = getc(yyin);
				if (ch == EOF)
					// END OF FILE�� ��� �޼ҵ� ���� (return 0;)
					return 0;
				state = 3;
			}
			// ���ڰ� ��� ���ؿ��� ������ ���� ���� state�� 99�� �����ȴ�.
			else {
				state = 99;
			}
			break;
		case 1:
			// ���ڰ� DIGIT�� �ش�Ǵ� ���
			if (charClass(ch) == DIGIT) {
				putbuf(ch);
				ch = getc(yyin);
				if (ch == EOF)
					// END OF FILE�� ��� �޼ҵ� ���� (return 0;)
					return 0;
				state = 1;
			} else {
				// ������ ���ڵ��� int�� ��ȯ�ϰ�,ival�� �Է� �ϰ�  INTLIT.
				yylval.ival = atoi(String(strbuf));
				state = 0;
				return INTLIT;
			}
			break;
		case 2:
			// ���ڰ� �����̰ų� �����ϰ�� ���� ID���� �Ǻ��ϴ� ���̽�
			token = (String(strbuf));
			//printf("%s   !!", token);
			// ���ڵ�� ������ ��ū�� ������ ���� ��� ��������� �Ǵ�
			// ������� ���, state�� 0�̵ǰ�, �ٽ� ���ڸ� �б� ����.
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
				// �ּ��� ���������� �Ǻ��ؾ� �ϹǷ� state = 5 ����
				//���� ���ߵ��� ���� �����Դϴ�..
				putbuf(ch);
				state = 0;
				return PRINT;
			} else if (strcmp(token, "void") == 0) {
				putbuf(ch);
				state = 0;
				return VOID;
			}

			// �������ʹ� ���� ���ǵ� ���� �ƴ� ����Դϴ�.
			// ���� ���ڳ� ���ڸ� �����Ͽ� �ٽ� �Ǻ��ϱ� ���� state=2 ����

			if (charClass(ch) == DIGIT || charClass(ch) == LETTER) {
				putbuf(ch);
				ch = getc(yyin);
				if (ch == EOF)
					// END OF FILE�� ��� �޼ҵ� ���� (return 0;)
					return 0;
				state = 2;
			} else {
				// �� ��� ������ ������ ���� token�̹Ƿ� ������ ID�� �Ǻ��˴ϴ�.
				yylval.sval = String(strbuf);
				// ���� buf�� ����� String (token�� �̸��� ����Ǿ�����)�� yylval.sval�� ����
				state = 0; // state�� 0�� ��
				return ID;  // ID ��ȯ�Ǿ� driver.c�� ���ο��� ID�� sval�� ��µ˴ϴ�.
			}
			break;

		case 3:
			symbol = String(strbuf)[0];
			// strbuf�� ����� �� ó�� index�� ���ڸ� ���� �Ǻ��մϴ�.
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

			// ���Ŀ� ������ symbol���� �Ѱ��� char�� �� �а��� �ٸ� ��ȣ�� �Ǻ��ɼ� �ִ� �͵��Դϴ�.
			} else if (symbol == '/') {
				// DIVIDE�̰ų� �ּ��� ���� �ִ� ��� state=4 ����
				state = 4;
			} else if (symbol == '=') {
				state = 4;
				// ASSIGN Ȥ�� EQ�� �ɼ� �ִ� ����̹Ƿ� state =4
			} else if (symbol == '!') {
				state = 4;
				// NOT Ȥ�� NEQ�� �ɼ� �ִ� ����̹Ƿ� state =4
			} else if (symbol == '<') {
				// LT�̰ų� LE�� ���� �ִ� ��� state=4 ����
				state = 4;
			} else if (symbol == '>') {
				// GT�̰ų� GE�� ���� �ִ� ��� state=4 ����
				state = 4;
			} else if (symbol == '&') {
				state = 4;
				// & (���ǵ��� ����) Ȥ�� AND�� �ɼ� �����Ƿ� state =4 �� ����
			} else if (symbol == '|') {
				state = 4;
				// | (���ǵ��� ����) Ȥ�� OR�� �ɼ� �����Ƿ� state =4 �� ����
			}

			else if (symbol == '"') {
				// ����� ���� String�� �����̹Ƿ� ���� �˱����� �ϴ� state=6�� ������.
				clearbuf();
				putbuf(ch);
				ch = getc(yyin);
				state = 6;
			}
			break;


		case 4:
			// case3�� ù��° �ɺ��� �ٸ� �ɺ��� ������ ���ο� �ǹ̸� ���� ���� �ִ� ����� SPECIAL�� ���� ó�� ���̽�
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
				// �ּ��� ���������� �Ǻ��ؾ� �ϹǷ�  ���� �˱����� �ϴ� state = 5 ����
				ch = getc(yyin);
				clearbuf();
				ch = getc(yyin);
				putbuf(ch);
				state = 5;
			} else if (strcmp(token, "//") == 0) {
				// �ּ��� ���������� �Ǻ��ؾ� �ϹǷ�  ���� �˱����� �ϴ� state = 7 ����
				ch = getc(yyin);
				clearbuf();
				ch = getc(yyin);
				putbuf(ch);
				state = 7;
			} else {
				// ���� ���ǿ� �������� �ʴ´ٸ� �ϳ��� �ɺ����� �ǹ̸�  ���� SPECIAL�̹Ƿ�
				// ch�� �ι�° �ɺ��� �ٽ� �Է��Ͽ� state�� �ʱ�ȭ��  ���Ӱ� �з��� �� �� �ֵ��� ����

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

				// �� ���, �ڿ� �����̳� �ǹ̾��� �ɺ��� ������ �����̹Ƿ� state = 99 ����
				state = 99;

			}
			break;

			// ���ڰ� �ּ��� ��츦 ó���ϴ� ���̽�
		case 5:
			token = (String(strbuf));
			if (strcmp(token, "*/") == 0) {
				// �ּ��� ���� ��� �ּ��� ���ڸ� ��� ����
				clearbuf();
				ch = getc(yyin);
				state = 0;
			} else {
				// ������ �ƴ϶�� ���ڸ� ��� �о� ���ۿ� �����ϸ�  �ּ�ó�� ����
				clearbuf();
				putbuf(ch);
				ch = getc(yyin);
				putbuf(ch);
			}
			break;

			// ���ڰ� ���ڿ��� ��츦 ó���ϴ� ���̽�
		case 6:
			token = (String(strbuf));

			if (ch == '"') {
				// "�� ������ STRING�� �ϼ���
				yylval.sval = String(strbuf);
				clearbuf();
				ch = getc(yyin);
				state = 0;
				return STRLIT;
			} else if (ch == '\\') {
				// \�ڿ��� ������ ��ū��
				putbuf(ch);
				ch = getc(yyin);
			}
			putbuf(ch);
			ch = getc(yyin);
			state = 6;
			break;

		case 7:
			if (ch == '\n') {
				// �ּ��� ���� ��� �ּ��� ���ڸ� ��� ����
				clearbuf();
				ch = getc(yyin);
				state = 0;
			} else {
				// ������ �ƴ϶�� ���ڸ� ��� �о� ���ۿ� �����ϸ�  �ּ�ó�� ����
				clearbuf();
				putbuf(ch);
				ch = getc(yyin);
				putbuf(ch);
			}
			break;
		case 99:
			// �̿��� ��� ��츦 ó���ϱ� ���� case
			if (charClass(ch) == WHITESPACE) {
				// ������ ��Ÿ���� char�� ���.
				ch = getc(yyin);
				if (ch == EOF)
					// END OF FILE�� ��� �޼ҵ� ���� (return 0;)
					return 0;
				state = 0;
			} else {
				int tok = ch;
				ch = getc(yyin);
				if (ch == EOF)
					// END OF FILE�� ��� �޼ҵ� ���� (return 0;)
					return 0;
				state = 0;
				return tok;
			}
			break;
		}
	}
}
