distance3 :: String -> String -> Float
distance3 x y = fromIntegral $ abs $ length x - length y

distanceFilter :: (String -> String -> Float) -> Float -> String -> [String] -> [String]

distanceFilter f d s ss =
    [x | x <- ss, f s x <= d]
