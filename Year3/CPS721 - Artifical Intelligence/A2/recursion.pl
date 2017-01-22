%Question 1 nth

nth(1,[Element], Element).
nth(1,[Head|Tail], Head).
nth(N,[Head|Tail],Element) :- X is N-1, nth(X,Tail,Element).

%Question 2 remove

remove(A, [], []).
remove(X, [X|XTail], Y) :- remove(X, XTail, Y).
remove(X, [T|XTail], [T|Y]) :- not(X = T), remove(X, XTail, Y).

%Question 3 splitEvenOdd

splitEvenOdd([], [], []).
splitEvenOdd([H|T], [H|X], Y) :- 0 is H mod 2, splitEvenOdd(T, X, Y).
splitEvenOdd([H|T], X, [H|Y]) :-1 is H mod 2, splitEvenOdd(T, X, Y).

%Question 4 lessThanEq

lessThanEq([],[],[]).
lessThanEq(List1, List2, List3) :-
		length(List1, N), length(List2,N1), length(List3, N2), Sum is N+N1, Sum =< N2.

length([], 0).
length([Element], 1).
length([Head|Tail], N) :- length(Tail, Sum), N is S + 1.



%Question 5 Pascal's Triangle

%Note: Program assumes first row is row 0 with value [1] according to pascals triangle, first row is row 0
%To match the queries on assignment outline, the basecase would be changed from row(0,[1]) to row(1,[1]).


row(0, [1]).
%Added 0 to nextRow, to make sum for elements on edge of triagle
row(N,R):- X is N-1, row(X,PrevRow), nextRow([0|PrevRow], R).

nextRow([E],[E]).
nextRow([E0,E1|T],[Head|Tail]):- nextRow([E1|T], Tail), Head is E0+E1.
