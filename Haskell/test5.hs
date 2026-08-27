nextIsGreater :: [Integer] -> [Integer]
nextIsGreater [] = []
nextIsGreater [_] = []
nextIsGreater (x:y:xs)
    | x < y     = x : nextIsGreater (y:xs)
    | otherwise = nextIsGreater (y:xs)