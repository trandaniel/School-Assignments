%Student, Section, CSID, SID
%Daniel Tran, 02, d45tran, 500554290
%Samuel Dindyal, 03, s2dindya, 500592528
%Balin Banh, 03, b2banh, 500574970


memberTree(X,tree(X,Left,Mid,Right)).
memberTree(X,tree(Y,Left,Mid,Right)) :- memberTree(X,Left).
memberTree(X,tree(Y,Left,Mid,Right)) :- memberTree(X,Mid).
memberTree(X,tree(Y,Left,Mid,Right)) :- memberTree(X,Right).
 
subsFirst(X,Y,void,void).
subsFirst(X,Y,Tree,Tree) :- not memberTree(X, Tree).
subsFirst(X,Y,tree(X,Left,Mid,Right),tree(Y,Left,Mid,Right)).
subsFirst(X,Y,tree(Root,Left,Mid,Right),tree(Root,SubLeft,Mid,Right)) :- not(X=Root),memberTree(X,Left),subsFirst(X,Y,Left,SubLeft).
subsFirst(X,Y,tree(Root,Left,Mid,Right),tree(Root,Left,SubMid,Right)) :- not(X=Root),memberTree(X,Mid),subsFirst(X,Y,Mid,SubMid).
subsFirst(X,Y,tree(Root,Left,Mid,Right),tree(Root,Left,Mid,SubRight)) :- not(X=Root),memberTree(X,Right),subsFirst(X,Y,Right,SubRight).