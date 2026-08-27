{-# LANGUAGE ScopedTypeVariables #-}
import qualified Data.Map
import Text.Read (readMaybe)
import Data.Char (isAlphaNum)

encode :: Int -> String -> String
encode shift msg = map (charmap Data.Map.!) msg
  where 
    charlist = ['0'..'9'] ++ ['A'..'Z'] ++ ['a'..'z']
    listlength = length charlist
    shiftedlist = take listlength (drop (shift `mod` listlength) (cycle charlist))
    charmap = Data.Map.fromList $ zip charlist shiftedlist

decode :: Int -> String -> String
decode shift msg = encode (negate shift) msg

main :: IO ()
main = loop
  where
    loop = do
      line <- getLine
      putStrLn ("> " ++ line)
      case words line of

        ["quit"] -> putStrLn "Bye"

        (cmd:shiftStr:rest) ->
          case (cmd, readMaybe shiftStr :: Maybe Int, rest) of
            ("encode", Just n, ws) | all (all isAlphaNum) ws -> do
                putStrLn (unwords (map (encode n) ws))
                loop
            ("decode", Just n, ws) | all (all isAlphaNum) ws -> do
                putStrLn (unwords (map (decode n) ws))
                loop
            _ -> do
                putStrLn "I cannot do that"
                loop

        _ -> do
          putStrLn "I cannot do that"
          loop
