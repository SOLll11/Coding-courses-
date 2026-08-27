headOrLast :: [String] -> Char -> [String]
headOrLast x y = [a | a <- x , head a == y || last a == y]