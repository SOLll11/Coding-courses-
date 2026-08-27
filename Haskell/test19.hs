import Text.Read (readMaybe)
import qualified Data.Map as Map
type Name = String
type PhoneBook = Map.Map Name [Phone]
type Name = String
data PhoneBook = Empty | Node String [Phone] PhoneBook PhoneBook deriving (Show,Eq)

data PhoneType
  = WorkLandline
  | PrivateMobile
  | WorkMobile
  | Other
  deriving (Show, Eq, Read)
  
data CountryCode = CountryCode Integer deriving (Show, Eq)
data PhoneNo = PhoneNo Integer deriving (Show, Eq)
  

toCountryCode :: Integer -> CountryCode
toCountryCode n = if n<0 then error "Negative country code" else CountryCode n

toPhoneNo :: Integer -> PhoneNo
toPhoneNo n = if n<0 then error "Negative phone number" else PhoneNo n


data Phone = Phone { phoneType :: Maybe PhoneType, 
   countryCode :: Maybe CountryCode, 
   phoneNo :: PhoneNo }deriving(Eq)
   
instance Show Phone where
  show (Phone t c n) = countryPart ++ numberPart ++ typePart
    where
      countryPart = case c of
        Just (CountryCode cc) -> "+" ++ show cc ++ " "
        Nothing               -> ""
      numberPart = case n of
        PhoneNo pn -> show pn
      typePart = case t of
        Just tt -> " (" ++ show tt ++ ")"
        Nothing -> ""

readPhoneType :: String -> Maybe PhoneType
readPhoneType "" = Nothing
readPhoneType s =
  case readMaybe s of
    Just t  -> Just t
    Nothing -> error "Incorrect phone type"



readCountryCode :: String -> Maybe CountryCode
readCountryCode "" = Nothing
readCountryCode s =
  case readMaybe s of
    Just n  -> if n < 0
                 then error "Negative country code"
                 else Just (CountryCode n)
    Nothing -> error "Incorrect country code"


readPhoneNo :: String -> PhoneNo
readPhoneNo s =
  case readMaybe s of
    Just n  -> if n < 0
                 then error "Negative phone number"
                 else PhoneNo n
    Nothing -> error "Incorrect phone number"
	
readPhone :: String -> String -> String -> Phone
readPhone phonetypestr countrycodestr phonenostr =
  Phone
    { phoneType   = readPhoneType phonetypestr
    , countryCode = readCountryCode countrycodestr
    , phoneNo     = readPhoneNo phonenostr
    }

singleton :: a -> Tree a  
singleton x = Node x EmptyTree EmptyTree  

findEntries :: Name -> PhoneBook -> [Phone]
findEntries name phonebook =
   

	   
addEntry :: Name -> String -> String -> String -> PhoneBook -> PhoneBook
addEntry name phonetype ccode phonenum currentbook =
    
	
	
emptyBook :: PhoneBook
emptyBook = Map.empty