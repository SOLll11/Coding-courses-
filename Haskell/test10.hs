averageResult :: [Integer -> Integer] -> ([Integer] -> Integer)
averageResult [] = error "At least one function required"
averageResult fs = \xs ->
    if length fs /= length xs
    then error "Lists have different lengths"
    else let results = zipWith ($) fs xs
             total = sum results
             count = fromIntegral (length results)
         in total `div` count
