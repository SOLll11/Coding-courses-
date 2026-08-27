number :: Int -> Char
number n = toEnum (n + fromEnum 'a' - 1)

integerProduct :: [Integer] -> [Integer]
integerProduct

charsDivisibleBy :: Integer -> [Char]
charsDivisibleBy x = [ number (fromInteger y) | y <- [1..26], y `mod` x == 0]

charsProductOf :: [Integer] -> [Char]
charsProductOf ns = [ 