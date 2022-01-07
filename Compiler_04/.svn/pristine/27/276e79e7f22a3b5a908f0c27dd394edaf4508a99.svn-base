
/* Main routine of Calculator */

#include	<stdio.h>

#include	"calcul.h"
YYSTYPE yylval;

double store [26];
int loc;
int tok;

double term (void);
double uterm(void);
double expression (void);
void statements (void);
void statement (void);
void yyerror (char* s) {
	printf ("*** Error: %s\n", s);
}
void program (void) {
	statements ();
	printf ("END\n");
}
void statements (void) {
	statement ();
	while (tok == SEMI || tok == NL) {
	   tok = yylex ();
	   statement ();
	}
	printf( "tok = %d\n", tok);
}
void statement (void) {
	if (tok == IDENT) {
	   loc = yylval.id - 'a';
	   tok = yylex();
	   if (tok == ASSIGN) {
	      tok = yylex();
	      store [loc] = expression();
	      printf ("Var %c  = %f\n", loc+'a', store[loc]);
	   }
	   else
	      yyerror ("ASSIGN expected");
	}
	else if (tok == PRINT) {
	   tok = yylex();
	   printf ("%f\n", expression ());
	}
/*****
	else
	   yyerror ("IDENT or PRINT expected");
*****/
}

double expression (void) {
	double temp;
	temp = uterm ();
	while (tok == PLUS) {
	   tok = yylex ();
	   temp = temp + term ();
	}
	return temp;
}
double uterm (void) {
	double temp;
	if (tok == MINUS){
		tok = yylex();
		temp = - term();
	}
	else {
		temp = term();
	}
	return temp;
}

double term (void) {
	double temp;
	if (tok == NUM) {
	   temp = yylval.num;
	   tok = yylex ();
	   return temp;
	}
	else if (tok == IDENT) {
	   temp = store[yylval.id - 'a'];
	   tok = yylex ();
	   return temp;
	}
	else {
	   yyerror ("NUM or IDENT expected");
	   return 0;
	}
}
int main (void)
{
	tok = yylex ();
	program ();
	return 0;
}
