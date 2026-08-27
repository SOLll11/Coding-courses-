/**
 * iteration4.cc
 *
 * Print all items of a list in a reverse order
 */

/**
 * DO NOT ADD ANY INCLUDES!!
 */

#include "iteration4.hh"
using namespace std;


void printReverse(const list<int>& lst)
{
    list<int>::const_iterator it;
    list<int>::const_iterator end  = std::prev(lst.end());
    list<int>::const_iterator start = std::prev(lst.begin());

        for (it = end; it != start; --it) {
            cout << *(it) << " ";
        }
        cout << endl;
}
