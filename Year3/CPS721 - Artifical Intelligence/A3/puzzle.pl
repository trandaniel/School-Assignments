carry(0).
carry(1).
digit(0).
digit(1).
digit(2).
digit(3).
digit(4).
digit(5).
digit(6).
digit(7).
digit(8).
digit(9).

%Print solution using solve2

print_solution :-
			solve2([C,R,O,S,A,D,N,G,E]), nl,
			write(' '), write(C), write(R), write(O), write(S), write(S), nl,
			write('+'), write(R), write(O), write(A), write(D), write(S), nl,
			write('______'), nl,
			write(D), write(A), write(N), write(G), write(E), write(R), nl.

%Print solution with list as input

print_solution([C,R,O,S,A,D,N,G,E]):-
			write(' '), write(C), write(R), write(O), write(S), write(S), nl,
			write('+'), write(R), write(O), write(A), write(D), write(S), nl,
			write('______'), nl,
			write(D), write(A), write(N), write(G), write(E), write(R), nl.

% Generate & Test 
solve1([C,R,O,S,A,D,N,G,E]) :- digit(C), digit(R), digit(O), digit(S),
							digit(A), digit(D), digit(N), digit(G),
							digit(E),
							C > 0, R > 0, D > 0,
							R is (S+S) mod 10, C1 is (S+S) // 10,
							E is (S+D+C1) mod 10, C10 is (S+D+C1) // 10,
							G is (O+A+C10) mod 10, C100 is (O+A+C10) // 10,
							N is (R+O+C100) mod 10, C1000 is (R+O+C100) // 10,
							A is (C+R+C1000) mod 10, D is (C+R+C1000) // 10,
							all_diff([C,R,O,S,A,D,N,G,E]).

% optimizing
% remove the guessing for digits that can be calculated 
% values (A,N,G,E,R) are all calculated and the guessing can be minimized
% carry values can be guessed, as they are either 1s or 0s 
% use dependency graph to calculate values and reduce the amount of guessing required

solve2([C,R,O,S,A,D,N,G,E]) :-
					carry(D), D > 0, carry(C1000),
					digit(R), R > 0,
					digit(C), C > 0,
					D is (R+C+C1000) // 10, A is (R+C+C1000) mod 10,
					digit(N), digit(O), carry(C100),
					N is (R+O+C100) mod 10, C1000 is (R+O+C100) // 10,
					carry(C10),
					G is (O+A+C10) mod 10, C100 is (O+A+C10) // 10,
					digit(S), carry(C1),
					E is (S+D+C1) mod 10, C10 is (S+D+C1) // 10,
					R is (S+S) mod 10, C1 is (S+S) // 10,
					all_diff([C,R,O,S,A,D,N,G,E]).




all_diff([]).
all_diff([N|L]) :- not(member(N,L)), all_diff(L).

member(N,[N|L]).
member(N,[M|L]) :- member(N,L).
