import Text.Read (readMaybe)

calculate :: [String] -> [String]
calculate = map calculateOne
  where
    calculateOne s =
      case words s of
        [a, "+", b] -> doCalc (+) a b
        [a, "-", b] -> doCalc (-) a b
        [a, "*", b] -> doCalc (*) a b
        _           -> "I cannot calculate that"
      where
        doCalc op x y =
          case (readMaybe x :: Maybe Int, readMaybe y :: Maybe Int) of
            (Just n1, Just n2) -> show (op n1 n2)
            _                  -> "I cannot calculate that"

    
