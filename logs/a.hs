l=$(grep Response ./example.hs | cut -f 2 -d : | cut -f 2 -d , | cut -f 4 -d ' ')
echo $l
