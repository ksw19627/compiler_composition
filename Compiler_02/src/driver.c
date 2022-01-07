/*
 * driver.c
 *
 *  Created on: 2010. 9. 1.
 *      Author: cskim
 */
#include <stdio.h>
#include "util.h"
#include "errormsg.h"
#include "tokens.h"

YYSTYPE yylval;

int yylex(); /* prototype for the lexing function */



string toknames[] = {
"ID", "STRLIT", "INTLIT", "COMMA", "SEMICOLON", "LPAREN",
"RPAREN", "LBRACK", "RBRACK", "LBRACE", "RBRACE", "DOT", "PLUS",
"MINUS", "TIMES", "DIVIDE", "EQ", "NEQ", "LT", "LE", "GT", "GE",
"AND", "OR", "NOT", "ASSIGN", "BOOLEAN", "CLASS", "INTERFACE", "ELSE", "EXTENDS",
"FALSE", "IF", "WHILE", "INTEGER", "LENGTH", "MAIN", "NEW", "PUBLIC", "RETURN",
"STATIC", "STRING", "THIS", "TRUE", "PRINT", "VOID"
};


string tokname(tok) {
  return tok<257 || tok>302 ? "BAD_TOKEN" : toknames[tok-257];
}

int main(int argc, char **argv)
{
   string fname; int tok;
   //if (argc!=2) {fprintf(stderr,"usage: a.out filename\n"); exit(1);}
   fname="binarytree.java";
   EM_open(fname);
   for(;;) {
      tok=yylex1();
      if (tok==0) break;
      switch(tok) {
      case ID: case STRLIT:
         printf("%s (%s)\n",tokname(tok),yylval.sval);
         break;
      case INTLIT:
         printf("%s (%d)\n",tokname(tok),yylval.ival);
         break;
      default:
         printf("%s \n",tokname(tok));
      }
   }
   return 0;
}

