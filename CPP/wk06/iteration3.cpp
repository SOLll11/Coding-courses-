/**
 * iteration3.cc
 *
 * Print beginning half of a list
 */

/**
 * DO NOT ADD ANY INCLUDES!!
 */

#include "iteration3.hh"
using namespace std;


void printHalf(const list<int>& lst)
{
    list<int>::const_iterator it;
    list<int>::const_iterator miditer = lst.begin();
    advance(miditer, distance(lst.begin(), lst.end()) / 2);


        for (it = lst.begin(); it != miditer; ++it) {
            cout << *it << " ";
        }
        cout << endl;
}
