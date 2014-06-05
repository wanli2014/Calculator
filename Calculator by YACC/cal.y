%{
        #define YYSTYPE double 
        #include <math.h>
        #include <stdio.h>
        #include <ctype.h>
        int yylex (void);
        void yyerror (char const *);
%}

%token NUM        
%left '-' '+' 
%left '*' '/'     
%left NEG       
%right '^'      

%%
input:              
        | input line      
;
line:     '\n'       
        | exp '\n'  { printf ("\t%.10g\n", $1); }
;
exp:      NUM                { $$ = $1;         }
        | exp '+' exp        { $$ = $1 + $3;    }
        | exp '-' exp        { $$ = $1 - $3;    }
        | exp '*' exp        { $$ = $1 * $3;    }
        | exp '/' exp        { $$ = $1 / $3;    }
        | '-' exp  %prec NEG { $$ = -$2;        }
        | exp '^' exp        { $$ = pow ($1, $3); }
        | '(' exp ')'        { $$ = $2;         }
;
%%

int main (void)
{
        return yyparse ();
}

int yylex (void)
{
        int c;       
        while ((c = getchar ()) == ' ' || c == '\t');
        if (c == '.' || isdigit (c)) {
                ungetc (c, stdin);
                scanf ("%lf", &yylval);
                return NUM;
        }
        if (c == EOF) return 0;
        return c;
}

void yyerror (char const *s)
{
        fprintf (stderr, "%s\n", s);
}
