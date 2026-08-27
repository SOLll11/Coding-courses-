data PhoneType
  = WorkLandline
  | PrivateMobile
  | WorkMobile
  | Other
  deriving (Show, Eq)
  
data CountryCode = CountryCode Integer deriving (Show, Eq)
data PhoneNo = PhoneNo Integer deriving (Show, Eq)
  

toCountryCode :: Integer -> CountryCode
toCountryCode n = if n<0 then error "Negative country code" else CountryCode n

toPhoneNo :: Integer -> PhoneNo
toPhoneNo n = if n<0 then error "Negative phone number" else PhoneNo n


data Phone = Phone { phoneType :: PhoneType, 
   countryCode :: CountryCode, 
   phoneNo :: PhoneNo }deriving(Show, Eq)
   