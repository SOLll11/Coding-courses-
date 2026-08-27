
distance1 :: String -> String -> Float
distance1 "" "" = 0
distance1 s1 s2 =
    fromIntegral (countDiff s1 s2 + countDiff s2 s1)
    / fromIntegral (length s1 + length s2)
  where
    countDiff xs ys = length [c | c <- xs, not (c `elem` ys)]

distance2 :: String -> String -> Float
distance2 "" "" = 0
distance2 s1 s2 =
    fromIntegral (countNonDigits s1 + countNonDigits s2)
    / fromIntegral (length s1 + length s2)
  where
    countNonDigits xs = length [c | c <- xs, not (c `elem` ['0'..'9'])]