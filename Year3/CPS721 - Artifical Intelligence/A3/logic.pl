caller(1).
caller(2).
caller(3).
caller(4).
caller(5).

%From statement 1:
%
%caller(Rob), not(Rob = 1), not(Rob = 5), 						
%caller(Sm), not(Sm = 2), not(Sm = 3), not(Sm = 4),
%caller(Gig), not(Gig = 2), not(Gig = 3), not(Gig = 4),
%caller(Mush), not(Mush = 2), not(Mush = 3), not(Mush = 4),
%caller(Sausage), not(Sausage = 2), not(Sausage = 3), not(Sausage = 4),
%
%From statement 2:
%caller(Mary), caller(Cindy), caller(Ben), Mary < Cindy, Ben > Rob, Ben < Mary,							
%
%From statement 3:
%
%caller(ExLarge), caller(Onion), caller(Large), ExLarge > Large, not(ExLarge = Onion), not(Large = Onion),
%caller(Med), Med =:= Gig + 1, Gig = Mush,
%
%From statement 4 and remaining values to guess:
%
%caller(Cheese), not(Ben = Cheese),
%caller(Emily), caller(Pepp),

%print solution with list as input

print_solution([Sm, Med, Large, ExLarge, Gig, Mush, Sausage, Pepp, Onion, Cheese, Ben, Cindy, Emily, Mary, Rob]):-
							member([1, S], [[Sm, 'Small'], [Med, 'Medium'], [Large, 'Large'], [ExLarge, 'Extra Large'], [Gig, 'Gigantic']]),
							member([1, T], [[Mush, 'Mushroom'], [Sausage, 'Sausage'], [Pepp, 'Pepperoni'], [Onion, 'Onion'], [Cheese, 'Cheese']]),
							member([1, N], [[Ben, 'Ben'], [Cindy, 'Cindy'], [Emily, 'Emily'], [Mary, 'Mary'], [Rob, 'Rob']]),
							write('Caller 1: '), write(N), write(', '), write(S), write(' pizza with '), write(T), nl,
							member([2, S2], [[Sm, 'Small'], [Med, 'Medium'], [Large, 'Large'], [ExLarge, 'Extra Large'], [Gig, 'Gigantic']]),
							member([2, T2], [[Mush, 'Mushroom'], [Sausage, 'Sausage'], [Pepp, 'Pepperoni'], [Onion, 'Onion'], [Cheese, 'Cheese']]),
							member([2, N2], [[Ben, 'Ben'], [Cindy, 'Cindy'], [Emily, 'Emily'], [Mary, 'Mary'], [Rob, 'Rob']]),
							write('Caller 2: '), write(N2), write(', '), write(S2), write(' pizza with '), write(T2), nl,
							member([3, S3], [[Sm, 'Small'], [Med, 'Medium'], [Large, 'Large'], [ExLarge, 'Extra Large'], [Gig, 'Gigantic']]),
							member([3, T3], [[Mush, 'Mushroom'], [Sausage, 'Sausage'], [Pepp, 'Pepperoni'], [Onion, 'Onion'], [Cheese, 'Cheese']]),
							member([3, N3], [[Ben, 'Ben'], [Cindy, 'Cindy'], [Emily, 'Emily'], [Mary, 'Mary'], [Rob, 'Rob']]),
							write('Caller 3: '), write(N3), write(', '), write(S3), write(' pizza with '), write(T3), nl,
							member([4, S4], [[Sm, 'Small'], [Med, 'Medium'], [Large, 'Large'], [ExLarge, 'Extra Large'], [Gig, 'Gigantic']]),
							member([4, T4], [[Mush, 'Mushroom'], [Sausage, 'Sausage'], [Pepp, 'Pepperoni'], [Onion, 'Onion'], [Cheese, 'Cheese']]),
							member([4, N4], [[Ben, 'Ben'], [Cindy, 'Cindy'], [Emily, 'Emily'], [Mary, 'Mary'], [Rob, 'Rob']]),
							write('Caller 4: '), write(N4), write(', '), write(S4), write(' pizza with '), write(T4), nl,
							member([5, S5], [[Sm, 'Small'], [Med, 'Medium'], [Large, 'Large'], [ExLarge, 'Extra Large'], [Gig, 'Gigantic']]),
							member([5, T5], [[Mush, 'Mushroom'], [Sausage, 'Sausage'], [Pepp, 'Pepperoni'], [Onion, 'Onion'], [Cheese, 'Cheese']]),
							member([5, N5], [[Ben, 'Ben'], [Cindy, 'Cindy'], [Emily, 'Emily'], [Mary, 'Mary'], [Rob, 'Rob']]),
							write('Caller 5: '), write(N5), write(', '), write(S5), write(' pizza with '), write(T5), nl.

print_solution :-
							solve([Sm, Med, Large, ExLarge, Gig, Mush, Sausage, Pepp, Onion, Cheese, Ben, Cindy, Emily, Mary, Rob]),
							member([1, S], [[Sm, 'Small'], [Med, 'Medium'], [Large, 'Large'], [ExLarge, 'Extra Large'], [Gig, 'Gigantic']]),
							member([1, T], [[Mush, 'Mushroom'], [Sausage, 'Sausage'], [Pepp, 'Pepperoni'], [Onion, 'Onion'], [Cheese, 'Cheese']]),
							member([1, N], [[Ben, 'Ben'], [Cindy, 'Cindy'], [Emily, 'Emily'], [Mary, 'Mary'], [Rob, 'Rob']]),
							write('Caller 1: '), write(N), write(', '), write(S), write(' pizza with '), write(T), nl,
							member([2, S2], [[Sm, 'Small'], [Med, 'Medium'], [Large, 'Large'], [ExLarge, 'Extra Large'], [Gig, 'Gigantic']]),
							member([2, T2], [[Mush, 'Mushroom'], [Sausage, 'Sausage'], [Pepp, 'Pepperoni'], [Onion, 'Onion'], [Cheese, 'Cheese']]),
							member([2, N2], [[Ben, 'Ben'], [Cindy, 'Cindy'], [Emily, 'Emily'], [Mary, 'Mary'], [Rob, 'Rob']]),
							write('Caller 2: '), write(N2), write(', '), write(S2), write(' pizza with '), write(T2), nl,
							member([3, S3], [[Sm, 'Small'], [Med, 'Medium'], [Large, 'Large'], [ExLarge, 'Extra Large'], [Gig, 'Gigantic']]),
							member([3, T3], [[Mush, 'Mushroom'], [Sausage, 'Sausage'], [Pepp, 'Pepperoni'], [Onion, 'Onion'], [Cheese, 'Cheese']]),
							member([3, N3], [[Ben, 'Ben'], [Cindy, 'Cindy'], [Emily, 'Emily'], [Mary, 'Mary'], [Rob, 'Rob']]),
							write('Caller 3: '), write(N3), write(', '), write(S3), write(' pizza with '), write(T3), nl,
							member([4, S4], [[Sm, 'Small'], [Med, 'Medium'], [Large, 'Large'], [ExLarge, 'Extra Large'], [Gig, 'Gigantic']]),
							member([4, T4], [[Mush, 'Mushroom'], [Sausage, 'Sausage'], [Pepp, 'Pepperoni'], [Onion, 'Onion'], [Cheese, 'Cheese']]),
							member([4, N4], [[Ben, 'Ben'], [Cindy, 'Cindy'], [Emily, 'Emily'], [Mary, 'Mary'], [Rob, 'Rob']]),
							write('Caller 4: '), write(N4), write(', '), write(S4), write(' pizza with '), write(T4), nl,
							member([5, S5], [[Sm, 'Small'], [Med, 'Medium'], [Large, 'Large'], [ExLarge, 'Extra Large'], [Gig, 'Gigantic']]),
							member([5, T5], [[Mush, 'Mushroom'], [Sausage, 'Sausage'], [Pepp, 'Pepperoni'], [Onion, 'Onion'], [Cheese, 'Cheese']]),
							member([5, N5], [[Ben, 'Ben'], [Cindy, 'Cindy'], [Emily, 'Emily'], [Mary, 'Mary'], [Rob, 'Rob']]),
							write('Caller 5: '), write(N5), write(', '), write(S5), write(' pizza with '), write(T5), nl.


solve([Sm, Med, Large, ExLarge, Gig, Mush, Sausage, Pepp, Onion, Cheese, Ben, Cindy, Emily, Mary, Rob]) :-
							caller(Rob), not(Rob = 1), not(Rob = 5), 						
							caller(Sm), not(Sm = 2), not(Sm = 3), not(Sm = 4),
							caller(Gig), not(Gig = 2), not(Gig = 3), not(Gig = 4),
							caller(Mush), not(Mush = 2), not(Mush = 3), not(Mush = 4),
							caller(Sausage), not(Sausage = 2), not(Sausage = 3), not(Sausage = 4),
							caller(Mary), caller(Cindy), caller(Ben), Mary < Cindy, Ben > Rob, Ben < Mary,
							caller(ExLarge), caller(Onion), caller(Large), ExLarge > Large, not(ExLarge = Onion), not(Large = Onion),
							caller(Med), Med =:= Gig + 1, Gig = Mush,
							caller(Cheese), not(Ben = Cheese),
							caller(Emily), caller(Pepp),
							all_diff([Sm, Med, Large, ExLarge, Gig]),
							all_diff([Mush, Sausage, Pepp, Onion, Cheese]),
							all_diff([Ben, Cindy, Emily, Mary, Rob]).


all_diff([]).
all_diff([N|L]) :- not(member(N,L)), all_diff(L).

member(N,[N|L]).
member(N,[M|L]) :- member(N,L).
