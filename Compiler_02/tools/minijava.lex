%{
#include <string.h>
#include "util.h"
#include "tokens.h"
#include "errormsg.h"

extern int atoi(const char*);
%}

%start C S

letter		[A-Za-z]
digit		[0-9]
integer		{digit}+
ws			[\ \t]

%%

<INITIAL>\n				{ EM_newline(); continue; }
<INITIAL>{ws}			{ continue; }
<INITIAL>{integer}		{ yylval.ival = atoi(yytext); return INTLIT; }
<INITIAL>","			{ return COMMA; }
<INITIAL>";"			{ return SEMICOLON; }
<INITIAL>"("			{ return LPAREN; }
<INITIAL>")"            { return RPAREN; }
<INITIAL>"["            { return LBRACK; }
<INITIAL>"]"            { return RBRACK; }
<INITIAL>"{"            { return LBRACE; }
<INITIAL>"}"            { return RBRACE; }
<INITIAL>"."            { return DOT; }
<INITIAL>"+"            { return PLUS; }
<INITIAL>"-"            { return MINUS; }
<INITIAL>"*"            { return TIMES; }
<INITIAL>"/"            { return DIVIDE; }
<INITIAL>"=="			{ return EQ; }
<INITIAL>"!="           { return NEQ; }
<INITIAL>"<"			{ return LT; }
<INITIAL>"<="			{ return LE; }
<INITIAL>">"			{ return GT; }
<INITIAL>">="			{ return GE; }
<INITIAL>"&&"			{ return AND; }
<INITIAL>"||"			{ return OR; }
<INITIAL>"!"			{ return NOT; }
<INITIAL>"="			{ return ASSIGN; }
<INITIAL>boolean		{ return BOOLEAN; }
<INITIAL>class			{ return CLASS; }
<INITIAL>interface		{ return INTERFACE; }
<INITIAL>else			{ return ELSE; }
<INITIAL>extends		{ return EXTENDS; }
<INITIAL>false			{ return FALSEX; }
<INITIAL>if				{ return IF; }
<INITIAL>while			{ return WHILE; }
<INITIAL>int			{ return INTEGER; }
<INITIAL>length			{ return LENGTH; }
<INITIAL>main			{ return MAIN; }
<INITIAL>new			{ return NEW; }
<INITIAL>public			{ return PUBLIC; }
<INITIAL>return			{ return RETURN; }
<INITIAL>static			{ return STATIC; }
<INITIAL>String			{ return STRING; }
<INITIAL>this			{ return THIS; }
<INITIAL>true			{ return TRUEX; }
<INITIAL>System.out.println		{ return PRINT; }
<INITIAL>void			{ return VOID; }

<INITIAL>{letter}({letter}|{digit}|_)*	{ yylval.sval= String(yytext); return ID; }
<INITIAL>"/*"			{ BEGIN C; continue; }
<INITIAL>"//"[^\n]*\n		{ EM_newline(); continue; }

<INITIAL>\"				{ BEGIN S; clearbuf(); continue; }
<S>[^"\\\n]				{ putbuf(yytext[0]); continue; }
<S>\"					{ BEGIN INITIAL; yylval.sval = String(strbuf);
		  					return STRLIT;
						}
<S>\n					{ EM_error("Unclosed string"); 
		  					BEGIN INITIAL; continue;
						}
<S>\\t					{ putbuf('\\'); putbuf('t'); continue; }
<S>\\n					{ putbuf('\\'); putbuf('n'); continue; }
<S>\\\"					{ putbuf('\"'); continue; }
<S>\\\\					{ putbuf('\\'); continue; }
<S>\\					{ EM_error("Illegal string escape"); continue; }

<C>\n					{ EM_newline(); continue; }
<C>[^*/\n]				{ continue; }
<C>"/*"					{ continue; }
<C>"*/"					{ BEGIN INITIAL; continue; }
<C>[*/]					{ continue; }

.						{ EM_error("illegal token"); }

%%

