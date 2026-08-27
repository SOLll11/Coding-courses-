{-# LANGUAGE OverloadedStrings #-}
import Data.Time.Calendar
import Data.Time.Format
import Data.List
import Text.Read (readMaybe)
import System.IO
import Control.Monad (when)
import qualified Data.Map.Strict as Map
import Data.Maybe (mapMaybe)

-- Event data type
data Event = Event {
    name :: String,
    place :: String,
    date :: Day
} deriving (Eq, Show)

type Calendar = Map.Map String Event

-- Main function
main :: IO ()
main = loop Map.empty

loop :: Calendar -> IO ()
loop cal = do
    line <- getLine
    putStrLn $ "> " ++ line
    let trimmed = trim line
    case parseCommand trimmed of
        Just (AddEvent n p d) -> do
            case parseDay d of
                Nothing -> putStrLn "Bad date" >> loop cal
                Just day -> 
                    if Map.member n cal
                        then putStrLn "Event already exists" >> loop cal
                        else putStrLn "Ok" >> loop (Map.insert n (Event n p day) cal)
        Just (TellAbout n) -> do
            case Map.lookup n cal of
                Nothing -> putStrLn "I do not know of such event"
                Just ev -> putStrLn $ "Event [ " ++ name ev ++ " ] happens at [ " ++ place ev ++ " ] on " ++ showDay (date ev)
            loop cal
        Just (WhatNear d) -> do
            case parseDay d of
                Nothing -> putStrLn "Bad date" >> loop cal
                Just day -> do
                    let eventsNear = filter (\e -> abs (diffDays (date e) day) <= 7) (Map.elems cal)
                    if null eventsNear
                        then putStrLn "Nothing that I know of"
                        else mapM_ (putStrLn . (\e -> "Event [ " ++ name e ++ " ] happens on " ++ showDay (date e))) 
                                   (sortOn (\e -> (date e, name e)) eventsNear)
                    loop cal
        Just (WhatAt p) -> do
            let eventsAt = filter (\e -> place e == p) (Map.elems cal)
            if null eventsAt
                then putStrLn "Nothing that I know of"
                else mapM_ (putStrLn . (\e -> "Event [ " ++ name e ++ " ] happens at [ " ++ place e ++ " ]")) 
                           (sortOn name eventsAt)
            loop cal
        Just Quit -> putStrLn "Bye"
        Nothing -> do
            putStrLn "I do not understand that"
            putStrLn "Explanation of the commands"
            putStrLn "Event [ <name> ] happens at [ <place> ] on <date>"
            loop cal

-- Command type
data Command = AddEvent String String String
             | TellAbout String
             | WhatNear String
             | WhatAt String
             | Quit
             deriving (Show)

-- Parsing functions
parseCommand :: String -> Maybe Command
parseCommand s
    | "Event " `isPrefixOf` s && " happens at " `isInfixOf` s && " on " `isInfixOf` s =
        let (n, rest1) = extractBracket s
            (p, rest2) = extractBracket rest1
            dateStr = dropWhile (==' ') $ drop 4 rest2 -- skip " on "
        in Just (AddEvent n p dateStr)
    | "Tell me about " `isPrefixOf` s =
        let (n, _) = extractBracket s
        in Just (TellAbout n)
    | "What happens near " `isPrefixOf` s =
        Just (WhatNear $ drop 17 s)
    | "What happens at " `isPrefixOf` s =
        let (p, _) = extractBracket s
        in Just (WhatAt p)
    | s == "Quit" = Just Quit
    | otherwise = Nothing

-- Extracts the first bracketed string and returns the rest
extractBracket :: String -> (String, String)
extractBracket s =
    let start = dropWhile (/= '[') s
    in case start of
        ('[':rest) -> let (content, rest') = span (/= ']') rest
                       in (trim content, drop 1 rest') -- drop ']'
        _ -> ("","")

-- Date parsing
parseDay :: String -> Maybe Day
parseDay = parseTimeM True defaultTimeLocale "%Y-%m-%d"

showDay :: Day -> String
showDay = formatTime defaultTimeLocale "%Y-%m-%d"

-- Utilities
trim :: String -> String
trim = f . f
   where f = reverse . dropWhile (==' ')
