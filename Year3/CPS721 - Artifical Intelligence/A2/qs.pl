%Student, Section, CSID, SID
%Daniel Tran, 02, d45tran, 500554290
%Samuel Dindyal, 03, s2dindya, 500592528
%Balin Banh, 03, b2banh, 500574970

%Quicksort algorithm

qs(nil,nil).
%qs(next(X,nil), next(X,nil)).
qs(next(Head,Tail), Output) :- part(Head, Tail, Ls, Gs), 
    							qs(Ls, Less),
    							qs(Gs, Greater),
    							mergeLists(Less, next(Head, Greater), Output).

part(Piv, nil, nil, nil).
part(Piv, next(Head, Tail), next(Head, Ls), Gs) :- Head =< Piv, part(Piv, Tail, Ls, Gs).
part(Piv, next(Head, Tail), Ls, next(Head, Gs)) :- Head > Piv, part(Piv, Tail, Ls, Gs).
                                
mergeLists(nil, Ls, Ls).
mergeLists(next(Piv, Greater), Ls, next(Piv, Result)) :- mergeLists(Greater, Ls, Result).