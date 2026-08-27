charsDivisibleBy :: Integer -> [Char]
charsDivisibleBy n = [ toEnum (fromInteger i + 96) :: Char | i <- [1..26], i `mod` n == 0 ]

charsProductOf :: [Integer] -> [Char]
charsProductOf ns = [ toEnum (fromInteger p + 96) :: Char 
                    | x <- ns, y <- ns, x < y,
                      let p = x * y, p >= 1, p <= 26 ]