#include <iterator>
#include <vector>
#include <algorithm>

#include "test.hh"

using namespace std;


/**
 * @brief Arrange vector in three subsequent sections:
 *        - those divisible by three (asc order)
 *        - those whose reminder is 1 (asc order)
 *        - those whose reminder is 2 (asc order)
 * @param v vector to be sorted
 * @return int EXIT_SUCCESS if everything went OK, EXIT_FAILURE otherwise
 */
bool isdivby3(int i){
    return((i%3==0));

}
bool isrem1(int i){
    return((i%3==1));
}
bool isrem2(int i){
    return((i%3==2));
}


int sortMod3(std::vector<int>& v)
{

    auto divby3_end = partition(v.begin(),v.end(),isdivby3);
    auto rem1_end = partition(divby3_end,v.end(),isrem1);
    sort(v.begin(), divby3_end);
    sort(divby3_end, rem1_end);
    sort(rem1_end, v.end());
    return EXIT_SUCCESS;


}

