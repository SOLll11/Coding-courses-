maxhr :: Float -> Float 
maxhr age = if age > 40 
    then 207-0.7*age 
    else 220-age