#include <iterator>
#include <vector>
#include <algorithm>

#include "test.hh" // NOT_FOUND constant

using namespace std;


/**
 * @brief Find the median value of a given vector, whose elements are in random
 *        order. Return NOT_FOUND if the size of the vector is zero.
 *
 * @param v unsorted vector
 * @return int calculated median of parameter vector
 */
int findMedian(std::vector<int>& v)
{
    if(v.size()==0){
        return NOT_FOUND;
    }
    sort(v.begin(), v.end());

    if (v.size() % 2 == 1) {
        return v[v.size() / 2];
    } else {

        int middle1 = v[v.size() / 2 - 1];
        int middle2 = v[v.size() / 2];
        return (middle1 + middle2) / 2;
    }

}

