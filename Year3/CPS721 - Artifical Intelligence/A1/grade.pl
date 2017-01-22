assignment(1, 60) .
assignment(2, 70) .
assignment(3, 85) .
assignment(4, 99) .
assignment(5, 100) .
quiz(1, 2) .
quiz(2, 1) .
quiz(3, 2) .
quiz(4, 1) .
quiz(5, 2) .
quiz(6, 2) .
quiz(7, 2) .
quiz(8, 2) .
quiz(9, 2) .
quiz(10, 2) .
midterm(16) .
exam(43) .

sumAssignments(A) :- assignment(1, A1), assignment(2, A2), assignment(3, A3), assignment(4, A4), assignment(5, A5), 
					A is (A1 + A2 + A3 + A4 + A5) / 25 .
sumQuizzes(Q) :- quiz(1, Q1), quiz(2, Q2), quiz(3, Q3), quiz(4, Q4), quiz(5, Q5), quiz(6, Q6), quiz(7, Q7), quiz(8, Q8),
				quiz(9, Q9), quiz(10, Q10), Q is (Q1 + Q2 + Q3 + Q4 + Q5 + Q6 + Q7 + Q8 + Q9 + Q10) / 2 .
grade(G) :- midterm(M), exam(E), sumQuizzes(Q), sumAssignments(A), G is (M + E + Q + A) .

letter(L, G) :- G >= 90, L = "A+" .
letter(L, G) :- G >= 85, G < 90, L = "A" .
letter(L, G) :- G >= 80, G < 85, L = "A-" .

letter(L, G) :- G >= 77, G < 80, L = "B+" .
letter(L, G) :- G >= 73, G < 77, L = "B" .
letter(L, G) :- G >= 70, G < 73, L = "B-" .

letter(L, G) :- G >= 67, G < 70, L = "C+" .
letter(L, G) :- G >= 63, G < 67, L = "C" .
letter(L, G) :- G >= 60, G < 63, L = "C-" .

letter(L, G) :- G >= 57, G < 60, L = "D+" .
letter(L, G) :- G >= 53, G < 57, L = "D" .
letter(L, G) :- G >= 50, G < 53, L = "D-" .

letter(L, G) :- G >= 0, G < 50, L = "F" .
