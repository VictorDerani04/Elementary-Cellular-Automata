It prints the ECA for any set of rules. It calls the parameters from the command line.
An example of parameters is "-rules 110 -off-color black -on-color cyan -random-seed 42 -size 50 -init 0.2 -iter 500".
It means that the user is calling the rule 110 where the off color is black, the on color is cyan, the seed used for randomization in the automaton is 42,
the size of the printing shape is 50, the initialization density of the automaton is 0.2 (which means that defines the probability that any given cell will start in the ”on” state),
and the code does 500 iterations. The user could also provide the rules as 8 doubles separated by spaces, one for each possible configuration of a cell and its neighbors,
specifying the transition behavior of the automaton. This list should start with the rule for 000 and end with the rule for 111.

Victor Derani
