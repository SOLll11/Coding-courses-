



onlyDigits :: String -> Bool

onlyDigits "" = True

onlyDigits (x:ns) = 
    if x >= '0' && x <= '9' then onlyDigits ns
    else False
    
