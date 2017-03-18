/*******************************************/
/**    Your solution goes in this file    **/
/*******************************************/

/*******************************************/
/**    Part 1 facts   **/
/*******************************************/
course(m16a, [], 3).
course(m21c, [], 3).
course(m22a, [], 3).
course(ecs10, [], 4).
course(ecs15, [], 4).
course(ecs30, [m16a], 4).
course(ecs35, [m16a], 4).
course(ecs40, [ecs30], 4).
course(ecs50, [ecs40], 4).
course(ecs100, [m21c], 3).
course(ecs110, [ecs40], 4).
/*course(ecs120, [ecs100, ecs110], 3).*/
course(ecs122a, [ecs100, ecs110], 3).
course(ecs122b, [ecs122a], 3).
course(ecs140a, [ecs110], 4).
course(ecs140b, [ecs140a], 4).
course(ecs142, [ecs140a, ecs120], 4).
course(ecs150, [ecs40, ecs154a, ecs154b], 4).
course(ecs151a, [ecs154a], 4).
course(ecs151b, [ecs151a], 3).
course(ecs152, [ecs154a], 3).
course(ecs153, [ecs150], 3).
course(ecs154a, [ecs50, ecs110], 4).
course(ecs154b, [ecs154a, ecs110], 4).
course(ecs158, [ecs154b, ecs150], 3).
course(ecs160, [ecs140a], 4).
course(ecs163, [ecs100, ecs110], 3).
course(ecs165a, [ecs110], 4).
course(ecs165b, [ecs165a], 4).
course(ecs170, [ecs140a], 4).
course(ecs172, [ecs110, m22a], 4).

student(amber, [ecs10, ecs15, ecs35]).
student(brad, [ecs140a, ecs165a, ecs151a, ecs158]).
student(cindi, [ecs140b, ecs152, ecs170, ecs172]).
student(dan, [ecs35, ecs110, ecs50]).
student(john, [ecs140a, ecs150, ecs154a, ecs163]).
student(ian, [ecs154a, ecs160, ecs163, ecs165a, ecs170]).
student(karl, [ecs10, ecs50, ecs100, ecs110]).
student(lam, [ecs142, ecs150, ecs160, ecs170, ecs172]).
student(nancy, [ecs140a, ecs150, ecs163]).
student(pam, [ecs10, ecs15, ecs30, ecs35]).

instructor(adam, [ecs50, ecs100, ecs10, ecs154a]).
instructor(ben, [ecs50, ecs15, ecs110, ecs172]).
instructor(charles, [ecs30, ecs15, ecs140a, ecs170, ecs165b]).
instructor(cindi, [ecs140a, ecs152, ecs154a, ecs179]).
instructor(davis, [ecs40, ecs122a, ecs163, ecs165a]).
instructor(diane, [ecs150, ecs151a, ecs154b, ecs158, m21c, ecs140b,  ecs142]).
instructor(eugene, [ecs122b, ecs50, ecs140b, ecs153, ecs158, ecs160]).
instructor(fox, [ecs122b, ecs50, ecs140b, ecs153, ecs158, ecs160]).
instructor(george, [ecs142, ecs50, ecs172, ecs153, ecs100, ecs20]).
instructor(ian, [ecs50, ecs165a, ecs165b, ecs122b, ecs151b]).
instructor(jim, [ecs100, ecs110, ecs160, ecs163]).
/*******************************************/
/**    Part 1   **/
/*******************************************/
% fc_course(Course):-course(Course,_,(3;4)).
% fc_course(Course):- (Units = 3 ; Units = 4), course(Course,_,Units).
fc_course(Course):- course(Course,Prereq,Units),(Units =< 4 , Units >= 3).  % (4,3)
prereq_110(Course):-course(Course,Prereq,_),member(ecs110,Prereq).
ecs140a_students(Student):-student(Student,Course),member(ecs140a,Course).
% instructor_names(Instructor):-student(john,Scourse),instructor(Instructor,Icourse),member(X,Scourse),member(X,Icourse).
instructor_names(Instructor):- /* student(john,Scourse),*/instructor(Instructor,Icourse),instructorHelp(Instructor,Icourse).
instructorHelp(Instructor,Icourse) :- student(john,Scourse),member(X,Scourse),member(X,Icourse),!.
% ,member(X,Scourse),member(X,Icourse).
% students(Student):-instructor(jim,Icourse),student(Student,Scourse),member(X,Icourse),member(X,Scourse).
students(Student):- student(Student,Scourse),studentsHelp(Student,Scourse).
studentsHelp(Student,Scourse) :- instructor(jim,Icourse), member(X,Icourse),member(X,Scourse),!.
% allprereq(Course,All_Prereq):-course(Course,[],_),All_prereq=[].
allprereq(Course,All_Prereq):- findall(X, allpreHelp(X,Course), All_Prereq).
allpreHelp(X, Course):- course(X,Pre,Units), allpreHelp2(Course,X).
allpreHelp2(Course,All_Prereq) :- course(Course,Pre,Units),(member(All_Prereq,Pre);member(Temp,Pre),allpreHelp2(Temp,All_Prereq)).
/*******************************************/
/**    Part 2   **/
/*******************************************/
all_length(H,Len):- H = [], Len is 0. %when empty give len 0
all_length([H|T],Len) :- atom(H), all_length(T,RestLen), Len is RestLen + 1. % Head is an atom
all_length([[H|HT] |T], Len) :- all_length([H|HT],RestHeadLen),all_length(T,RestTailLen),Len is RestHeadLen + RestTailLen.%recursive call

% equal_a_b(L):- equalHelp(L,0,0).
% equalHelp(L,NumA,NumB):- L = [], NumA =:= NumB. %base case
% % equalHelp([H|T],NumA,NumB):- equalHelp2(H,T,NumA,NumB); equalHelp(T,NumA,NumB).
% % equalHelp2(a,T,NumA,NumB):- Tmp is NumA + 1, equalHelp(T,Tmp,NumB).% if is a
% % equalHelp2(b,T,NumA,NumB):- Tmp is NumB + 1, equalHelp(T,NumA,Tmp).% if is b
% equalHelp([H|T],NumA,NumB):- (H = a ; H = b) -> equalHelp2(H,T,NumA,NumB); equalHelp(T,NumA,NumB).
% equalHelp2(a,T,NumA,NumB):- Tmp is NumA + 1, equalHelp(T,Tmp,NumB).% if is a
% equalHelp2(b,T,NumA,NumB):- Tmp is NumB + 1, equalHelp(T,NumA,Tmp).% if is b
equal_a_b(L):- equalHelp(a,L,NumA),equalHelp(b,L,NumB),NumA = NumB.
%count A
equalHelp(a,[H|T],Num):- equalHelp(a,H,NumH),equalHelp(a,T,NumT),Num is NumH + NumT,!.
equalHelp(a,L,Len):- L = a -> Len is 1.
equalHelp(a,L,Len):- Len is 0.
%count B
equalHelp(b,[H|T],Num):- equalHelp(b,H,NumH),equalHelp(b,T,NumT),Num is NumH + NumT,!.
equalHelp(b,L,Len):- L = b -> Len is 1.
equalHelp(b,L,Len):- Len is 0.

swap_prefix_suffix(K,L,S):- appendHelp(Pre,K,Suf,L),appendHelp(Suf,K,Pre,S).%
appendHelp(Pre,K,Suf,L):- append(Pre,Tmp,L),append(K,Suf,Tmp).%

palin(A):-reverse(A,Reversed),A=Reversed.%if A=12321 then A' = 12321 = A.if A = 12 then A' = 21 =!=A
%http://eclipseclp.org/doc/bips/lib/lists/reverse-2.html

good(A):- goodHelp(A).
goodHelp([1|T]):- append(List1,List2,T),goodHelp(List1),goodHelp(List2).
goodHelp(A):- A = [0].

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% state(Farmer, Wolf, Goat, Cabbage).
% opposite(A,B).
% unsafe(A).
% safe(A).
% take(N,X,Y).
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% initial state: state(left, left, left, left).
% final state:   state(right,right,right,right).
% transition state
opposite(left,right).
opposite(right,left).
% take(Atom,OneSide,Other):-opposite(OneSide,Other).
take(Instance,Position,Other).

printPath(state(left,left,N,left)):- write('take(goat, '),write(N),write(', '),opposite(N,Y),write(Y),write(')'),nl.
%nothing r2l
go(A, B,Rec):-Rec =:= 1 -> printPath2(A), Rec is Rec + 1.

arc(take(none,left,right),state(left,_,_,_),state(right,_,_,_)):-take(none,left,right).
hist(N):- write('take(none, '), opposite(N,X), write(X),write(', ') ,write(N),write(')'),nl,  write('take(wolf, '),write(N),write(', '), write(X),write(')'), nl,
% arc(X,D),
write('take(goat, '), write(X), write(', '),write(N),write(')'),nl,
arcHis(X).
%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%% haven't figure out, gonna regrade about this%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%
arcHis(X):-
opposite(X,Oppo),
arc(Tmp,Var,State2,0),nl,
safe(X,left,right,left)-> arc(State3,Sate2,State0,1),nl,
\+ safe(left,right,right,X)-> printPath(state(left,left,Oppo,left)).
%wolfr2l
arc(take(wolf,left,right),state(left,left,_,_),state(right,right,_,_)):-take(wolf,left,right).
%goatr2l
arc(take(goat,left,right),state(left,_,left,_),state(right,_,right,_)):-take(goat,left,right).
%cabr2l
arc(take(cabbage,left,right),state(left,_,_,left),state(right,_,_,right),0):- take(cabbage,_,_),write('take(cabbage,left,right)').
%nothing l2r
arc(take(none,right,left),state(right,_,_,_),state(left,_,_,_),1):-take(none,Side,Other),write('take(none, right, left)').
%wolfl2r
arc(take(wolf,right,left),state(right,right,_,_),state(left,left,_,_)):-take(wolf,right,left).
%goatl2r
arc(take(goat,right,left),state(right,_,right,_),state(left,_,left,_)):-take(goat,right,left).
%cabl2r
arc(take(cabbage,right,left),state(right,_,_,right),state(left,_,_,left)):-take(cabbage,right,left).
unsafe(state(Farmer, Wolf, Goat, Cabbage)):-opposite(Farmer,Wolf), Wolf = Goat.
unsafe(state(Farmer, Wolf, Goat, Cabbage)):-opposite(Farmer,Goat), Goat = Cabbage.
safe(Farmer, Wolf, Goat, Cabbage):- \+ unsafe(state(Farmer, Wolf, Goat, Cabbage)).
go(state(left, left, left, left), state(right, right, right, right),[]).%%%
go(state(I, I, I, I), state(F, F, F, F),Rec):- safe(state(I, I, I, I)), Tmp = arc(_,state(I, I, I, I), state(F, F, F, F)),\+member(A,Rec),printPath(state(left,left,left,left)).
go(A, B):- arc(_,A,B), printPath(A),hist(left),printPath(B).
go(I, F, Num):- safe(F) -> arc(T,A,B), printPath(A), printPath(B).
% go(A, B, 0).
solve:- \+ go(state(left, left, left, left), state(right, right, right, right)).
