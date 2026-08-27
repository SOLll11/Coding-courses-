gap :: (Char, Char) -> Integer -> String -> Integer
gap (a, b) g s = go (a, b) g s 0
    where
      go :: (Char, Char) -> Integer -> String -> Integer -> Integer
      go (a, b) _ "" acc = acc
      go _ g str acc | g+1 >= fromIntegral (length str) = acc

      go (a, b) g (x:xs) acc
        | x == a && xs !! fromIntegral g == b = go (a, b) g (xs) (acc+1)
        |otherwise = go (a, b) g (xs) acc
