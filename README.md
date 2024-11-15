# mp-sorting-maven

An exploration of sorting in Java.

Authors

- Jacob Bell
- Samuel A. Rebelsky (starter code)

Acknowledgements

- ChatGPT

This code may be found at <https://github.com/jakerss2/mp-sorting-maven>. The original code may be found at <https://github.com/Grinnell-CSC207/mp-sorting-maven>.

## Description of custom sorting algorithm

My Unique sorting algorithm utilizes insertion and merge
sorting. It also uses forkJoinPool which splits the arrays
and allows them to be sorted at the same time(parallelization).

## Notes on using Copilot (or other AI)

I asked ChatGPT to give me the fastest sorting algorithms. It gave me about
10 algorithms and I asked for a further explanation of TimSort.
Once I got a grasp on what it was, I asked for it to take my implementation
of merge and insertion sort and incorporate it to be something like TimSort.
I then asked it to continue to optimize it, which started to include things
like forkJoinPool. Overall, it was fairly helpful. After submitting, I
tried to run time tests on my sort. I then realized it is extremely slow
because it takes a lot more effort to call forkJoinPool

## Notes about my first submission for MP8

I believe I should get an R(I think I've done the requirements).
However, I'd like to acknowledge that I will not get an M or above.
As time dwindles down to submit, I've realized there is way too many
style issues(some with the starter code), and I don't have enough time
to cut it all down. I also didn't decompose all of the algorithms
which I didn't realize was a requirement for every algorithm. I'd just like
to acknowledge this to save whoever is reading some time.
