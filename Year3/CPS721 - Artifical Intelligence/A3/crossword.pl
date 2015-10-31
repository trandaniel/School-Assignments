%Student, Section, CSID, SID
%Daniel Tran, 02, d45tran, 500554290
%Samuel Dindyal, 03, s2dindya, 500592528
%Balin Banh, 03, b2banh, 500574970

word([a,d,d]).	word([a,d,o]).	word([a,g,e]).
word([a,g,o]).	word([a,i,d]).	word([a,i,l]).	word([a,i,m]).
word([a,i,r]).	word([a,n,d]).	word([a,n,y]).	word([a,p,e]).
word([a,p,t]).	word([a,r,c]).	word([a,r,m]).	word([a,r,t]).
word([a,s,h]).	word([a,s,k]).	word([a,u,k]).	word([a,w,e]).
word([a,w,l]).	word([a,y,e]).
word([b,a,d]).	word([b,a,g]).	word([b,a,n]).	word([b,e,e]).	word([b,o,a]).
word([c,a,t]).	word([c,o,d]).	word([c,o,g]).	word([c,u,t]).
word([d,a,m]).	word([d,i,m]).	
word([e,a,r]).	word([e,e,l]).	word([e,f,t]).	
word([f,a,r]).	word([f,a,t]).	word([f,i,t]).	word([f,o,g]).
word([g,i,g]).	word([g,n,u]).
word([h,a,m]).	word([h,a,t]).	word([h,a,y]).	word([h,e,x]).
word([i,c,e]).	word([i,n,k]).	word([i,r,k]).
word([j,a,m]).	word([j,o,b]).	word([j,o,g]).
word([k,e,n]).	word([k,e,y]).	word([k,i,n]).	word([k,i,t]).
word([l,e,e]).	word([l,e,g]).	word([l,i,t]).	word([l,o,b]).
word([m,a,d]).	word([m,a,t]).	word([m,o,b]).
word([n,e,e]).	word([n,e,t]).	word([n,u,t]).
word([o,a,k]).	word([o,a,r]).
word([p,a,n]).	word([p,e,g]).	word([p,e,n]).	word([p,e,t]).
word([p,i,n]).	word([p,i,t]).	word([p,o,p]).	word([p,o,t]).
word([p,o,w]).	word([p,u,n]).	word([p,u,t]).
word([r,a,m]).	word([r,a,t]).	word([r,i,g]).	word([r,i,m]).
word([s,a,c]).	word([s,a,g]).	word([s,a,p]).	word([s,a,x]).
word([s,e,a]).	word([s,e,x]).	word([s,i,n]).	word([s,k,i]).
word([s,o,b]).	word([s,u,m]).
word([t,a,g]).	word([t,a,r]).	word([t,i,e]).

%Letters for the domain of part 2

letter(a).
letter(b).
letter(c).
letter(d).
letter(e).
letter(f).
letter(g).
letter(h).
letter(i).
letter(j).
letter(k).
letter(l).
letter(m).
letter(n).
letter(o).
letter(p).
letter(q).
letter(r).
letter(s).
letter(t).
letter(u).
letter(v).
letter(w).
letter(x).
letter(y).
letter(z).

%Print the solution given the list

print_solution([R1, R2, R3, C1, C2, C3]) :- 
					write(R1), nl, 
					write(R2), nl,
					write(R3), nl,
					write('Row 1: '), write(R1), nl,
					write('Row 2: '), write(R2), nl,
					write('Row 3: '), write(R3), nl,
					write('Col 1: '), write(C1), nl,
					write('Col 2: '), write(C2), nl,
					write('Col 3: '), write(C3), nl.

%Print the solution without an input list, using solve1 to solve

print_solution :-
					solve1([R1, R2, R3, C1, C2, C3]),
					write(R1), nl, 
					write(R2), nl,
					write(R3), nl,
					write('Row 1: '), write(R1), nl,
					write('Row 2: '), write(R2), nl,
					write('Row 3: '), write(R3), nl,
					write('Col 1: '), write(C1), nl,
					write('Col 2: '), write(C2), nl,
					write('Col 3: '), write(C3), nl.

%Part 1, solving by guessing words for each row/column

solve1([R1, R2, R3, C1, C2, C3]) :-
					word([R11, R12, R13]), R1 = [R11, R12, R13], 
					word([R21, R22, R23]), R2 = [R21, R22, R23], 
					word([R31, R32, R33]), R3 = [R31, R32, R33],
					word([R11, R21, R31]), C1 = [R11, R21, R31], 
					word([R12, R22, R32]), C2 = [R12, R22, R32], 
					word([R13, R23, R33]), C3 = [R13, R23, R33],
					all_diff([R1, R2, R3, C1, C2, C3]).

%Part 2, solving each letter, and using the words as constraints
%Started by guessing the letters on the outside before guessing center word

solve2([R1, R2, R3, C1, C2, C3]) :-
					letter(R11), letter(R12), letter(R13), word([R11, R12, R13]), R1 = [R11, R12, R13],
					letter(R21), letter(R31), word([R11, R21, R31]), C1 = [R11, R21, R31],
					letter(R23), letter(R33), word([R13, R23, R33]), C3 = [R13, R23, R33],
					letter(R32), word([R31, R32, R33]), R3 = [R31, R32, R33],
					letter(R22), word([R21, R22, R23]), word([R12, R22, R32]), R2 = [R21, R22, R23], C2 = [R12, R22, R32],
					all_diff([R1, R2, R3, C1, C2, C3]).

all_diff([]).
all_diff([N|L]) :- not(member(N,L)), all_diff(L).

member(N,[N|L]).
member(N,[M|L]) :- member(N,L).

%
%Generate & test method for part 2, takes 250 seconds to form a solution
%
%solve2([R1, R2, R3, C1, C2, C3]) :-
%					letter(R11), letter(R12), letter(R13), word([R11, R12, R13]), R1 = [R11, R12, R13],
%					letter(R21), letter(R22), letter(R23), word([R21, R22, R23]), R2 = [R12, R22, R23],
%					letter(R31), letter(R32), letter(R33), word([R31, R32, R33]), R3 = [R31, R32, R33],
%					word([R11, R21, R31]), C1 = [R11, R21, R31],
%					word([R12, R22, R32]), C2 = [R12, R22, R32],
%					word([R13, R23, R33]), C3 = [R13, R23, R33],
%					all_diff([R1, R2, R3, C1, C2, C3]).					
