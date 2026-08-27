credits :: (Char, Integer) -> (Char, Integer) -> Integer
credits ('s', 14) _ = 14
credits _ ('s',14) = 14
credits (s1, r1) (s2, r2)
  | s1 == s2 && abs (r1 - r2) == 1 = 8
  | r1 == r2                       = 6
  | abs (r1 - r2) == 1             = 4
  | s1 == s2                       = 2
  | otherwise                      = 0