addpos :: [Integer] -> Integer

addpos = foldr (\x acc -> if x > 0 then x + acc else if x == 0 then 0 else acc) 0