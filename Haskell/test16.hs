speeds :: [Maybe Integer] -> [Integer] -> [Either String Integer]
speeds [ _ ] [ _ ] = [Left "One point only"]
speeds positions timepoints
  | length positions > length timepoints =
      calcSpeeds ++ [Left "Too many positions"]
  | length positions < length timepoints =
      calcSpeeds ++ [Left "Too many timepoints"]
  | otherwise = calcSpeeds
  where
    timePairs = zip timepoints (tail timepoints)
    calcSpeeds = [ calc p1 p2 t1 t2
                 | ((p1, p2), (t1, t2))
                 <- zip (zip positions (tail positions)) timePairs
                 ]

    calc (Just p1) (Just p2) t1 t2
      | t1 == t2  = Left "Same time in two measurements"
      | t2 < t1   = Left "Space-time continuum broken"
      | otherwise = Right ((p2 - p1) `div` (t2 - t1))
    calc _ _ _ _ = Left "Missing data"
