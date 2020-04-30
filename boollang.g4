grammar boollang;

/*
 * Lexer Rules
 */
fragment A : ('A' | 'a') ;
fragment B : ('B' | 'b') ;
fragment C : ('C' | 'c') ;
fragment D : ('D' | 'd') ;
fragment E : ('E' | 'e') ;
fragment F : ('F' | 'f') ;
fragment G : ('G' | 'g') ;
fragment H : ('H' | 'h') ;
fragment I : ('I' | 'i') ;
fragment J : ('J' | 'j') ;
fragment K : ('K' | 'k') ;
fragment L : ('L' | 'l') ;
fragment M : ('M' | 'm') ;
fragment N : ('N' | 'n') ;
fragment O : ('O' | 'o') ;
fragment P : ('P' | 'p') ;
fragment Q : ('Q' | 'q') ;
fragment R : ('R' | 'r') ;
fragment S : ('S' | 's') ;
fragment T : ('T' | 't') ;
fragment U : ('U' | 'u') ;
fragment V : ('V' | 'v') ;
fragment W : ('W' | 'w') ;
fragment X : ('X' | 'x') ;
fragment Y : ('Y' | 'y') ;
fragment Z : ('Z' | 'z') ;

TRUE   : T R U E ;
FALSE  : F A L S E ;

AND    : A N D ;
OR     : O R ;
NOT    : N O T ;
IF     : I F ;

ADD    : A D D ;
SUB    : S U B ;
NEG    : N E G ;
MULT   : M U L T ;
DIV    : D I V ;
GT     : G T ;
GTE    : G T E ;
LT     : L T ;
LTE    : L T E ;

LAMBDA : L A M B D A ;
CALL   : C A L L ;

EQ     : E Q ;
NEQ    : N E Q ;
LET    : L E T ;

fragment LETTER : ('a'..'z') | ('A'..'Z') ;
fragment DIGIT  : ('0'..'9') ;

VAR : LETTER+ ;
NUM : '-'? DIGIT+ ;

WHITESPACE : (' ' | '\n') -> skip ;

/*
 * Parser Rules
 */

sentence : exp ;

exp : (opp | op | val | var) ;

number : NUM ;
bool : (TRUE | FALSE) ;
val : (number | bool) ;
var : VAR ;

/* Boolean Operations */
ands : AND exp exp ;
ors  :  OR exp exp ;
nots : NOT exp ;

/* Numeric Operations */
adds  : ADD exp exp ;
subs  : SUB exp exp ;
negs  : NEG exp ;
mults : MULT exp exp ;
divs  : DIV exp exp ;
gts   : GT exp exp ;
gtes  : GTE exp exp ;
lts   : LT exp exp ;
ltes  : LTE exp exp ;

/* Function Operations */
lambdas : LAMBDA var+ exp exp ;
funcs   : CALL var exp* ;

/* Mixed Datatype Operations */
eqs  : EQ exp exp ;
neqs : NEQ exp exp ;
lets : LET var exp exp;
ifs  : IF exp exp exp ;

op : (ands | ors | nots | ifs |
      adds | subs | negs | mults | divs | gts | gtes | lts | ltes |
      eqs | neqs | lets |
      lambdas | funcs) ;
opp : '(' exp ')' ;