%Student, Section, CSID, SID
%Daniel Tran, 02, d45tran, 500554290
%Samuel Dindyal, 03, s2dindya, 500592528
%Balin Banh, 03, b2banh, 500574970

:- discontiguous(northOf/2) .
:- discontiguous(southOf/2) .
:- discontiguous(eastOf/2) .
:- discontiguous(westOf/2) .

%TTC Subway Stations

just_north(yongebloor, wellesley) .
just_north(wellesley, college) .
just_north(college, dundas) .
just_north(dundas, queen) .
just_north(queen, king) .
just_north(stgeorge, museum) .
just_north(museum, queenspark) .
just_north(queenspark, stpatrick) .
just_north(stpatrick, osgoode) .
just_north(osgoode, standrew) .
just_north(christie, museum) .
just_north(bathurst, museum) .
just_north(bay, museum) .
just_north(yongebloor, museum) .
just_north(christie, wellesley) .
just_north(bathurst, wellesley) .
just_north(stgeorge, wellesley) .
just_north(bay, wellesley) .
just_east(bathurst, christie) .
just_east(spadina, bathurst) .
just_east(stgeorge, spadina) .
just_east(bay, stgeorge) .
just_east(yongebloor, bay) .
just_east(wellesley, museum) .
just_east(college, queenspark) .
just_east(dundas, stpatrick) .
just_east(queen, osgoode) .
just_east(king, standrew) .



southOf(Ent1, Ent2) :- northOf(Ent2, Ent1) .
westOf(Ent1, Ent2) :- eastOf(Ent2, Ent1) .

northOf(Ent1, Ent2) :- just_north(Ent1, Ent2) .
northOf(Ent1, Ent2) :- just_north(Ent1, Ent3), eastOf(Ent3, Ent2) .
northOf(Ent1, Ent2) :- just_north(Ent1, Ent3), westOf(Ent3, Ent2) .
northOf(Ent1, Ent2) :- just_north(Ent1, Ent3), northOf(Ent3, Ent2) .

eastOf(Ent1, Ent2) :- just_east(Ent1, Ent2) .
eastOf(Ent1, Ent2) :- just_east(Ent1, Ent3), southOf(Ent3, Ent2) .
eastOf(Ent1, Ent2) :- just_east(Ent1, Ent3), northOf(Ent3, Ent2) .
eastOf(Ent1, Ent2) :- just_east(Ent1, Ent3), eastOf(Ent3, Ent2) .
