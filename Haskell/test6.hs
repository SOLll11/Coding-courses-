onlyDigits :: String -> Bool

onlyDigits "" = False

onlyDigits (x:xs)
    | x >= '0' && x <= '9' = null xs|| onlyDigits xs
    |otherwise = False