clusters :: (String -> String -> Float) -> Float -> [String] -> [[String]]

clusters f d ss =
    [[x | x <- ss, f s x <= d] | s <- ss]