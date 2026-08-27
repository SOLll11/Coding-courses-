#include <iterator>
#include <vector>
#include <algorithm>

using namespace std;


/**
 * @brief Erases every second item from the vector. Example: {1, 2, 3, 4} -> {1, 3}
 *
 * @param vec vector where every second item is erased.
 */
void eraseEverySecond(std::vector<int>& vec) {
    auto it = vec.begin();
    bool eraseNext = false;

    while (it != vec.end()) {
        if (eraseNext) {
            it = vec.erase(it);
        } else {
            ++it;
        }
        eraseNext = !eraseNext;
    }
}

