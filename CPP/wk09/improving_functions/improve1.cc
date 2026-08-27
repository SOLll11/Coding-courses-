#include <algorithm>
#include <random>
#include <vector>

/**
 * @brief creates a vector of integers with ascending numbers from 0 - n-1
 *
 * @param n the size of the vector to be created
 * @return std::vector<int>
 */
std::vector<int> ascendingVector(int n){
    std::vector<int> v(n);
    int val = 0;
    std::iota(v.begin(),v.end(), val);
    return v;
}
