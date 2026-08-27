addpos :: [Integer] -> Integer
addpos list = sum (filter(>0) (takeWhile(/= 0) list))