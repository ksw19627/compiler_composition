/*
 * driver.c
 *
 *  Created on: 2010. 9. 1.
 *      Author: cskim
 */
#include <stdio.h>

#include "../src/errormsg.h"
#include "../src/tokens.h"
#include "../src/util.h"

YYSTYPE yylval;

int yylex(); /* prototype for the lexing function */



string toknames[] = {
"ID", "STRLIT", "INTLIT", "COMMA", "SEMICOLON", "LPAREN",
"RPAREN", "LBRACK", "RBRACK", "LBRACE", "RBRACE", "DOT", "PLUS",
"MINUS", "TIMES", "DIVIDE", "EQ", "NEQ", "LT", "LE", "GT", "GE",
"AND", "OR", "NOT", "ASSIGN", "BOOLEAN", "CLASS", "INTERFACE", "ELSE", "EXTENDS",
"FALSE", "IF", "WHILE", "INTEGER", "LENGTH", "MAIN", "NEW", "PUBLIC", "RETURN",
"STATIC", "STRING", "THIS", "TRUE", "PRINT", "VOID", "FLOATLIT", "DOTDOT"
};


string tokname(tok) {
  return tok<257 || tok>304 ? "BAD_TOKEN" : toknames[tok-257];
}

int main(int argc, char **argv)
{
   string fname; int tok;
   //if (argc!=2) {fprintf(stderr,"usage: a.out filename\n"); exit(1);}
   fname="test.txt";
   EM_open(fname);
   for(;;) {
      tok=yylex();
      if (tok==0) break;
      switch(tok) {
      case ID: case STRING:
               printf("%s (%s)\n",tokname(tok),yylval.sval);
               break;
      case INTLIT:
         printf("%s (%d)\n",tokname(tok),yylval.ival);
         break;
      case FLOATLIT:
    	  printf("%s (%f)\n",tokname(tok),yylval.fval);
    	  break;
      default:
         printf("%s \n",tokname(tok));
      }

   }
   return 0;
}

