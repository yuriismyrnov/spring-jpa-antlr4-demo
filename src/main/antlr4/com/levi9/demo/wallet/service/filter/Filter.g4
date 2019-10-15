grammar Filter;

parse
 : predicate EOF
 ;

predicate
 : NOT predicate                                                                #NotPredicate
 | OPAR predicate CPAR                                                          #ParenthesesPredicate
 | any_expression operation=comparison_operation value                          #ComparisonPredicate
 | path_expression operation=nullness_operation                                 #NullnessPredicate
 | predicate AND predicate                                                      #AndPredicate
 | predicate OR predicate                                                       #OrPredicate
 ;

comparison_operation
 : EQ
 | NE
 | LE
 | GE
 | LT
 | GT
 ;

nullness_operation
 : NULL
 | NOT_NULL
 ;

any_expression
 : path_expression
 | function_expression
 ;

function_expression
 : length_expression
 ;

length_expression
 : LENGTH OPAR path_expression CPAR
 ;

path_expression
 : PATH
 ;

value
 : STRING           #StringValue
 | DATE             #DateValue
 | DATETIME         #DateTimeValue
 | INTEGER          #IntegerValue
 | DECIMAL          #DecimalValue
 | (TRUE | FALSE)   #BooleanValue
 ;

OR : 'or';
AND : 'and';
EQ : 'eq';
NE : 'ne';
GT : 'gt';
LT : 'lt';
GE : 'ge';
LE : 'le';
NOT : 'not';

OPAR : '(';
CPAR : ')';

LENGTH : 'length';

TRUE : 'true';
FALSE : 'false';
NULL: 'is null';
NOT_NULL: 'is not null';

PATH
 : ([A-Za-z]+[A-Za-z0-9_\-]+)('.'[A-Za-z]+[A-Za-z0-9_\-]+)*
 ;

INTEGER
 : ([0-9]+)
 ;

DECIMAL
 : [0-9]+ ('.' [0-9]+)?
 ;

STRING
 : '\'' (~['])* '\''
 ;

DATE
 : ([0-9][0-9][0-9][0-9]'-'[0-9][0-9]'-'[0-9][0-9])
 ;

DATETIME
 : ([0-9][0-9][0-9][0-9]'-'[0-9][0-9]'-'[0-9][0-9]'T'[0-9][0-9]':'[0-9][0-9]':'[0-9][0-9]'Z')
 ;

SPACE
 : [ \t\r\n] -> skip
 ;
